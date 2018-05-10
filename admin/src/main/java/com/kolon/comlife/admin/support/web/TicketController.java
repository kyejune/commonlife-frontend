package com.kolon.comlife.admin.support.web;

import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.manager.model.AdminInfo;
import com.kolon.comlife.admin.support.exception.OperationFailedException;
import com.kolon.comlife.admin.support.model.TicketFileInfo;
import com.kolon.comlife.admin.support.model.TicketInfo;
import com.kolon.comlife.admin.support.service.SupportService;
import com.kolon.comlife.admin.support.service.TicketFileStoreService;
import com.kolon.comlife.admin.support.service.TicketService;
import com.kolon.comlife.admin.users.model.UserExtInfo;
import com.kolon.comlife.admin.users.service.UserService;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.paginate.PaginateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller("ticketController")
@RequestMapping("admin/support/ticket/*")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
    private static final int  RECORD_COUNT_PER_PAGE = 10;

    @Autowired
    private ComplexService complexService;

    @Autowired
    private SupportService supportService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketFileStoreService storeService;


    private int getPageNumParam(HttpServletRequest request) throws NumberFormatException {
        String pageNumStr;

        pageNumStr = request.getParameter("pageNum");
        if( pageNumStr == null ) {
            return 1;
        }

        return Integer.parseInt( pageNumStr );
    }

    @GetMapping( value = "ticketList.do" )
    public ModelAndView ticketList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam(required=false, defaultValue = "1") int pageNum
           )
    {
        PaginateInfo        paginateInfo;
        ComplexInfoDetail   complexInfo;
        AdminInfo           adminInfo;
        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        int recCntPerPage = RECORD_COUNT_PER_PAGE;
        int complexId = adminInfo.getCmplxId();

        complexInfo = complexService.getComplexById( complexId );
        paginateInfo = ticketService.getTicketList( complexId, pageNum, recCntPerPage );

        mav.addObject("adminInfo", adminInfo);
        mav.addObject("complexInfo", complexInfo);
        mav.addObject("paginateInfo", paginateInfo);
        mav.addObject("ticketList", paginateInfo.getData());

        return mav;
    }

    // 편집화면
    @GetMapping( value = "ticketView.do" )
    public ModelAndView ticketView (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam                 int tktIdx
            , @RequestParam(required=false, defaultValue = "1") int pageNum
    )
    {
        TicketInfo        ticketInfo;
        TicketFileInfo    ticketFileInfo = null;
        ComplexInfoDetail complexInfo;
        UserExtInfo       userExtInfo;
        String            ticketFileImgUrl = null;
        AdminInfo         adminInfo;

        adminInfo = (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        if(pageNum < 1) {
            pageNum = 1;
        }

        complexInfo = complexService.getComplexById( adminInfo.getCmplxId() );
        if( complexInfo == null ) {
            return mav.addObject("error", "해당하는 현장 정보가 없습니다.");
        }

        ticketInfo = ticketService.getTicket( adminInfo.getCmplxId(), tktIdx );
        if( ticketInfo == null ) {
            return mav.addObject("error", "해당하는 티켓 정보가 없습니다.");
        }
        if( ticketInfo.getTicketFiles() != null && ticketInfo.getTicketFiles().size() > 0 ) {
            ticketFileInfo = ticketInfo.getTicketFiles().get(0);

            logger.debug(">>> " + ticketFileInfo);
            if( ticketFileInfo != null ) {
                // image 가져오기 경로 ex: {{ADMIN_HOST}}/admin/support/ticket/ticketFiles/1
                ticketFileImgUrl = "/admin/support/ticket/ticketFiles/" + ticketFileInfo.getTktFileIdx() + "/s";
                logger.debug(">>> imgUrl: " + ticketFileImgUrl);
            }
        }

        userExtInfo = userService.getUserExtInfoById( ticketInfo.getUsrId() );
        if( userExtInfo == null ) {
            return mav.addObject("error", "티켓의 사용자 정보가 없습니다.");
        }

        mav.addObject("adminInfo", adminInfo);
        mav.addObject("userExtInfo", userExtInfo);
        mav.addObject("complexInfo", complexInfo);
        mav.addObject("ticketInfo", ticketInfo);
        mav.addObject("ticketFileImgUrl", ticketFileImgUrl);
        mav.addObject("pageNum", pageNum );

        return mav;
    }


    //////////////////////// TICKET FILE METHODS ////////////////////////////////

    // Response Header 생성. 현재는 MIME Type 을 지정하는 역할만을 수행한다.
    private HttpHeaders getFileTypeHeaders( String type ) {
        final HttpHeaders headers = new HttpHeaders();
        switch ( type ) {
            case "image/png" :
                headers.setContentType(MediaType.IMAGE_PNG);
                break;
            case "image/gif" :
                headers.setContentType(MediaType.IMAGE_GIF);
                break;
            case "image/jpg" :
            case "image/jpeg" :
                headers.setContentType(MediaType.IMAGE_JPEG);
                break;
            default :
        }

        return headers;
    }

    @GetMapping(
            value = "ticketFiles/{id}"
    )
    public ResponseEntity getTicketFile(@PathVariable( "id" ) int id ) {
        byte[]       outputFile;
        HttpHeaders headers;
        TicketFileInfo ticketFileInfo;

        ticketFileInfo = ticketService.getTicketFile( id );
        if( ticketFileInfo == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new SimpleErrorInfo("해당하는 이미지가 없습니다."));
        }
        logger.debug(">>>> ticketFileInfo" + ticketFileInfo);
        logger.debug(">>>> postIdx:" + ticketFileInfo.getTktIdx());
        logger.debug(">>>> filePath:" + ticketFileInfo.getFilePath());
        logger.debug(">>>> postFileIdx:" + ticketFileInfo.getTktFileIdx());

        try {
            outputFile = storeService.getTicketFile( ticketFileInfo );
        } catch( OperationFailedException e ) {

            return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( null );
        }

        // Set Header
        headers = getFileTypeHeaders( ticketFileInfo.getMimeType() );

        return new ResponseEntity(outputFile, headers, HttpStatus.OK);
    }


    @GetMapping(
            value = "ticketFiles/{id}/{size}"
    )
    public ResponseEntity getTicketFileBySize( @PathVariable( "id" ) int id, @PathVariable( "size" ) String size ) {
        byte[]       outputFile;
        HttpHeaders  headers;
        TicketFileInfo ticketFileInfo;

        ticketFileInfo = ticketService.getTicketFile( id );
        if( ticketFileInfo == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new SimpleErrorInfo("해당하는 이미지가 없습니다."));
        }
        logger.debug(">>>> postFileInfo" + ticketFileInfo);
        logger.debug(">>>> postIdx:" + ticketFileInfo.getTktIdx());
        logger.debug(">>>> filePath:" + ticketFileInfo.getFilePath());
        logger.debug(">>>> postFileIdx:" + ticketFileInfo.getTktFileIdx());

        try {
            outputFile = storeService.getTicketFileBySize( ticketFileInfo, size );
        } catch( OperationFailedException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status( HttpStatus.NOT_FOUND )
                    .body( new SimpleErrorInfo(e.getMessage()) );
        }

        // Set Header
        headers = getFileTypeHeaders( ticketFileInfo.getMimeType() );

        return new ResponseEntity(outputFile, headers, HttpStatus.OK);
    }


    @PostMapping(
            value = "updateComment",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateComment (
              HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session

    ) {
        // todo: 구현 안되어 있음 구현해야 함

        return null;
    }
}
