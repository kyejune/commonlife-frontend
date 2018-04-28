package com.kolon.comlife.reservation.service;

import com.kolon.comlife.homeHead.model.HomeHeadInfo;
import com.kolon.comlife.reservation.model.ReservationInfo;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    public List<ReservationInfo> index(Map params);
    public List<ReservationInfo> available(Map params);
    public ReservationInfo show(int idx);
    public int create(ReservationInfo info, HomeHeadInfo head);
    public int update(ReservationInfo info);
    public int delete(ReservationInfo info);
}
