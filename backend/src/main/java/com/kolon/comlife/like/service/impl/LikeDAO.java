package com.kolon.comlife.like.service.impl;

import com.kolon.comlife.like.model.LikeInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("likeDAO")
public class LikeDAO {
    @Resource
    private SqlSession sqlSession;

    public List<LikeInfo> selectLikeList( int parentIdx ) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put( "parentIdx", parentIdx );
        return sqlSession.selectList( "Like.selectLikeList", params );
    }

    public boolean hasLike( int parentIdx, int usrIdx ) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put( "parentIdx", parentIdx );
        params.put( "usrIdx", usrIdx );
        sqlSession.insert( "Like.addLike", params );
        return sqlSession.selectOne( "Like.hasLike", params ) != null;
    }

    public LikeInfo addLike( int parentIdx, int usrIdx ) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put( "parentIdx", parentIdx );
        params.put( "usrIdx", usrIdx );
        sqlSession.insert( "Like.addLike", params );
        return sqlSession.selectOne( "Like.selectLatestLike", params );
    }

    public void cancelLike( int parentIdx, int usrIdx ) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put( "parentIdx", parentIdx );
        params.put( "usrIdx", usrIdx );
        sqlSession.update( "Like.cancelLike", params );
        return;
    }
}
