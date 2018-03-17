package com.kolon.comlife.iot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.comlife.iot.exception.IotControlOperationFailedException;
import com.kolon.comlife.iot.exception.IotControlTimeoutException;
import com.kolon.comlife.iot.exception.IotInfoNoDataException;
import com.kolon.comlife.iot.model.*;
import com.kolon.comlife.iot.service.IotControlService;
import com.kolon.comlife.iot.service.IotInfoService;
import com.kolon.common.http.HttpPutRequester;
import com.kolon.common.prop.ServicePropertiesMap;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

@Service("iotControlService")
public class IotControlServiceImpl implements IotControlService {

    private static final Logger logger = LoggerFactory.getLogger(IotControlServiceImpl.class);

    private static final int IOK_CONTROL_TIMEOUT_SEC = 10; // 10초

    // IOK에 기기 작동하고 폴링하는 시간 = IOK_CONTROL_RETRY_INTERVAL_MSEC * IOK_CONTROL_RETRY_COUNT
    private static final int IOK_CONTROL_RETRY_COUNT = 4;  // 반드시 1 보다 커야함
    private static final int IOK_CONTROL_RETRY_INTERVAL_MSEC =  300; // ms

    private static final String IOK_CONTROL_HOST_PROP_GROUP = "IOK";
    private static final String IOK_CONTROL_HOST_PROP_KEY = "IOT_CONTROL_HOST";

    private static final String IOK_CONTROL_MODE_SWITCH_FMT_PATH = "/iok/mode/%s/cmplx/%d/home/%d";
    private static final String IOK_CONTROL_SCENARIO_EXECUTE_FMT_PATH = "/iok/scenario/%d/cmplx/%d/home/%d";
    private static final String IOK_CONTROL_DEVICE_EXECUTE_FMT_PATH = "/iok/device/%s"; // CLNT_ID


    @Resource(name = "servicePropertiesMap")
    private ServicePropertiesMap serviceProperties;

    @Autowired
    private IotInfoService iotInfoService;

    @Autowired
    private CloseableHttpClient httpClient;

    public IotControlServiceImpl() {
    }


    // 5. 개별 IOT 버튼의 대표 기능 실행
    public IotButtonListInfo executeMyIotButtonPrimeFunction
            (int complexId, int homeId, String userId, int buttonId) throws Exception {
        HttpPutRequester    requester;
        Map<String, Map>    result;
        IotButtonListInfo   buttonInfo;
        Map<String, Object> datum;
        Integer             scnaId;

        buttonInfo = iotInfoService.getMyIotButtonListById(complexId, homeId, userId, buttonId);

        // button의 종류를 확인하여, 1) 기기-주요기능 , 2) 시나리오 인지 구분
        datum = buttonInfo.getData().get(0); // 1개의 값만 갖고 있음
        String btType = (String) datum.get("btType");
        String iconType = (String) datum.get("btRightIconType");

        switch(btType) {
            case "device":
                // todo: 작업중
                if( iconType != null && iconType.equals("button") ) {
                    // do!
                    // 주기능 실행 ... 그리고, 해당 기능이 바뀌었는지 확인
                    // todo: 장비 기능 실행하는 명령이 추가되면 ... 여기에 적용해야겠다.
                    throw new IotControlOperationFailedException("(DEV) 현재 지원하지 않는 기능입니다.");
                } else {
                    throw new IotControlOperationFailedException("지원하지 않는 기능입니다.");
                }
            case "automation":
                // do!
                scnaId = (Integer)datum.get("scnaId");
                if( scnaId == null ) {
                    throw new IotControlOperationFailedException("오토메이션을 실행 할 수 없습니다.");
                }

                try {
                    String execUrl = String.format(
                            IOK_CONTROL_SCENARIO_EXECUTE_FMT_PATH,
                            scnaId.intValue(),
                            complexId,
                            homeId );
                    logger.debug(" URL PATH: switchToMode(): " + execUrl);
                    requester = new HttpPutRequester(
                            httpClient,
                            serviceProperties.getByKey(IOK_CONTROL_HOST_PROP_GROUP, IOK_CONTROL_HOST_PROP_KEY),
                            execUrl);
                    result = requester.executeWithTimeout(IOK_CONTROL_TIMEOUT_SEC);
                    logger.debug(" >>>> RESULT >>> " + result.toString());
                    // todo: exception handling
                } catch( SocketException se ) {
                    // socket is closed
                    if( "Socket closed".equals(se.getMessage())) {
                        throw new IotControlTimeoutException("'오토메이션 실행 시간이 초과하였습니다.");
                    } else {
                        throw se;
                    }
                } catch( Exception e) {
                    throw e;
                }

                break;
            case "information":
                throw new IotControlOperationFailedException("지원하지 않는 기능입니다.");
            default:
                throw new IotControlOperationFailedException("입력값이 잘못 되었습니다.");
        }

        buttonInfo.setMsg("MyIOT의 개별 IOT 버튼의 대표 기능 실행 - 성공");

        return buttonInfo;
    }

