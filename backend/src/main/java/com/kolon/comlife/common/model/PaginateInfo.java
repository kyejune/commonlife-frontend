package com.kolon.comlife.common.model;

import java.util.List;

public class PaginateInfo extends SimpleMsgInfo {
    private int currentPage;
    private double totalPages;
    private int perPage;
    private List data;
    private String feedWriteAllowYn;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public double getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(double totalPages) {
        this.totalPages = totalPages;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public String getFeedWriteAllowYn() {
        return feedWriteAllowYn;
    }

    public void setFeedWriteAllowYn(String feedWriteAllowYn) {
        this.feedWriteAllowYn = feedWriteAllowYn;
    }
}
