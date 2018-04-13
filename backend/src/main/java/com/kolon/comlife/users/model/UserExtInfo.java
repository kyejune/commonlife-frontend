package com.kolon.comlife.users.model;

import com.kolon.comlife.imageStore.model.ImageInfoUtil;

public class UserExtInfo extends UserInfo {

    String userAlias;       // Unused
    int    imageIdx;        // ImageStore(CL_IMAGE_INFO)내의 IMAGE_IDX 값
    String headNm;          // 세대주 이름 (HOME_HEAD_M.HEAD_NM)
    String startDt;         // 입주일     (HOME_HEAD_M.START_DT)
    int    points;           // 포인트     (HOME_HEAD_M_EXT.POINT)

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getImageSrc() {
        return ImageInfoUtil.getImagePath(
                ImageInfoUtil.SIZE_SUFFIX_MEDIUM,
                this.imageIdx );
    }

    public int getImageIdx() {
        return imageIdx;
    }

    public void setImageIdx(int imageIdx) {
        this.imageIdx = imageIdx;
    }


    public String getHeadNm() {
        return headNm;
    }

    public void setHeadNm(String headNm) {
        this.headNm = headNm;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
