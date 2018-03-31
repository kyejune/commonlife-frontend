package com.kolon.comlife.admin.support.service.impl;

import com.kolon.comlife.admin.board.model.BoardInfo;
import com.kolon.comlife.admin.support.model.SupportCategoryInfo;
import com.kolon.comlife.admin.support.model.SupportInfo;
import com.kolon.common.admin.model.FileInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Board Data Access Object
 */
@Repository("supportDAO")
public class SupportDAO {
    @Resource
    private SqlSession sqlSession;

    public List<SupportCategoryInfo> selectCategoryListByComplexId(SupportCategoryInfo categoryInfo) {
        return sqlSession.selectList("Support.selectCategoryListByComplexId", categoryInfo);
    }

    /**
     * 공통게시판 AND 페이스북 리스트 조회
     * @param boardInfo
     * @return
     */
    public List<BoardInfo> selectBoardAndFbList(BoardInfo boardInfo) {
        return sqlSession.selectList("Board.selectBoardAndFbList", boardInfo);
    }

    /**
     * 공통게시판 상세 조회
     * @param boardInfo
     * @return
     */
    public BoardInfo selectBoardDetail(BoardInfo boardInfo) {
        return sqlSession.selectOne("Board.selectBoardDetail", boardInfo);
    }

    /**
     * 공통게시판 다음 상세 조회
     * @param boardInfo
     * @return
     */
    public BoardInfo selectBoardDetailNext(BoardInfo boardInfo) {
        return sqlSession.selectOne("Board.selectBoardDetailNext", boardInfo);
    }

    /**
     * 공통게시판 이전 상세 조회
     * @param boardInfo
     * @return
     */
    public BoardInfo selectBoardDetailPrev(BoardInfo boardInfo) {
        return sqlSession.selectOne("Board.selectBoardDetailPrev", boardInfo);
    }

    /**
     * 공통게시판 AND 페이스북 다음 상세 조회
     * @param boardInfo
     * @return
     */
    public BoardInfo selectBoardAndFbDetailNext(BoardInfo boardInfo) {
        return sqlSession.selectOne("Board.selectBoardAndFbDetailNext", boardInfo);
    }

    /**
     * 공통게시판 AND 페이스북 이전 상세 조회
     * @param boardInfo
     * @return
     */
    public BoardInfo selectBoardAndFbDetailPrev(BoardInfo boardInfo) {
        return sqlSession.selectOne("Board.selectBoardAndFbDetailPrev", boardInfo);
    }


    /**
     * 공통게시판 TOP 1 상세 조회
     * @param boardInfo
     * @return
     */
    public BoardInfo selectBoardDetailTop1(BoardInfo boardInfo) {
        return sqlSession.selectOne("Board.selectBoardDetailTop1", boardInfo);
    }


    /**
     * 공통게시판 이벤트 TOP 3 상세 조회
     * @param boardInfo
     * @return
     */
    public List<BoardInfo> selectBoardEventDetailTop3(BoardInfo boardInfo) {
        return sqlSession.selectList("Board.selectBoardEventDetailTop3", boardInfo);
    }


    /**
     * 공통게시판 오픈 종료 기간 조회
     * @param boardInfo
     * @return
     */
    public List<BoardInfo> selectBoardOpenCloseDtDetail(BoardInfo boardInfo) {
        return sqlSession.selectList("Board.selectBoardOpenCloseDtDetail", boardInfo);
    }

    /**
     * 공통게시판 금일 기준 오픈 종료 기간 조회
     * @param boardInfo
     * @return
     */
    public List<BoardInfo> selectBoardTodayOpenCloseDtDetail(BoardInfo boardInfo) {
        return sqlSession.selectList("Board.selectBoardTodayOpenCloseDtDetail", boardInfo);
    }



    /**
     * 공통게시판 자주하는 질문 GROUP BY  CONTENT TYPE
     * @param boardInfo
     * @return
     */
    public List<BoardInfo> selectBoardContentTypeGroupBy(BoardInfo boardInfo) {
        return sqlSession.selectList("Board.selectBoardContentTypeGroupBy", boardInfo);
    }


    /**
     * 공통게시판 등록
     * @param boardInfo
     * @return
     */
    public int insertBoard(BoardInfo boardInfo) {
        return sqlSession.insert("Board.insertBoard", boardInfo);
    }

    /**
     * 공통게시판 수정
     * @param boardInfo
     * @return
     */
    public int updateBoard(BoardInfo boardInfo) {
        return sqlSession.update("Board.updateBoard", boardInfo);
    }


    /**
     * 공통게시판 READ COUNT 업데이u트
     * @param boardInfo
     * @return
     */
    public int updateReadCntBoard(BoardInfo boardInfo) {
        return sqlSession.update("Board.updateReadCntBoard", boardInfo);
    }


    /**
     * 공통게시판 첨부파일 등록
     * @param fileInfo
     * @return
     */
    public int insertBoardFile(FileInfo fileInfo) {
        return sqlSession.insert("Board.insertBoardFile", fileInfo);
    }


    /**
     * 공통게시판 첨부파일 리스트 조회
     * @param boardInfo
     * @return
     */
    public List<BoardInfo> selectBoardFile(BoardInfo boardInfo) {
        return sqlSession.selectList("Board.selectBoardFile", boardInfo);
    }


    /**
     * 공통게시판 첨부파일 삭제처리
     * @param boardInfo
     * @return
     */
    public int updateDeleteBoardFile(BoardInfo boardInfo) {
        return sqlSession.update("Board.deleteBoardFile", boardInfo);
    }
}
