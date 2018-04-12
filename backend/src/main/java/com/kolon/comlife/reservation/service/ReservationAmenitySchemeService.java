package com.kolon.comlife.reservation.service;

import com.kolon.comlife.reservation.model.ReservationAmenitySchemeInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ReservationAmenitySchemeService {
    public List<ReservationAmenitySchemeInfo> index(Map params);
    public ReservationAmenitySchemeInfo show(int idx);
    public int create(ReservationAmenitySchemeInfo info);
    public int delete(HashMap params);
}
