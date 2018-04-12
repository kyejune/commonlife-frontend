package com.kolon.comlife.reservation.service;

import com.kolon.comlife.reservation.model.ReservationAmenityInfo;

import java.util.List;

public interface ReservationAmenityService {
    public List<ReservationAmenityInfo> index();
    public ReservationAmenityInfo show(int idx);
    public int create(ReservationAmenityInfo info);
    public int update(ReservationAmenityInfo info);
    public int delete(ReservationAmenityInfo info);
}
