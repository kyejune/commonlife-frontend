package com.kolon.comlife.point.service.impl;

import com.kolon.comlife.point.model.PointHistoryInfo;
import com.kolon.comlife.point.service.PointHistoryService;

import javax.annotation.Resource;

public class PointHistoryServiceImpl implements PointHistoryService {
    @Resource(name = "pointHistoryDAO")
    private PointHistoryDAO dao;

    @Override
    public int create(PointHistoryInfo info) {
        return dao.create( info );
    }
}