    // 8. 기기의 기능 수행
    public IotDeviceListInfo executeDeviceFunction(
            int complexId, int homeId, int deviceId, IotDeviceControlMsg ctrlMsg) throws Exception {

        HttpPutRequester requester;
        Map<String, Map> result;
        String           clientId = null;
        String           mid = null;
        String           nextClientId = null;
        String           nextMid = null;
        String           nextProtcKey = null;
        IotDeviceListInfo deviceInfo;
        IotDeviceControlInfo deviceCtrlInfo = new IotDeviceControlInfo();
        int retryCount = IOK_CONTROL_RETRY_COUNT;
        Map msgAttributes = new HashMap();
        Map msgInformation = new HashMap();
        boolean noDataFlag = true; // TRUE == NO DATA!

        // 1-1. deviceId(moThingsId)로 부터 client_id, mid 가져오기
        deviceInfo = iotInfoService.getDeviceInfo(complexId, homeId, deviceId);
        logger.debug(" deviceInfo: " + deviceInfo);
        logger.debug(" deviceInfo.geData(): " + deviceInfo.getData());
        logger.debug(" deviceInfo.geData().size(): " + deviceInfo.getData().size());

        // 1-2. 세부 정보에서 CLIENT_ID, MID 값 찾기
        for( Map data : deviceInfo.getData() ) {
            clientId = (String)data.get("clntId");
            mid      = (String)data.get("moClntId");

            // CLIENT_ID 및 MO_CLIENT_ID가 있는 경우에만 데이터 처리
            if( (clientId != null && !clientId.equals("")) &&
                (mid != null      && !mid.equals("")) )
            {
                logger.debug(">>>>    Client ID: " + clientId);
                logger.debug(">>>> MO Client ID: " + mid);
                logger.debug(">>>> attributes:protcKey: " + ctrlMsg.getProtcKey());
                logger.debug(">>>> attributes:val: " + ctrlMsg.getValue());
                noDataFlag = false; // data exist!
                break;
            }
        }

        if( noDataFlag ) {
            throw new IotInfoNoDataException("해당 기기 정보가 없습니다.");
        }

        // 2. 기기 제어 메시지 셋팅
        msgAttributes.put(ctrlMsg.getProtcKey(), ctrlMsg.getValue());
        msgInformation.put("attributes", msgAttributes);
        msgInformation.put("mid", mid);
        deviceCtrlInfo.getInformations().add(msgInformation);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(deviceCtrlInfo);
        logger.debug(">>>>>> JSON STR: " + jsonInString );

        // 3. 기기 명령 수행
        try {
            String execUrl = String.format( IOK_CONTROL_DEVICE_EXECUTE_FMT_PATH, clientId );
            logger.debug(" URL PATH: switchToMode(): " + execUrl);
            requester = new HttpPutRequester(
                    httpClient,
                    serviceProperties.getByKey(IOK_CONTROL_HOST_PROP_GROUP, IOK_CONTROL_HOST_PROP_KEY),
                    execUrl);
            // Set body
            requester.setBody(jsonInString);
            result = requester.executeWithTimeout(IOK_CONTROL_TIMEOUT_SEC);
            logger.debug(" >>>> RESULT >>> " + result.toString());
            // todo: exception handling
        } catch( SocketException se ) {
            // socket is closed
            if( "Socket closed".equals(se.getMessage())) {
                throw new IotControlTimeoutException("'기기 명령 실행'시간이 초과하였습니다.");
            } else {
                throw se;
            }
        } catch( Exception e) {
            throw e;
        }

        String expectedStatus = null;
        while(true) {
            // 3. 기기의 상태 값이 정상적으로 바뀌었는지 확인
            try {
                deviceInfo = iotInfoService.getDeviceInfo(complexId, homeId, deviceId);
                logger.debug(">> deviceInfo: " + deviceInfo);
                logger.debug(">> deviceInfo.geData(): " + deviceInfo.getData());
                logger.debug(">> deviceInfo.geData().size(): " + deviceInfo.getData().size());

                // 1-2. 세부 정보에서 CLIENT_ID, MID 값 찾기
                noDataFlag = true;
                for( Map data : deviceInfo.getData() ) {
                    nextClientId = (String)data.get("clntId");
                    nextMid      = (String)data.get("moClntId");
                    nextProtcKey = (String)data.get("protcKey");
                    expectedStatus = (String)data.get("currSts");

                    // 기존의 CLIENT_ID 및 MO_CLIENT_ID가 가져온 값과 동일한 경우에만 체크
                    if( clientId.equals(nextClientId) && mid.equals(nextMid) ) {
                        if( ctrlMsg.getProtcKey().equals(nextProtcKey) ) {
                            logger.debug(">>>> >>   Client ID: " + clientId);
                            logger.debug(">>>> >>MO Client ID: " + mid);
                            logger.debug(">>>> >>attributes:protcKey: " + ctrlMsg.getProtcKey());
                            logger.debug(">>>> >>attributes:val: " + ctrlMsg.getValue());
                            logger.debug(">>>> >>expected value: " + expectedStatus);
                            noDataFlag = false; // data exist!
                            break;
                        }
                    }
                }

                if( noDataFlag ) {
                    throw new IotInfoNoDataException("해당 기기 정보가 확인되지 않습니다.");
                }

                if( expectedStatus != null &&
                        expectedStatus.equals((String)ctrlMsg.getValue()) ) {
                    // 변경 성공 --> do nothing
                    break;
                } else {
                    // 변경 값이 아직 바뀌지 않은 경우
                    if( retryCount > 0 ) {
                        // Retry
                        retryCount--;
                        Thread.sleep(IOK_CONTROL_RETRY_INTERVAL_MSEC);
                        continue;
                    } else {
                        throw new IotControlOperationFailedException("기기 속성 변경이 실패하였습니다. 잠시 후에 다시 시도하세요.");
                    }
                }
            } catch( IotInfoNoDataException e ) {
                // 모드가 변경되지 않음
                throw e;
            } catch( Exception e ) {
                throw e;
            }
        }

        deviceInfo.setMsg("기기의 기능 수행");

        return deviceInfo;
    }

