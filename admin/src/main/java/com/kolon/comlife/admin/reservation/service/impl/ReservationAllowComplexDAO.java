package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationAllowComplexInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("reservationAllowComplexDAO")
public class ReservationAllowComplexDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationAllowComplexInfo> index(Map params) {
        return sqlSession.selectList("ReservationAllowComplex.index", params);
    }

    public ReservationAllowComplexInfo show(int idx) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne("ReservationAllowComplex.show", params);
    }

    public int create(ReservationAllowComplexInfo info) {
        return sqlSession.insert("ReservationAllowComplex.create", info);
    }

    public int delete(HashMap params) {
        return sqlSession.delete("ReservationAllowComplex.delete", params);
    }
}
