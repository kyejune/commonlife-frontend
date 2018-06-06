package com.kolon.comlife.admin.complexes.service;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.model.ComplexInfoDetail;
import com.kolon.comlife.admin.complexes.model.ComplexRegion;
import com.kolon.comlife.admin.complexes.model.ComplexSimpleInfo;

import java.util.HashMap;
import java.util.List;

public interface ComplexService {

    ComplexInfoDetail getComplexById(int id);

    List<ComplexInfo> getComplexList();

    List<ComplexSimpleInfo> getComplexSimpleList();

    int updateComplexGroupTypeById(int cmplxId, int cmplxGrpId) throws Exception ;

    void updateComplexName(int cmplxId, String name, int adminIdx ) throws Exception;
    void updateComplexAddr(int cmplxId, String addr, int adminIdx ) throws Exception;
    void updateComplexMapSrc(int cmplxId, String mapSrc, int adminIdx ) throws Exception;

    void updateComplexIotUseYn(int cmplxId, boolean useYn, int adminIdx ) throws Exception;
    void updateComplexReservationUseYn(int cmplxId, boolean useYn, int adminIdx ) throws Exception;
    void updateComplexFeedWriteAllowYn(int cmplxId, boolean writeAllowYn, int adminIdx ) throws Exception;

    List<HashMap> selectComplexGroupType();

    List<ComplexRegion> getComplexRegion();
}

