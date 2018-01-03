package com.kolon.common.model;

import java.util.List;

/**
 */
public class FileInfo extends BaseUserInfo {
    private int attachNo;
    private int fileSn;

    /** 공통게시판 IDX **/
    private String boardIdx;
    private String fileIdx;

    private String fileStreCours;
    private String fileNm;
    private String fileOrgNm;
    private String filePath;
    private String fileExtsn;
    private String fileType;
    private long fileSize = 0L;

    private String fileNm2;
    private String fileOrgNm2;
    private String filePath2;
    private String fileExtsn2;
    private String fileType2;
    private long fileSize2 = 0L;

    private String delYn;
    private String dispOrder;

    private String useYn;
    private String regDttm;
    private String regUserId;
    private String updDttm;
    private String updUserId;
    private List<FileInfo> atchFileList;
    private List<FileInfo> atchFileDelList;

    public int getAttachNo() {
        return attachNo;
    }

    public void setAttachNo(int attachNo) {
        this.attachNo = attachNo;
    }

    public int getFileSn() {
        return fileSn;
    }

    public void setFileSn(int fileSn) {
        this.fileSn = fileSn;
    }

    public String getFileIdx() {
        return fileIdx;
    }

    public void setFileIdx(String fileIdx) {
        this.fileIdx = fileIdx;
    }

    public String getBoardIdx() {
        return boardIdx;
    }

    public void setBoardIdx(String boardIdx) {
        this.boardIdx = boardIdx;
    }

    public String getFileStreCours() {
        return fileStreCours;
    }

    public void setFileStreCours(String fileStreCours) {
        this.fileStreCours = fileStreCours;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFileOrgNm() {
        return fileOrgNm;
    }

    public void setFileOrgNm(String fileOrgNm) {
        this.fileOrgNm = fileOrgNm;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileExtsn() {
        return fileExtsn;
    }

    public void setFileExtsn(String fileExtsn) {
        this.fileExtsn = fileExtsn;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileNm2() {
        return fileNm2;
    }

    public void setFileNm2(String fileNm2) {
        this.fileNm2 = fileNm2;
    }

    public String getFileOrgNm2() {
        return fileOrgNm2;
    }

    public void setFileOrgNm2(String fileOrgNm2) {
        this.fileOrgNm2 = fileOrgNm2;
    }

    public String getFilePath2() {
        return filePath2;
    }

    public void setFilePath2(String filePath2) {
        this.filePath2 = filePath2;
    }

    public String getFileExtsn2() {
        return fileExtsn2;
    }

    public void setFileExtsn2(String fileExtsn2) {
        this.fileExtsn2 = fileExtsn2;
    }

    public String getFileType2() {
        return fileType2;
    }

    public void setFileType2(String fileType2) {
        this.fileType2 = fileType2;
    }

    public long getFileSize2() {
        return fileSize2;
    }

    public void setFileSize2(long fileSize2) {
        this.fileSize2 = fileSize2;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(String dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
    }

    public String getRegUserId() {
        return regUserId;
    }

    public void setRegUserId(String regUserId) {
        this.regUserId = regUserId;
    }

    public String getUpdDttm() {
        return updDttm;
    }

    public void setUpdDttm(String updDttm) {
        this.updDttm = updDttm;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    public List<FileInfo> getAtchFileList() {
        return atchFileList;
    }

    public void setAtchFileList(List<FileInfo> atchFileList) {
        this.atchFileList = atchFileList;
    }

    public List<FileInfo> getAtchFileDelList() {
        return atchFileDelList;
    }

    public void setAtchFileDelList(List<FileInfo> atchFileDelList) {
        this.atchFileDelList = atchFileDelList;
    }
}
