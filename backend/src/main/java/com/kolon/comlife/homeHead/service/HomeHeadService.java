package com.kolon.comlife.homeHead.service;

import com.kolon.comlife.homeHead.model.HomeHeadInfo;

import java.util.List;
import java.util.Map;

public interface HomeHeadService {
    public List<HomeHeadInfo> index(Map params );
    public HomeHeadInfo show( int idx );
    public boolean existExt( int idx );
    public int createExt( int idx );
    public int update( HomeHeadInfo info );
    public int delete( HomeHeadInfo info );
}
