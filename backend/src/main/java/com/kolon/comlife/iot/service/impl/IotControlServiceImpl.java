package com.kolon.comlife.iot.service.impl;

import com.kolon.comlife.iot.exception.IotControlOperationFailedException;
import com.kolon.comlife.iot.exception.IotControlTimeoutException;
import com.kolon.comlife.iot.exception.IotInfoNoDataException;
import com.kolon.comlife.iot.model.*;
import com.kolon.comlife.iot.service.IotControlService;
import com.kolon.comlife.iot.service.IotInfoService;
import com.kolon.comlife.iot.web.IotController;
import com.kolon.common.http.HttpGetRequester;
import com.kolon.common.http.HttpPutRequester;
import com.kolon.common.http.HttpRequestFailedException;
import com.kolon.common.prop.ServicePropertiesMap;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service("iotControlService")
public class IotControlServiceImpl implements IotControlService {

    private static final Logger logger = LoggerFactory.getLogger(IotControlServiceImpl.class);

    private static final int IOK_CONTROL_TIMEOUT_SEC = 10; // 10초

    // IOK에 기기 작동하고 폴링하는 시간 = IOK_CONTROL_RETRY_INTERVAL_MSEC * IOK_CONTROL_RETRY_COUNT
    private static final int IOK_CONTROL_RETRY_COUNT = 4;  // 반드시 1 보다 커야함
    private static final int IOK_CONTROL_RETRY_INTERVAL_MSEC =  200; // ms

    private static final String IOK_CONTROL_HOST_PROP_GROUP = "IOK";
    private static final String IOK_CONTROL_HOST_PROP_KEY = "IOT_CONTROL_HOST";

    private static final String IOK_CONTROL_MODE_SWITCH_FMT_PATH = "/iok/mode/%s/cmplx/%d/home/%d";

    @Resource(name = "servicePropertiesMap")
    private ServicePropertiesMap serviceProperties;

    @Autowired
    private IotInfoService iotInfoService;

    @Autowired
    private CloseableHttpClient httpClient;

    public IotControlServiceImpl() {
    }

    public IotModeListInfo getModesList(int complexId, int homeId) {
        return null;
    }

    public IotModeInfo getActiveMode(int complexId, int homeId) {
        return null;
    }

    public IotButtonListInfo getMyIotButtonList(int complexId, int homeId) {
        return null;
    }

    public IotButtonInfo getMyIotButton(int complexId, int homeId) {
        return null;
    }

    public IotButtonInfo executeMyIotButtonPrimeFunction(int complexId, int homeId, int buttonId) {
        return null;
    }

    public Map getValueInfoOfWeather(int complexId) {
        return null;
    }
    
    public Map executeDevicePrimeFunction(int complexId, int homeId, int deviceId) {
        return null;
    }

    public IotRoomListInfo getRoomList(int complexId, int homeId) {
        return null;
    }
    
    public IotDeviceListInfo getRoomsWithDevicesList(int complexId, int homeId) {
        return null;
    }

    
    public IotDeviceInfo getDeviceInfo(int complexId, int homeId, int deviceId) {
        return null;
    }

    
    public IotDeviceGroupListInfo getDeviceGroupList(int complexId, int homeId) {
        return null;
    }

    
    public IotDeviceListInfo getDeviceListByDeviceGroup(int complexId, int homeId) {
        return null;
    }

    
    public IotModeInfo getModeInfo(int complexId, int homeId, int modeId) {
        return null;
    }

    
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
        } catch( SocketException se ) {
            // socket is closed
            if( "Socket closed".equals(se.getMessage())) {
                throw new IotControlTimeoutException("'모드 변경' 요청 시간 초과");
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
    
    public IotModeListInfo getModesOrder(int complexId, int homeId) {
        return null;
    }

    
    public IotModeListInfo updateModesOrder(int complexId, int homeId) {
        return null;
    }

    
    public IotModeInfo updateModeInfo(int complexId, int homeId, int modeId) {
        return null;
    }

    
    public Map getButtons(int complexId, int homeId) {
        return null;
    }

    
    public IotAutomationInfo getAutomation(int complexId, int homeId, int automationId) {
        return null;
    }

    
    public IotAutomationInfo createAutomation(int complexId, int homeId) {
        return null;
    }

    
    public IotAutomationInfo updateAutomation(int complexId, int homeId, int automationId) {
        return null;
    }

    
    public IotSensorListInfo getSensorsListOnAutomation(int complexId, int homeId, int automationId) {
        return null;
    }

    
    public IotSensorListInfo updateSensorOnAutomation(int complexId, int homeId, int automationId, int sensorId) {
        return null;
    }

    
    public IotDeviceListInfo getDevicesListOnAutomation(int complexId, int homeId, int automationId) {
        return null;
    }

    
    public IotDeviceInfo getDevicesListOnAutomation(int complexId, int homeId, int automationId, int deviceId) {
        return null;
    }

    
    public EnergyInfo getEnergyUsage(int complexId, int homeId, String energyType) {
        return null;
    }

    
    public EnergyInfo createEnergyUsageExceedAlarm(int complexId, int homeId, String energyType) {
        return null;
    }

    
    public EnergyInfo deleteEnergyUsageExceedAlarm(int complexId, int homeId, String energyType) {
        return null;
    }

    
    public EnergyInfo setEnergyUsageExceedAlarm(int complexId, int homeId, String energyType) {
        return null;
    }


//    private IotBaseInfo commonExceptionHandler(Exception e) throws Exception {
//        if( e instanceof URISyntaxException) {
//            logger.error( "Invalid URI: " + e.getMessage() );
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
//        } else if( e instanceof IOException) {
//            logger.error( "Failed to request: " + e.getMessage() );
//            return ResponseEntity
//                    .status( HttpStatus.SERVICE_UNAVAILABLE )
//                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
//        } else if( e instanceof HttpRequestFailedException) {
//            HttpRequestFailedException httpException = (HttpRequestFailedException)e;
//            logger.error( "Failed response[StatusCode:" + httpException.getStatusCode() + "]:" + e.getMessage() );
//            if (httpException.getStatusCode() >= 500 ) {
//                return ResponseEntity
//                        .status(HttpStatus.SERVICE_UNAVAILABLE)
//                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
//            }
//            else {
//                return ResponseEntity
//                        .status(HttpStatus.BAD_REQUEST)
//                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
//            }
//        } else {
//            // 공통적으로 처리되지 않는 Exception은 별도 처리
//            throw e;
//        }
//    }

}
