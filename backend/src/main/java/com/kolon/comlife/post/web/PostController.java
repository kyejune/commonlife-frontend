package com.kolon.comlife.post.web;

import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
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
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts/*")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private static final int LIMIT_COUNT = 20;  // 한 번에 로딩할 게시글 갯수

    private static final String POST_TYPE_FEED  = "feed";
    private static final String POST_TYPE_EVENT = "event";
    private static final String POST_TYPE_NEWS  = "news";

    @Resource(name = "postService")
    private PostService postService;


    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPostListInJson(HttpServletRequest request) {

        AuthUserInfo         currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        PaginateInfo         paginateInfo;
        Map<String, Object>  postParams = new HashMap<>();
        int                  cmplxId;
        String               page;
        int                  pageNum;

        logger.debug(">>> currUser>CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> currUser>UserId: " + currUser.getUserId());
        logger.debug(">>> currUser>UsrId: " + currUser.getUsrId());

        try {
            if (request.getParameter("cmplxId") != null) {
                cmplxId = StringUtil.parseInt(request.getParameter("cmplxId"), currUser.getCmplxId());
            } else {
                cmplxId = currUser.getCmplxId();
            }
            page = request.getParameter("page");
            pageNum = StringUtil.parseInt(page, 1);
        } catch( NumberFormatException e ) {
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleErrorInfo("잘못된 파라미터가 입력되었습니다. "));
        }

        String postType = request.getParameter("postType");
        if( postType != null ) {
            if( POST_TYPE_FEED.equals(postType) ||
                POST_TYPE_EVENT.equals(postType) ||
                POST_TYPE_NEWS.equals(postType)) {
                postParams.put( "postType", postType );
            } else {
                return ResponseEntity
                        .status( HttpStatus.BAD_REQUEST )
                        .body(new SimpleErrorInfo("잘못된 postType 값이 입력되었습니다. "));
            }
        }

        postParams.put( "limit", LIMIT_COUNT);
        postParams.put( "pageNum", Integer.valueOf(pageNum) );
        postParams.put( "cmplxId", Integer.valueOf(cmplxId) );
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

        // todo: 해당 현장의 글쓰기 아이콘 표시 유/무 결정 - 테스트를 위한 dummy 값 추가
        paginateInfo.setFeedWriteAllowYn("Y");
        
        return ResponseEntity.status( HttpStatus.OK ).body( paginateInfo );
    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setPost( HttpServletRequest request,
                                   @RequestBody Map args ) throws Exception {
        PostInfo           newPost = new PostInfo();
        PostInfo           retPost;

        List<Integer>      postFilesIdList;
        AuthUserInfo       currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        int                usrId;
        int                cmplxId;

        logger.debug(">>> currUser>CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> currUser>UserId: " + currUser.getUserId());
        logger.debug(">>> currUser>UsrId: " + currUser.getUsrId());

        try {
            usrId = currUser.getUsrId();
            if( request.getParameter( "cmplxId" ) != null ) {
                cmplxId = StringUtil.parseInt( request.getParameter( "cmplxId" ), currUser.getCmplxId() );
            } else {
                cmplxId = currUser.getCmplxId();
            }
        } catch( NumberFormatException e ) {
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body(new SimpleErrorInfo("잘못된 cmplxId 값이 입력되었습니다. "));
        }

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
        PostInfo result;

        try {
            result = postService.getPostById( id, currUser.getUsrId() );
        } catch( Exception e ) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo(e.getMessage()) );
        }

        if( result == null ) {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( new SimpleErrorInfo("해당 게시물을 열람할 수 없습니다. ") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @PutMapping(
            value = "/{id}"
    )
    public ResponseEntity updatePost( HttpServletRequest        request,
                                      @PathVariable("id") int   id,
                                      @RequestBody        Map   params ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        PostInfo postInfo;

        params.put("usrId", currUser.getUsrId()); // usrId
        params.put("postIdx", id); // postIdx

        if( params.get("content") == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleErrorInfo("본문 내용이 없습니다."));
        }

        if( params.get("postFiles") != null ) {
            if( !(params.get("postFiles") instanceof List) ) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body( new SimpleErrorInfo("잘못된 이미지 정보가 전달 되었습니다. "));
            }
        }

        postInfo = postService.updatePost( params );
        if( postInfo == null ) {
            return ResponseEntity.
                    status( HttpStatus.CONFLICT ).
                    body(new SimpleErrorInfo("해당 내용을 수정할 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( postInfo );
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
