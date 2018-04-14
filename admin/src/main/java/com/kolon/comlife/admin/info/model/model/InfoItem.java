package com.kolon.comlife.admin.info.model.model;

import com.kolon.comlife.common.model.SimpleMsgInfo;
import org.apache.ibatis.type.Alias;

@Alias("infoItem")
public class InfoItem extends SimpleMsgInfo {

    private int    itemIdx;
    private String itemNm;

    private int    cmplxId;

    private int    cateIdx;
    private String cateNm;
    private String cateId;

    private String imgSrc;
    private String desc;
    private int    dispOrder;
    private String setYn;
    private String delYn;

    private String regDttm;
    private String updDttm;

    public String getItemNm() {
        return itemNm;
    }

    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }

    public int getItemIdx() {
        return itemIdx;
    }

    public void setItemIdx(int itemIdx) {
        this.itemIdx = itemIdx;
    }

    public int getCateIdx() {
        return cateIdx;
    }

    public void setCateIdx(int cateIdx) {
        this.cateIdx = cateIdx;
    }

    public int getCmplxId() {
        return cmplxId;
    }

    public void setCmplxId(int cmplxId) {
        this.cmplxId = cmplxId;
    }

    public String getCateNm() {
        return cateNm;
    }

    public void setCateNm(String cateNm) {
        this.cateNm = cateNm;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(int dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getSetYn() {
        return setYn;
    }

    public void setSetYn(String setYn) {
        this.setYn = setYn;
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
