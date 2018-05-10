package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeOptionInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("reservationSchemeOptionDAO")
public class ReservationSchemeOptionDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationSchemeOptionInfo> index(Map params) {
        return sqlSession.selectList("ReservationSchemeOption.index", params);
    }

    public int create(ReservationSchemeOptionInfo info) {
        return sqlSession.insert("ReservationSchemeOption.create", info);
    }

    public int update(ReservationSchemeOptionInfo info) {
        return sqlSession.update( "ReservationSchemeOption.update", info );
    }

    public ReservationSchemeOptionInfo show( int id ) {
        HashMap params = new HashMap();
        params.put( "idx", id );
        return sqlSession.selectOne( "ReservationSchemeOption.show", params );
    }

    public ReservationSchemeOptionInfo latest() { return sqlSession.selectOne( "ReservationSchemeOption.latest" ); }

    public int delete(ReservationSchemeOptionInfo info) {
        return sqlSession.update( "ReservationSchemeOption.delete", info );
    }
}
