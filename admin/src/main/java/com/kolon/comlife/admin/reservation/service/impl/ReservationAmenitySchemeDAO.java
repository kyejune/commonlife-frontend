package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationAmenitySchemeInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("reservationAmenitySchemeDAO")
public class ReservationAmenitySchemeDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationAmenitySchemeInfo> index(Map params) {
        return sqlSession.selectList("ReservationAmenityScheme.index", params);
    }

    public ReservationAmenitySchemeInfo show(int idx) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne("ReservationAmenityScheme.show", params);
    }

    public int create(ReservationAmenitySchemeInfo info) {
        return sqlSession.insert("ReservationAmenityScheme.create", info);
    }

    public int delete(HashMap params) {
        return sqlSession.delete("ReservationAmenityScheme.delete", params);
    }
}
