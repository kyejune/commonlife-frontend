package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.admin.reservation.service.ReservationSchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("reservationSchemeService")
public class ReservationSchemeServiceImpl implements ReservationSchemeService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationSchemeServiceImpl.class);

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
    public ReservationSchemeInfo create(ReservationSchemeInfo info) {
        dao.create( info );
        return dao.latest();
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
