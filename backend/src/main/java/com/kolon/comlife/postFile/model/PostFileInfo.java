package com.kolon.comlife.postFile.model;

import com.kolon.comlife.post.model.PostInfo;
import org.apache.ibatis.type.Alias;

@Alias("postFileInfo")
public class PostFileInfo {

    private int fileIdx;
    private int boardIdx;
    private String fileType;
    private String fileNm;
    private String fileOrgNm;
    private String filePath;
    private String fileSize;
    private String delYn;
    private String dispOrder;
    private String regDttm;
    private String regUserid;
    private String updDttm;
    private String updUserid;

    // Relations
    private PostInfo post;

    public int getFileIdx() {
        return fileIdx;
    }

    public void setFileIdx(int fileIdx) {
        this.fileIdx = fileIdx;
    }

    public int getBoardIdx() {
        return boardIdx;
    }

    public void setBoardIdx(int boardIdx) {
        this.boardIdx = boardIdx;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
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

    public String getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
    }

    public String getRegUserid() {
        return regUserid;
    }

    public void setRegUserid(String regUserid) {
        this.regUserid = regUserid;
    }

    public String getUpdDttm() {
        return updDttm;
    }

    public void setUpdDttm(String updDttm) {
        this.updDttm = updDttm;
    }

    public String getUpdUserid() {
        return updUserid;
    }

    public void setUpdUserid(String updUserid) {
        this.updUserid = updUserid;
    }

    /*
        Relations Getter and Setter
     */

    public PostInfo getPost() {
        return post;
    }

    public void setPost(PostInfo post) {
        this.post = post;
    }

}
