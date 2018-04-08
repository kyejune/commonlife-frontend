package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationAmenityIconInfo;
import com.kolon.comlife.admin.reservation.service.ReservationAmenityIconService;
import com.kolon.comlife.admin.reservation.service.ReservationAmenityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("reservationAmenityIconService")
public class ReservationAmenityIconServiceImpl implements ReservationAmenityIconService {
    @Resource(name = "reservationAmenityIconDAO")
    ReservationAmenityIconDAO dao;

    @Override
    public List<ReservationAmenityIconInfo> index() {
        return dao.index();
    }

    @Override
    public ReservationAmenityIconInfo show(int idx) {
        return dao.show( idx );
    }

    @Override
    public ReservationAmenityIconInfo create(ReservationAmenityIconInfo info) {
        dao.create( info );
        return dao.latest();
    }

    @Override
    public int update(ReservationAmenityIconInfo info) {
        return dao.update( info );
    }

    @Override
    public int delete(ReservationAmenityIconInfo info) {
        return dao.delete( info );
    }
}
