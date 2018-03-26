package com.kolon.comlife.like.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.like.model.LikeInfo;
import com.kolon.comlife.like.model.LikeStatusInfo;
import com.kolon.comlife.like.service.LikeService;
import com.kolon.comlife.post.web.PostController;
import com.kolon.comlife.users.model.UserInfo;
import com.kolon.comlife.users.service.UserService;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/likes/*")
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Resource(name = "likeService")
    private LikeService likeService;
    @Resource(name = "userService")
    private UserService userService;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<LikeInfo>> getLikeList(@PathVariable( "postId" ) int postId ) {
        List<LikeInfo> likes = likeService.getLikeList( postId );

        // USR_ID 추출
        List<Integer> userIds = new ArrayList<Integer>();
        for (LikeInfo e : likes) {
            userIds.add( e.getUsrId() );
        }

        if( userIds.size() > 0 ) {
            // 추출한 ID로 유저 정보 SELECT
            List<UserInfo> userList = userService.getUserListById( userIds );

            // 유저 정보 바인딩
            for( LikeInfo like : likes ) {
                for( UserInfo user : userList ) {
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
        return likeService.hasLike( postId, usrIdx );
    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity addLike(
                    HttpServletRequest            request,
                    @PathVariable( "postId" ) int postId ) {
        AuthUserInfo currUser;
        LikeStatusInfo result;
        try {
            currUser = AuthUserInfoUtil.getAuthUserInfo( request );
            result = likeService.addLike(postId, currUser.getUsrId());
        } catch(Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body( new SimpleErrorInfo("LIKE 추가 명령이 실패했습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @DeleteMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity cancelLike(
                    HttpServletRequest            request,
                    @PathVariable( "postId" ) int postId) {
        AuthUserInfo currUser;
        LikeStatusInfo result;
        try {
            currUser = AuthUserInfoUtil.getAuthUserInfo( request );
            result = likeService.cancelLike( postId, currUser.getUsrId() );
        } catch(Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body( new SimpleErrorInfo("LIKE 추가 명령이 실패했습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }
}
