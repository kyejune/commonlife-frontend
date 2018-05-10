package com.kolon.comlife.admin.info.web;

import com.kolon.comlife.admin.info.exception.DataNotFoundException;
import com.kolon.comlife.admin.info.exception.OperationFailedException;
import com.kolon.comlife.admin.info.model.InfoItem;
import com.kolon.comlife.admin.info.service.InfoService;
import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.common.paginate.PaginateInfo;
import com.kolon.common.admin.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("admin/info/")
public class InfoItemController {
    private static final Logger logger = LoggerFactory.getLogger(InfoItemController.class);

    private static final int LIMIT_COUNT = 10;  // 한 번에 로딩할 게시글 갯수

    private static final String INFO_ITEM_TYPE_GUIDE     = "guide";
    private static final String INFO_ITEM_TYPE_BENEFITS  = "benefits";

    @Autowired
    private InfoService infoService;


    /////// 현장 관리자 용 ///////

    private ModelAndView getListInternal( ModelAndView mav, String itemType, int pageNum ) {
        AdminInfo            adminInfo;
        PaginateInfo         paginateInfo;
        Map<String, Object>  postParams = new HashMap<>();
        List<InfoItem>       itemList;
        int                  cmplxId;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        cmplxId = adminInfo.getCmplxId();

        postParams.put( "itemType", itemType ); // NEWS == NOTICE
        postParams.put( "limit",    LIMIT_COUNT);
        postParams.put( "pageNum",  Integer.valueOf(pageNum) );
        postParams.put( "cmplxId",  Integer.valueOf( cmplxId ) );
        postParams.put( "adminIdx", adminInfo.getAdminIdx() );

        try {
            if( INFO_ITEM_TYPE_GUIDE.equals( itemType ) ) {
                paginateInfo = infoService.getInfoGuideItemList( cmplxId, pageNum, LIMIT_COUNT );
            } else if( INFO_ITEM_TYPE_BENEFITS.equals( itemType ) ) {
                paginateInfo = infoService.getInfoBenefitsItemList( cmplxId, pageNum, LIMIT_COUNT );
            } else {
                return mav.addObject("error", "잘못된 파라미터 값이 입력 되었습니다. itemType: " + itemType);
            }
        } catch( Exception e ) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return mav.addObject("error",
                    "목록 가져오기를 실패했습니다. 문제가 지속되면 담당자에게 문의하세요." );
        }

        itemList = paginateInfo.getData();

        logger.debug(">>>> paginateInfo.getTotalRecordCount:" + paginateInfo.getTotalRecordCount() );
        logger.debug(">>>> paginateInfo.getPageSize:" + paginateInfo.getPageSize());
        logger.debug(">>>> paginateInfo.getTotalPageCount:" + paginateInfo.getTotalPageCount() );

        mav.addObject("adminInfo",    adminInfo);
        mav.addObject("itemType",     itemType);
        mav.addObject("paginateInfo", paginateInfo);
        mav.addObject("itemList",     itemList);
        mav.addObject("pageNum",      pageNum);

        mav.setViewName("admin/info/itemList");

