package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.admin.reservation.service.ReservationSchemeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("reservationSchemeService")
public class ReservationSchemeServiceImpl implements ReservationSchemeService {
    @Resource(name = "reservationSchemeDAO")
    ReservationSchemeDAO reservationSchemeDAO;

    public int createReserverationScheme(ReservationSchemeInfo info) {
        return reservationSchemeDAO.createReserverationScheme( info );
    }
}
