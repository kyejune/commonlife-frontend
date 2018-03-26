package com.kolon.comlife.post.model;

import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.model.UserInfo;
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
    private String delYn;
    private int likesCount;
    private boolean myLikeFlag;
    private String regDttm;
    private String updDttm;

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
