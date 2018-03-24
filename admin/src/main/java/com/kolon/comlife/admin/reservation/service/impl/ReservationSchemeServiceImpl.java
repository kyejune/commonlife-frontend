package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.admin.reservation.service.ReservationSchemeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("reservationSchemeService")
public class ReservationSchemeServiceImpl implements ReservationSchemeService {
    @Resource(name = "reservationSchemeDAO")
    ReservationSchemeDAO dao;

    @Override
    public List<ReservationSchemeInfo> index( Map params ) {
        return dao.index( params );
    }

    @Override
    public ReservationSchemeInfo show( int idx ) {
        return dao.show( idx );
    }

    @Override
    public int create(ReservationSchemeInfo info) {
        return dao.create( info );
    }

    @Override
    public int update(ReservationSchemeInfo info) {
        return dao.update( info );
    }

    @Override
    public int delete(ReservationSchemeInfo info) {
        return dao.delete( info );
    }
}
