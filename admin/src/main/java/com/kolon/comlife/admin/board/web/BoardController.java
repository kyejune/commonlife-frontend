package com.kolon.comlife.admin.board.web;

import com.kolon.comlife.admin.board.model.BoardInfo;
import com.kolon.comlife.admin.board.service.BoardService;
import com.kolon.common.admin.pagination.PaginationInfoExtension;
import com.kolon.common.admin.pagination.PaginationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


/**
 * 공통게시판 컨트롤러
 */
@Controller("boardController")
@RequestMapping("/board/*")
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Resource(name = "boardService")
    private BoardService boardService;

    /**
     * 공통게시판 리스트
     * @param request
     * @param response
     * @param mav
     * @return
     */
    @RequestMapping(value = "list.do")
    public ModelAndView boardList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute BoardInfo boardInfo
    ) {
        if(boardInfo.getSearchKeyword1() == null){
            boardInfo.setSearchKeyword1("");
        }

        logger.debug("=========> boardInfo.getSearchKeyword1 : {} ",boardInfo.getSearchKeyword1());

        List<BoardInfo> boardList = boardService.selectBoardList(boardInfo);
        PaginationInfoExtension pagination = PaginationSupport.setPaginationVO(request, boardInfo, "1", boardInfo.getRecordCountPerPage(), 10);
        pagination.setTotalRecordCountVO(boardList);


        mav.addObject("boardType", boardInfo.getBoardType());
        mav.addObject("boardList", boardList);
        mav.addObject("pagination", pagination);

        return mav;
    }


    /**
     * 공통게시판 작성화면
     * @param request
     * @param response
     * @param mav
     * @return
     */
    @RequestMapping(value = "write.do")
    public ModelAndView boardWrite (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute BoardInfo boardInfo
    ) {
//        logger.debug("=========> boardInfo.getMngId : {} ",boardInfo.getMngId());
        logger.debug("=========> boardInfo.getBoardType : {} ",boardInfo.getBoardType());
        logger.debug("=========> boardInfo.getBoardIdx : {} ",boardInfo.getBoardIdx());

        BoardInfo boardDetail = boardService.selectBoardDetail(boardInfo);
        mav.addObject("boardDetail", boardDetail);
        mav.addObject("boardType", boardInfo.getBoardType());
        return mav;
    }


    /**
     * 공통게시판 상세화면
     * @param request
     * @param response
     * @param mav
     * @return
     */
    @RequestMapping(value = "detail.do")
    public ModelAndView boardDetail (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute BoardInfo boardInfo
    ) {
//        logger.debug("=========> boardInfo.getMngId : {} ",boardInfo.getMngId());
        logger.debug("=========> boardInfo.getBoardType : {} ",boardInfo.getBoardType());
        logger.debug("=========> boardInfo.getBoardIdx : {} ",boardInfo.getBoardIdx());

        BoardInfo boardDetail = boardService.selectBoardDetail(boardInfo);
        PaginationInfoExtension pagination = PaginationSupport.setPaginationVO(request, boardInfo, "1", boardInfo.getRecordCountPerPage(), 10);

        mav.addObject("boardDetail", boardDetail);

        return mav;
    }

    /**
     * 공통게시판 처리 (등록/수정/삭제)
     * @param request
     * @param response
     * @param mav
     * @return
     */
    @RequestMapping(value = "proc.*", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView boardProc (
            Principal principal
            , HttpServletRequest request
            , HttpServletResponse response
            , ModelMap model
            , ModelAndView mav
            , @ModelAttribute BoardInfo boardInfo

            //, @ModelAttribute FileInfo fileInfo
    ) {

        int rs = -1;

        logger.debug("=========> BOARD VOC ==========================");
        logger.debug("=========> boardInfo.getHp1 : {} ", boardInfo.getHp1());
        logger.debug("=========> boardInfo.getHp2 : {} ", boardInfo.getHp2());
        logger.debug("=========> boardInfo.getHp3 : {} ", boardInfo.getHp3());
        logger.debug("=========> boardInfo.getEm1 : {} ", boardInfo.getEm1());
        logger.debug("=========> boardInfo.getEm2 : {} ", boardInfo.getEm2());
        logger.debug("=========> boardInfo.getEm2Select : {} ", boardInfo.getEm2Select());

        logger.debug("=========> boardInfo.getWriteNm : {} ", boardInfo.getWriteNm());
        logger.debug("=========> boardInfo.getOrdererDateTime : {} ", boardInfo.getOrdererDateTime());
        logger.debug("=========> boardInfo.getTitle : {} ", boardInfo.getTitle());
        logger.debug("=========> boardInfo.getContent : {} ", boardInfo.getContent());


        if (!"1".equals(boardInfo.getEm2Select())) {
            boardInfo.setEm2(boardInfo.getEm2Select());
        }


        boardInfo.setWriteHp(boardInfo.getHp1() + "-" + boardInfo.getHp2() + "-" + boardInfo.getHp3());
        boardInfo.setWriteEmail(boardInfo.getEm1() + "@" + boardInfo.getEm2());
        boardInfo.setRegUserId(boardInfo.getWriteNm());
        boardInfo.setUpdUserId(boardInfo.getWriteNm());
        boardInfo.setUseYn("Y");


        if ("INS".equals(boardInfo.getMode())) {
            rs = boardService.insertBoard(boardInfo);
        } else if ("UPD".equals(boardInfo.getMode())) {
            rs = boardService.updateBoard(boardInfo);
        } else {
//            rs = boardService.updateBoard(boardInfo);
        }

        logger.info("======> {} Rs : {}", boardInfo.getMode(), rs);

        // 첨부파일
//        boardService.doUploadAtchFiles(request, boardInfo, fileInfo);
        boardService.doUploadAtchFiles(request, boardInfo);

        /*
        if(!"".equals(boardInfo.getFileNm()) && !"DEL".equals(boardInfo.getMode())){
            if(boardInfo.getMode().equals("UPD")){
                boardService.updateDeleteBoardFile(boardInfo);
            }
        }
        */

        model.addAttribute("result", rs > 0);

        return new ModelAndView("jsonView", model);
    }
}
