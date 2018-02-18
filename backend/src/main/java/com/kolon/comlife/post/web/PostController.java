package com.kolon.comlife.post.web;

import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
import com.kolon.comlife.users.model.UserInfo;
import com.kolon.comlife.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts/*")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Resource(name = "postService")
    private PostService postService;
    @Resource(name = "userService")
    private UserService userService;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginateInfo> getPostsInJson(HttpServletRequest request) {
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

        return ResponseEntity.status( HttpStatus.OK ).body( paginateInfo );
    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostInfo> setPost(@RequestBody PostInfo post) {
        // TODO: USR_ID 는 토큰 등을 통해서 전달받도록 추후 업데이트 필요
        // TODO: 파일은 S3에 저장할 것인가? 저장 시점은 언제로 할 것인가?
        PostInfo result = postService.setPost( post );
        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostInfo> getPostInJson( @PathVariable("id") int id ) {
        PostInfo result = postService.getPost( id );
        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @PutMapping(
            value = "/{id}"
    )
    public ResponseEntity<PostInfo> updatePost( @PathVariable("id") int id, @RequestBody PostInfo post ) {
        post.setBoardIdx( id );
        PostInfo result = postService.updatePost( post );
        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<PostInfo> deletePost( @PathVariable("id") int id ) {
        postService.deletePost( id );
        return ResponseEntity.status( HttpStatus.OK ).body( null );
    }
}
