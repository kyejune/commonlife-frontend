package com.kolon.comlife.admin.post.web;

import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.post.model.PostInfo;
import com.kolon.comlife.admin.post.service.PostService;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.common.paginate.PaginateInfo;
import com.kolon.common.admin.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/posts/*")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private static final int LIMIT_COUNT = 10;  // 한 번에 로딩할 게시글 갯수

    private static final String POST_TYPE_FEED  = "feed";
    private static final String POST_TYPE_EVENT = "event";
    private static final String POST_TYPE_NEWS  = "news";   // <- NOTICE 임

    @Resource(name = "postService")
    private PostService postService;


    private ModelAndView getListInternal( ModelAndView mav, String postType, int pageNum ) {
        AdminInfo            adminInfo;
        PaginateInfo         paginateInfo;
        Map<String, Object>  postParams = new HashMap<>();
        List<PostInfo>       postList;
        int                  cmplxId;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        cmplxId = adminInfo.getCmplxId();

        postParams.put( "postType", postType ); // NEWS == NOTICE
        postParams.put( "limit", LIMIT_COUNT);
        postParams.put( "pageNum", Integer.valueOf(pageNum) );
        postParams.put( "cmplxId", Integer.valueOf( cmplxId ) );
        postParams.put( "adminIdx", adminInfo.getAdminIdx() );

        // 포스트 목록 추출
        try {
            paginateInfo = postService.getPostWithLikeInfoList( postParams );
        } catch( Exception e ) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return mav.addObject("error",
                    "목록 가져오기를 실패했습니다. 문제가 지속되면 담당자에게 문의하세요." );
        }

        postList = paginateInfo.getData();

        logger.debug(">>>> paginateInfo.getTotalRecordCount:" + paginateInfo.getTotalRecordCount() );
        logger.debug(">>>> paginateInfo.getPageSize:" + paginateInfo.getPageSize());
        logger.debug(">>>> paginateInfo.getTotalPageCount:" + paginateInfo.getTotalPageCount() );

        mav.addObject("postType", postType);
        mav.addObject("paginateInfo", paginateInfo);
        mav.addObject("postList", postList);
        mav.addObject("pageNum", pageNum);

        mav.setViewName("admin/posts/feedList");

        return mav;
    }

    @GetMapping( value = "feedList.*" )
    public ModelAndView getFeedList( HttpServletRequest request,
                                     HttpServletResponse response,
                                     ModelAndView mav,
                                     HttpSession session,
                                     @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, POST_TYPE_FEED, pageNum );
    }

    @GetMapping( value = "eventList.*" )
    public ModelAndView getEventList( HttpServletRequest request,
                                      HttpServletResponse response,
                                      ModelAndView mav,
                                      HttpSession session,
                                      @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, POST_TYPE_EVENT, pageNum );
    }

    @GetMapping( value = "noticeList.*" )
    public ModelAndView getNoticeList( HttpServletRequest request,
                                      HttpServletResponse response,
                                      ModelAndView mav,
                                      HttpSession session,
                                      @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, POST_TYPE_NEWS, pageNum );
    }


    @GetMapping( value = "feedEdit.*" )
    public ModelAndView editPost( HttpServletRequest request,
                                       HttpServletResponse response,
                                       ModelAndView mav,
                                       HttpSession session,
                                       @RequestParam(required=false, defaultValue = "1") int pageNum,
                                       @RequestParam(required=false, defaultValue = "1") int postIdx ) {



        mav.addObject("postType", POST_TYPE_EVENT );
        mav.setViewName("/admin/posts/feedEdit");

        return mav;
    }


    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setPost( HttpServletRequest request,
                                   @RequestBody Map   args ) throws Exception {
        AdminInfo adminInfo;
        PostInfo           newPost = new PostInfo();
        PostInfo           retPost;

        List<Integer>      postFilesIdList;
        int                usrId;
        int                cmplxId;
        String             postType;

        postType = (String) args.get( "postType" );

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        try {
            usrId = adminInfo.getAdminIdx();
            if( request.getParameter( "cmplxId" ) != null ) {
                cmplxId = StringUtil.parseInt( request.getParameter( "cmplxId" ), adminInfo.getCmplxId() );
            } else {
                cmplxId = adminInfo.getCmplxId();
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
            retPost = postService.setPostWithImage( newPost, postFilesIdList, adminInfo.getAdminIdx() );
        } catch( Exception e ){
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo("게시물 작성이 실패하였습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( retPost );
    }

    ////// AJAX CALL //////
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPostInJson( HttpServletRequest      request,
                                         @PathVariable("id") int id ) {
        AdminInfo adminInfo;
        PostInfo result;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        try {
            result = postService.getPostById( id, adminInfo.getAdminIdx() );
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
    public ResponseEntity updatePost( HttpServletRequest      request,
                                      @PathVariable("id") int id,
                                      @RequestBody PostInfo   post ) {
        AdminInfo adminInfo;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        post.setUsrId( adminInfo.getAdminIdx() );
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
        AdminInfo adminInfo;
        PostInfo  deletedPostInfo;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        deletedPostInfo = postService.deletePost( id, adminInfo.getCmplxId(), adminInfo.getAdminIdx() );
        if( deletedPostInfo == null ) {
            return ResponseEntity.
                    status( HttpStatus.BAD_REQUEST ).
                    body(new SimpleErrorInfo("해당 게시물을 비공개 처리할 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("해당 게시물을 비공개 처리하였습니다.") );
    }
}
