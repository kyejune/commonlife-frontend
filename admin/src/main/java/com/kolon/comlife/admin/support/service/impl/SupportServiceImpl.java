package com.kolon.comlife.admin.support.service.impl;

import com.kolon.comlife.admin.board.model.BoardInfo;
import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.admin.support.exception.SupportGeneralException;
import com.kolon.comlife.admin.support.model.SupportCategoryInfo;
import com.kolon.comlife.admin.support.service.SupportService;
import com.kolon.common.admin.model.FileInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service("supportService")
public class SupportServiceImpl implements SupportService {
    public static final Logger logger = LoggerFactory.getLogger(SupportServiceImpl.class);

    @Autowired
    private SupportDAO supportDAO;

    @Autowired
    private ComplexDAO complexDAO;


    @Override
    public List<SupportCategoryInfo> getCategoryInfoByComplexId(SupportCategoryInfo categoryInfo)
                                        throws SupportGeneralException
    {
        ComplexInfo               complexInfo;
        List<SupportCategoryInfo> categoryInfoList;


        if( categoryInfo == null ) {
            throw new SupportGeneralException("입력값이 잘못 입력되었습니다.");
        }

        complexInfo = complexDAO.selectComplexById( categoryInfo.getCmplxId() );
        if(complexInfo == null) {
            throw new SupportGeneralException("해당 현장에 대한 정보가 없습니다.");
        }

        categoryInfoList = supportDAO.selectCategoryListByComplexId( categoryInfo );

        return categoryInfoList;
    }

    @Override
    public void updateCategoryInfoDisplayByComplexId(List<SupportCategoryInfo> dispOrderList)
            throws SupportGeneralException
    {
        int resultCnt;

        for(SupportCategoryInfo e : dispOrderList ) {
            resultCnt = supportDAO.updateCategoryDisplayOrder( e );
            if( resultCnt < 1 ) {
                logger.error("resultCnt는 1보다 커야 합니다. CL_LIVING_SUPPORT_CONF 테이블의 데이터를 확인하세요.");
                throw new SupportGeneralException("업데이트 과정에 문제가 발생했습니다. 문제가 지속되면 담당자에게 문의하세요.");
            }
        }

        return;
    }

//    @Override
//    public List<SupportInfo> selectBoardList(SupportInfo supportInfo) {
//        return supportDAO.selectBoardList(supportInfo);
//    }

    @Override
    public List<BoardInfo> selectBoardAndFbList(BoardInfo boardInfo) {
        return supportDAO.selectBoardAndFbList(boardInfo);
    }

    @Override
    public BoardInfo selectBoardDetail(BoardInfo boardInfo) {
        return supportDAO.selectBoardDetail(boardInfo);
    }

    @Override
    public BoardInfo selectBoardDetailNext(BoardInfo boardInfo) {
        return supportDAO.selectBoardDetailNext(boardInfo);
    }

    @Override
    public BoardInfo selectBoardDetailPrev(BoardInfo boardInfo) {
        return supportDAO.selectBoardDetailPrev(boardInfo);
    }

    @Override
    public BoardInfo selectBoardAndFbDetailNext(BoardInfo boardInfo) {
        return supportDAO.selectBoardAndFbDetailNext(boardInfo);
    }

    @Override
    public BoardInfo selectBoardAndFbDetailPrev(BoardInfo boardInfo) {
        return supportDAO.selectBoardAndFbDetailPrev(boardInfo);
    }


    @Override
    public BoardInfo selectBoardDetailTop1(BoardInfo boardInfo) {
        return supportDAO.selectBoardDetailTop1(boardInfo);
    }

    @Override
    public List<BoardInfo> selectBoardEventDetailTop3(BoardInfo boardInfo) {
        return supportDAO.selectBoardEventDetailTop3(boardInfo);
    }

    @Override
    public List<BoardInfo> selectBoardOpenCloseDtDetail(BoardInfo boardInfo){
        return supportDAO.selectBoardOpenCloseDtDetail(boardInfo);
    }

    @Override
    public List<BoardInfo> selectBoardTodayOpenCloseDtDetail(BoardInfo boardInfo) {
        return supportDAO.selectBoardTodayOpenCloseDtDetail(boardInfo);
    }

    @Override
    public List<BoardInfo> selectBoardContentTypeGroupBy(BoardInfo boardInfo){
        return supportDAO.selectBoardContentTypeGroupBy(boardInfo);
    }



    @Override
    public int insertBoard(BoardInfo boardInfo) {
        return supportDAO.insertBoard(boardInfo);
    }

    @Override
    public int updateBoard(BoardInfo boardInfo) {
        return supportDAO.updateBoard(boardInfo);
    }

    @Override
    public int updateReadCntBoard(BoardInfo boardInfo) {
        return supportDAO.updateReadCntBoard(boardInfo);
    }

    @Override
    public int insertBoardFile(FileInfo fileInfo) {
        return supportDAO.insertBoardFile(fileInfo);
    }

    @Override
    public List<BoardInfo> selectBoardFile(BoardInfo boardInfo) {
        return supportDAO.selectBoardFile(boardInfo);
    }

    @Override
    public int updateDeleteBoardFile(BoardInfo boardInfo) {
        return supportDAO.updateDeleteBoardFile(boardInfo);
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
                    insertCount = insertCount + supportDAO.insertBoardFile(vvo);
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
                    insertCount = insertCount + supportDAO.insertBoardFile(vo);
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
