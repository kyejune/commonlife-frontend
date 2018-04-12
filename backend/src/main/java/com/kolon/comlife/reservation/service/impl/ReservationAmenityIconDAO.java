package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationAmenityIconInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository("reservationAmenityIconDAO")
public class ReservationAmenityIconDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationAmenityIconInfo> index() {
        return sqlSession.selectList( "ReservationAmenityIcon.index" );
    }

    public ReservationAmenityIconInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne("ReservationAmenityIcon.show", params);
    }

    public ReservationAmenityIconInfo latest() {
        return sqlSession.selectOne( "ReservationAmenityIcon.latest" );
    }

    public int create( ReservationAmenityIconInfo info ) {
        return sqlSession.insert( "ReservationAmenityIcon.create", info );
    }

    public int update( ReservationAmenityIconInfo info ) {
        return sqlSession.update("ReservationAmenityIcon.update", info);
    }

    public int delete( ReservationAmenityIconInfo info ) {
        return sqlSession.delete("ReservationAmenityIcon.delete", info);
    }
}
