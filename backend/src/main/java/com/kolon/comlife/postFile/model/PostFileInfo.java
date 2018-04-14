package com.kolon.comlife.postFile.model;

import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.post.model.PostInfo;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;

@Alias("postFileInfo")
public class PostFileInfo {

    private int postFileIdx;
    private int postIdx;
    private int usrId;
    private String host;
    private String mimeType;
    private String fileName;
    private String filePath;
    private String delYn;
    private String regDttm;
    private String updDttm;

    private String originPath;
    private String smallPath;
    private String mediumPath;
    private String largePath;

    public int getPostFileIdx() {
        return postFileIdx;
    }

    public void setPostFileIdx(int postFileIdx) {
        this.postFileIdx = postFileIdx;
    }

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

    /*
        Relations
     */
    private PostInfo post;

    /*
        Relations Getter and Setter
     */

    public PostInfo getPost() {
        return post;
    }

    public void setPost(PostInfo post) {
        this.post = post;
    }

    /*
        이미지 경로
     */
    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }
    public String getOriginPath() {
        return this.originPath;
    }
    public String getSmallPath() {
        return this.originPath + ImageInfoUtil.SIZE_SUFFIX_SMALL;
    }
    public String getMediumPath() {
        return this.originPath + ImageInfoUtil.SIZE_SUFFIX_MEDIUM;
    }
    public String getLargePath() {
        return this.originPath + ImageInfoUtil.SIZE_SUFFIX_LARGE;
    }
}
