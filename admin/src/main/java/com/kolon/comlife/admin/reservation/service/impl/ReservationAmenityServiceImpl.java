package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationAmenityInfo;
import com.kolon.comlife.admin.reservation.service.ReservationAmenityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("reservationAmenityService")
public class ReservationAmenityServiceImpl implements ReservationAmenityService {
    @Resource(name = "reservationAmenityDAO")
    ReservationAmenityDAO dao;

    @Override
    public List<ReservationAmenityInfo> index(HashMap params) {
        return dao.index(params);
    }

    @Override
    public List<ReservationAmenityInfo> indexOfSchemes(HashMap params) {
        return dao.indexOfSchemes(params);
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
