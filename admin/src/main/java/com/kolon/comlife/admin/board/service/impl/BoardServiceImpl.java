package com.kolon.comlife.admin.board.service.impl;

import com.kolon.comlife.admin.board.model.BoardInfo;
import com.kolon.comlife.admin.board.service.BoardService;
import com.kolon.common.admin.model.FileInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 공통게시판 서비스 구현체
 * @author nacsde
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-31   조신득          최초 생성
 * </pre>
 */
@Service("boardService")
public class BoardServiceImpl implements BoardService {
    public static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

    @Resource(name = "boardDAO")
    private BoardDAO boardDAO;

    @Override
    public List<BoardInfo> selectBoardList(BoardInfo boardInfo) {
        return boardDAO.selectBoardList(boardInfo);
    }

    @Override
    public List<BoardInfo> selectBoardAndFbList(BoardInfo boardInfo) {
        return boardDAO.selectBoardAndFbList(boardInfo);
    }

    @Override
    public BoardInfo selectBoardDetail(BoardInfo boardInfo) {
        return boardDAO.selectBoardDetail(boardInfo);
    }

    @Override
    public BoardInfo selectBoardDetailNext(BoardInfo boardInfo) {
        return boardDAO.selectBoardDetailNext(boardInfo);
    }

    @Override
    public BoardInfo selectBoardDetailPrev(BoardInfo boardInfo) {
        return boardDAO.selectBoardDetailPrev(boardInfo);
    }

    @Override
    public BoardInfo selectBoardAndFbDetailNext(BoardInfo boardInfo) {
        return boardDAO.selectBoardAndFbDetailNext(boardInfo);
    }

    @Override
    public BoardInfo selectBoardAndFbDetailPrev(BoardInfo boardInfo) {
        return boardDAO.selectBoardAndFbDetailPrev(boardInfo);
    }


    @Override
    public BoardInfo selectBoardDetailTop1(BoardInfo boardInfo) {
        return boardDAO.selectBoardDetailTop1(boardInfo);
    }

    @Override
    public List<BoardInfo> selectBoardEventDetailTop3(BoardInfo boardInfo) {
        return boardDAO.selectBoardEventDetailTop3(boardInfo);
    }

    @Override
    public List<BoardInfo> selectBoardOpenCloseDtDetail(BoardInfo boardInfo){
        return boardDAO.selectBoardOpenCloseDtDetail(boardInfo);
    }

    @Override
    public List<BoardInfo> selectBoardTodayOpenCloseDtDetail(BoardInfo boardInfo) {
        return boardDAO.selectBoardTodayOpenCloseDtDetail(boardInfo);
    }

    @Override
    public List<BoardInfo> selectBoardContentTypeGroupBy(BoardInfo boardInfo){
        return boardDAO.selectBoardContentTypeGroupBy(boardInfo);
    }



    @Override
    public int insertBoard(BoardInfo boardInfo) {
        return boardDAO.insertBoard(boardInfo);
    }

    @Override
    public int updateBoard(BoardInfo boardInfo) {
        return boardDAO.updateBoard(boardInfo);
    }

    @Override
    public int updateReadCntBoard(BoardInfo boardInfo) {
        return boardDAO.updateReadCntBoard(boardInfo);
    }

    @Override
    public int insertBoardFile(FileInfo fileInfo) {
        return boardDAO.insertBoardFile(fileInfo);
    }

    @Override
    public List<BoardInfo> selectBoardFile(BoardInfo boardInfo) {
        return boardDAO.selectBoardFile(boardInfo);
    }

    @Override
    public int updateDeleteBoardFile(BoardInfo boardInfo) {
        return boardDAO.updateDeleteBoardFile(boardInfo);
    }

    @Override
//    public int doUploadAtchFiles(HttpServletRequest request, BoardInfo boardInfo, FileInfo fileInfo) {
    public int doUploadAtchFiles(HttpServletRequest request, BoardInfo boardInfo) {
        int insertCount = 0;

        List<FileInfo> atchFileList = boardInfo.getAtchFileList();

//        if (CollectionUtils.isNotEmpty(atchFileList)){
//            logger.debug("################### >> atchFileList.size() : {}", atchFileList.size());
//        }else{
//            logger.debug("################### >> atchFileList.size() : XXXXXXXXXXXXXXXXXXX");
//        }

        try {
            if (CollectionUtils.isNotEmpty(atchFileList)){
                for ( FileInfo vo : atchFileList ){
                    BoardInfo vvo = (BoardInfo)vo;
                    vvo.setUseYn("Y");
                    vvo.setBoardIdx(boardInfo.getBoardIdx());
                    insertCount = insertCount + boardDAO.insertBoardFile(vvo);
                }
            }

        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("파일 업로드 에러");
        }
        return insertCount;

        /*
        int insertCount = 0;

        logger.debug("################### boardInfo.getFileNm() >> {}", boardInfo.getFileNm());
        logger.debug("################### boardInfo.getAtchFileList() >> {}", boardInfo.getAtchFileList().size());


        List<FileInfo> atchFileList = boardInfo.getAtchFileList();



        if (CollectionUtils.isNotEmpty(atchFileList)){
            logger.debug("################### >> atchFileList.size() : {}", atchFileList.size());
        }else{
            logger.debug("################### >> atchFileList.size() : XXXXXXXXXXXXXXXXXXX");
        }

        try {
            if (CollectionUtils.isNotEmpty(atchFileList)){
                for ( FileInfo vo : atchFileList ){
                    vo.setUseYn("Y");
                    vo.setBoardIdx(boardInfo.getBoardIdx());
                    insertCount = insertCount + boardDAO.insertBoardFile(vo);
                }
            }

        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("파일 업로드 에러");
        }
        return insertCount;
        */
    }

}
