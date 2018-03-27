package com.kolon.comlife.reservation.service;

import com.kolon.comlife.reservation.model.ReservationGroupInfo;

import java.util.List;
import java.util.Map;

public interface ReservationGroupService {
    public List<ReservationGroupInfo> index( Map params );
    public ReservationGroupInfo show( int idx );
    public int create( ReservationGroupInfo info );
    public int update( ReservationGroupInfo info );
    public int delete( ReservationGroupInfo info );
}
