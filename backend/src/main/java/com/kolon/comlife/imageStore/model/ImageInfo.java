package com.kolon.comlife.imageStore.model;

import org.apache.ibatis.type.Alias;

@Alias("imageInfo")
public class ImageInfo {
    private int     imageIdx;
    private String  imageType;
    private int     parentIdx;
    private int     parentType;
    private String  parentTypeNm;
    private int     usrId;
    private String  host;
    private String  mimeType;
    private String  fileNm;
    private String  filePath;
    private String  delYn;
    private String  regDttm;
    private String  updDttm;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

    public String getParentTypeNm() {
        return parentTypeNm;
    }

    public void setParentTypeNm(String parentTypeNm) {
        this.parentTypeNm = parentTypeNm;
    }

    public int getImageIdx() {
        return imageIdx;
    }

    public void setImageIdx(int imageIdx) {
        this.imageIdx = imageIdx;
    }

    public int getParentIdx() {
        return parentIdx;
    }

    public void setParentIdx(int parentIdx) {
        this.parentIdx = parentIdx;
    }

    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
}
