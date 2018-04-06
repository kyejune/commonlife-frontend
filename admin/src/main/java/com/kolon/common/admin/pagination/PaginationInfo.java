package com.kolon.common.admin.pagination;

import com.kolon.comlife.common.model.SimpleMsgInfo;

public class PaginationInfo extends SimpleMsgInfo {
    private int currentPageNo;
    private int recordCountPerPage;
    private int pageSize;
    private int totalRecordCount;
    private int totalPageCount;
    private int firstPageNoOnPageList;
    private int lastPageNoOnPageList;
    private int firstRecordIndex;
    private int lastRecordIndex;

    public PaginationInfo() {
        super();
    };

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getRecordCountPerPage() {
        return recordCountPerPage;
    }

    public void setRecordCountPerPage(int recordCountPerPage) {
        this.recordCountPerPage = recordCountPerPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    public void setFirstPageNoOnPageList(int firstPageNoOnPageList) {
        this.firstPageNoOnPageList = firstPageNoOnPageList;
    }

    public void setLastPageNoOnPageList(int lastPageNoOnPageList) {
        this.lastPageNoOnPageList = lastPageNoOnPageList;
    }

    public void setFirstRecordIndex(int firstRecordIndex) {
        this.firstRecordIndex = firstRecordIndex;
    }

    public void setLastRecordIndex(int lastRecordIndex) {
        this.lastRecordIndex = lastRecordIndex;
    }

    public int getTotalPageCount()
    {
        this.totalPageCount = ((getTotalRecordCount() - 1) / getRecordCountPerPage() + 1);
        return this.totalPageCount;
    }

    public int getFirstPageNo()
    {
        return 1;
    }

    public int getLastPageNo()
    {
        return getTotalPageCount();
    }

    public int getFirstPageNoOnPageList()
    {
        this.firstPageNoOnPageList = ((getCurrentPageNo() - 1) / getPageSize() * getPageSize() + 1);
        return this.firstPageNoOnPageList;
    }

    public int getLastPageNoOnPageList()
    {
        this.lastPageNoOnPageList = (getFirstPageNoOnPageList() + getPageSize() - 1);
        if (this.lastPageNoOnPageList > getTotalPageCount()) {
            this.lastPageNoOnPageList = getTotalPageCount();
        }
        return this.lastPageNoOnPageList;
    }

    public int getFirstRecordIndex()
    {
        this.firstRecordIndex = ((getCurrentPageNo() - 1) * getRecordCountPerPage());
        return this.firstRecordIndex;
    }

    public int getLastRecordIndex()
    {
        this.lastRecordIndex = (getCurrentPageNo() * getRecordCountPerPage());
        return this.lastRecordIndex;
    }
}
