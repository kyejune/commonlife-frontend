package com.kolon.comlife.reservation.service.impl;

import com.kolon.comlife.reservation.model.ReservationGroupInfo;
import com.kolon.comlife.reservation.service.ReservationGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("reservationGroupService")
public class ReservationGroupServiceImpl implements ReservationGroupService {
    public static final Logger logger = LoggerFactory.getLogger(ReservationGroupServiceImpl.class);

    @Resource(name = "reservationGroupDAO")
    private ReservationGroupDAO dao;

    @Override
    public List<ReservationGroupInfo> index(Map params) {
        return dao.index( params );
    }

    @Override
    public ReservationGroupInfo show(int idx) {
        return dao.show( idx );
    }

    @Override
    public int create(ReservationGroupInfo info) {
        return dao.create( info );
    }

    @Override
    public int update(ReservationGroupInfo info) {
        return dao.update( info );
    }

    @Override
    public int delete(ReservationGroupInfo info) {
        return dao.delete( info );
    }
}
