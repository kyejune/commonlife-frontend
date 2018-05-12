package com.kolon.comlife.post.model;

import com.kolon.comlife.users.model.PostUserInfo;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

@Alias("postInfo")
public class PostInfo {

    private int postIdx;
    private int usrId;
    private int cmplxId;
    private String postType;
    private String content;
    private String title;
    private String delYn;
    private int likesCount;
    private boolean myLikeFlag;
    private String regDttm;
    private String updDttm;
    private String rsvYn;
    private int    rsvMaxCnt;
    private int    rsvCount;
    private boolean  rsvFlag;
    private String shareYn;
    private String eventBeginDttm;
    private String eventEndDttm;
    private String eventCmplxNm;
    private String eventPlaceNm;
    private String inquiryYn;
    private String inquiryType;
    private String inquiryInfo;

    private int    adminIdx;
    private String adminYn;

    public int getPostIdx() {
        return postIdx;
    }

    public void setPostIdx(int postIdx) {
        this.postIdx = postIdx;
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

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
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

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isMyLikeFlag() {
        return myLikeFlag;
    }

    public void setMyLikeFlag(boolean myLikeFlag) {
        this.myLikeFlag = myLikeFlag;
    }

    public String getRsvYn() {
        return rsvYn;
    }

    public void setRsvYn(String rsvYn) {
        this.rsvYn = rsvYn;
    }

    public int getRsvMaxCnt() {
        return rsvMaxCnt;
    }

    public void setRsvMaxCnt(int rsvMaxCnt) {
        this.rsvMaxCnt = rsvMaxCnt;
    }

    public String getShareYn() {
        return shareYn;
    }

    public void setShareYn(String shareYn) {
        this.shareYn = shareYn;
    }

    public String getEventBeginDttm() {
        return eventBeginDttm;
    }

    public void setEventBeginDttm(String eventBeginDttm) {
        this.eventBeginDttm = eventBeginDttm;
    }

    public String getEventEndDttm() {
        return eventEndDttm;
    }

    public void setEventEndDttm(String eventEndDttm) {
        this.eventEndDttm = eventEndDttm;
    }

    public String getEventCmplxNm() {
        return eventCmplxNm;
    }

    public void setEventCmplxNm(String eventCmplxNm) {
        this.eventCmplxNm = eventCmplxNm;
    }

    public String getEventPlaceNm() {
        return eventPlaceNm;
    }

    public void setEventPlaceNm(String eventPlaceNm) {
        this.eventPlaceNm = eventPlaceNm;
    }

    public String getInquiryYn() {
        return inquiryYn;
    }

    public void setInquiryYn(String inquiryYn) {
        this.inquiryYn = inquiryYn;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getInquiryInfo() {
        return inquiryInfo;
    }

    public void setInquiryInfo(String inquiryInfo) {
        this.inquiryInfo = inquiryInfo;
    }

    public int getRsvCount() {
        return rsvCount;
    }

    public void setRsvCount(int rsvCount) {
        this.rsvCount = rsvCount;
    }

    public boolean isRsvFlag() {
        return rsvFlag;
    }

    public void setRsvFlag(boolean rsvFlag) {
        this.rsvFlag = rsvFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAdminIdx() {
        return adminIdx;
    }

    public void setAdminIdx(int adminIdx) {
        this.adminIdx = adminIdx;
    }

    public String getAdminYn() {
        return adminYn;
    }

    public void setAdminYn(String adminYn) {
        this.adminYn = adminYn;
    }

    /*
                            Relations
                         */
    private PostUserInfo user;
    private List<PostFileInfo> postFiles = new ArrayList<PostFileInfo>();

    /*
        Relations Getter and Setter
     */

    public List<PostFileInfo> getPostFiles() {
        return postFiles;
    }

    public void setPostFiles(List<PostFileInfo> postFiles) {
        this.postFiles = postFiles;
    }

    public PostUserInfo getUser() {
        return user;
    }

    public void setUser(PostUserInfo user) {
        this.user = user;
    }
}
