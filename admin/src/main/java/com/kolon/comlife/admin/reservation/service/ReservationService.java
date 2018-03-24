package com.kolon.comlife.admin.reservation.service;

import com.kolon.comlife.admin.reservation.model.ReservationInfo;

import java.util.List;

public interface ReservationService {
    public List<ReservationInfo> index();
    public ReservationInfo show( int idx );
    public int create( ReservationInfo info );
    public int update( ReservationInfo info );
    public int delete( ReservationInfo info );
}
