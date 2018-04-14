package com.kolon.comlife.admin.info.service.impl;


import com.kolon.comlife.admin.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.admin.info.exception.InfoGeneralException;
import com.kolon.comlife.admin.info.model.model.InfoData;
import com.kolon.comlife.admin.info.model.model.InfoMain;
import com.kolon.comlife.admin.info.service.InfoService;
import com.kolon.comlife.admin.support.exception.NoDataException;
import com.kolon.comlife.admin.support.exception.SupportGeneralException;
import com.kolon.comlife.admin.support.model.SupportCategoryInfo;
import com.kolon.comlife.admin.users.service.impl.UserDAO;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("infoService")
public class InfoServiceImpl implements InfoService {
    private static final Logger logger = LoggerFactory.getLogger( InfoServiceImpl.class );

    private String NO_NOTICE = null; // 공지사항 없는 경우, null 값 전송

    @Autowired
    UserDAO userDAO;

    @Autowired
    ComplexDAO complexDAO;

    @Autowired
    InfoDAO infoDAO;


    public List<InfoData> getInfoDataList( int cmplxId ) throws NoDataException {

        List<InfoData> infoDataList;

        infoDataList = infoDAO.getInfoDataListByCmplxId( cmplxId );
        if( infoDataList == null ) {
            logger.error("INFO data list가 없음");
            throw new NoDataException("표시할 목록이 없습니다. 문제가 지속되면 담당자에게 문의하세요.");
        }

        return infoDataList;
    }

    public void updateCategoryInfoDisplayByComplexId(List<InfoData> dispOrderList)
            throws InfoGeneralException
    {
        int resultCnt;

        for(InfoData e : dispOrderList ) {
            resultCnt = infoDAO.updateCategoryDisplayOrder( e );
            if( resultCnt < 1 ) {
                logger.error("resultCnt는 1보다 커야 합니다. CL_INFO_CONF 테이블의 데이터를 확인하세요.");
                throw new InfoGeneralException( "업데이트 과정에 문제가 발생했습니다. 문제가 지속되면 담당자에게 문의하세요.");
            }
        }

        return;
    }
}
