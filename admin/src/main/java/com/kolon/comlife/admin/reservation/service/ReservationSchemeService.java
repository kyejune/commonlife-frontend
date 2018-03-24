package com.kolon.comlife.admin.reservation.service;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;

import java.util.List;

public interface ReservationSchemeService {
    public List<ReservationSchemeInfo> index();
    public ReservationSchemeInfo show( int idx );
    public int create( ReservationSchemeInfo info );
    public int update( ReservationSchemeInfo info );
    public int delete( ReservationSchemeInfo info );
}
