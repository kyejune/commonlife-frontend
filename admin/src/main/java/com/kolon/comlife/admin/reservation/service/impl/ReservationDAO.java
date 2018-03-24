package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository("reservationDAO")
public class ReservationDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationInfo> index() {
        return sqlSession.selectList( "Reservation.index" );
    }

    public ReservationInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne("Reservation.show", params);
    }

    public int create( ReservationInfo info ) {
        return sqlSession.insert( "Reservation.create", info );
    }

    public int update( ReservationInfo info ) {
        return sqlSession.update("Reservation.update", info);
    }

    public int delete( ReservationInfo info ) {
        return sqlSession.delete("Reservation.delete", info);
    }
}
