package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationInfo;
import com.kolon.comlife.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {
    public static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Resource(name = "reservationDAO")
    private ReservationDAO dao;

    @Override
    public List<ReservationInfo> index(Map params) {
        return dao.index( params );
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
}
