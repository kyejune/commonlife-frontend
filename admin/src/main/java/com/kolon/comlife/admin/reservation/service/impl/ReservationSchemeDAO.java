package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("reservationSchemeDAO")
public class ReservationSchemeDAO {
    @Resource
    private SqlSession sqlSession;

    public List<ReservationSchemeInfo> getReservationSchemeList() {
        return null;
    }

    public ReservationSchemeInfo getReservationScheme() {
        return null;
    }

    public ReservationSchemeInfo createReserverationScheme() {
        return null;
    }

    public ReservationSchemeInfo updateReservationScheme() {
        return null;
    }

    public ReservationSchemeInfo deleteReservationScheme() {
        return null;
    }
}
