package com.kolon.comlife.post.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.post.exception.PostRsvGeneralException;
import com.kolon.comlife.post.exception.ReservedAlreadyException;
import com.kolon.comlife.post.model.PostRsvInfo;
import com.kolon.comlife.post.model.PostRsvStatusInfo;
import com.kolon.comlife.post.service.PostRsvService;
import com.kolon.comlife.users.service.UserService;
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

@RestController
@RequestMapping("/posts/{postId}/rsv/*")
public class PostRsvController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRsvService postRsvService;
    @Autowired
    private UserService userService;

    /**
     * 해당 포스팅에 참여 신청한 사용자 목록 가져오기
     * @param postId
     * @return
     */
    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getRsvUserList(@PathVariable( "postId" ) int postId ) {
        PostRsvInfo rsvInfoList;
        PostRsvInfo rsvInfo;

        try {
            rsvInfoList = postRsvService.getRsvInfoWithUserListByPostId(postId);
        } catch( Exception e ) {
            e.printStackTrace();
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body( new SimpleErrorInfo("참여 신청자 내역을 가져올 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( rsvInfoList );
    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity requestRsv(
                    HttpServletRequest            request,
                    @PathVariable( "postId" ) int postId ) {
        AuthUserInfo      currUser;
        PostRsvStatusInfo result;

        try {
            currUser = AuthUserInfoUtil.getAuthUserInfo(request);
            result = postRsvService.requestRsv(postId, currUser.getUsrId());
            logger.debug(">>>> request rsv Success!!");

        } catch( ReservedAlreadyException e) {
            return ResponseEntity
                    .status( HttpStatus.CONFLICT)
                    .body( new SimpleErrorInfo( e.getMessage() ) );

        } catch( PostRsvGeneralException e) {
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo( e.getMessage() ) );

        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body( new SimpleErrorInfo("현재 참여 신청이 가능하지 않습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @DeleteMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity cancelRsv(
                    HttpServletRequest            request,
                    @PathVariable( "postId" ) int postId){
        AuthUserInfo      currUser;
        PostRsvStatusInfo result;

        try {
            currUser = AuthUserInfoUtil.getAuthUserInfo( request );
            result = postRsvService.cancelRsv( postId, currUser.getUsrId() );

        } catch( ReservedAlreadyException e) {
            return ResponseEntity
                    .status( HttpStatus.CONFLICT)
                    .body( new SimpleErrorInfo( e.getMessage() ) );

        } catch( PostRsvGeneralException e) {
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo( e.getMessage() ) );

        } catch(Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body( new SimpleErrorInfo("참여 취소 신청이 가능하지 않습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }
}
