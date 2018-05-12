package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.post.model.LikeInfo;
import com.kolon.comlife.post.model.LikeStatusInfo;
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

    public boolean hasLike( int parentIdx, int usrId ) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put( "parentIdx", parentIdx );
        params.put( "usrId", usrId );
        sqlSession.insert( "Like.requestRsv", params );
        return sqlSession.selectOne( "Like.hasLike", params ) != null;
    }

    public LikeStatusInfo selectLikeCountByPostId(int parentIdx, int usrId ) {
        Map<String, Integer> params = new HashMap<>();

        params.put("parentIdx", Integer.valueOf(parentIdx));
        params.put("usrId", Integer.valueOf(usrId));

        return sqlSession.selectOne( "Like.selectLikeCountByPostId", params );
    }

    public int addLike( int parentIdx, int usrId ) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put( "parentIdx", parentIdx );
        params.put( "usrId", usrId );
        return sqlSession.insert( "Like.addLike", params );
    }

    public void cancelLike( int parentIdx, int usrId ) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put( "parentIdx", parentIdx );
        params.put( "usrId", usrId );
        sqlSession.update( "Like.cancelLike", params );
        return;
    }
}
