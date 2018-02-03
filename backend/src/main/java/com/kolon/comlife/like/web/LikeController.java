package com.kolon.comlife.like.web;

import com.kolon.comlife.like.model.LikeInfo;
import com.kolon.comlife.like.service.LikeService;
import com.kolon.comlife.post.web.PostController;
import com.kolon.comlife.user.model.UserInfo;
import com.kolon.comlife.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/like/*")
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Resource(name = "likeService")
    private LikeService likeService;
    @Resource(name = "userService")
    private UserService userService;

    @GetMapping(
            value = "/{parentIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<LikeInfo> getLikeList( @PathVariable("parentIdx") int parentIdx ) {
        List<LikeInfo> likes = likeService.getLikeList( parentIdx );

        // USR_ID 추출
        List<Integer> userIds = new ArrayList<Integer>();
        for (LikeInfo e : likes) {
            userIds.add( e.getUsrIdx() );
        }

        if( userIds.size() > 0 ) {
            // 추출한 ID로 유저 정보 SELECT
            List<UserInfo> userList = userService.getUserListById( userIds );

            // 유저 정보 바인딩
            for( LikeInfo like : likes ) {
                for( UserInfo user : userList ) {
                    if( like.getUsrIdx() == user.getUsrId() ) {
                        like.setUser( user );
                    }
                }
            }
        }

        return likes;
    }

    @GetMapping(
            value = "/has/{parentIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public boolean hasLike( @PathVariable("parentIdx") int parentIdx, HttpServletRequest request ) {
        int usrIdx = Integer.parseInt( request.getParameter( "usrIdx" ) );
        return likeService.hasLike( parentIdx, usrIdx );
    }

    @PostMapping(
            value = "/{parentIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public LikeInfo addLike(@PathVariable("parentIdx") int parentIdx, @RequestBody LikeInfo likeInfo ) {
        int usrIdx = likeInfo.getUsrIdx();
        return likeService.addLike( parentIdx, usrIdx );
    }

    @DeleteMapping(
            value = "/{parentIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void cancelLike(@PathVariable("parentIdx") int parentIdx, @RequestBody LikeInfo likeInfo) {
        int usrIdx = likeInfo.getUsrIdx();
        likeService.cancelLike( parentIdx, usrIdx );
        return;
    }
}
