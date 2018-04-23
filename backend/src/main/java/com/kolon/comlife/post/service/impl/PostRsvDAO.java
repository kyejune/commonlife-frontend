package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.like.model.LikeStatusInfo;
import com.kolon.comlife.post.model.PostRsvInfo;
import com.kolon.comlife.post.model.PostRsvItemInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("postRsvDAO")
public class PostRsvDAO {
    private static final Logger logger = LoggerFactory.getLogger(PostRsvDAO.class);

    @Resource
    private SqlSession sqlSession;

    public  PostRsvInfo selectRsvInfo(int parentIdx ) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put( "parentIdx", parentIdx );
        return sqlSession.selectOne( "PostRsv.selectRsvList", params );
    }

    public List<PostRsvItemInfo> selectRsvItemList(int parentIdx ) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put( "parentIdx", parentIdx );
        return sqlSession.selectList( "PostRsv.selectRsvItemList", params );
    }

    public boolean isReserved( int parentIdx, int usrId ) {
        PostRsvItemInfo      rsvItem;
        Map<String, Integer> params = new HashMap<String, Integer>();

        params.put( "parentIdx", parentIdx );
        params.put( "usrId", usrId );

        rsvItem = sqlSession.selectOne( "PostRsv.selectRsvItemByParentIdxAndUsrId", params );

        return (rsvItem != null) ? true : false;
    }

    public LikeStatusInfo selectRsvCountByPostId(int parentIdx, int usrId ) {
        Map<String, Integer> params = new HashMap<>();

        params.put("parentIdx", Integer.valueOf(parentIdx));
        params.put("usrId", Integer.valueOf(usrId));

        return sqlSession.selectOne( "PostRsv.selectRsvCountByPostId", params );
    }

    public int selectRsvAvailableWithLock( int parentIdx ) {
        Map<String, Integer> params = new HashMap<String, Integer>();

        params.put( "postIdx", parentIdx );

        return sqlSession.selectOne( "PostRsv.selectRsvAvailableWithLock", params );
    }

    public int incRsvCntIfAvailable( int parentIdx ) {
        Map<String, Integer> params = new HashMap<String, Integer>();

        params.put( "parentIdx", parentIdx );

        return sqlSession.insert( "PostRsv.incRsvCntIfAvailable", params );
    }

    public int decRsvCntIfAvailable( int parentIdx ) {
        Map<String, Integer> params = new HashMap<String, Integer>();

        params.put( "parentIdx", parentIdx );

        return sqlSession.insert( "PostRsv.decRsvCntIfAvailable", params );
    }

    public int addRsvItem( int parentIdx, int usrId ) {
        Map<String, Integer> params = new HashMap<String, Integer>();

        params.put( "parentIdx", parentIdx );
        params.put( "usrId", usrId );

        return sqlSession.insert( "PostRsv.addRsvItem", params );
    }


    public int removeRsvItem( int parentIdx, int usrId ) {
        Map<String, Integer> params = new HashMap<String, Integer>();

        params.put( "parentIdx", parentIdx );
        params.put( "usrId", usrId );

        return sqlSession.update( "PostRsv.removeRsvItem", params );
    }
}