    // 17. 18. '모드' 변경 수행
    public IotModeListInfo switchToMode(int complexId, int homeId, String modeId) throws Exception {
        HttpPutRequester requester;
        Map<String, Map> result;
        IotModeListInfo  activeModeList;
        String           changedModeId;
        int retryCount = IOK_CONTROL_RETRY_COUNT;

        // 1. '모드 변경' 수행
        try {
            String execUrl = String.format( IOK_CONTROL_MODE_SWITCH_FMT_PATH, modeId, complexId, homeId );
            logger.debug(" URL PATH: switchToMode(): " + execUrl);
            requester = new HttpPutRequester(
                    httpClient,
                    serviceProperties.getByKey(IOK_CONTROL_HOST_PROP_GROUP, IOK_CONTROL_HOST_PROP_KEY),
                    execUrl);
            result = requester.executeWithTimeout(IOK_CONTROL_TIMEOUT_SEC);
            logger.debug(" >>>> RESULT >>> " + result.toString());
            // todo: exception handling
        } catch( SocketException se ) {
            // socket is closed
            if( "Socket closed".equals(se.getMessage())) {
                throw new IotControlTimeoutException("'모드 변경'시간이 초과하였습니다.");
            } else {
                throw se;
            }
        } catch( Exception e) {
            throw e;
        }

        while(true) {
            // 2. 변경한 모드 값이 확인되면 수행
            try {
                activeModeList = iotInfoService.getActiveMode(complexId, homeId);
                changedModeId = (String)activeModeList.getData().get(0).get("mode");

                if( modeId.equals(changedModeId) ) {
                    // 변경 성공 --> do nothing
                    break;
                } else {
                    // 변경 값이 아직 바뀌지 않은 경우
                    if( retryCount > 0 ) {
                        // Retry
                        retryCount--;
                        Thread.sleep(IOK_CONTROL_RETRY_INTERVAL_MSEC);
                        continue;
                    } else {
                        throw new IotControlOperationFailedException("모드 변경이 실패하였습니다. 잠시 후에 다시 시도하세요.");
                    }
                }
            } catch( IotInfoNoDataException e ) {
                // 모드가 변경되지 않음
                throw e;
            } catch( Exception e ) {
                throw e;
            }
        }

        return activeModeList;
    }



    public IotModeListInfo updateModesOrder(int complexId, int homeId) {
        return null;
    }

    
    public IotModeAutomationInfo updateModeInfo(int complexId, int homeId, int modeId) {
        return null;
    }
    
    public IotAutomationInfo createAutomation(int complexId, int homeId) {
        return null;
    }

    
    public IotAutomationInfo updateAutomation(int complexId, int homeId, int automationId) {
        return null;
    }

    public IotSensorListInfo updateSensorOnAutomation(int complexId, int homeId, int automationId, int sensorId) { return null; };
    
    public EnergyInfo createEnergyUsageExceedAlarm(int complexId, int homeId, String energyType) {
        return null;
    }

    
    public EnergyInfo deleteEnergyUsageExceedAlarm(int complexId, int homeId, String energyType) {
        return null;
    }

    
    public EnergyInfo setEnergyUsageExceedAlarm(int complexId, int homeId, String energyType) {
        return null;
    }
}
