package com.kolon.comlife.users.web;

import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.imageStore.exception.ImageBase64Exception;
import com.kolon.comlife.imageStore.model.ImageBase64;
import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import com.kolon.comlife.postFile.service.exception.OperationFailedException;
import com.kolon.comlife.users.exception.NotAcceptedUserIdException;
import com.kolon.comlife.users.exception.UserNotExistException;
import com.kolon.comlife.users.exception.UsersGeneralException;
import com.kolon.comlife.users.model.AgreementInfo;
import com.kolon.comlife.users.model.UserInfo;
import com.kolon.comlife.users.service.UserRegistrationService;
import com.kolon.comlife.users.service.UserService;
import com.kolon.comlife.users.util.IokUtil;
import com.kolonbenit.benitware.framework.http.parameter.RequestParameter;
import com.kolonbenit.iot.mobile.controller.MobileUserCertNoController;
import com.kolonbenit.iot.mobile.controller.MobileUserController;
import com.kolonbenit.iot.mobile.service.MobileUserCertNoService;
import com.kolonbenit.iot.mobile.service.MobileUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/users/registration/*")
public class UserRegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    private UserRegistrationService regService;

    @Autowired
    private UserService userService;

    @Autowired
    private ComplexService complexService;

    @Autowired
    private ImageStoreService imageStoreService;

    // IOK mobile user services
    @Autowired
    private MobileUserService mobileUserService;

    @Autowired
    private MobileUserCertNoService mobileUserCertNoService;

    @Autowired
    private MessageSource messageSource;

    private final Locale locale = Locale.KOREA;


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
        List<Object> dongList;    // 동 목록
        DataListInfo retBody;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            parameter.put("cmplxId", id);
            dongList = mobileUserService.listDongInfo(parameter);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

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
        List<Object> hoList;    // 호 목록
        DataListInfo retBody;

        parameter = IokUtil.buildRequestParameter(request);

        try {
            parameter.put("cmplxId", id);
            parameter.put("dong", dong);

            hoList = mobileUserService.listHoInfo(parameter);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

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
            result = mobileUserCertNoService.sendHeadCertificationNumber(parameter);
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
            result = mobileUserCertNoService.confirmHeadCertificationNumber(parameter);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        resFlag = IokUtil.getResFlag(result);
        if (!resFlag) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body( IokUtil.lowerMsgKeyName(result) );
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
        String msg;
        Map ret = new HashMap();

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
            result = mobileUserService.checkUserId(parameter);
        } catch (Exception e) {
            logger.error("Failed to call: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new SimpleErrorInfo("일시적으로 서비스에 문제가 있습니다."));
        }

        if( result == null ) {
            String[] userIdArray = { parameter.getString("userId") };
            msg = messageSource.getMessage("mobile.check.userid.0002", userIdArray, locale);


            ret.put("isExisted", false);
            ret.put("msg", msg);
            return ResponseEntity.status(HttpStatus.OK).body( ret );
        }

        msg = messageSource.getMessage("mobile.check.userid.0001", null, locale);
        ret.put("isExisted", true);
        ret.put("msg", msg);
        return ResponseEntity.status(HttpStatus.OK).body( ret );
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
            result = mobileUserCertNoService.sendUserCertificationNumber(parameter);
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
            result = mobileUserCertNoService.confirmUserCertificationNumber(parameter);
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
    public ResponseEntity registerNewUser(HttpServletRequest request) throws Exception {
        RequestParameter parameter;
        Map<String, Object> result;
        boolean resFlag;
        UserInfo userInfo;
        String userId;
        String userPw;

        parameter = IokUtil.buildRequestParameter(request);
        userId = parameter.getString("userId");
        userPw = parameter.getString("userPw");

        // User id validation
        try {
            regService.isAcceptedUserId( userId );
        } catch (NotAcceptedUserIdException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo( e.getMessage() ));
        }

        // 중복 가입인지 확인
        if( userService.isExistedUser( userId ) ) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body( new SimpleMsgInfo( "이미 가입이 완료되었습니다." ) );
        }

        try {
            result = mobileUserService.registerMember(parameter);
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

        try {
            userInfo = regService.setUserExt( parameter.getString("userId"), parameter.getString("userPw"));
        } catch( UserNotExistException e ){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body( new SimpleErrorInfo( e.getMessage() ));
        } catch( UsersGeneralException e ){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body( new SimpleErrorInfo( e.getMessage() ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(IokUtil.lowerMsgKeyName(result));
    }

    /**
     * 3-5. 이미지 업로드
     */
    @PostMapping(
            value = "/newUser/photo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadPhoto( HttpServletRequest request,
                                       @RequestBody HashMap<String, String> bodyParams ) {
        UserInfo userInfo;
        ImageInfo imageInfo;

        String userId = bodyParams.get("userId");
        String userPw = bodyParams.get("userPw");
        String file   = bodyParams.get("file");
        ImageBase64 imageBase64 = new ImageBase64();
        byte[]       imageBytes;
        String imageType = ImageInfoUtil.IMAGE_TYPE_PROFILE;

        if(userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleErrorInfo( "userId를 입력하세요." ));
        }
        if(userPw == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleErrorInfo( "userPw를 입력하세요." ));
        }
        if(file == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleErrorInfo( "file를 입력하세요." ));
        }

        //등록된 회원
        userInfo = userService.getUsrIdByUserIdAndPwd( userId, userPw );
        if( userInfo == null ) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body( new SimpleMsgInfo( "인증이 실패하였습니다." ) );
        }

        // parameter 체크 및 이미지 생성에 전달
        try {
            imageBase64.parseBase64(file);
        } catch( ImageBase64Exception e ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleMsgInfo( e.getMessage() ));
        }

        imageBytes = imageBase64.getByteData();
        try {
            imageInfo = imageStoreService.createImage(
                    new ByteArrayInputStream( imageBytes ),
                    imageBytes.length,
                    imageType,
                    imageBase64.getFileType(),
                    userInfo.getUsrId() );
        } catch( OperationFailedException e ) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleMsgInfo( e.getMessage() ) );
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SimpleMsgInfo("프로필 사진이 업데이트 되었습니다."));
    }


    /**
     * 4. 사용자 ID 찾기
     */
    @GetMapping(
            value = "/findUserId",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findUserId( HttpServletRequest request,
                                      @RequestParam("headNm")   String headNm,
                                      @RequestParam("headCell") String headCell,
                                      @RequestParam("userNm")   String userNm,
                                      @RequestParam("userCell") String userCell ) {
        RequestParameter parameter = new RequestParameter();
        Map<String, Object> ret;

        parameter.put("headNm", headNm);
        parameter.put("headCell", headCell);
        parameter.put("userNm", userNm);
        parameter.put("userCell", userCell);

        try {
            ret = mobileUserService.searchUserId( parameter );
            if( ret == null ) {
                return ResponseEntity
                        .status( HttpStatus.NOT_FOUND )
                        .body( new SimpleMsgInfo("입력한 정보가 틀리거나 사용자가 존재하지 않습니다.") );
            }
        } catch ( Exception e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.CONFLICT )
                    .body(new SimpleErrorInfo(
                            "아이디 찾기가 실패하였습니다. 잠시 후에 다시 시도하세요. 만약, 문제가 지속된다면 지원센터에 문의하세요.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("아이디가 사용자 휴대폰으로 전송되었습니다.") );
    }

    /**
     * 5. 사용자 비밀번호 재설정
     */
    @GetMapping(
            value = "/resetPwd",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity resetPwd( HttpServletRequest request,
                                      @RequestParam("headNm")   String headNm,
                                      @RequestParam("headCell") String headCell,
                                      @RequestParam("userId")   String userId,
                                      @RequestParam("userCell") String userCell ) {
        RequestParameter parameter = new RequestParameter();
        Map<String, Object> ret;

        parameter.put("headNm", headNm);
        parameter.put("headCell", headCell);
        parameter.put("userId", userId);
        parameter.put("userCell", userCell);

        try {
            ret = mobileUserService.searchUserPw( parameter );
            if( ret == null ) {
                return ResponseEntity
                        .status( HttpStatus.NOT_FOUND )
                        .body( new SimpleMsgInfo("입력한 정보가 틀리거나 사용자가 존재하지 않습니다.") );
            }
        } catch ( Exception e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.CONFLICT )
                    .body(new SimpleErrorInfo(
                            "비밀번호 재설정이 실패하였습니다. 잠시 후에 다시 시도하세요. 만약, 문제가 지속된다면 지원센터에 문의하세요.") );
        }

        return ResponseEntity
                .status( HttpStatus.OK )
                .body( new SimpleMsgInfo("임시 비밀번호를 사용자 휴대폰 번호로 전송하였습니다. 로그인 즉시 비밀번호를 변경해주세요.") );
    }


}
