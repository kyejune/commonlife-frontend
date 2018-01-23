package com.kolon.comlife.post.web;

import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
import com.kolon.comlife.user.model.UserInfo;
import com.kolon.comlife.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post/*")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Resource(name = "postService")
    private PostService postService;
    @Resource(name = "userService")
    private UserService userService;

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginateInfo getPostInJson(HttpServletRequest request) {
        String page = request.getParameter( "page" );
        if( page == null ) {
            page = "1";
        }
        int pageNum = Integer.parseInt( page );
        int limit = 20;
        int offset = limit * ( pageNum - 1 );

        Map<String, Integer> paginationParams = new HashMap<String, Integer>();
        paginationParams.put( "limit", limit );
        paginationParams.put( "offset", offset );

        // 포스트 목록 추출
        List<PostInfo> postInfoList = postService.getPostList( paginationParams );

        // USR_ID 추출
        List<Integer> userIds = new ArrayList<Integer>();
        for (PostInfo e : postInfoList) {
            userIds.add( e.getUsrId() );
        }

        if( userIds.size() > 0 ) {
            // 추출한 ID로 유저 정보 SELECT
            List<UserInfo> userList = userService.getUserListById( userIds );

            // 유저 정보 바인딩
            for( PostInfo post : postInfoList ) {
                for( UserInfo user : userList ) {
                    if( post.getUsrId() == user.getUsrId() ) {
                        post.setUser( user );
                    }
                }
            }
        }

        // 페이지네이션 계산
        int countPosts = postService.countPostList();
        double totalPages = Math.ceil( ( double ) countPosts / ( double ) limit );
        PaginateInfo paginateInfo = new PaginateInfo();
        paginateInfo.setCurrentPage( pageNum );
        paginateInfo.setTotalPages( totalPages );
        paginateInfo.setPerPage( limit );
        paginateInfo.setData( postInfoList );

        return paginateInfo;
    }
}
