package com.kolon.comlife.push.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;

@Repository("pushDAO")
public class PushDAO {
    @Resource
    private SqlSession sqlSession;

    public int register(HashMap params) {
        return sqlSession.insert( "Push.register", params );
    }
}
