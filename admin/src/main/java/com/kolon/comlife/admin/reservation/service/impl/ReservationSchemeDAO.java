package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("reservationSchemeDAO")
public class ReservationSchemeDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationSchemeInfo> index(Map params) {
        return sqlSession.selectList("ReservationScheme.index", params);
    }

    public ReservationSchemeInfo show(int idx) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne("ReservationScheme.show", params);
    }

    public int create(ReservationSchemeInfo info) {
        return sqlSession.insert("ReservationScheme.create", info);
    }

    public int update(ReservationSchemeInfo info) {
        return sqlSession.update("ReservationScheme.update", info);
    }

    public int delete(ReservationSchemeInfo info) {
        return sqlSession.delete("ReservationScheme.delete", info);
    }
}
