package com.kolon.comlife.complexes.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Complex Service Implementation
 */
@Service("complexService")
public class ComplexServiceImpl implements ComplexService {
    public static final Logger logger = LoggerFactory.getLogger(ComplexServiceImpl.class);

    @Autowired
    private ImageStoreService imageStoreService;

    @Autowired
    private ComplexDAO complexDAO;

    @Override
    public ComplexInfo getComplexById(int id ) {
        ComplexInfo cmplxInfo;

        cmplxInfo = complexDAO.selectComplexById( id );
        if( cmplxInfo.getLogoImgIdx() > 0 ) {
            cmplxInfo.setClLogoImgSrc(
                    imageStoreService.getImageFullPathByIdx(
                            cmplxInfo.getLogoImgIdx(),
                            ImageInfoUtil.SIZE_SUFFIX_MEDIUM ));
        } else {
            cmplxInfo.setClLogoImgSrc( null );
        }

        return cmplxInfo;
    }

    @Override
    public List<ComplexInfo> getComplexList() {
        List<ComplexInfo> cmplxList;

        cmplxList = complexDAO.selectComplexList();

        // clLogoImgSrc (CL_LOGO_IMG_SRC) 값을 imageStore로 부터 생성
        for( ComplexInfo cmplxInfo : cmplxList ) {
            if( cmplxInfo.getLogoImgIdx() > 0 ) {
                cmplxInfo.setClLogoImgSrc(
                        imageStoreService.getImageFullPathByIdx(
                                cmplxInfo.getLogoImgIdx(),
                                ImageInfoUtil.SIZE_SUFFIX_MEDIUM ));
            } else {
                cmplxInfo.setClLogoImgSrc( null );
            }
        }

        return cmplxList;
    }

    @Override
    public List<ComplexSimpleInfo> getComplexSimpleList() {
        List<ComplexSimpleInfo> cmplxList;

        cmplxList = complexDAO.selectComplexInfoList();

        // clLogoImgSrc (CL_LOGO_IMG_SRC) 값을 imageStore로 부터 생성
        for( ComplexSimpleInfo cmplxInfo : cmplxList ) {
            if( cmplxInfo.getLogoImgIdx() > 0 ) {
                cmplxInfo.setClLogoImgSrc(
                        imageStoreService.getImageFullPathByIdx(
                                cmplxInfo.getLogoImgIdx(),
                                ImageInfoUtil.SIZE_SUFFIX_MEDIUM ));
            } else {
                cmplxInfo.setClLogoImgSrc( null );
            }
        }

        return cmplxList;
    }

    @Override
    public List<ComplexSimpleInfo> getComplexListInSameGroup( int cmplxId ) {

        List<ComplexSimpleInfo> cmplxList;

        cmplxList = complexDAO.selectComplexListInSameGroup( cmplxId );

        // clLogoImgSrc (CL_LOGO_IMG_SRC) 값을 imageStore로 부터 생성
        for( ComplexSimpleInfo cmplxInfo : cmplxList ) {
            if( cmplxInfo.getLogoImgIdx() > 0 ) {
                cmplxInfo.setClLogoImgSrc(
                        imageStoreService.getImageFullPathByIdx(
                                cmplxInfo.getLogoImgIdx(),
                                ImageInfoUtil.SIZE_SUFFIX_MEDIUM ));
            } else {
                cmplxInfo.setClLogoImgSrc( null );
            }
        }

        return cmplxList;
    }
}