        return mav;
    }

    @GetMapping( value = "guide/list.*" )
    public ModelAndView getGuideList( ModelAndView        mav,
                                      @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, INFO_ITEM_TYPE_GUIDE, pageNum );
    }

    @GetMapping( value = "benefits/list.*" )
    public ModelAndView getBenefitsList( ModelAndView        mav,
                                         @RequestParam(required=false, defaultValue = "1") int pageNum ) {

        return this.getListInternal( mav, INFO_ITEM_TYPE_BENEFITS, pageNum );
    }


    /**
     * Living Guide, Benefits 공통 생성 화면 (Internal)
     */
    private ModelAndView newItemInternal( ModelAndView mav, String itemType ) {
        AdminInfo     adminInfo;
        InfoItem      itemInfo = new InfoItem();

        mav.setViewName("admin/info/itemEdit");
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        mav.addObject("adminInfo", adminInfo);
        mav.addObject("itemInfo", itemInfo );
        mav.addObject("itemType", itemType.toLowerCase() );
        mav.addObject("itemUpdateYn", "N" ); // N == 새로운 글 생성

        return mav;
    }

    /**
     * Living Guide 생성 화면
     */
    @GetMapping( value = "newGuide.*" )
    public ModelAndView newGuide( HttpServletRequest  request,
                                  HttpServletResponse response,
                                  ModelAndView        mav,
                                  HttpSession         session) {
        return newItemInternal( mav, INFO_ITEM_TYPE_GUIDE );
    }

    /**
     * Benefits 생성 화면
     */
    @GetMapping( value = "newBenefits.*" )
    public ModelAndView newBenefits( HttpServletRequest  request,
                                    HttpServletResponse response,
                                    ModelAndView        mav,
                                    HttpSession         session) {
        return newItemInternal( mav, INFO_ITEM_TYPE_BENEFITS );
    }


    /**
     * Benefits, Living Guide 공통 편집 화면
     */
    @GetMapping( value = "{itemType}/edit.*" )
    public ModelAndView editPost( HttpServletRequest  request,
                                  HttpServletResponse response,
                                  ModelAndView        mav,
                                  HttpSession         session,
                                  @PathVariable("itemType") String itemType,
                                  @RequestParam( name = "itemIdx", required=false, defaultValue = "-1") int itemIdx ) {
        AdminInfo     adminInfo;
        InfoItem      infoItem = null;

        mav.setViewName("/admin/info/itemEdit");

        logger.debug(">>> itemIdx: " + itemIdx );
        if( itemIdx < 0 ) {
            mav.addObject("error", "해당 게시물의 정보를 가져올 수 없습니다." );
            return mav;
        }

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        try {
            if( INFO_ITEM_TYPE_GUIDE.equals( itemType ) ) {
                infoItem = infoService.getInfoGuideItem( adminInfo.getCmplxId(), itemIdx );
            } else if( INFO_ITEM_TYPE_BENEFITS.equals( itemType ) ) {
                infoItem = infoService.getInfoBenefitsItem( adminInfo.getCmplxId(), itemIdx );
            } else  {
                mav.addObject("error", "잚못된 경로입니다.");
                return mav;
            }

        } catch( Exception e ) {
            mav.addObject("error", "해당 피드 정보를 가져올 수 없습니다. " + e.getMessage());
            return mav;
        }

        mav.addObject("adminInfo", adminInfo);
        mav.addObject("itemIdx", itemIdx);
        mav.addObject("itemUpdateYn", "Y" );
        mav.addObject("itemType", itemType );
        mav.addObject("itemInfo", infoItem );

        return mav;
    }


    ////// AJAX CALL //////

    private String setString( String value, String defaultValue ) {
        return value == value ? defaultValue : value;
    }

    /**
     * 새로운 게시물 작성 (ajax)
     *  - Living Guide, item
     */
    @PostMapping(
            value = "/proc.*",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity setInfoItem( HttpServletRequest request,
                                       @RequestBody Map   params ) throws Exception {
        AdminInfo adminInfo;
        InfoItem newItem = new InfoItem();
        InfoItem retItem;

//        List<Integer>      postFilesIdList = new ArrayList<>();
        int                adminIdx;
        int                cmplxId;
        String             itemType;

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

        newItem.setCmplxId( cmplxId );
        newItem.setAdminIdx( adminIdx );
//        newItem.setAdminYn( "Y" );

        newItem.setCateId( (String) params.get("itemType") );
        newItem.setDesc( (String) params.get("desc") );
        newItem.setItemNm( (String) params.get("itemNm") );

        try {
            retItem = infoService.setItemWithImage( newItem, null, adminInfo.getAdminIdx() );
        } catch( Exception e ){
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST)
                    .body( new SimpleErrorInfo("게시물 작성이 실패하였습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( retItem );
    }

    /**
     * 게시물 상세 정보 가져오기 (ajax)
     *  - Living Guide, Benefits
     */
    @GetMapping(
            value = "{itemType}/items/{itemIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity getLivingGuideItemInJson( HttpServletRequest           request,
                                                    @PathVariable("itemType") String itemType,
                                                    @PathVariable("itemIdx") int  itemIdx ) {
        AdminInfo adminInfo;
        InfoItem  result;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        try {
            if( INFO_ITEM_TYPE_GUIDE.equals( itemType )) {
                result = infoService.getInfoGuideItem( adminInfo.getCmplxId(), itemIdx );
            } else if( INFO_ITEM_TYPE_BENEFITS.equals( itemType )) {
                result = infoService.getInfoBenefitsItem( adminInfo.getCmplxId(), itemIdx );
            } else {
                return ResponseEntity
                        .status( HttpStatus.BAD_REQUEST )
                        .body( new SimpleErrorInfo( "잘못된 type 입니다." ) );
            }
        } catch( DataNotFoundException e ) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( e.getMessage()) );
        } catch( OperationFailedException e ) {
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
     *  - Living Guide, Benefit 가능
     */
    @PutMapping(
            value = "{itemType}/items/{itemIdx}",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity updateItem( HttpServletRequest      request,
                                      @PathVariable("itemType") String itemType,
                                      @PathVariable("itemIdx") int itemIdx,
                                      @RequestBody InfoItem   item ) {
        AdminInfo adminInfo;
        InfoItem  retInfoItem;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        item.setAdminIdx( adminInfo.getAdminIdx() );
        item.setItemIdx( itemIdx );
        item.setCmplxId( adminInfo.getCmplxId() );
        item.setCateId( itemType.toLowerCase() );
//        logger.debug(">>>> postFiles " + item.getPostFiles());
//        logger.debug(">>>> postFiles.size() " + post.getPostFiles().size());
//
//        if(post.getPostFiles() != null && post.getPostFiles().size() > 0 )
//        {
//            logger.debug("postFiles:" + post.getPostFiles().size());
//            logger.debug("postFiles[0]:" + post.getPostFiles().get(0).getPostIdx() );
//        }

        try {
            retInfoItem = infoService.updateItem( item );
        } catch ( OperationFailedException e ) {
            return ResponseEntity
                    .status( HttpStatus.CONFLICT )
                    .body( new SimpleErrorInfo( e.getMessage() ) );
        }

        if( retInfoItem == null ) {
            return ResponseEntity.
                    status( HttpStatus.BAD_REQUEST ).
                    body(new SimpleErrorInfo("해당 내용을 수정할 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( retInfoItem );
    }


    /**
     * 게시물 공개 하기 (ajax) - 사용자 앱의 목록에서 표시하지 않습니다.
     *  - Event, Notice, 사용자 Feed 가능
     */
    @PutMapping(
            value = "{itemType}/items/{itemIdx}/public",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity makeItemPublic( HttpServletRequest               request,
                                          @PathVariable("itemType") String itemType,
                                          @PathVariable("itemIdx")  int    itemIdx ) {
        AdminInfo adminInfo;
        InfoItem  infoItem;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        infoItem = infoService.makeItemPublic( itemIdx, adminInfo.getCmplxId(), adminInfo.getAdminIdx() );
        if( infoItem == null ) {
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
            value = "{itemType}/items/{itemIdx}/private",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity makeItemPrivate( HttpServletRequest               request,
                                           @PathVariable("itemType") String itemType,
                                           @PathVariable("itemIdx")  int    itemIdx) {
        AdminInfo adminInfo;
        InfoItem  infoItem;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        logger.debug(">>> currUser>CmplxId: "  + adminInfo.getCmplxId());
        logger.debug(">>> currUser>AdminIdx: " + adminInfo.getAdminIdx());
        logger.debug(">>> currUser>AdminId: "  + adminInfo.getAdminId());

        infoItem = infoService.makeItemPrivate( itemIdx, adminInfo.getCmplxId(), adminInfo.getAdminIdx() );
        if( infoItem == null ) {
            return ResponseEntity.
                    status( HttpStatus.BAD_REQUEST ).
                    body(new SimpleErrorInfo("해당 게시물을 비공개 처리할 수 없습니다.") );
        }

        return ResponseEntity.status( HttpStatus.OK ).body( new SimpleMsgInfo("해당 게시물을 비공개 처리하였습니다.") );
    }
}
