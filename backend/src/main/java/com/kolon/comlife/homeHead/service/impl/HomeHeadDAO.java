package com.kolon.comlife.homeHead.service.impl;

import com.kolon.comlife.homeHead.model.HomeHeadInfo;
import com.kolon.comlife.point.model.PointHistoryInfo;
import com.kolon.comlife.point.service.impl.PointHistoryDAO;
import com.kolon.comlife.reservation.model.ReservationInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("homeHeadDAO")
public class HomeHeadDAO {
    private static final Logger logger = LoggerFactory.getLogger(HomeHeadDAO.class);

    @Resource
    private SqlSession sqlSession;

    @Resource( name = "pointHistoryDAO" )
    private PointHistoryDAO pointHistoryDAO;

    public List<HomeHeadInfo> index(Map params ) {
        return sqlSession.selectList( "HomeHead.index", params );
    }

    public HomeHeadInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne( "HomeHead.show", params );
    }

    public boolean existExt( int idx ) {
        HashMap params = new HashMap();
        params.put( "idx", idx );
        return sqlSession.selectOne( "HomeHead.existExt", params );
    }

    public int createExt( int idx ) {
        HashMap params = new HashMap();
        params.put( "idx", idx );
        return sqlSession.insert("HomeHead.createExt", params);
    }

    public int update(HomeHeadInfo info) {
        return sqlSession.update("HomeHead.update", info);
    }

    public int delete(HomeHeadInfo info) {
        return sqlSession.delete("HomeHead.delete", info);
    }

    public  int updatePoints(HomeHeadInfo head, ReservationInfo reservation, int points, String description ) {
        HashMap pointParams = new HashMap();
        pointParams.put( "idx", head.getHeadID() );
        pointParams.put( "points", points );
        sqlSession.update( "HomeHead.updatePoints", pointParams );

        PointHistoryInfo history = new PointHistoryInfo();
        history.setHomeHeadID( head.getHeadID() );
        history.setUsrID( reservation.getUsrID() );
        history.setPoint( points );
        history.setDescription( description );

        pointHistoryDAO.create( history );
        return pointHistoryDAO.create( history );
    }
}
