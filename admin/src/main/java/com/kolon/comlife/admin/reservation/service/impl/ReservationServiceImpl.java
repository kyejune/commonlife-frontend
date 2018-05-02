package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationInfo;
import com.kolon.comlife.admin.reservation.service.ReservationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {
    @Resource(name = "reservationDAO")
    ReservationDAO dao;

    @Override
    public List<ReservationInfo> index() {
        return dao.index();
    }

    @Override
    public List<ReservationInfo> index(HashMap params) {
        return dao.index( params );
    }

    @Override
    public List<ReservationInfo> queue() {
        return dao.queue();
    }

    @Override
    public ReservationInfo show(int idx) {
        return dao.show( idx );
    }

    @Override
    public int create(ReservationInfo info) {
        return dao.create( info );
    }

    @Override
    public int update(ReservationInfo info) {
        return dao.update( info );
    }

    @Override
    public int delete(ReservationInfo info) {
        return dao.delete( info );
    }

    @Override
    public int updateStatus(ReservationInfo info) {
        return dao.updateStatus( info );
    }
}
