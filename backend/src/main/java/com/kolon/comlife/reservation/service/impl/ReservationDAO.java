package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.homeHead.model.HomeHeadInfo;
import com.kolon.comlife.homeHead.service.impl.HomeHeadDAO;
import com.kolon.comlife.reservation.model.ReservationInfo;
import com.kolon.comlife.reservation.model.ReservationSchemeInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("reservationDAO")
public class ReservationDAO {
    @Resource
    private SqlSession sqlSession;

    @Resource(name = "reservationSchemeDAO")
    private ReservationSchemeDAO schemeDAO;

    @Resource(name = "homeHeadDAO")
    private HomeHeadDAO homeHeadDAO;

    /**
     * 예약 목록에 예약 틀 목록을 바인딩 해서 반환
     *
     * @param reservations
     * @return
     */
    private List<ReservationInfo> bindSchemesToReservations( List<ReservationInfo> reservations ) {
        ArrayList<Integer> schemeIds = new ArrayList<Integer>();
        for( ReservationInfo info : reservations ) {
            schemeIds.add( info.getParentIdx() );
        }

        // 스키마 아이디가 있을 경우 관계 모델 찾아서 바인딩
        if( schemeIds.size() > 0 ) {
            HashMap schemeParams = new HashMap();
            schemeParams.put( "ids", schemeIds );
            List<ReservationSchemeInfo> schemes = schemeDAO.index( schemeParams );

            for( ReservationInfo info : reservations ) {
                for( ReservationSchemeInfo scheme : schemes ) {
                    if( info.getParentIdx() == scheme.getIdx() ) {
                        info.setScheme( scheme );
                    }
                }
            }
        }
        return reservations;
    }

    /**
     * 예약 데이터를 임시로 목록으로 만들어 예약 틀을 바인딩 한 후 객체만 반환
     *
     * @param reservation
     * @return
     */
    private ReservationInfo bindSchemeToReservation( ReservationInfo reservation ) {
        List<ReservationInfo> list = new ArrayList<>();
        list.add( reservation );
        list = this.bindSchemesToReservations( list );
        return list.get( 0 );
    }

    public List<ReservationInfo> index(Map params ) {
        List<ReservationInfo> reservations = sqlSession.selectList( "Reservation.index", params );

        reservations = this.bindSchemesToReservations( reservations );

        return reservations;
    }

    public List<ReservationInfo> available(Map params ) {
        return sqlSession.selectList( "Reservation.available", params );
    }

    public ReservationInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        ReservationInfo reservation = sqlSession.selectOne( "Reservation.show", params );
        reservation = this.bindSchemeToReservation( reservation );
        return reservation;
    }

    public int create(ReservationInfo info, HomeHeadInfo head) {
        // 포인트 사용하고 로그 기록
        homeHeadDAO.updatePoints( head, info, head.getPoints() - info.getPoint(), "예약" );

        return sqlSession.insert("Reservation.create", info);
    }

    public int update(ReservationInfo info) {
        return sqlSession.update("Reservation.update", info);
    }

    public int delete(ReservationInfo info) {
        return sqlSession.delete("Reservation.delete", info);
    }
}
