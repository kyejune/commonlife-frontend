package com.kolon.comlife.admin.imageStore.model;

public class ImageInfoExt {
    private String originPath;
    private String smallPath;
    private String mediumPath;
    private String largePath;
    private String mimeType;

    public ImageInfoExt() {
    }

    public ImageInfoExt(String originPath, String smallPath, String mediumPath, String largePath, String mimeType) {
        this.originPath = originPath;
        this.smallPath = smallPath;
        this.mediumPath = mediumPath;
        this.largePath = largePath;
        this.mimeType = mimeType;
    }

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public String getSmallPath() {
        return smallPath;
    }

    public void setSmallPath(String smallPath) {
        this.smallPath = smallPath;
    }

    public String getMediumPath() {
        return mediumPath;
    }

    public void setMediumPath(String mediumPath) {
        this.mediumPath = mediumPath;
    }

    public String getLargePath() {
        return largePath;
    }

    public void setLargePath(String largePath) {
        this.largePath = largePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
