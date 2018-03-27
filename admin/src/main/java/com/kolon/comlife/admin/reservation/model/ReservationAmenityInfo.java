package com.kolon.comlife.admin.reservation.model;

import org.apache.ibatis.type.Alias;

@Alias("reservationAmenityInfo")
public class ReservationAmenityInfo {
    private int idx;
    private String useIcon;
    private String iconClass;
    private String imagePath;
    private String name;
    private String regDttm;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getUseIcon() {
        return useIcon;
    }

    public void setUseIcon(String useIcon) {
        this.useIcon = useIcon;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
    }
}
