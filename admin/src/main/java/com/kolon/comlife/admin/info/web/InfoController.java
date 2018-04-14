package com.kolon.comlife.admin.info.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.service.ComplexService;
import com.kolon.comlife.admin.info.exception.InfoGeneralException;
import com.kolon.comlife.admin.info.model.model.InfoData;
import com.kolon.comlife.admin.info.service.InfoService;
import com.kolon.comlife.admin.support.exception.NoDataException;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller("infoController")
@RequestMapping("admin/info/*")
public class InfoController {

    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Autowired
    private ComplexService complexService;

    @Autowired
    private InfoService infoService;

    @GetMapping( value = "categoryList.do" )
    public ModelAndView categoryList (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @ModelAttribute("cmplxId") int cmplxId
    ) {

        ComplexInfoDetail complexInfo;
        List<InfoData> categoryList;
        String                    categoryListJson;
        ObjectMapper mapper = new ObjectMapper();

        try {
            complexInfo = complexService.getComplexById( cmplxId );
            if( complexInfo == null ) {
                return mav.addObject( "error",
                        "해당 현장에 대한 정보가 없습니다. 문제가 지속되면 담당자에게 문의하세요.");
            }

            categoryList = infoService.getInfoDataList( cmplxId );
            if( categoryList == null ) {
                return mav.addObject( "error",
                        "해당 현장에 대한 INFO 정보가 없습니다. 문제가 지속되면 담당자에게 문의하세요.");
            }
        } catch( NoDataException e ) {
            logger.error( e.getMessage() );
            return mav.addObject(
                    "error",
                    e.getMessage() );
        } catch ( Exception e ) {
            logger.error( e.getMessage() );
            return mav.addObject(
                    "error",
                    "내부 오류가 발생했습니다. 문제가 지속되면 담당자에게 문의하세요.");
        }

        try {
            categoryListJson = mapper.writeValueAsString(categoryList);
            logger.debug(">>>>>>>>>>>>> categorListJson: " + categoryListJson);
        } catch( Exception e) {
            return mav.addObject(
                    "error",
                    "작업 처리과정에 에러가 발생했습니다. 문제가 지속되면 담당자에게 문의하세요.");
        }

        mav.addObject("complexInfo",  complexInfo );
        mav.addObject("categoryList", categoryListJson);
        mav.addObject("cmplxId",      complexInfo.getCmplxId());

        return mav;
    }


    @PostMapping(
            value = "procIns.do",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity procIns (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestBody List<Map<String, Object>> dispOrderParam
            , @RequestParam("cmplxId") int cmplxId
    ) {
        List<InfoData> dispOrderList;

        if( cmplxId < 1 ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("잘못된 현장아이디(cmplxId)를 입력했습니다. 다시 확인하세요."));
        }

        if( dispOrderParam == null ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("업데이트 할 내용이 없습니다. 전송 내용을 다시 확인하세요. "));
        }


        dispOrderList = new ArrayList<>();
        for( Map e: dispOrderParam ) {
            InfoData cateInfo = new InfoData();


            cateInfo.setInfoKey( (String) e.get("infoKey")  );
            cateInfo.setCmplxId( cmplxId );
            cateInfo.setDispOrder( ((Integer)e.get("dispOrder")).intValue() );
            cateInfo.setDelYn( (String) e.get("delYn") );

            dispOrderList.add(cateInfo);


            logger.debug(">>> " + cateInfo.getInfoKey() );
            logger.debug(">>> " + cateInfo.getDispOrder() );
            logger.debug(">>> " + cateInfo.getDelYn() );
            logger.debug("----");
        }

        try {
            infoService.updateCategoryInfoDisplayByComplexId( dispOrderList );
        } catch( InfoGeneralException e ) {
            logger.error( e.getMessage() );
            e.printStackTrace();
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( new SimpleMsgInfo(e.getMessage()) );
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SimpleMsgInfo("업데이트를 완료했습니다."));
    }
}
