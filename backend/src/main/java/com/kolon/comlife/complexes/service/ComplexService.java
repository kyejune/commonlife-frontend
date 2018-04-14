package com.kolon.comlife.complexes.service;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;

import java.util.List;

public interface ComplexService {

    ComplexInfo getComplexById(int id);

    List<ComplexInfo> getComplexList();

    List<ComplexSimpleInfo> getComplexSimpleList();

    List<ComplexSimpleInfo> getComplexListInSameGroup( int cmplxId );


}

