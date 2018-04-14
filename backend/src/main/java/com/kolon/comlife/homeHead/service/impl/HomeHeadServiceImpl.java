package com.kolon.comlife.homeHead.service.impl;

import com.kolon.comlife.homeHead.model.HomeHeadInfo;
import com.kolon.comlife.homeHead.service.HomeHeadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("homeHeadService")
public class HomeHeadServiceImpl implements HomeHeadService {

    @Resource(name = "homeHeadDAO")
    private HomeHeadDAO dao;

    @Override
    public List<HomeHeadInfo> index(Map params) {
        return dao.index(params);
    }

    @Override
    public HomeHeadInfo show(int idx) {
        return dao.show(idx);
    }

    @Override
    public int create(HomeHeadInfo info) {
        return dao.create(info);
    }

    @Override
    public int update(HomeHeadInfo info) {
        return dao.update(info);
    }

    @Override
    public int delete(HomeHeadInfo info) {
        return dao.delete(info);
    }
}
