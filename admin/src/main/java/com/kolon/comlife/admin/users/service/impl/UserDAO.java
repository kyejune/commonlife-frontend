package com.kolon.comlife.admin.users.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userDAO")
public class UserDAO {
    @Resource
    private SqlSession sqlSession;

    public int selectUserCountByComplexId(int cmplxId) {
        Map params = new HashMap();
        Map result;
        params.put("cmplxId", cmplxId);
        result = sqlSession.selectOne("User.selectUserCountByComplexId", params);

        return ((Long)result.get("USER_COUNT")).intValue();
    }

}
