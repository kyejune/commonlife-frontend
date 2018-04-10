package com.kolon.comlife.admin.reservation.model;

import org.apache.ibatis.type.Alias;

@Alias("reservationAmenitySchemeInfo")
public class ReservationAmenitySchemeInfo {
    private int idx;
    private int amenityIdx;
    private int schemeIdx;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getAmenityIdx() {
        return amenityIdx;
    }

    public void setAmenityIdx(int amenityIdx) {
        this.amenityIdx = amenityIdx;
    }

    public int getSchemeIdx() {
        return schemeIdx;
    }

    public void setSchemeIdx(int schemeIdx) {
        this.schemeIdx = schemeIdx;
    }
}
