package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationSchemeOptionInfo;
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

    public List<ReservationSchemeOptionInfo> index(Map params ) {
        return sqlSession.selectList( "ReservationSchemeOption.index", params );
    }

    public ReservationSchemeOptionInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne( "ReservationSchemeOption.show", params );
    }

    public int create(ReservationSchemeOptionInfo info) {
        return sqlSession.insert("ReservationSchemeOption.create", info);
    }

    public int update(ReservationSchemeOptionInfo info) {
        return sqlSession.update("ReservationSchemeOption.update", info);
    }

    public int delete(ReservationSchemeOptionInfo info) {
        return sqlSession.delete("ReservationSchemeOption.delete", info);
    }
}
