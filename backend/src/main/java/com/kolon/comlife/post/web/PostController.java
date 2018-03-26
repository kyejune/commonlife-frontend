package com.kolon.comlife.post.web;

import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.PostFileService;
import com.kolon.comlife.users.service.UserService;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import com.kolon.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts/*")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private static final int LIMIT_COUNT = 20;  // 한 번에 로딩할 게시글 갯수

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
    public ResponseEntity getPostListInJson(HttpServletRequest request) {

        PaginateInfo paginateInfo;
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );

        logger.debug(">>> CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> UserId: " + currUser.getUserId());
        logger.debug(">>> UsrId: " + currUser.getUsrId());

        int complexId = currUser.getCmplxId();
        String page = request.getParameter( "page" );
        int pageNum = StringUtil.parseInt(page, 1);

        Map<String, Integer> postParams = new HashMap<String, Integer>();
        postParams.put( "limit", LIMIT_COUNT);
        postParams.put( "pageNum", Integer.valueOf(pageNum) );
        postParams.put( "cmplxId", Integer.valueOf(complexId) );
        postParams.put( "usrId", currUser.getUsrId() );

        // 포스트 목록 추출
        try {
            paginateInfo = postService.getPostWithLikeInfoList( postParams );
        } catch( Exception e ) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.SERVICE_UNAVAILABLE )
                    .body( new SimpleErrorInfo("목록 가져오기를 실패했습니다."));
        }

        return ResponseEntity.status( HttpStatus.OK ).body( paginateInfo );
    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setPost( HttpServletRequest request,
                                             @RequestBody Map args ) throws Exception {
        PostInfo           newPost = new PostInfo();
        PostInfo           retPost;

        List<PostFileInfo> postFiles;
        List<Integer>      postFilesIdList;
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

        postFilesIdList = (List)args.get( "postFiles" );

        try {
            retPost = postService.setPostWithImage( newPost, postFilesIdList, currUser.getUsrId() );
        } catch( Exception e ){
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo("게시물 작성이 실패하였습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( retPost );
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPostInJson( HttpServletRequest request,
                                         @PathVariable("id") int id ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        PostInfo result = postService.getPostById( id );

        if( result == null ) {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( new SimpleErrorInfo("해당 게시물을 열람할 수 없습니다. ") );
        }

//        if( currUser.getCmplxId() != result.getCmplxId() ) {
//            return ResponseEntity
//                    .status( HttpStatus.UNAUTHORIZED )
//                    .body( new SimpleErrorInfo("해당 게시물을 열람할 수 없습니다. ") );
//        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @PutMapping(
            value = "/{id}"
    )
    public ResponseEntity updatePost( HttpServletRequest      request,
                                      @PathVariable("id") int id,
                                      @RequestBody PostInfo   post ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );

        post.setUsrId( currUser.getUsrId() );
        post.setPostIdx( id );

        PostInfo result = postService.updatePost( post );
        if( result == null ) {
            return ResponseEntity.
                    status( HttpStatus.BAD_REQUEST ).
                    body(new SimpleErrorInfo("해당 내용을 수정할 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity deletePost( HttpServletRequest      request,
                                      @PathVariable("id") int id ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        PostInfo deletedPostInfo = null;

        deletedPostInfo = postService.deletePost( id, currUser.getUsrId() );
        if( deletedPostInfo == null ) {
            return ResponseEntity.
                    status( HttpStatus.BAD_REQUEST ).
                    body(new SimpleErrorInfo("해당 내용을 삭제할 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("해당 게시물을 삭제하였습니다.") );
    }
}
