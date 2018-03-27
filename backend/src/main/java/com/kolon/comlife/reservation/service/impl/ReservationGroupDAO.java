package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationGroupInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("reservationGroupDAO")
public class ReservationGroupDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationGroupInfo> index( Map params ) {
        return sqlSession.selectList( "ReservationGroup.index", params );
    }

    public ReservationGroupInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne( "ReservationGroup.index", params );
    }

    public int create(ReservationGroupInfo info) {
        return sqlSession.insert("ReservationGroup.create", info);
    }

    public int update(ReservationGroupInfo info) {
        return sqlSession.update("ReservationGroup.update", info);
    }

    public int delete(ReservationGroupInfo info) {
        return sqlSession.delete("ReservationGroup.delete", info);
    }
}
