package com.kolon.comlife.push.service.impl;

import com.kolon.comlife.push.model.PushHistoryInfo;
import com.kolon.comlife.push.model.PushInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository("pushHistoryDAO")
public class PushHistoryDAO {
    @Resource
    private SqlSession sqlSession;

    public List<PushHistoryInfo> index(HashMap params) {
        return sqlSession.selectList( "Push.history", params );
    }
}
