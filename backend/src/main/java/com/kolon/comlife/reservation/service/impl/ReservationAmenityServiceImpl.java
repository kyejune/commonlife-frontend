package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationAmenityInfo;
import com.kolon.comlife.reservation.service.ReservationAmenityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("reservationAmenityService")
public class ReservationAmenityServiceImpl implements ReservationAmenityService {
    @Resource(name = "reservationAmenityDAO")
    ReservationAmenityDAO dao;

    @Override
    public List<ReservationAmenityInfo> index() {
        return dao.index();
    }

    @Override
    public ReservationAmenityInfo show(int idx) {
        return dao.show( idx );
    }

    @Override
    public int create(ReservationAmenityInfo info) {
        return dao.create( info );
    }

    @Override
    public int update(ReservationAmenityInfo info) {
        return dao.update( info );
    }

    @Override
    public int delete(ReservationAmenityInfo info) {
        return dao.delete( info );
    }
}
