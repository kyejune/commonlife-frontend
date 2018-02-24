package com.kolon.common.admin.model;

import java.io.Serializable;

/**
 * Created by agcdDev on 2017-07-07.
 */
public class BaseInfo implements Serializable {
    private static final long serialVersionUID = 7063100128997446283L;
    public static final String PAGING_ENABLE_ON = "1";
    public static final String PAGING_ENABLE_OFF = "0";
    public static final String MODE_UPDATE = "update";
    public static final String MODE_CREATE = "create";

    public BaseInfo() {}

    public BaseInfo(String pagingEnable, String condOrder, String condAlign)
    {
        this.pagingEnable = pagingEnable;
        this.condOrder = condOrder;
        this.condAlign = condAlign;
    }

    private int pageIndex = 1;
    private int pageUnit = 10;
    private int pageSize = 10;
    private int firstIndex = 1;
    private int lastIndex = 1;
    private int recordCountPerPage = 10;
    private String pagingEnable = "1";
    private String condOrder = "";
    private String condAlign = "";
    private String jsessionId = "";
    private String mode = "";
    private int menuNo;
    private int upperMenuNo;
    private String customView = "";
    private int tot = 0;
    private int rnum = 0;
    private String searchType1;
    private String searchType2;
    private String searchType3;
    private String searchType4;
    private String searchType5;
    private String searchType6;
    private String searchType7;
    private String searchType8;

    private String searchKeyword1;
    private String searchKeyword2;
    private String searchKeyword3;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageUnit() {
        return pageUnit;
    }

    public void setPageUnit(int pageUnit) {
        this.pageUnit = pageUnit;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getRecordCountPerPage() {
        return recordCountPerPage;
    }

    public void setRecordCountPerPage(int recordCountPerPage) {
        this.recordCountPerPage = recordCountPerPage;
    }

    public String getPagingEnable() {
        return pagingEnable;
    }

    public void setPagingEnable(String pagingEnable) {
        this.pagingEnable = pagingEnable;
    }

    public String getCondOrder() {
        return condOrder;
    }

    public void setCondOrder(String condOrder) {
        this.condOrder = condOrder;
    }

    public String getCondAlign() {
        return condAlign;
    }

    public void setCondAlign(String condAlign) {
        this.condAlign = condAlign;
    }

    public String getJsessionId() {
        return jsessionId;
    }

    public void setJsessionId(String jsessionId) {
        this.jsessionId = jsessionId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(int menuNo) {
        this.menuNo = menuNo;
    }

    public int getUpperMenuNo() {
        return upperMenuNo;
    }

    public void setUpperMenuNo(int upperMenuNo) {
        this.upperMenuNo = upperMenuNo;
    }

    public String getCustomView() {
        return customView;
    }

    public void setCustomView(String customView) {
        this.customView = customView;
    }

    public int getTot() {
        return tot;
    }

    public void setTot(int tot) {
        this.tot = tot;
    }

    public int getRnum() {
        return rnum;
    }

    public void setRnum(int rnum) {
        this.rnum = rnum;
    }

    public String getSearchType1() {
        return searchType1;
    }

    public void setSearchType1(String searchType1) {
        this.searchType1 = searchType1;
    }

    public String getSearchType2() {
        return searchType2;
    }

    public void setSearchType2(String searchType2) {
        this.searchType2 = searchType2;
    }

    public String getSearchType3() {
        return searchType3;
    }

    public void setSearchType3(String searchType3) {
        this.searchType3 = searchType3;
    }

    public String getSearchType4() {
        return searchType4;
    }

    public void setSearchType4(String searchType4) {
        this.searchType4 = searchType4;
    }

    public String getSearchType5() {
        return searchType5;
    }

    public void setSearchType5(String searchType5) {
        this.searchType5 = searchType5;
    }

    public String getSearchType6() {
        return searchType6;
    }

    public void setSearchType6(String searchType6) {
        this.searchType6 = searchType6;
    }

    public String getSearchType7() {
        return searchType7;
    }

    public void setSearchType7(String searchType7) {
        this.searchType7 = searchType7;
    }

    public String getSearchType8() {
        return searchType8;
    }

    public void setSearchType8(String searchType8) {
        this.searchType8 = searchType8;
    }

    public String getSearchKeyword1() {
        return searchKeyword1;
    }

    public void setSearchKeyword1(String searchKeyword1) {
        this.searchKeyword1 = searchKeyword1;
    }

    public String getSearchKeyword2() {
        return searchKeyword2;
    }

    public void setSearchKeyword2(String searchKeyword2) {
        this.searchKeyword2 = searchKeyword2;
    }

    public String getSearchKeyword3() {
        return searchKeyword3;
    }

    public void setSearchKeyword3(String searchKeyword3) {
        this.searchKeyword3 = searchKeyword3;
    }
}
