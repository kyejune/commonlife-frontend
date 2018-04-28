package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.reservation.model.ReservationGroupInfo;
import com.kolon.comlife.reservation.model.ReservationInfo;
import com.kolon.comlife.reservation.model.ReservationSchemeInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("reservationSchemeDAO")
public class ReservationSchemeDAO {
    @Resource
    private SqlSession sqlSession;

    @Resource(name = "reservationGroupDAO")
    private ReservationGroupDAO groupDAO;

    @Resource(name = "complexDAO")
    private ComplexDAO complexDAO;

    public List<ReservationSchemeInfo> index(Map params ) {
        List<ReservationSchemeInfo> schemes = sqlSession.selectList( "ReservationScheme.index", params );
        ArrayList<Integer> groupIds = new ArrayList<Integer>();
        for( ReservationSchemeInfo info : schemes ) {
            groupIds.add( info.getParentIdx() );
        }

        // 그룹 아이디가 있을 경우 관계 모델 찾아서 바인딩
        if( groupIds.size() > 0 ) {
            HashMap groupParams = new HashMap();
            groupParams.put( "ids", groupIds );
            List<ReservationGroupInfo> groups = groupDAO.index( groupParams );

            for( ReservationSchemeInfo info : schemes ) {
                for( ReservationGroupInfo group : groups ) {
                    if( info.getParentIdx() == group.getIdx() ) {
                        info.setGroup( group );
                    }
                }
            }
        }

        List<ComplexInfo> complexes = complexDAO.selectComplexList();

        for( ReservationSchemeInfo info : schemes ) {
            for( ComplexInfo complex : complexes ) {
                if( info.getCmplxIdx() == complex.getCmplxId() ) {
                    info.setComplex( complex );
                }
            }
        }

        return schemes;
    }

    public ReservationSchemeInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        return sqlSession.selectOne( "ReservationScheme.show", params );
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
