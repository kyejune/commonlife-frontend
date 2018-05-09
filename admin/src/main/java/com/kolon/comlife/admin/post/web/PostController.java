package com.kolon.comlife.admin.post.web;

import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.post.exception.NotFoundException;
import com.kolon.comlife.admin.post.exception.OperationFailedException;
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
import java.util.ArrayList;
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
    private static final String POST_TYPE_NEWS  = "news";   // == NOTICE

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

        mav.addObject("adminInfo",  adminInfo);
        mav.addObject("postType", postType);
        mav.addObject("paginateInfo", paginateInfo);
        mav.addObject("postList", postList);
        mav.addObject("pageNum", pageNum);

        mav.setViewName("admin/posts/feedList");

        return mav;
    }

    @GetMapping( value = "feedList.*" )
    public ModelAndView getFeedList( ModelAndView        mav,
                                     @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, POST_TYPE_FEED, pageNum );
    }

    @GetMapping( value = "eventList.*" )
    public ModelAndView getEventList( ModelAndView        mav,
                                      @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, POST_TYPE_EVENT, pageNum );
    }

    @GetMapping( value = "noticeList.*" )
    public ModelAndView getNoticeList( ModelAndView        mav,
                                      @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, POST_TYPE_NEWS, pageNum );
    }


    /**
     * Event, Notice 공통 생성 화면 (Internal)
     */
    private ModelAndView newPostInternal( ModelAndView mav, String postType ) {
        AdminInfo     adminInfo;
        PostInfo      postInfo;

        mav.setViewName("/admin/posts/feedEdit");
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        mav.addObject("adminInfo", adminInfo);
        mav.addObject("postType", postType );
        mav.addObject("postUpdateYn", "N" ); // N == 새로운 글 생성

        return mav;
    }

    /**
     * Event 생성 화면
     */
    @GetMapping( value = "newEvent.*" )
    public ModelAndView newEvent( HttpServletRequest  request,
                                   HttpServletResponse response,
                                   ModelAndView        mav,
                                   HttpSession         session) {
        return newPostInternal( mav, POST_TYPE_EVENT );
    }

    /**
     * Notice 생성 화면
     */
    @GetMapping( value = "newNotice.*" )
    public ModelAndView newNotice( HttpServletRequest  request,
                                   HttpServletResponse response,
                                   ModelAndView        mav,
                                   HttpSession         session) {
        return newPostInternal( mav, POST_TYPE_NEWS );
    }


    /**
     * Event, Notice 공통 편집 화면
     */
    @GetMapping( value = "feedEdit.*" )
    public ModelAndView editPost( HttpServletRequest  request,
                                  HttpServletResponse response,
                                  ModelAndView        mav,
                                  HttpSession         session,
                                  @RequestParam( name = "postIdx", required=false, defaultValue = "-1") int postIdx ) {
        AdminInfo     adminInfo;
        PostInfo      postInfo = null;

        mav.setViewName("/admin/posts/feedEdit");

        logger.debug(">>> postIdx: " + postIdx );
        if( postIdx < 0 ) {
            mav.addObject("error", "해당 피드 정보를 가져올 수 없습니다." );
            return mav;
        }

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        try {
            postInfo = postService.getPostById( postIdx, -1 );
        } catch( Exception e ) {
            mav.addObject("error", "해당 피드 정보를 가져올 수 없습니다. " + e.getMessage());
            return mav;
        }

        mav.addObject("adminInfo", adminInfo);
        mav.addObject("postIdx", postIdx );
        mav.addObject("postUpdateYn", "Y" );
        mav.addObject("postType", postInfo.getPostType() );
        mav.addObject("postInfo", postInfo );

        return mav;
    }


    ////// AJAX CALL //////

    private String setString( String value, String defaultValue ) {
        return value == value ? defaultValue : value;
    }

    /**
     * 새로운 게시물 작성 (ajax)
     *  - Event, Notice 가능, 사용자 Feed 불가능
     */
    @PostMapping(
            value = "/proc.*",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity setPost( HttpServletRequest request,
                                   @RequestBody Map   params ) throws Exception {
        AdminInfo adminInfo;
        PostInfo           newPost = new PostInfo();
        PostInfo           retPost;

        List<Integer>      postFilesIdList = new ArrayList<>();
        int                adminIdx;
        int                cmplxId;
        String             postType;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        try {
            adminIdx = adminInfo.getAdminIdx();
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


        for( Object key : params.keySet() ) {
            logger.debug("key/value>>>> " + key + "/" + params.get(key));
        }

        newPost.setCmplxId( cmplxId );
        newPost.setAdminIdx( adminIdx );
        newPost.setAdminYn( "Y" );

        newPost.setPostType( (String) params.get("postType") );
        newPost.setContent( (String) params.get("content") );

        // 이벤트 기간
        newPost.setEventBeginDttm( (String) params.get("eventBeginDttm") );
        newPost.setEventEndDttm( (String) params.get("eventEndDttm") );
        newPost.setEventPlaceNm( (String) params.get("eventPlaceNm") );

        // 문의 관련
        if( params.get("inquiryYn") != null && params.get("inquiryYn").equals("Y") ) {
            newPost.setInquiryInfo( (String) params.get("inquiryInfo") );
            newPost.setInquiryType( (String) params.get("inquiryType") );
        }
        newPost.setInquiryYn( (String) params.get("inquiryYn") );

        // 참여신청 관련
        if( params.get("rsvYn") != null && params.get("rsvYn").equals("Y") ) {
            newPost.setRsvMaxCnt( Integer.valueOf((String)params.get("rsvMaxCnt")) );
        }
        newPost.setRsvYn( (String) params.get("rsvYn") );

        // 외부공유 기능 설정
        newPost.setShareYn( (String) params.get("shareYn") );
//        postFilesIdList = (List)args.get( "postFiles" );

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

    /**
     * 게시물 상세 정보 가져오기 (ajax)
     *  - Event, Notice, 사용자 Feed 가능
     */
    @GetMapping(
            value = "/{postIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity getPostInJson( HttpServletRequest           request,
                                         @PathVariable("postIdx") int postIdx ) {
        AdminInfo adminInfo;
        PostInfo result;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        try {
            result = postService.getPostById( postIdx, adminInfo.getAdminIdx() );
        } catch( NotFoundException e ) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( e.getMessage()) );
        }

        if( result == null ) {
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( new SimpleErrorInfo("해당 게시물이 존재하지 않습니다." ) );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }


    /**
     * 게시물 내용 변경하기 (ajax)
     *  - Event, Notice 가능, 사용자 Feed 불가능
     */
    @PutMapping(
            value = "/{postIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity updatePost( HttpServletRequest      request,
                                      @PathVariable("postIdx") int postIdx,
                                      @RequestBody PostInfo   post ) {
        AdminInfo adminInfo;
        PostInfo  retPostInfo;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        post.setAdminIdx( adminInfo.getAdminIdx() );
        post.setAdminYn( "Y" );
        post.setPostIdx( postIdx );
        logger.debug(">>>> postFiles " + post.getPostFiles());
        logger.debug(">>>> postFiles.size() " + post.getPostFiles().size());

        if(post.getPostFiles() != null && post.getPostFiles().size() > 0 )
        {
            logger.debug("postFiles:" + post.getPostFiles().size());
            logger.debug("postFiles[0]:" + post.getPostFiles().get(0).getPostIdx() );
        }

        try {
            retPostInfo = postService.updatePost( post );
        } catch ( OperationFailedException e ) {
            return ResponseEntity
                    .status( HttpStatus.CONFLICT )
                    .body( new SimpleErrorInfo( e.getMessage() ) );
        }

        if( retPostInfo == null ) {
            return ResponseEntity.
                    status( HttpStatus.BAD_REQUEST ).
                    body(new SimpleErrorInfo("해당 내용을 수정할 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( retPostInfo );
    }


    /**
     * 게시물 공개 하기 (ajax) - 사용자 앱의 목록에서 표시하지 않습니다.
     *  - Event, Notice, 사용자 Feed 가능
     */
    @PutMapping(
            value = "/{postIdx}/public",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity makePostPublic( HttpServletRequest      request,
                                          @PathVariable("postIdx") int postIdx ) {
        AdminInfo adminInfo;
        PostInfo  postInfo;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        postInfo = postService.makePostPublic( postIdx, adminInfo.getCmplxId(), adminInfo.getAdminIdx() );
        if( postInfo == null ) {
            return ResponseEntity.
                    status( HttpStatus.BAD_REQUEST ).
                    body(new SimpleErrorInfo("해당 게시물을 공개로 변경할 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("해당 게시물을 공개 상태로 변경하였습니다.") );
    }

    /**
     * 게시물 비공개 하기 (ajax) - 사용자 앱의 목록에서 표시하지 않습니다.
     *  - Event, Notice, 사용자 Feed 가능
     */
    @PutMapping(
            value = "/{postIdx}/private",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity makePostPrivate( HttpServletRequest      request,
                                           @PathVariable("postIdx") int postIdx ) {
        AdminInfo adminInfo;
        PostInfo  postInfo;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        postInfo = postService.makePostPrivate( postIdx, adminInfo.getCmplxId(), adminInfo.getAdminIdx() );
        if( postInfo == null ) {
            return ResponseEntity.
                    status( HttpStatus.BAD_REQUEST ).
                    body(new SimpleErrorInfo("해당 게시물을 비공개 처리할 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("해당 게시물을 비공개 처리하였습니다.") );
    }
}
