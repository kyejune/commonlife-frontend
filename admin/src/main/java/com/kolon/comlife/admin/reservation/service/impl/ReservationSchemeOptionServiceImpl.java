package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeOptionInfo;
import com.kolon.comlife.admin.reservation.service.ReservationSchemeOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("reservationSchemeOptionService")
public class ReservationSchemeOptionServiceImpl implements ReservationSchemeOptionService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationSchemeOptionServiceImpl.class);

    @Resource(name = "reservationSchemeOptionDAO")
    ReservationSchemeOptionDAO dao;

    @Override
    public List<ReservationSchemeOptionInfo> index( Map params ) {
        return dao.index( params );
    }

    @Override
    public ReservationSchemeOptionInfo create(ReservationSchemeOptionInfo info) {
        dao.create( info );
        return dao.latest();
    }
}
