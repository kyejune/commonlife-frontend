package com.kolon.comlife.users.web;

import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.users.exception.NotAcceptedUserIdException;
import com.kolon.comlife.users.model.AgreementInfo;
import com.kolon.comlife.users.service.UserRegistrationService;
import com.kolon.comlife.users.service.UserService;
import com.kolon.comlife.users.util.IokUtil;
import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import com.kolonbenit.iot.mobile.controller.MobileUserCertNoController;
import com.kolonbenit.iot.mobile.controller.MobileUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/registration/*")
public class UserRegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    @Resource(name = "registrationService")
    private UserRegistrationService regService;

    @Resource(name = "complexService")
    private ComplexService complexService;   // todo: replaced with MobileUserController

    @Autowired
    private MobileUserController mobileUserController;

    @Autowired
    private MobileUserCertNoController mobileUserCertNoController;


    /**
     * 0. "개인정보 취급 정책" 전송
     */
    @GetMapping(
            value = "/agreement",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAgreement(HttpServletRequest request) {
        List<AgreementInfo> agreements;

        agreements = regService.getLatestAgreement();

        if (agreements.isEmpty()) {
            logger.debug("return value is NULL");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SimpleMsgInfo("표시할 정보가 없습니다."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(agreements.get(0));
    }


    /**
     * 1. 등록지점 선택 중, 모든 현장 목록 가져오기
     */
    @GetMapping(
            value = "/complexes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataListInfo> getComplexList() {
        DataListInfo result = new DataListInfo();
        List<ComplexSimpleInfo> complexList = null;

        complexList = complexService.getComplexSimpleList();
        result.setData(complexList);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 2-1. 동 목록 가져오기
     */
    @GetMapping(
            value = "/complexes/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getComplexDongList(HttpServletRequest request, @PathVariable("id") int id) {
        RequestParameter parameter;
        Map<String, Object> result;
        List<String> dongList;    // 동 목록
        DataListInfo retBody;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            parameter.put("cmplxId", id);

            result = mobileUserController.listDongInfo(parameter, null);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        dongList = (List) ((Map) result.get("DATA")).get("DONG");
        if (dongList.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("해당하는 동이 없습니다"));
        }

        retBody = new DataListInfo(dongList);

        return ResponseEntity.status(HttpStatus.OK).body(retBody);
    }

    /**
     * 2-2. 호 목록 가져오기
     */
    @GetMapping(
            value = "/complexes/{id}/{dong}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getComplexDongHoList(HttpServletRequest request, @PathVariable("id") int id, @PathVariable("dong") String dong) {
        RequestParameter parameter;
        Map<String, Object> result;
        List<String> hoList;    // 호 목록
        DataListInfo retBody;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            parameter.put("cmplxId", id);
            parameter.put("dong", dong);

            result = mobileUserController.listHoInfo(parameter, null);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        hoList = (List) ((Map) result.get("DATA")).get("HO");
        if (hoList.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo("해당하는 동/호가 없습니다"));
        }
        retBody = new DataListInfo(hoList);

        return ResponseEntity.status(HttpStatus.OK).body(retBody);
    }

    /**
     * 2-3. 세대주 휴대폰 인증번호 요청
     * <p>
     * params : cmplxId, dong, ho, headNm, headCell
     */
    @GetMapping(
            value = "/certHeadCellNo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity requestCertHeadCellNo(HttpServletRequest request) {
        RequestParameter parameter;
        Map<String, Object> result;
        boolean resFlag;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            result = mobileUserCertNoController.reqHeadCertNumber(parameter, null);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        resFlag = IokUtil.getResFlag(result);
        if (!resFlag) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo((String) result.get("msg")));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     * 2-4. 세대주 휴대폰 인증번호 확인
     * <p>
     * params : cmplxId, dong, ho, headNm, headCell, userCertId, headCertNum
     */
    @PostMapping(
            value = "/certHeadCellNo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateCertHeadCellNo(HttpServletRequest request) {
        RequestParameter parameter;
        Map<String, Object> result;
        boolean resFlag;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            result = mobileUserCertNoController.confirmHeadCertNumber(parameter, null);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        resFlag = IokUtil.getResFlag(result);
        if (!resFlag) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorInfo((String) result.get("msg")));
        }

        return ResponseEntity.status(HttpStatus.OK).body(IokUtil.lowerMsgKeyName(result));
    }


    /**
     * 3-1. 사용자 아이디 중복 확인
     */
    @GetMapping(
            value = "/existedUser/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkExistedUser(HttpServletRequest request, @PathVariable("userId") String userId) {
        RequestParameter parameter;
        Map<String, Object> result;
        boolean resFlag;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            regService.isAcceptedUserId(userId);
        } catch (NotAcceptedUserIdException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo( e.getMessage() ));
        }

        try {
            parameter.put("userId", userId);
            result = mobileUserController.checkUserId(parameter, null);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        resFlag = IokUtil.getResFlag(result);
        if (!resFlag) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(IokUtil.lowerMsgKeyName(result));
        }

        return ResponseEntity.status(HttpStatus.OK).body(IokUtil.lowerMsgKeyName(result));
    }


    /**
     * 3-2. 사용자 휴대폰 인증번호 요청
     * <p>
     * params: cmplxId, dong, ho, headNm, headCell, userNm, userCell, userCertId
     */
    @GetMapping(
            value = "/certUserCellNo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity requestCertUserCellNo(HttpServletRequest request) {
        RequestParameter parameter;
        Map<String, Object> result;
        boolean resFlag;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            result = mobileUserCertNoController.reqUserCertNumber(parameter, null);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        resFlag = IokUtil.getResFlag(result);
        if (!resFlag) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 3-3. 사용자 휴대폰 인증번호 확인
     */
    @PostMapping(
            value = "/certUserCellNo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateCertUserCellNo(HttpServletRequest request) {
        RequestParameter parameter;
        Map<String, Object> result;
        boolean resFlag;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            result = mobileUserCertNoController.confirmUserCertNumber(parameter, null);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        resFlag = IokUtil.getResFlag(result);
        if (!resFlag) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(IokUtil.lowerMsgKeyName(result));
        }

        return ResponseEntity.status(HttpStatus.OK).body(IokUtil.lowerMsgKeyName(result));
    }


    /**
     * 3-4. 회원 가입
     * <p>
     * params(12):
     * - cmplxId, dong, ho, headNm, headCell,
     * - userNm, userCell, certNum, smsChkYn, smsChkDt,
     * - userId, userPw
     */
    @PostMapping(
            value = "/newUser",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerNewUser(HttpServletRequest request) {
        RequestParameter parameter;
        Map<String, Object> result;
        boolean resFlag;

        parameter = IokUtil.buildRequestParameter(request);

        // User id validation
        try {
            regService.isAcceptedUserId(parameter.getString("userId"));
        } catch (NotAcceptedUserIdException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo( e.getMessage() ));
        }

        try {
            result = mobileUserController.registerMember(parameter, null);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        resFlag = IokUtil.getResFlag(result);
        if (!resFlag) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(IokUtil.lowerMsgKeyName(result));
        }

        return ResponseEntity.status(HttpStatus.OK).body(IokUtil.lowerMsgKeyName(result));
    }
}
