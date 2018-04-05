package com.kolon.comlife.point.service.impl;

import com.kolon.comlife.point.model.PointHistoryInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("pointHistoryDAO")
public class PointHistoryDAO {
    @Resource
    private SqlSession sqlSession;

    public int create(PointHistoryInfo info) {
        return sqlSession.insert( "PointHistory.create", info );
    }
}
