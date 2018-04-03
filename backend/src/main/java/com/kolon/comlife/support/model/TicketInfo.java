package com.kolon.comlife.support.model;

import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.users.model.PostUserInfo;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

@Alias("ticketInfo")
public class TicketInfo {

    private int lvngSuptTktIdx;
    private int usrId;
    private int cmplxId;
    private int lvngSuptCateIdx; // 지원티켓 분류(Category) IDX
    private String content;
    private String delYn;
    private String regDttm;
    private String updDttm;

    public int getLvngSuptTktIdx() {
        return lvngSuptTktIdx;
    }

    public void setLvngSuptTktIdx(int lvngSuptTktIdx) {
        this.lvngSuptTktIdx = lvngSuptTktIdx;
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

    public int getLvngSuptCateIdx() {
        return lvngSuptCateIdx;
    }

    public void setLvngSuptCateIdx(int lvngSuptCateIdx) {
        this.lvngSuptCateIdx = lvngSuptCateIdx;
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


    //    private List<PostFileInfo> postFiles = new ArrayList<PostFileInfo>();
//
//    /*
//        Relations Getter and Setter
//     */
//
//    public List<PostFileInfo> getPostFiles() {
//        return postFiles;
//    }
//
//    public void setPostFiles(List<PostFileInfo> postFiles) {
//        this.postFiles = postFiles;
//    }
}
