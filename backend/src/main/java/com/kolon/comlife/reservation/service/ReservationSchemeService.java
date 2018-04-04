package com.kolon.comlife.reservation.service;

import com.kolon.comlife.reservation.model.ReservationSchemeInfo;

import java.util.List;
import java.util.Map;

public interface ReservationSchemeService {
    public List<ReservationSchemeInfo> index(Map params);
    public ReservationSchemeInfo show(int idx);
    public int create(ReservationSchemeInfo info);
    public int update(ReservationSchemeInfo info);
    public int delete(ReservationSchemeInfo info);
}
