package com.kolon.comlife.admin.reservation.service;

import com.kolon.comlife.admin.reservation.model.ReservationAllowComplexInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ReservationAllowComplexService {
    public List<ReservationAllowComplexInfo> index(Map params);
    public ReservationAllowComplexInfo show(int idx);
    public int create(ReservationAllowComplexInfo info);
    public int delete(HashMap params);
}
