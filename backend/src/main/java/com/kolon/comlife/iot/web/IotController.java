package com.kolon.comlife.iot.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.iot.exception.*;
import com.kolon.comlife.iot.model.*;
import com.kolon.comlife.iot.service.IotControlService;
import com.kolon.comlife.iot.service.IotInfoService;
import com.kolon.common.http.HttpRequestFailedException;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import com.kolon.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example Controller
 */
@RestController
@RequestMapping("/iot")
public class IotController {
    private static final Logger logger = LoggerFactory.getLogger(IotController.class);


    @Autowired
    private IotControlService iotControlService;

    @Autowired
    private IotInfoService iotInfoService;

    /**
     * Controller Test Code
     */
    @GetMapping(
            path = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> getIotStatus()
    {
        Map result = new HashMap();
        result.put("msg", "ok!");

        return ResponseEntity.status(HttpStatus.OK).body( result );
    }


    /**
     * 토큰에서 가져온 사용자 정보와 Path로 넘어온 Variable을 비교함
     * 값이 차이가 있는 경우, IotInvalidPathVariableException 예외 반환
     */
    private void validateAuthUserWithPathInfoCmplxId(AuthUserInfo authUserInfo,
                                                     int          cmplxId)
            throws IotInvalidPathVariableException {

        if( cmplxId != authUserInfo.getCmplxId() ) {
            throw new IotInvalidPathVariableException("현장 정보가 사용자 인증정보와 일치하지 않습니다.");
        }
    }


    /**
     * 토큰에서 가져온 사용자 정보와 Path로 넘어온 Variable을 비교함
     * 값이 차이가 있는 경우, IotInvalidPathVariableException 예외 반환
     */
    private void validateAuthUserWithPathInfo(AuthUserInfo authUserInfo,
                                              int          cmplxId,
                                              Integer      homeId )
            throws IotInvalidPathVariableException {

        if( cmplxId != authUserInfo.getCmplxId() ) {
            throw new IotInvalidPathVariableException("현장 정보가 사용자 인증정보와 일치하지 않습니다.");
        }
        if( homeId != authUserInfo.getHomeId() ) {
            throw new IotInvalidPathVariableException("세대 정보가 사용자 인증정보와 일치하지 않습니다.");
        }
    }

    /**
     * 1. '모드'의 전체 목록 가져오기 at Dashboard
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModesList(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo currUser;
        IotModeOrAutomationListInfo modesList;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            modesList = iotInfoService.getModeList(complexId, homeId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( modesList );
    }

    /**
     * 2. 현재 적용된 '모드'의 가져오기 at Dashboard
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/active",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActiveMode(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo currUser;
        IotModeOrAutomationListInfo activeModeList;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            activeModeList = iotInfoService.getActiveMode(complexId, homeId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        activeModeList.setMsg("현재 동작중인 모드 가져오기");
        return ResponseEntity.status(HttpStatus.OK).body( activeModeList );
    }

    /**
     * 3. My IOT의 IOT 버튼 목록 및 정보 가져오기 at Dashboard
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotButtonList(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo currUser;
        IotButtonListInfo buttonList;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            buttonList = iotInfoService.getMyIotButtonList(complexId, homeId, userId, true);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( buttonList );
    }

    /**
     * 4. MyIOT의 IOT 버튼 개별 목록 가져오기 at Dashboard Button
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/buttons/{buttonId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotButton(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("buttonId")  int buttonId )
    {
        AuthUserInfo      currUser;
        IotButtonListInfo buttonList;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            buttonList = iotInfoService.getMyIotButtonListById(complexId, homeId, userId, buttonId, true);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( buttonList );
    }

    /**
     * 5. MyIOT 내, 개별 IOT 버튼의 대표 기능 실행 at Dashboard Button
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/buttons/{buttonId}/action",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity executeMyIotButtonPrimeFunction(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("buttonId")  int buttonId )
    {
        AuthUserInfo      currUser;
        IotButtonListInfo buttonInfo;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            buttonInfo = iotControlService.executeMyIotButtonPrimeFunction(complexId, homeId, userId, buttonId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler(e);
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( buttonInfo );
    }

    /**
     * 6. 가치정보에서 날씨 화면의 세부 정보 가져오기 at Dashboard Button
     */
    @GetMapping(
            path = "/complexes/{complexId}/valueInfo/weather",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getValueInfoOfWeather(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId )
    {
        AuthUserInfo currUser;
        Map weatherInfo = new HashMap();

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfoCmplxId( currUser, complexId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body( weatherInfo );
    }

//   todo: 에너지사용량은 가치정보로 취급 안함
//    /**
//     * 7. 에너지 사용량 정보 가져오기 at Dashboard Button
//     */
//    @GetMapping(
//            path = "/complexes/{complexId}/valueInfo/energy",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map>  getEnergyInfo(
//            @PathVariable("complexId") int complexId )
//    {
//        Map weatherInfo = new HashMap();
//
//        return ResponseEntity.status(HttpStatus.OK).body( weatherInfo );
//    }

    /**
     * 8. 기기의 대표 기능 수행 - AWS Lambda 호출
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/devices/{deviceId}/action",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  executeDevicePrimeFunction(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("deviceId")  int deviceId )
    {
        AuthUserInfo        currUser;
        IotDeviceControlMsg ctrlInfo = new IotDeviceControlMsg();
        IotDeviceListInfo   deviceInfo;
        String              protcKey;
        Object              value;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        protcKey = request.getParameter("protcKey");
        if(protcKey == null || protcKey.equals("")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("잘못된 파라미터입니다. protcKey 값을 확인하세요."));
        }

        value = request.getParameter("value");
        if(value == null || value.equals("")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("잘못된 파라미터입니다. value 값을 확인하세요."));
        }
        logger.debug("> > > protcKey: " + protcKey);
        logger.debug("> > > value   : " + value);

        ctrlInfo.setProtcKey(protcKey);
        ctrlInfo.setValue(value);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            deviceInfo = iotControlService.executeDeviceFunction(complexId, homeId, deviceId, ctrlInfo);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceInfo );
    }

    /**
     * 10. 공간 목록 가져오기 at Quick IOT 제어 > 공간별 보기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/rooms",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRoomList(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo    currUser;
        IotRoomListInfo roomList;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            roomList = iotInfoService.getRoomList(complexId, homeId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( roomList );
    }

    /**
     * 11. 공간별 '기기 목록' 가져오기 at Quick IOT 제어 > 공간별 보기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/rooms/{roomId}/devices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRoomsWithDevicesList(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("roomId")    int roomId)
    {
        AuthUserInfo      currUser;
        IotDeviceListInfo deviceListInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            deviceListInfo = iotInfoService.getRoomsWithDevicesList(complexId, homeId, roomId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 12. 기기 상세 정보 가져오기 at Quick IOT 제어 > 공간별 보기 or 기기별 보기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/devices/{deviceId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDeviceInfo(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("deviceId")  int deviceId )
    {
        AuthUserInfo      currUser;
        IotDeviceListInfo deviceListInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            deviceListInfo = iotInfoService.getDeviceInfo(complexId, homeId, deviceId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 13. 기기 카테고리 가져오기 at Quick IOT 제어 > 기기별 보기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/deviceCategory",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDeviceGroupList(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo      currUser;
        IotDeviceGroupListInfo deviceGroupListInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            deviceGroupListInfo = iotInfoService.getDeviceGroupList(complexId, homeId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceGroupListInfo );
    }

    /**
     * 14. 카테고리 별 기기 목록 가져오기 at Quick IOT 제어 > 기기별 보기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/deviceCategory/{categoryCode}/devices",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDeviceListByDeviceGroup(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("categoryCode")    String categoryCode)
    {
        AuthUserInfo      currUser;
        IotDeviceListInfo deviceListInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            deviceListInfo = iotInfoService.getDeviceListByDeviceGroup(complexId, homeId, categoryCode);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 15-a. 기기의 '사용내역' 정보 가져오기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/devices/usageHistory",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDevicesUsageHistory(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo      currUser;
        int               pageNo;
        int               pageRow;
        IotDeviceListInfo deviceListInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        pageNo = StringUtil.parseInt(request.getParameter("pageNo"), 1);
        pageRow = StringUtil.parseInt(request.getParameter("pageRow"), 10);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            deviceListInfo = iotInfoService.getDevicesUsageHistory(complexId, homeId, pageNo, pageRow);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceListInfo );
    }

    /**
     * 16. 개별 '모드'의 상세 정보 가져오기 at IOT 모드 실행
     *
     * modeId == scnaId
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/{mode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModeInfo(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("mode")    String mode )
    {
        AuthUserInfo                currUser;
        IotModeAutomationInfo       modeInfo;
        IotModeOrAutomationListInfo modeList;
        int                         automationId = -1;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            modeList = iotInfoService.getModeList(complexId, homeId);
            for( Map e : modeList.getData()) {
                if( e.get("mode").equals(mode) ) {
                    if( e.get("scnaId") instanceof Integer ) {
                        automationId = ((Integer)e.get("scnaId")).intValue();
                    } else if( e.get("scnaId") instanceof String ) {
                        automationId = Integer.valueOf((String)e.get("scnaId")).intValue();
                    }
                    break;
                }
            }
            if( automationId == -1 ) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new SimpleErrorInfo("모드 값이 잘못되었습니다. 모드를 확인하세요."));
            }

            modeInfo = iotInfoService.getModeOrAutomationDetail(complexId, homeId, automationId, true);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( modeInfo );
    }

    /**
     * 17. 개별 '모드'로 전환 at IOT 모드 실행
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/{mode}/switchTo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity switchToMode(
            HttpServletRequest                request,
            @PathVariable("complexId") int    complexId,
            @PathVariable("homeId")    int    homeId,
            @PathVariable("mode")      String mode )
    {
        AuthUserInfo                currUser;
        IotModeOrAutomationListInfo changedMode;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            changedMode = iotControlService.switchToMode( complexId, homeId, mode );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        changedMode.setMsg("개별 '모드' 전환");

        return ResponseEntity.status(HttpStatus.OK).body( changedMode );
    }

    /**
     * 17-2. '모드' 상태 종료 at IOT 모드 실행
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/turnOff",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity turnOffMode(
            HttpServletRequest                request,
            @PathVariable("complexId") int    complexId,
            @PathVariable("homeId")    int    homeId)
    {
        AuthUserInfo                currUser;
        IotModeOrAutomationListInfo offMode;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            offMode = iotControlService.turnOffMode( complexId, homeId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            e.printStackTrace();
            try {
                e.printStackTrace();
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                unhandledEx.printStackTrace();
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        offMode.setMsg("'모드' 종료");

        return ResponseEntity.status(HttpStatus.OK).body( offMode );
    }

    /**
     * 21. '모드' 편집에서, 모드 목록 및 순서를 가져오기 at Mode 내용 편집
     *  ===> 1. 번과 동일
     */


    /**
     * 22. '모드' 편집에서, 모드 목록의 순서를 변경하기 at Mode 내용 편집
     */
    @PostMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/order",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateModesOrder(
            HttpServletRequest                request,
            @PathVariable("complexId") int    complexId,
            @PathVariable("homeId")    int    homeId,
            @RequestBody               List<Map<String, Object>> body)
    {
        AuthUserInfo                currUser;
        IotModeOrAutomationListInfo modeListInfo = new IotModeOrAutomationListInfo();

        logger.debug(">>> " + body.toString());
        logger.debug(">>> " + body.size());
        logger.debug(">>> " + body.get(0).get("seqNo"));
        logger.debug(">>> " + body.get(0).get("sortOrder"));

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        // validation
        if( body.size() < 1 ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("입력값이 잘못되었습니다. 입력값을 다시 확인하세요."));
        }

        for(Map<String, Object> e: body) {
            if( e.get("seqNo") == null ) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("입력값이 잘못되었습니다. 모드를 다시 확인하세요."));
            }
            if( e.get("sortOrder") == null ) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("입력값이 잘못되었습니다. 정렬순서를 다시 확인하세요."));
            }
        }

        // Populate data
        modeListInfo.setData(body);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            modeListInfo = iotInfoService.updateModesOrder(complexId, homeId, modeListInfo);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( modeListInfo );
    }

    private IotModeAutomationInfo convertModeAutomationRequestBody(Map<String, Object> reqBody) throws IotInfoGeneralException {
        IotModeAutomationInfo automationInfo = new IotModeAutomationInfo();

        if( reqBody.get("scna") != null && reqBody.get("scna") instanceof List ) {
            automationInfo.setScna((List)reqBody.get("scna"));
        } else {
            throw new IotInfoGeneralException("자동화 기본정보가 잘못되었습니다. 입력값을 다시 확인하세요.");
        }

        if( reqBody.get("scnaIfThings") != null && reqBody.get("scnaIfThings") instanceof List ) {
            automationInfo.setScnaIfThings((List)reqBody.get("scnaIfThings"));
        }

        if( reqBody.get("scnaThings") != null && reqBody.get("scnaThings") instanceof List ) {
            automationInfo.setScnaThings((List)reqBody.get("scnaThings"));
        }

        if( reqBody.get("scnaIfAply") != null && reqBody.get("scnaIfAply") instanceof List ) {
            automationInfo.setScnaIfAply((List)reqBody.get("scnaIfAply"));
        }

        if( reqBody.get("scnaIfSpc") != null && reqBody.get("scnaIfSpc") instanceof List ) {
            automationInfo.setScnaIfSpc((List)reqBody.get("scnaIfSpc"));
        }

        return automationInfo;
    }

    /**
     * 23. '모드' 상세에서 수정한 설정 정보 업데이트하기 at IOT 모드 실행
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/modes/{mode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateModeInfo(
            HttpServletRequest                             request,
            @PathVariable("complexId") int                 complexId,
            @PathVariable("homeId")    int                 homeId,
            @PathVariable("mode")      String              mode,
            @RequestBody               Map<String, Object> body)
    {
        AuthUserInfo                currUser;
        IotModeAutomationInfo       modeInfo;
        IotModeOrAutomationListInfo modeList;
        IotModeAutomationIdInfo     updateModeInfo;
        int                         modeAutomationId = -1;
        String                      userId;


        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        // Mode에서 scnaId 가져오기
        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );

            modeInfo = this.convertModeAutomationRequestBody( body );

            modeList = iotInfoService.getModeList(complexId, homeId);
            for( Map e : modeList.getData()) {
                if( e.get("mode").equals(mode) ) {
                    if( e.get("scnaId") instanceof Integer ) {
                        modeAutomationId = ((Integer)e.get("scnaId")).intValue();
                    } else if( e.get("scnaId") instanceof String ) {
                        modeAutomationId = Integer.valueOf((String)e.get("scnaId")).intValue();
                    }
                    logger.debug(">> MODE: " + mode + " >>> ScnaId: " + modeAutomationId);
                    break;
                }
            }
            if( modeAutomationId == -1 ) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new SimpleErrorInfo("모드 값이 잘못되었습니다. 모드를 확인하세요."));
            }

            updateModeInfo = iotInfoService.updateAutomation(complexId, homeId, modeAutomationId, userId, modeInfo, true);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body( updateModeInfo );
    }

    /**
     * 24. MyIOT에 추가할 수 있는 모든 기기/가치정보/오토메이션 목록 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/buttons/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotButtonListAvailable(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo      currUser;
        IotButtonListInfo buttonsList;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            buttonsList = iotInfoService.getMyIotButtonListAvailable(complexId, homeId, userId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body( buttonsList );
    }

    /**
     * 24-2. MyIOT내, 추가 가능한 자동화/시나리오 목록 가져오기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/automation/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotAutomationListAvailable(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo      currUser;
        IotButtonListInfo buttonsList;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            buttonsList = iotInfoService.getMyIotAutomationListAvailable(complexId, homeId, userId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body( buttonsList );
    }

    /**
     * 24-3-1. MyIOT내, 추가 가능한 기기(공간+카테고리 정보 포함) 목록 가져오기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/rooms/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotRoomsListAvailable(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo      currUser;
        IotRoomListInfo   roomsList;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            roomsList = iotInfoService.getMyIotRoomsListAvailable(complexId, homeId, userId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( roomsList );
    }

    /**
     * 24-3-2. MyIOT내, 추가 가능한 기기(공간+카테고리 정보 포함) 목록 가져오기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/rooms/{roomId}/devices/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotDevicesListByRoomAvailable(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("roomId")    int roomId)
    {
        AuthUserInfo      currUser;
        IotButtonListInfo buttonsList;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            buttonsList = iotInfoService.getMyIotDevicesListByRoomAvailable(complexId, homeId, userId, roomId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( buttonsList );
    }

    /**
     * 24-3-3. MyIOT내, 추가 가능한 기기(공간+카테고리 정보 포함) 목록 가져오기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/deviceCategory/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotDeviceCategoryListAvailable(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo           currUser;
        IotDeviceGroupListInfo deviceGroupList;
        String                 userId;


        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            deviceGroupList = iotInfoService.getMyIotDeviceCategoryListAvailable(complexId, homeId, userId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceGroupList );
    }

    /**
     * 24-3-4. MyIOT내, 추가 가능한 기기(공간+카테고리 정보 포함) 목록 가져오기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/deviceCategory/{categoryCode}/devices/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotDevicesListByCategoryAvailable(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("categoryCode") String categoryCode)
    {
        AuthUserInfo      currUser;
        IotButtonListInfo buttonsList;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            buttonsList = iotInfoService.getMyIotDevicesListByCategoryAvailable(complexId, homeId, userId, categoryCode );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body( buttonsList );
    }


    /**
     * 24-4. MyIOT내, 추가 가능한 가치정보 목록 가져오기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/valueInfo/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyIotValueInfoListAvailable(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId )
    {
        AuthUserInfo      currUser;
        IotButtonListInfo buttonsList;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            buttonsList = iotInfoService.getMyIotValueInfoListAvailable(complexId, homeId, userId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body( buttonsList );
    }


    /**
     * 26. MyIOT 편집 화면에서 '기기/시나리오/정보'의 순서를 신규등록 at MyIOT 추가
     */
    @PostMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addMyIotButton(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @RequestBody               Map<String, List> body)

    {
        AuthUserInfo              currUser;
        IotButtonListInfo         addedButtonList = null;
        String                    userId;
        String                    myIotId;
        List<Map<String, Object>> myIotIdList;


        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();;

        myIotIdList = (List<Map<String, Object>>)body.get("data");
        if( myIotIdList == null ) {
            ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo("추가 할 수 없는 IOT 정보/기기입니다.") );
        }

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            addedButtonList = iotInfoService.addMyIotButtonByMyIotID(complexId, homeId, userId, myIotIdList);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(addedButtonList);
    }

    /**
     * 26-2. MyIOT 편집 화면에서 '기기/시나리오/정보'의 순서를 변경하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateMyIotButtonOrder(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @RequestBody               Map<String, List> body)

    {
        AuthUserInfo              currUser;
        IotButtonListInfo         currentButtonList = null;
        String                    userId;
        String                    myIotId;
        List<Map<String, Object>> myIotIdOrderList;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        myIotIdOrderList = (List<Map<String, Object>>)body.get("data");
        if( myIotIdOrderList == null ) {
            ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo("입력된 정보가 없습니다.") );
        }

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            currentButtonList = iotInfoService.updateMyIotButtonOrder(complexId, homeId, userId, myIotIdOrderList);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(currentButtonList);
    }



    /**
     * 27. MyIOT 편집 화면에서 '기기/시나리오/정보'를 목록에서 제거하기 at MyIOT 추가
     */
    @DeleteMapping(
            path = "/complexes/{complexId}/homes/{homeId}/myiot/buttons/{buttonId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteMyIotButton(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("buttonId")  int buttonId)

    {
        AuthUserInfo      currUser;
        IotButtonListInfo buttonList;
        String            userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            buttonList = iotInfoService.deleteMyIotButtonListById(complexId, homeId, userId, buttonId, true);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( buttonList );
    }


    /**
     * 28. MyIOT에서 '시나리오/오토메이션'의 상세정보 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAutomation(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        AuthUserInfo          currUser;
        IotModeAutomationInfo automationInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            automationInfo = iotInfoService.getModeOrAutomationDetail(complexId, homeId, automationId, false);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( automationInfo );
    }

    /**
     * 29. MyIOT에서 '시나리오/오토메이션' 생성하기 at MyIOT 추가
     */
    @PostMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createAutomation(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @RequestBody               Map<String, Object> body)
    {
        AuthUserInfo            currUser;
        IotModeAutomationInfo   automationInfo = new IotModeAutomationInfo();
        IotModeAutomationIdInfo createdAutomationInfo;
        String userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        body.get("scnaIfAply");
        body.get("scnaIfSpc");
        body.get("scnaIfThings");
        body.get("scnaThings");

        if( body.get("scna") != null && body.get("scna") instanceof List ) {
            automationInfo.setScna((List)body.get("scna"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("자동화 기본정보가 잘못되었습니다. 입력값을 다시 확인하세요."));
        }

        if( body.get("scnaIfThings") != null && body.get("scnaIfThings") instanceof List ) {
            automationInfo.setScnaIfThings((List)body.get("scnaIfThings"));
        }

        if( body.get("scnaThings") != null && body.get("scnaThings") instanceof List ) {
            automationInfo.setScnaThings((List)body.get("scnaThings"));
        }

        if( body.get("scnaIfAply") != null && body.get("scnaIfAply") instanceof List ) {
            automationInfo.setScnaIfAply((List)body.get("scnaIfAply"));
        }

        if( body.get("scnaIfSpc") != null && body.get("scnaIfSpc") instanceof List ) {
            automationInfo.setScnaIfSpc((List)body.get("scnaIfSpc"));
        }

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            createdAutomationInfo = iotInfoService.createAutomation(complexId, homeId, userId, automationInfo);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch ( IotInfoGeneralException e ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch ( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( createdAutomationInfo );
    }

    /**
     * 30. MyIOT에서 '시나리오/오토메이션' 업데이트하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateAutomation(
            HttpServletRequest                request,
            @PathVariable("complexId")    int complexId,
            @PathVariable("homeId")       int homeId,
            @PathVariable("automationId") int automationId,
            @RequestBody                  Map<String, Object> body)
    {
        AuthUserInfo            currUser;
        IotModeAutomationInfo   automationInfo;
        IotModeAutomationIdInfo updateAutomationInfo;
        String                  userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );

            automationInfo = this.convertModeAutomationRequestBody( body );
            updateAutomationInfo = iotInfoService.updateAutomation(complexId, homeId, automationId, userId, automationInfo, false);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch ( IotInfoGeneralException e ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch ( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( updateAutomationInfo );
    }

    /**
     * 31. 특정 시나리오의 '조건(IF)' 목록 및 속성 가져오기 MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/conditions",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModeOrAutomationConditions(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        AuthUserInfo          currUser;
        IotModeAutomationInfo conditionInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            conditionInfo = iotInfoService.getModeOrAutomationConditions(complexId, homeId, automationId, false);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( conditionInfo );
    }

    /**
     * 32. 특정 시나리오의 '조건(IF)' 목록 및 속성 변경하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/conditions",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateModeOrAutomationConditions(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId,
            @PathVariable("sensorId") int sensorId)
    {
        AuthUserInfo      currUser;
        IotSensorListInfo sensorsList = new IotSensorListInfo();

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        try {
            validateAuthUserWithPathInfo(currUser, complexId, homeId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body( sensorsList );
    }

    /**
     * 33. 특정 시나리오의 '작동기기(ACTOR)' 목록 및 속성 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/actors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModeOrAutomationActors(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        AuthUserInfo          currUser;
        IotModeAutomationInfo actorsInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            actorsInfo = iotInfoService.getModeOrAutomationActors(complexId, homeId, automationId, false);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( actorsInfo );
    }

    /**
     * 33-2. 특정 시나리오의 '작동기기' 상세조회 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/actors/{deviceId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModeOrAutomationActorDetail(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId,
            @PathVariable("deviceId") int deviceId)
    {
        AuthUserInfo      currUser;
        IotDeviceListInfo actorDetailInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            actorDetailInfo = iotInfoService.getModeOrAutomationActorDetail(complexId, homeId, automationId, deviceId, false);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( actorDetailInfo );
    }


    /**
     * 34. 특정 시나리오의 '기기' 목록 및 속성 변경하기 at MyIOT 추가
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/actors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateModeOrAutomationActors(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId,
            @PathVariable("automationId") int deviceId )
    {
        AuthUserInfo  currUser;
        IotDeviceInfo deviceInfo = new IotDeviceInfo();

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        try {
            validateAuthUserWithPathInfo(currUser, complexId, homeId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceInfo );
    }

    /**
     * 35. 에너지 사용량 정보 가져오기 at MyIOT - 에너지사용량 상세 화면
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/energy/{energyType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEnergyUsage(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("energyType") String  energyType)
    {
        AuthUserInfo currUser;
        EnergyInfo   energyInfo = new EnergyInfo();

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        try {
            validateAuthUserWithPathInfo(currUser, complexId, homeId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body( energyInfo );
    }

    /**
     * 36. 에너지사용량 초과 알람 설정하기 at MyIOT - 에너지사용량 상세 화면
     */
    @PostMapping(
            path = "/complexes/{complexId}/homes/{homeId}/energy/{energyType}/exceedAlarm",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createEnergyUsageExceedAlarm(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("energyType") String  energyType)
    {
        AuthUserInfo currUser;
        EnergyInfo   energyInfo = new EnergyInfo();

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body( energyInfo );
    }

    /**
     * 38. 에너지사용량 알람 변경하기 at MyIOT - 에너지사용량 상세 화면
     */
    @PutMapping(
            path = "/complexes/{complexId}/homes/{homeId}/energy/{energyType}/exceedAlarm",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setEnergyUsageExceedAlarm(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("energyType") String  energyType)
    {
        AuthUserInfo currUser;
        EnergyInfo   energyInfo = new EnergyInfo();

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body( energyInfo );
    }

    /**
     * 37. 에너지사용량 초과 알람 삭제하기 at MyIOT - 에너지사용량 상세 화면
     */
    @DeleteMapping(
            path = "/complexes/{complexId}/homes/{homeId}/energy/{energyType}/exceedAlarm",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEnergyUsageExceedAlarm(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("energyType") String  energyType)
    {
        AuthUserInfo  currUser;
        EnergyInfo    energyInfo = new EnergyInfo();

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body( energyInfo );
    }

    /**
     * 39. 특정 시나리오에서 추가조건(IF) 목록(리스트)를 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/conditions/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModeOrAutomationConditionsAvailable(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        AuthUserInfo          currUser;
        IotModeAutomationInfo actorsInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            actorsInfo = iotInfoService.getModeOrAutomationConditionsAvailable(complexId, homeId, automationId, false);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( actorsInfo );
    }


    /**
     * 40. 특정 시나리오에서 추가작동기기(ACTOR) 목록(리스트)를 가져오기 at MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}/actors/available",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModeOrAutomationActorsAvailable(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("automationId") int automationId )
    {
        AuthUserInfo          currUser;
        IotModeAutomationInfo actorsInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            currUser = AuthUserInfoUtil.getAuthUserInfo(request);
            actorsInfo = iotInfoService.getModeOrAutomationActorsAvailable(complexId, homeId, automationId, false);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( actorsInfo );
    }


    /**
     * 42. 특정 시나리오에서 추가작동기기(ACTOR) 목록(리스트)를 가져오기 at MyIOT 추가
     */
    @PostMapping(
            path = "/complexes/{complexId}/homes/{homeId}/devices/{deviceId}/desc",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateDeviceDesc(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId,
            @PathVariable("deviceId")  int deviceId )
    {
        AuthUserInfo      currUser;
        IotDeviceListInfo deviceInfo;
        String description = request.getParameter("value");
        logger.debug(">>> Param.value: " + description);

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            currUser = AuthUserInfoUtil.getAuthUserInfo(request);
            deviceInfo = iotInfoService.updateDeviceDesc(complexId, homeId, deviceId, description);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( deviceInfo );
    }

    /**
     * 43. '시나리오/오토메이션' 삭제하기
     */
    @DeleteMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/{automationId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAutomation(
            HttpServletRequest                request,
            @PathVariable("complexId")    int complexId,
            @PathVariable("homeId")       int homeId,
            @PathVariable("automationId") int automationId)
    {
        AuthUserInfo            currUser;
        IotModeAutomationIdInfo updateAutomationInfo;
        String                  userId;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);
        userId = currUser.getUserId();

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            updateAutomationInfo = iotInfoService.deleteAutomation(complexId, homeId, automationId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch ( IotInfoGeneralException e ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch ( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( updateAutomationInfo );
    }

    /**
     * 44. 전체 시나리오 리스트 조회
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAutomationAll(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId)
    {
        AuthUserInfo                currUser;
        IotModeOrAutomationListInfo automationList;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            automationList = iotInfoService.getAutomationAll(complexId, homeId);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( automationList );
    }

    /**
     * 45. 시나리오에서 생성시, 추가조건(IF) 목록(리스트)를 가져오기
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/conditions",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModeOrAutomationAllConditions(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId)
    {
        AuthUserInfo          currUser;
        IotModeAutomationInfo conditionInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            conditionInfo = iotInfoService.getModeOrAutomationAllConditions(complexId, homeId, false);
        } catch( IotInvalidPathVariableException e ) {
            logger.error("토큰 인증정보와 path 정보에 문제가 있습니다. 사용자정보(" + currUser.getCmplxId() + "/" + currUser.getHomeId() + ")" );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( conditionInfo );
    }


    /**
     * 46. 특정 시나리오의 '조건(IF)' 목록 및 속성 가져오기 MyIOT 추가
     */
    @GetMapping(
            path = "/complexes/{complexId}/homes/{homeId}/automation/actors",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getModeOrAutomationAllActors(
            HttpServletRequest             request,
            @PathVariable("complexId") int complexId,
            @PathVariable("homeId")    int homeId)
    {
        AuthUserInfo          currUser;
        IotModeAutomationInfo conditionInfo;

        currUser = AuthUserInfoUtil.getAuthUserInfo(request);

        try {
            validateAuthUserWithPathInfo( currUser, complexId, homeId );
            conditionInfo = iotInfoService.getModeOrAutomationAllActors(complexId, homeId, false);
        } catch( Exception e ) {
            try {
                return this.commonExceptionHandler( e );
            } catch( Exception unhandledEx ) {
                logger.error("Unhandled Exception: " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("예상하지 못한 예외가 발생하였습니다."));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body( conditionInfo );
    }


    private ResponseEntity commonExceptionHandler(Exception e) throws Exception {
        if( e instanceof  URISyntaxException ) {
            logger.error( "Invalid URI: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
        } else if( e instanceof IOException ) {
            logger.error( "Failed to request: " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        } else if( e instanceof HttpRequestFailedException ) {
            HttpRequestFailedException httpException = (HttpRequestFailedException)e;
            logger.error( "Failed response[StatusCode:" + httpException.getStatusCode() + "]:" + e.getMessage() );
            if (httpException.getStatusCode() >= 500 ) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorInfo("경로 또는 입력값이 잘못 되었습니다."));
            }
        } else if( e instanceof IotInfoNoDataException) {
            logger.error( "No data : " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body(new SimpleErrorInfo(e.getMessage()));
        } else if( e instanceof IotInfoUpdateFailedException) {
            logger.error( "Update failed : " + e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleErrorInfo(e.getMessage()));
        } else if( e instanceof  IotControlOperationFailedException) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo( e.getMessage() ));
        } else if( e instanceof  IotControlOperationFailedException ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo(e.getMessage()));
        } else {
            // 공통적으로 처리되지 않는 exception은 별도 처리
            throw e;
        }
    }
}