package com.kolon.comlife.support.web;

import com.kolon.comlife.common.model.DataListInfo;
import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.support.exception.SupportGeneralException;
import com.kolon.comlife.support.model.SupportCategoryInfo;
import com.kolon.comlife.support.model.TicketInfo;
import com.kolon.comlife.support.service.SupportService;
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
import javax.xml.ws.Response;
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
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity submitTicket( HttpServletRequest request,
                                        @RequestBody Map args ) throws Exception {
        TicketInfo newTicket = new TicketInfo();
        TicketInfo retTicket;

//        List<PostFileInfo> postFiles;
//        List<Integer>      postFilesIdList;
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
        newTicket.setLvngSuptCateIdx( ((Integer) args.get( "lvngSuptCateIdx" )).intValue() );
        newTicket.setContent( (String) args.get( "content" ) );

//        postFilesIdList = (List)args.get( "postFiles" );

        try {
//            retPost = postService.setPostWithImage( newPost, postFilesIdList, currUser.getUsrId() );
            retTicket = ticketService.submitTicket( newTicket );
        } catch( Exception e ){
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo("지원요청 작성이 실패하였습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( retTicket );
    }
}
