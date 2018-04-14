package com.kolon.comlife.homeHead.service.impl;

import com.kolon.comlife.homeHead.model.HomeHeadInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("homeHeadDAO")
public class HomeHeadDAO {
    @Resource
    private SqlSession sqlSession;

    public List<HomeHeadInfo> index(Map params ) {
        return sqlSession.selectList( "HomeHead.index", params );
    }

    public HomeHeadInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne( "HomeHead.show", params );
    }

    public int create(HomeHeadInfo info) {
        return sqlSession.insert("HomeHead.create", info);
    }

    public int update(HomeHeadInfo info) {
        return sqlSession.update("HomeHead.update", info);
    }

    public int delete(HomeHeadInfo info) {
        return sqlSession.delete("HomeHead.delete", info);
    }
}
