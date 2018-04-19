package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationSchemeOptionInfo;
import com.kolon.comlife.reservation.service.ReservationSchemeOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("reservationSchemeOptionService")
public class ReservationSchemeOptionServiceImpl implements ReservationSchemeOptionService {
    public static final Logger logger = LoggerFactory.getLogger(ReservationSchemeOptionServiceImpl.class);

    @Resource(name = "reservationSchemeOptionDAO")
    private ReservationSchemeOptionDAO dao;

    @Override
    public List<ReservationSchemeOptionInfo> index(Map params) {
        return dao.index( params );
    }

    @Override
    public ReservationSchemeOptionInfo show(int idx) {
        return dao.show( idx );
    }

    @Override
    public int create(ReservationSchemeOptionInfo info) {
        return dao.create( info );
    }

    @Override
    public int update(ReservationSchemeOptionInfo info) {
        return dao.update( info );
    }

    @Override
    public int delete(ReservationSchemeOptionInfo info) {
        return dao.delete( info );
    }
}
