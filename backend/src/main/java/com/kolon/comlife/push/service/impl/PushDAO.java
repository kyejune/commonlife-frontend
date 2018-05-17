package com.kolon.comlife.push.service.impl;

import com.kolon.comlife.push.model.PushInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository("pushDAO")
public class PushDAO {
    @Resource
    private SqlSession sqlSession;

    public List<PushInfo> index(HashMap params) {
        return sqlSession.selectList( "Push.index", params );
    }
}
