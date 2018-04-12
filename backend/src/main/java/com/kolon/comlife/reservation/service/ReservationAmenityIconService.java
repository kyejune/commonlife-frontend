package com.kolon.comlife.reservation.service;

import com.kolon.comlife.reservation.model.ReservationAmenityIconInfo;

import java.util.List;

public interface ReservationAmenityIconService {
    public List<ReservationAmenityIconInfo> index();
    public ReservationAmenityIconInfo show(int idx);
    public ReservationAmenityIconInfo create(ReservationAmenityIconInfo info);
    public int update(ReservationAmenityIconInfo info);
    public int delete(ReservationAmenityIconInfo info);
}
