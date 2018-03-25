package com.kolon.comlife.post.web;

import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.PostFileService;
import com.kolon.comlife.users.model.PostUserInfo;
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
    @Resource(name = "postFileService")
    private PostFileService postFileService;

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginateInfo> getPostsInJson(HttpServletRequest request) {

        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );

        logger.debug(">>> CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> UserId: " + currUser.getUserId());
        logger.debug(">>> UsrId: " + currUser.getUsrId());

        int complexId = currUser.getCmplxId();

        String page = request.getParameter( "page" );
        if( page == null ) {
            page = "1";
        }
        int pageNum = Integer.parseInt( page );
        int limit = 20;
        int offset = limit * ( pageNum - 1 );

        Map<String, Integer> postParams = new HashMap<String, Integer>();
        postParams.put( "limit", limit );
        postParams.put( "offset", offset );
        postParams.put( "cmplxId", complexId );

        // 포스트 목록 추출
        List<PostInfo> postInfoList = postService.getPostListByComplexId( postParams );

        // USR_ID 추출
        List<Integer> userIds = new ArrayList<Integer>();
        for (PostInfo e : postInfoList) {
            userIds.add( e.getUsrId() );
        }

        if( userIds.size() > 0 ) {
            // 추출한 ID로 유저 정보 SELECT
            List<PostUserInfo> userList = userService.getUserListForPostById( userIds );
            Map<Integer, PostUserInfo> userListMap = new HashMap();

            // 사용자 정보 Map 생성
            for( PostUserInfo user : userList ) {
                userListMap.put(Integer.valueOf(user.getUsrId()), user);
            }

            // 유저 정보 바인딩
            for( PostInfo post : postInfoList ) {
                PostUserInfo userInfo;
                userInfo = userListMap.get( post.getUsrId() );
                post.setUser(userInfo);
            }
        }

        // POST_IDX 추출
        List<Integer> postIdxs = new ArrayList<Integer>();
        for (PostInfo e : postInfoList) {
            postIdxs.add( e.getPostIdx() );
        }

        if( postIdxs.size() > 0 ) {
            List<PostFileInfo> postFileList = postFileService.getPostFilesByPostIds( postIdxs );

            // Post File 정보 바인딩
            for( PostInfo post : postInfoList ) {
                for( PostFileInfo postFile : postFileList ) {
                    if( post.getPostIdx() == postFile.getPostIdx() ) {
                        post.getPostFiles().add( postFile );
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
    public ResponseEntity<PostInfo> setPost( HttpServletRequest request,
                                             @RequestBody Map args ) {
        PostInfo           newPost = new PostInfo();

        List<PostFileInfo> postFiles;
        List               postFilesIdList;
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        int          usrId = -1;
        int          cmplxId = -1;

        logger.debug(">>> CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> UserId: " + currUser.getUserId());
        logger.debug(">>> UsrId: " + currUser.getUsrId());

        usrId = currUser.getUsrId();
        cmplxId = currUser.getCmplxId();

        newPost.setUsrId( usrId );
        newPost.setCmplxId( cmplxId );
        newPost.setPostType( (String) args.get( "postType" ) );
        newPost.setContent( (String) args.get( "content" ) );
        PostInfo result = postService.setPost( newPost );

        postFilesIdList = (List)args.get( "postFiles" );
        logger.debug(">>> Post Files: " +  postFilesIdList);
        if( (postFilesIdList != null) && (postFilesIdList.size() > 0) ) {
            logger.debug(">>> Post Files Count: " +  postFilesIdList.size());
            postFiles = postFileService.bindPostToPostFiles( result.getPostIdx(), (List<Integer>) args.get( "postFiles" ) );
            result.setPostFiles( postFiles );
        }
        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPostInJson( HttpServletRequest request,
                                                   @PathVariable("id") int id ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        PostInfo result = postService.getPost( id );

        if( result == null ) {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( new SimpleErrorInfo("해당 게시물을 열람할 수 없습니다. ") );
        }

        if( currUser.getCmplxId() != result.getCmplxId() ) {
            return ResponseEntity
                    .status( HttpStatus.UNAUTHORIZED )
                    .body( new SimpleErrorInfo("해당 게시물을 열람할 수 없습니다. ") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @PutMapping(
            value = "/{id}"
    )
    public ResponseEntity<PostInfo> updatePost( @PathVariable("id") int id, @RequestBody PostInfo post ) {
        post.setPostIdx( id );
        // todo: 사용자 인증 체크 필요
        PostInfo result = postService.updatePost( post );
        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<PostInfo> deletePost( @PathVariable("id") int id ) {
        // todo: 사용자 인증 체크 필요
        postService.deletePost( id );
        return ResponseEntity.status( HttpStatus.OK ).body( null );
    }
}
