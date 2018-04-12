package com.kolon.comlife.admin.reservation.service;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;

import java.util.List;
import java.util.Map;

public interface ReservationSchemeService {
    public List<ReservationSchemeInfo> index( Map params );
    public ReservationSchemeInfo show( int idx );
    public ReservationSchemeInfo create( ReservationSchemeInfo info );
    public ReservationSchemeInfo update( ReservationSchemeInfo info );
    public int delete( ReservationSchemeInfo info );
}
