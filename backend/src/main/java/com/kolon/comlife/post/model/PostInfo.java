package com.kolon.comlife.post.model;

import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.users.model.UserInfo;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("postInfo")
public class PostInfo {

    private int boardIdx;
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
    private String writeEmail;
    private String ordererDateTime;
    private String orderNo;
    private String regDttm;
    private int regUserid;
    private String updDttm;
    private int updUserid;
    private int usrId;
    private int cmplxId;

    // Relations
    private UserInfo user;
    private List<PostFileInfo> postFiles;
    private UserInfo register;
    private UserInfo updater;

    public int getBoardIdx() {
        return boardIdx;
    }

    public void setBoardIdx(int boardIdx) {
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

    public int getRegUserid() {
        return regUserid;
    }

    public void setRegUserid(int regUserid) {
        this.regUserid = regUserid;
    }

    public String getUpdDttm() {
        return updDttm;
    }

    public void setUpdDttm(String updDttm) {
        this.updDttm = updDttm;
    }

    public int getUpdUserid() {
        return updUserid;
    }

    public void setUpdUserid(int updUserid) {
        this.updUserid = updUserid;
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

    /*
        Relations Getter and Setter
     */

    public List<PostFileInfo> getPostFiles() {
        return postFiles;
    }

    public void setPostFiles(List<PostFileInfo> postFiles) {
        this.postFiles = postFiles;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserInfo getRegister() {
        return register;
    }

    public void setRegister(UserInfo register) {
        this.register = register;
    }

    public UserInfo getUpdater() {
        return updater;
    }

    public void setUpdater(UserInfo updater) {
        this.updater = updater;
    }

}
