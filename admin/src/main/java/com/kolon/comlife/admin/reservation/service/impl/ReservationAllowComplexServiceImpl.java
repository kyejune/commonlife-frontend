package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationAllowComplexInfo;
import com.kolon.comlife.admin.reservation.service.ReservationAllowComplexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reservationAllowComplexService")
public class ReservationAllowComplexServiceImpl implements ReservationAllowComplexService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationAllowComplexServiceImpl.class);

    @Resource(name = "reservationAllowComplexDAO")
    ReservationAllowComplexDAO dao;

    @Override
    public List<ReservationAllowComplexInfo> index(Map params ) {
        return dao.index( params );
    }

    @Override
    public ReservationAllowComplexInfo show( int idx ) {
        return dao.show( idx );
    }

    @Override
    public int create(ReservationAllowComplexInfo info) {
        return dao.create( info );
    }

    @Override
    public int delete(HashMap params) {
        return dao.delete( params );
    }
}
