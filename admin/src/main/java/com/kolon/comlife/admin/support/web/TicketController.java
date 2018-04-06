package com.kolon.comlife.admin.support.web;

import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.support.exception.OperationFailedException;
import com.kolon.comlife.admin.support.model.TicketFileInfo;
import com.kolon.comlife.admin.support.model.TicketInfo;
import com.kolon.comlife.admin.support.service.SupportService;
import com.kolon.comlife.admin.support.service.TicketFileStoreService;
import com.kolon.comlife.admin.support.service.TicketService;
import com.kolon.comlife.admin.users.model.UserExtInfo;
import com.kolon.comlife.admin.users.model.UserInfo;
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

        int recCntPerPage = RECORD_COUNT_PER_PAGE;
        int complexId = 125; // todo: 관리자 로그인 정보로 부터 cmplxId 가져오기

        complexInfo = complexService.getComplexById( complexId );
        paginateInfo = ticketService.getTicketList( complexId, pageNum, recCntPerPage );

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

        if(pageNum < 1) {
            pageNum = 1;
        }

        int complexId = 125; // todo: 관리자 로그인 정보로 부터 cmplxId 가져오기

        // todo: 현장 관리자가 확인 권한 있는지 체크

        complexInfo = complexService.getComplexById( complexId );
        if( complexInfo == null ) {
            return mav.addObject("error", "해당하는 현장 정보가 없습니다.");
        }

        ticketInfo = ticketService.getTicket( complexId, tktIdx );
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
        

        return null;
    }

//    @PostMapping(
//            value = "procIns.do",
//            produces = MediaType.APPLICATION_JSON_VALUE )
//    public ResponseEntity procIns (
//            HttpServletRequest request
//            , HttpServletResponse response
//            , ModelAndView mav
//            , HttpSession session
//            , @RequestBody List<Map<String, Object>> dispOrderParam
//            , @RequestParam("cmplxId") int cmplxId
//    ) {
//        List<SupportCategoryInfo> dispOrderList;
//
//        if( cmplxId < 1 ) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(new SimpleErrorInfo("잘못된 현장아이디(cmplxId)를 입력했습니다. 다시 확인하세요."));
//        }
//
//        if( dispOrderParam == null ) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(new SimpleErrorInfo("업데이트 할 내용이 없습니다. 전송 내용을 다시 확인하세요. "));
//        }
//
//
//        dispOrderList = new ArrayList<>();
//        for( Map e: dispOrderParam ) {
//            SupportCategoryInfo cateInfo = new SupportCategoryInfo();
//
//            cateInfo.setCateIdx( Integer.parseInt( (String) e.get("cateIdx") ) );
//            cateInfo.setCmplxId( cmplxId );
//            cateInfo.setDispOrder( ((Integer)e.get("dispOrder")).intValue() );
//            cateInfo.setDelYn( (String) e.get("delYn") );
//
//            dispOrderList.add(cateInfo);
//
//
//            logger.debug(">>> " + cateInfo.getCateIdx() );
//            logger.debug(">>> " + cateInfo.getDispOrder() );
//            logger.debug(">>> " + cateInfo.getDelYn() );
//            logger.debug("----");
//        }
//
//        try {
//            supportService.updateCategoryInfoDisplayByComplexId( dispOrderList );
//        } catch( SupportGeneralException e ) {
//            logger.error( e.getMessage() );
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body(new SimpleMsgInfo("업데이트를 완료했습니다."));
//    }
//
//

