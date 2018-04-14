package com.kolon.comlife.info.model;

import com.kolon.comlife.post.model.PostInfo;
import org.apache.ibatis.type.Alias;

@Alias("ticketFileInfo")
public class TicketFileInfo {

    private int tktFileIdx;
    private int tktIdx;
    private int usrId;
    private String host;
    private String mimeType;
    private String fileName;
    private String filePath;
    private String delYn;
    private String regDttm;
    private String updDttm;

    public int getTktFileIdx() {
        return tktFileIdx;
    }

    public void setTktFileIdx(int tktFileIdx) {
        this.tktFileIdx = tktFileIdx;
    }

    public int getTktIdx() {
        return tktIdx;
    }

    public void setTktIdx(int tktIdx) {
        this.tktIdx = tktIdx;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
