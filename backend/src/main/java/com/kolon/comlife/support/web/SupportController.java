package com.kolon.comlife.support.web;

import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.support.exception.OperationFailedException;
import com.kolon.comlife.support.exception.SupportGeneralException;
import com.kolon.comlife.support.model.SupportCategoryInfo;
import com.kolon.comlife.support.model.TicketFileInfo;
import com.kolon.comlife.support.model.TicketInfo;
import com.kolon.comlife.support.service.SupportService;
import com.kolon.comlife.support.service.TicketFileStoreService;
import com.kolon.comlife.support.service.TicketService;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/info/livingSupport/*")
public class SupportController {

    private static final Logger logger = LoggerFactory.getLogger(SupportController.class);

    @Autowired
    private SupportService supportService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketFileStoreService ticketFileStoreService;


    /**
     * 해당 현장의 Category List를 가져오는 API
     * @param request
     * @return
     */
    @CrossOrigin
    @GetMapping(
            value = "/category",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getSupportCategoryList( HttpServletRequest request ) {

        AuthUserInfo               currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        SupportCategoryInfo        cateInfoParam;
        List<SupportCategoryInfo>  cateInfoList;
        DataListInfo               retCateInfoList;


        logger.debug(">>> CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> UserId: " + currUser.getUserId());
        logger.debug(">>> UsrId: " + currUser.getUsrId());

        cateInfoParam = new SupportCategoryInfo();
        cateInfoParam.setCmplxId( currUser.getCmplxId() );

        try {
            cateInfoList = supportService.getCategoryInfoByComplexId( cateInfoParam );
        } catch ( SupportGeneralException e ) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( new SimpleErrorInfo("내부오류가 발생했습니다.") );
        }

        retCateInfoList = new DataListInfo();
        retCateInfoList.setData(cateInfoList);
        retCateInfoList.setMsg("Support Ticket Category 목록");

        return ResponseEntity.status(HttpStatus.OK).body(retCateInfoList);
    }

    @PostMapping(
            value = "/ticket",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity submitTicket( HttpServletRequest request,
                                        @RequestBody Map args ) throws Exception {
        TicketInfo newTicket = new TicketInfo();
        TicketInfo retTicket;

        List<TicketFileInfo> ticketFiles;
        List<Integer>        ticketFilesIdList;

        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        int          usrId = -1;
        int          cmplxId = -1;

        logger.debug(">>> CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> UserId: " + currUser.getUserId());
        logger.debug(">>> UsrId: " + currUser.getUsrId());

        usrId = currUser.getUsrId();
        cmplxId = currUser.getCmplxId();

        newTicket.setUsrId( usrId );
        newTicket.setCmplxId( cmplxId );
        newTicket.setCateIdx( ((Integer) args.get( "cateIdx" )).intValue() );
        newTicket.setContent( (String) args.get( "content" ) );

        ticketFilesIdList = (List)args.get( "ticketFiles" );

        try {
            retTicket = ticketService.submitTicketWithImage( newTicket, ticketFilesIdList, currUser.getUsrId() );
//            retTicket = ticketService.submitTicket( newTicket );
        } catch( Exception e ){
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo("지원요청 작성이 실패하였습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( retTicket );
    }


    @CrossOrigin
    @PostMapping(
            value = "/ticketFiles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createTicketFile( HttpServletRequest request,
                                          @RequestBody HashMap<String, String> params ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        int          usrId;
        TicketInfo  ticketInfo;
        TicketFileInfo  ticketFileInfo;
        byte[]       imageBytes;

        logger.debug(">>> CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> UserId: " + currUser.getUserId());
        logger.debug(">>> UsrId: " + currUser.getUsrId());

        usrId = currUser.getUsrId();

        String base64 = params.get( "file" );

        if( base64 == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleMsgInfo( "file은 필수 입력 항목입니다." ));
        }

        String[] base64Components = base64.split(",");

        if (base64Components.length != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleMsgInfo( "잘못된 데이터입니다." ));
        }

        String base64Data = base64Components[0];
        String fileType = base64Data.substring(base64Data.indexOf('/') + 1, base64Data.indexOf(';'));
        String base64Image = base64Components[1];
        imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

        try {
            // Upload to S3
            ticketFileInfo = ticketFileStoreService.createTicketFile( imageBytes, fileType );
            ticketFileInfo.setUsrId( usrId );

            // 테이블 업데이트
            ticketFileInfo = ticketService.setTicketFile( ticketFileInfo );
        } catch(OperationFailedException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleMsgInfo( "이미지 업로드가 실패하였습니다." ));
        }

        return ResponseEntity.status(HttpStatus.OK).body( ticketFileInfo );
    }
}
