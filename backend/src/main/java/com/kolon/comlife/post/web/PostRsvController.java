package com.kolon.comlife.post.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.like.model.LikeInfo;
import com.kolon.comlife.like.model.LikeStatusInfo;
import com.kolon.comlife.post.service.PostRsvService;
import com.kolon.comlife.users.model.PostUserInfo;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/rsv/*")
public class PostRsvController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRsvService postRsvService;
    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<LikeInfo>> getLikeList(@PathVariable( "postId" ) int postId ) {
        List<LikeInfo> likes = postRsvService.getLikeList( postId );

        // USR_ID 추출
        List<Integer> userIds = new ArrayList<Integer>();
        for (LikeInfo e : likes) {
            userIds.add( e.getUsrId() );
        }

        if( userIds.size() > 0 ) {
            // 추출한 ID로 유저 정보 SELECT
            List<PostUserInfo> userList = userService.getUserListForPostById( userIds );

            // 유저 정보 바인딩
            for( LikeInfo like : likes ) {
                for( PostUserInfo user : userList ) {
                    if( like.getUsrId() == user.getUsrId() ) {
                        like.setUser( user );
                    }
                }
            }
        }

        return ResponseEntity.status( HttpStatus.OK ).body( likes );
    }

    @GetMapping(
            value = "/has",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public boolean hasLike( @PathVariable( "postId" ) int postId, HttpServletRequest request ) {
        int usrIdx = Integer.parseInt( request.getParameter( "usrIdx" ) );
        return postRsvService.hasLike( postId, usrIdx );
    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity requestRsv(
                    HttpServletRequest            request,
                    @PathVariable( "postId" ) int postId ) {
        AuthUserInfo currUser;
        LikeStatusInfo result;
        try {
            currUser = AuthUserInfoUtil.getAuthUserInfo( request );
            result = postRsvService.requestRsv( postId, currUser.getUsrId());
        } catch(Exception e) {
            logger.error(e.getMessage());
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
                    @PathVariable( "postId" ) int postId) {
        AuthUserInfo currUser;
        LikeStatusInfo result;
        try {
            currUser = AuthUserInfoUtil.getAuthUserInfo( request );
            result = postRsvService.cancelRsv( postId, currUser.getUsrId() );
        } catch(Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body( new SimpleErrorInfo("참여 취소 신청이 가능하지 않습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }
}
