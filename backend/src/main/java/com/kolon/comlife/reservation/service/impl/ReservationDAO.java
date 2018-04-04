package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("reservationDAO")
public class ReservationDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationInfo> index(Map params ) {
        return sqlSession.selectList( "Reservation.index", params );
    }

    public ReservationInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne( "Reservation.index", params );
    }

    public int create(ReservationInfo info) {
        return sqlSession.insert("Reservation.create", info);
    }

    public int update(ReservationInfo info) {
        return sqlSession.update("Reservation.update", info);
    }

    public int delete(ReservationInfo info) {
        return sqlSession.delete("Reservation.delete", info);
    }
}
