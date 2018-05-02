package com.kolon.comlife.admin.reservation.service;

import com.kolon.comlife.admin.reservation.model.ReservationInfo;

import java.util.HashMap;
import java.util.List;

public interface ReservationService {
    public List<ReservationInfo> index();
    public List<ReservationInfo> index( HashMap params );
    public List<ReservationInfo> queue();
    public ReservationInfo show( int idx );
    public int create( ReservationInfo info );
    public int update( ReservationInfo info );
    public int delete( ReservationInfo info );
    public int updateStatus( ReservationInfo info );
}
