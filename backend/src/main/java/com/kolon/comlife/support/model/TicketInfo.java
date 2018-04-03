package com.kolon.comlife.support.model;

import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

@Alias("ticketInfo")
public class TicketInfo {

    private int tktIdx;
    private int usrId;
    private int cmplxId;
    private int cateIdx; // 지원티켓 분류(Category) IDX
    private String content;
    private String delYn;
    private String regDttm;
    private String updDttm;

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

    public int getCmplxId() {
        return cmplxId;
    }

    public void setCmplxId(int cmplxId) {
        this.cmplxId = cmplxId;
    }

    public int getCateIdx() {
        return cateIdx;
    }

    public void setCateIdx(int cateIdx) {
        this.cateIdx = cateIdx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    private List<TicketFileInfo> ticketFiles = new ArrayList<>();

    /*
        Relations Getter and Setter
     */
    public List<TicketFileInfo> getTicketFiles() {
        return ticketFiles;
    }

    public void setTicketFiles(List<TicketFileInfo> ticketFiles) {
        this.ticketFiles = ticketFiles;
    }
}
