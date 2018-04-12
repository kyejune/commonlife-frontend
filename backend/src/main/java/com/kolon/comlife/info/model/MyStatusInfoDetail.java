package com.kolon.comlife.info.model;

public class MyStatusInfoDetail extends  MyStatusInfo {

    String chargesDate;

    String downloadLink;

    int totalCharges;

    int rentalCharges;
    int electricityCharges;
    int waterCharges;
    int gasCharges;
    int parkingCharges;

    public String getChargesDate() {
        return chargesDate;
    }

    public void setChargesDate(String chargesDate) {
        this.chargesDate = chargesDate;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public int getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(int totalCharges) {
        this.totalCharges = totalCharges;
    }

    public int getRentalCharges() {
        return rentalCharges;
    }

    public void setRentalCharges(int rentalCharges) {
        this.rentalCharges = rentalCharges;
    }

    public int getElectricityCharges() {
        return electricityCharges;
    }

    public void setElectricityCharges(int electricityCharges) {
        this.electricityCharges = electricityCharges;
    }

    public int getWaterCharges() {
        return waterCharges;
    }

    public void setWaterCharges(int waterCharges) {
        this.waterCharges = waterCharges;
    }

    public int getGasCharges() {
        return gasCharges;
    }

    public void setGasCharges(int gasCharges) {
        this.gasCharges = gasCharges;
    }

    public int getParkingCharges() {
        return parkingCharges;
    }

    public void setParkingCharges(int parkingCharges) {
        this.parkingCharges = parkingCharges;
    }
}
