package com.kolon.comlife.admin.support.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.comlife.admin.support.exception.SupportGeneralException;
import com.kolon.comlife.admin.support.model.SupportCategoryInfo;
import com.kolon.comlife.admin.support.service.SupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller("supportController")
@RequestMapping("admin/support/*")
public class SupportController {

    private static final Logger logger = LoggerFactory.getLogger(SupportController.class);

    @Resource(name = "supportService")
    private SupportService supportService;

    @RequestMapping(value = "categoryList.do")
    public ModelAndView categoryList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute("categoryInfo") SupportCategoryInfo categoryInfo
    ) {
        ObjectMapper mapper = new ObjectMapper();
        String categoryListJson;
        List<SupportCategoryInfo> categoryList;

        try {
            categoryList = supportService.getCategoryInfoByComplexId(categoryInfo);
        } catch( SupportGeneralException e ) {
            return mav.addObject(
                    "error",
                    e.getMessage() );
        }

        try {
            categoryListJson = mapper.writeValueAsString(categoryList);
        } catch( Exception e) {
            return mav.addObject(
                    "error",
                    "작업 처리과정에 에러가 발생했습니다. 문제가 지속되면 담당자에게 문의하세요.");
        }

        mav.addObject("categoryInfo", categoryInfo); // Parameters
        mav.addObject("categoryList", categoryListJson);
        mav.addObject("cmplxId", categoryInfo.getCmplxId());

        return mav;
    }


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
