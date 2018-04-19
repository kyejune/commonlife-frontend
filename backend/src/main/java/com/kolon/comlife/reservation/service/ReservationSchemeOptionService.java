package com.kolon.comlife.reservation.service;

import com.kolon.comlife.reservation.model.ReservationSchemeOptionInfo;

import java.util.List;
import java.util.Map;

public interface ReservationSchemeOptionService {
    public List<ReservationSchemeOptionInfo> index(Map params);
    public ReservationSchemeOptionInfo show(int idx);
    public int create(ReservationSchemeOptionInfo info);
    public int update(ReservationSchemeOptionInfo info);
    public int delete(ReservationSchemeOptionInfo info);
}
