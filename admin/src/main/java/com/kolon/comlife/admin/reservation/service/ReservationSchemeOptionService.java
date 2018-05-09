package com.kolon.comlife.admin.reservation.service;

import com.kolon.comlife.admin.reservation.model.ReservationSchemeOptionInfo;

import java.util.List;
import java.util.Map;

public interface ReservationSchemeOptionService {
    public List<ReservationSchemeOptionInfo> index(Map params);
    public ReservationSchemeOptionInfo create(ReservationSchemeOptionInfo info);
    public ReservationSchemeOptionInfo show(int id);
    public int update(ReservationSchemeOptionInfo info);
    public int delete(ReservationSchemeOptionInfo info);
}
