package com.kolon.comlife.admin.reservation.model;

import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("reservationSchemeInfo")
public class ReservationSchemeInfo {
    private int idx;
    private int cmplxIdx;
    private int parentIdx;
    private String code;
    private String icon;
    private String reservationType;
    private String images;
    private String title;
    private String summary;
    private String description;
    private String isOpen;
    private String startDt;
    private String startTime;
    private String endDt;
    private String endTime;
    private String openTime;
    private String closeTime;
    private String availableInWeekend;
    private int point;
    private int amount;
    private int inStock;
    private int maxQty;
    private String activateDuration;
    private String maintenanceStartAt;
    private String maintenanceEndAt;
    private String options;
    private String precautions;
    private String delYn;
    private String regDttm;
    private String updDttm;

    private List<ReservationAmenityInfo> amenities;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getCmplxIdx() {
        return cmplxIdx;
    }

    public void setCmplxIdx(int cmplxIdx) {
        this.cmplxIdx = cmplxIdx;
    }

    public int getParentIdx() {
        return parentIdx;
    }

    public void setParentIdx(int parentIdx) {
        this.parentIdx = parentIdx;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getReservationType() {
        return reservationType;
    }

    public void setReservationType(String reservationType) {
        this.reservationType = reservationType;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getAvailableInWeekend() {
        return availableInWeekend;
    }

    public void setAvailableInWeekend(String availableInWeekend) {
        this.availableInWeekend = availableInWeekend;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(int maxQty) {
        this.maxQty = maxQty;
    }

    public String getActivateDuration() {
        return activateDuration;
    }

    public void setActivateDuration(String activateDuration) {
        this.activateDuration = activateDuration;
    }

    public String getMaintenanceStartAt() {
        return maintenanceStartAt;
    }

    public void setMaintenanceStartAt(String maintenanceStartAt) {
        this.maintenanceStartAt = maintenanceStartAt;
    }

    public String getMaintenanceEndAt() {
        return maintenanceEndAt;
    }

    public void setMaintenanceEndAt(String maintenanceEndAt) {
        this.maintenanceEndAt = maintenanceEndAt;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
    }

    public String getUpdDttm() {
        return updDttm;
    }

    public void setUpdDttm(String updDttm) {
        this.updDttm = updDttm;
    }

    public List<ReservationAmenityInfo> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<ReservationAmenityInfo> amenities) {
        this.amenities = amenities;
    }
}
