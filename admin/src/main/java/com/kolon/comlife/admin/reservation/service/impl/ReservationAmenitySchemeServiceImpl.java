package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationAmenitySchemeInfo;
import com.kolon.comlife.admin.reservation.service.ReservationAmenitySchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reservationAmenitySchemeService")
public class ReservationAmenitySchemeServiceImpl implements ReservationAmenitySchemeService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationAmenitySchemeServiceImpl.class);

    @Resource(name = "reservationAmenitySchemeDAO")
    ReservationAmenitySchemeDAO dao;

    @Override
    public List<ReservationAmenitySchemeInfo> index(Map params ) {
        return dao.index( params );
    }

    @Override
    public ReservationAmenitySchemeInfo show( int idx ) {
        return dao.show( idx );
    }

    @Override
    public int create(ReservationAmenitySchemeInfo info) {
        return dao.create( info );
    }

    @Override
    public int delete(HashMap params) {
        return dao.delete( params );
    }
}
