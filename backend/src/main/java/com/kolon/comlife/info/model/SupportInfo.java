package com.kolon.comlife.info.model;

import org.apache.ibatis.type.Alias;

@Alias("supportInfo")
public class SupportInfo {
    private String boardIdx;
    private String boardType;
    private String title;
    private String content;
    private String contentType;
    private String readCnt;
    private String openDt;
    private String closeDt;
    private String htmlYn;
    private String useYn;
    private String delYn;
    private String writeNm;
    private String writeHp;

    private String hp1;
    private String hp2;
    private String hp3;

    private String em1;
    private String em2;
    private String em2Select;

    private String writeEmail;
    private String ordererDateTime;
    private String orderNo;
    private String regDttm;
    private String regUserId;
    private String updDttm;
    private String updUserId;

    private String link;
    private String imageLink;

    private String createdTime;
    private String updatedTime;

    private String rownum;

    private String eventOnYn;

    public String getBoardIdx() {
        return boardIdx;
    }

    public void setBoardIdx(String boardIdx) {
        this.boardIdx = boardIdx;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getReadCnt() {
        return readCnt;
    }

    public void setReadCnt(String readCnt) {
        this.readCnt = readCnt;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getCloseDt() {
        return closeDt;
    }

    public void setCloseDt(String closeDt) {
        this.closeDt = closeDt;
    }

    public String getHtmlYn() {
        return htmlYn;
    }

    public void setHtmlYn(String htmlYn) {
        this.htmlYn = htmlYn;
    }

    
    public String getUseYn() {
        return useYn;
    }

    
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getWriteNm() {
        return writeNm;
    }

    public void setWriteNm(String writeNm) {
        this.writeNm = writeNm;
    }

    public String getWriteHp() {
        return writeHp;
    }

    public void setWriteHp(String writeHp) {
        this.writeHp = writeHp;
    }

    public String getHp1() {
        return hp1;
    }

    public void setHp1(String hp1) {
        this.hp1 = hp1;
    }

    public String getHp2() {
        return hp2;
    }

    public void setHp2(String hp2) {
        this.hp2 = hp2;
    }

    public String getHp3() {
        return hp3;
    }

    public void setHp3(String hp3) {
        this.hp3 = hp3;
    }

    public String getEm1() {
        return em1;
    }

    public void setEm1(String em1) {
        this.em1 = em1;
    }

    public String getEm2() {
        return em2;
    }

    public String getEm2Select() {
        return em2Select;
    }

    public void setEm2Select(String em2Select) {
        this.em2Select = em2Select;
    }

    public void setEm2(String em2) {
        this.em2 = em2;
    }


    public String getWriteEmail() {
        return writeEmail;
    }

    public void setWriteEmail(String writeEmail) {
        this.writeEmail = writeEmail;
    }

    public String getOrdererDateTime() {
        return ordererDateTime;
    }

    public void setOrdererDateTime(String ordererDateTime) {
        this.ordererDateTime = ordererDateTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getEventOnYn() { return eventOnYn; }

    public void setEventOnYn(String eventOnYn) {
        this.eventOnYn = eventOnYn;
    }
}
