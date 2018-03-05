package com.kolon.comlife.admin.complexes.service;

import com.kolon.comlife.admin.complexes.model.ComplexInfo;
import com.kolon.comlife.admin.complexes.model.ComplexSimpleInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ComplexService {

    public ComplexInfo getComplexById(int id);

    public List<ComplexInfo> getComplexList();

    public List<ComplexSimpleInfo> getComplexSimpleList();

    public int updateComplexGroupTypeById(int cmplxId, int cmplxGrpId);

    public List<HashMap> selectComplexGroupType();
}

