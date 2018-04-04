package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationGroupInfo;
import com.kolon.comlife.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.reservation.service.ReservationGroupService;
import com.kolon.comlife.reservation.service.ReservationSchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("reservationSchemeService")
public class ReservationSchemeServiceImpl implements ReservationSchemeService {
    public static final Logger logger = LoggerFactory.getLogger(ReservationSchemeServiceImpl.class);

    @Resource(name = "reservationSchemeDAO")
    private ReservationSchemeDAO dao;

    @Override
    public List<ReservationSchemeInfo> index(Map params) {
        return dao.index( params );
    }

    @Override
    public ReservationSchemeInfo show(int idx) {
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
