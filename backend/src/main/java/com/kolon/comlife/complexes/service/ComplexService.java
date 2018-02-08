package com.kolon.comlife.complexes.service;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.model.ComplexSimpleInfo;

import java.util.List;

public interface ComplexService {

    public ComplexInfo getComplexById(int id);

    public List<ComplexInfo> getComplexList();

    public List<ComplexSimpleInfo> getComplexSimpleList();
}

