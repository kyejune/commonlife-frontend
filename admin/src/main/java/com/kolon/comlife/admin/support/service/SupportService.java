package com.kolon.comlife.admin.support.service;

import com.kolon.comlife.admin.board.model.BoardInfo;
import com.kolon.comlife.admin.support.exception.SupportGeneralException;
import com.kolon.comlife.admin.support.model.SupportCategoryInfo;
import com.kolon.common.admin.model.FileInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface SupportService {

    // 카테고리 가져오기
    List<SupportCategoryInfo> getCategoryInfoByComplexId(SupportCategoryInfo categoryInfo ) throws SupportGeneralException;

//    /**
//     * 공통게시판 리스트 조회
//     * @param boardInfo
//     * @return
//     */
//    List<BoardInfo> selectBoardList(BoardInfo boardInfo);

    /**
     * 공통게시판 AND 페이스북 리스트 조회
     * @param boardInfo
     * @return
     */
    List<BoardInfo> selectBoardAndFbList(BoardInfo boardInfo);

    /**
     * 공통게시판 상세 조회
     * @param boardInfo
     * @return
     */
    BoardInfo selectBoardDetail(BoardInfo boardInfo);

    /**
     * 공통게시판 다음 상세 조회
     * @param boardInfo
     * @return
     */
    BoardInfo selectBoardDetailNext(BoardInfo boardInfo);

    /**
     * 공통게시판 이전 상세 조회
     * @param boardInfo
     * @return
     */
    BoardInfo selectBoardDetailPrev(BoardInfo boardInfo);

    /**
     * 공통게시판 AND 페이스북 다음 상세 조회
     * @param boardInfo
     * @return
     */
    BoardInfo selectBoardAndFbDetailNext(BoardInfo boardInfo);

    /**
     * 공통게시판 AND 페이스북 이전 상세 조회
     * @param boardInfo
     * @return
     */
    BoardInfo selectBoardAndFbDetailPrev(BoardInfo boardInfo);

    /**
     * 공통게시판 TOP 1 상세 조회
     * @param boardInfo
     * @return
     */
    BoardInfo selectBoardDetailTop1(BoardInfo boardInfo);


    /**
     * 공통게시판 이벤트 TOP 3 상세 조회
     * @param boardInfo
     * @return
     */
    List<BoardInfo> selectBoardEventDetailTop3(BoardInfo boardInfo);

    /**
     * 공통게시판 등록
     * @param boardInfo
     * @return
     */
    int insertBoard(BoardInfo boardInfo);

    /**
     * 공통게시판 오픈 종료 기간 조회
     * @param boardInfo
     * @return
     */
    List<BoardInfo> selectBoardOpenCloseDtDetail(BoardInfo boardInfo);


    /**
     * 공통게시판 금일 기준 오픈 종료 기간 조회
     * @param boardInfo
     * @return
     */
    public List<BoardInfo> selectBoardTodayOpenCloseDtDetail(BoardInfo boardInfo);

    /**
     * 공통게시판 자주하는 질문 GROUP BY  CONTENT TYPE
     * @param boardInfo
     * @return
     */
    List<BoardInfo> selectBoardContentTypeGroupBy(BoardInfo boardInfo);



    /**
     * 공통게시판 수정
     * @param boardInfo
     * @return
     */
    int updateBoard(BoardInfo boardInfo);

    /**
     * 공통게시판 READ COUNT 업데이트
     * @param boardInfo
     * @return
     */
    int updateReadCntBoard(BoardInfo boardInfo);

    /**
     * 공통게시판 첨부파일 등록
     * @param fileInfo
     * @return
     */
    int insertBoardFile(FileInfo fileInfo);

    /**
     * 공통게시판 첨부파일 리스트 조회
     * @param boardInfo
     * @return
     */
    List<BoardInfo> selectBoardFile(BoardInfo boardInfo);

    /**
     * 공통게시판 첨부파일 삭제처리
     * @param boardInfo
     * @return
     */
    int updateDeleteBoardFile(BoardInfo boardInfo);



    /**
     * 파일 업로드 처리
     * @param request
     * @param boardInfo
     * @param fileInfo
     * @return
     */
//    int doUploadAtchFiles(HttpServletRequest request, BoardInfo boardInfo, FileInfo fileInfo);
    int doUploadAtchFiles(HttpServletRequest request, BoardInfo boardInfo);

}