//    /**
//     * 공통게시판 작성화면
//     * @param request
//     * @param response
//     * @param mav
//     * @return
//     */
//    @RequestMapping(value = "write.do")
//    public ModelAndView boardWrite (
//            HttpServletRequest request
//            , HttpServletResponse response
//            , ModelAndView mav
//            , HttpSession session
//            , @ModelAttribute BoardInfo boardInfo
//    ) {
////        logger.debug("=========> boardInfo.getMngId : {} ",boardInfo.getMngId());
//        logger.debug("=========> boardInfo.getBoardType : {} ",boardInfo.getBoardType());
//        logger.debug("=========> boardInfo.getBoardIdx : {} ",boardInfo.getBoardIdx());
//
//        BoardInfo boardDetail = supportService.selectBoardDetail(boardInfo);
//        mav.addObject("boardDetail", boardDetail);
//        mav.addObject("boardType", boardInfo.getBoardType());
//        return mav;
//    }
//
//
//    /**
//     * 공통게시판 상세화면
//     * @param request
//     * @param response
//     * @param mav
//     * @return
//     */
//    @RequestMapping(value = "detail.do")
//    public ModelAndView boardDetail (
//            HttpServletRequest request
//            , HttpServletResponse response
//            , ModelAndView mav
//            , HttpSession session
//            , @ModelAttribute BoardInfo boardInfo
//    ) {
////        logger.debug("=========> boardInfo.getMngId : {} ",boardInfo.getMngId());
//        logger.debug("=========> boardInfo.getBoardType : {} ",boardInfo.getBoardType());
//        logger.debug("=========> boardInfo.getBoardIdx : {} ",boardInfo.getBoardIdx());
//
//        BoardInfo boardDetail = supportService.selectBoardDetail(boardInfo);
//        PaginationInfoExtension pagination = PaginationSupport.setPaginationVO(request, boardInfo, "1", boardInfo.getRecordCountPerPage(), 10);
//
//        mav.addObject("boardDetail", boardDetail);
//
//        return mav;
//    }
//
//    /**
//     * 공통게시판 처리 (등록/수정/삭제)
//     * @param request
//     * @param response
//     * @param mav
//     * @return
//     */
//    @RequestMapping(value = "proc.*", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView boardProc (
//            Principal principal
//            , HttpServletRequest request
//            , HttpServletResponse response
//            , ModelMap model
//            , ModelAndView mav
//            , @ModelAttribute BoardInfo boardInfo
//
//            //, @ModelAttribute FileInfo fileInfo
//    ) {
//
//        int rs = -1;
//
//        logger.debug("=========> BOARD VOC ==========================");
//        logger.debug("=========> boardInfo.getHp1 : {} ", boardInfo.getHp1());
//        logger.debug("=========> boardInfo.getHp2 : {} ", boardInfo.getHp2());
//        logger.debug("=========> boardInfo.getHp3 : {} ", boardInfo.getHp3());
//        logger.debug("=========> boardInfo.getEm1 : {} ", boardInfo.getEm1());
//        logger.debug("=========> boardInfo.getEm2 : {} ", boardInfo.getEm2());
//        logger.debug("=========> boardInfo.getEm2Select : {} ", boardInfo.getEm2Select());
//
//        logger.debug("=========> boardInfo.getWriteNm : {} ", boardInfo.getWriteNm());
//        logger.debug("=========> boardInfo.getOrdererDateTime : {} ", boardInfo.getOrdererDateTime());
//        logger.debug("=========> boardInfo.getTitle : {} ", boardInfo.getTitle());
//        logger.debug("=========> boardInfo.getContent : {} ", boardInfo.getContent());
//
//
//        if (!"1".equals(boardInfo.getEm2Select())) {
//            boardInfo.setEm2(boardInfo.getEm2Select());
//        }
//
//
//        boardInfo.setWriteHp(boardInfo.getHp1() + "-" + boardInfo.getHp2() + "-" + boardInfo.getHp3());
//        boardInfo.setWriteEmail(boardInfo.getEm1() + "@" + boardInfo.getEm2());
//        boardInfo.setRegUserId(boardInfo.getWriteNm());
//        boardInfo.setUpdUserId(boardInfo.getWriteNm());
//        boardInfo.setUseYn("Y");
//
//
//        if ("INS".equals(boardInfo.getMode())) {
//            rs = supportService.insertBoard(boardInfo);
//        } else if ("UPD".equals(boardInfo.getMode())) {
//            rs = supportService.updateBoard(boardInfo);
//        } else {
////            rs = supportService.updateBoard(boardInfo);
//        }
//
//        logger.info("======> {} Rs : {}", boardInfo.getMode(), rs);
//
//        // 첨부파일
////        supportService.doUploadAtchFiles(request, boardInfo, fileInfo);
//        supportService.doUploadAtchFiles(request, boardInfo);
//
//        /*
//        if(!"".equals(boardInfo.getFileNm()) && !"DEL".equals(boardInfo.getMode())){
//            if(boardInfo.getMode().equals("UPD")){
//                supportService.updateDeleteBoardFile(boardInfo);
//            }
//        }
//        */
//
//        model.addAttribute("result", rs > 0);
//
//        return new ModelAndView("jsonView", model);
//    }
}
