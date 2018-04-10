package com.kolon.comlife.admin.reservation.model;

import org.apache.ibatis.type.Alias;

@Alias("reservationAllowComplexInfo")
public class ReservationAllowComplexInfo {
    private int idx;
    private int schemeIdx;
    private int complexIdx;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getSchemeIdx() {
        return schemeIdx;
    }

    public void setSchemeIdx(int schemeIdx) {
        this.schemeIdx = schemeIdx;
    }

    public int getComplexIdx() {
        return complexIdx;
    }

    public void setComplexIdx(int complexIdx) {
        this.complexIdx = complexIdx;
    }
}
