package com.kolon.comlife.admin.support.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.support.exception.OperationFailedException;
import com.kolon.comlife.admin.support.exception.SupportGeneralException;
import com.kolon.comlife.admin.support.model.SupportCategoryInfo;
import com.kolon.comlife.admin.support.model.TicketFileInfo;
import com.kolon.comlife.admin.support.service.SupportService;
import com.kolon.comlife.admin.support.service.TicketFileStoreService;
import com.kolon.comlife.admin.support.service.TicketService;
import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
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
import sun.security.krb5.internal.Ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


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
    private TicketService ticketService;

    @Autowired
    private TicketFileStoreService storeService;

    @GetMapping( value = "ticketList.do" )
    public ModelAndView ticketList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
           )
    {
        // todo: 관리자 로그인 정보로 부터 cmplxId 가져오기
        PaginateInfo paginateInfo;
        String pageNumStr;
        int pageNum = 1;
        int recCntPerPage = RECORD_COUNT_PER_PAGE;

        pageNumStr = request.getParameter("pageNum");
        if( pageNumStr == null ) {
            pageNum=1;
        } else {
            try {
                pageNum = Integer.parseInt(pageNumStr);
            } catch( NumberFormatException e ) {
                //todo: 잘못된 파라메터 입력 됨
                logger.error("잘못된 pageNum 입력");
                mav.addObject("error", "잘못된 pageNum가 입력되었습니다.");
                return mav;
            }
        }

        paginateInfo = ticketService.getTicketList( 125, pageNum, recCntPerPage );

//        ComplexInfoDetail         complexInfo;
//        List<SupportCategoryInfo> categoryList;
//        String                    categoryListJson;
//        ObjectMapper              mapper = new ObjectMapper();
//
//        try {
//            complexInfo = complexService.getComplexById(categoryInfo.getCmplxId());
//            categoryList = supportService.getCategoryInfoByComplexId(categoryInfo);
//        } catch( SupportGeneralException e ) {
//            return mav.addObject(
//                    "error",
//                    e.getMessage() );
//        }
//
//        try {
//            categoryListJson = mapper.writeValueAsString(categoryList);
//        } catch( Exception e) {
//            return mav.addObject(
//                    "error",
//                    "작업 처리과정에 에러가 발생했습니다. 문제가 지속되면 담당자에게 문의하세요.");
//        }
//
//        mav.addObject("complexInfo", complexInfo );
//        mav.addObject("categoryInfo", categoryInfo); // Parameters
//        // JSON value
//        mav.addObject("categoryList", categoryListJson);
//        mav.addObject("cmplxId", categoryInfo.getCmplxId());

        mav.addObject("paginateInfo", paginateInfo);
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
