package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationAmenityInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository("reservationAmenityDAO")
public class ReservationAmenityDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationAmenityInfo> index() {
        return sqlSession.selectList( "ReservationAmenity.index" );
    }

    public ReservationAmenityInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne("ReservationAmenity.show", params);
    }

    public int create( ReservationAmenityInfo info ) {
        return sqlSession.insert( "ReservationAmenity.create", info );
    }

    public int update( ReservationAmenityInfo info ) {
        return sqlSession.update("ReservationAmenity.update", info);
    }

    public int delete( ReservationAmenityInfo info ) {
        return sqlSession.delete("ReservationAmenity.delete", info);
    }
}
