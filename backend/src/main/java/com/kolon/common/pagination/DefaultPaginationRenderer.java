package com.kolon.common.pagination;

public class DefaultPaginationRenderer
        extends AbstractPaginationRenderer
{
    public DefaultPaginationRenderer()
    {
        /*
        this.firstPageLabel = "<a href=\"#\" class=\"before\" title=\"처음\" onclick=\"{0}({1}); return false;\">처음</a>&#160;";
        this.previousPageLabel = "<a href=\"#\" class=\"first\" title=\"이전\" onclick=\"{0}({1}); return false;\"></a>&#160;";
        this.currentPageLabel = "<a href=\"#\" class=\"pageNumber active\">{0}</a>&#160;";
        this.otherPageLabel = "<a href=\"#\" class=\"pageNumber\" onclick=\"{0}({1}); return false;\">{2}</a>&#160;";
        this.nextPageLabel = "<a href=\"#\" class=\"end\" title=\"다음\" onclick=\"{0}({1}); return false;\"></a>&#160;";
        this.lastPageLabel = "<a href=\"#\" class=\"next\" title=\"끝\" onclick=\"{0}({1}); return false;\">끝</a>&#160;";
        */

        this.firstPageLabel = "<a href=\"javascript:\" class=\"btn_first\" title=\"처음\" onclick=\"{0}({1}); return false;\">" +
                "<img src=\"/images/page_first.png\" alt=\"처음으로\" /></a>&#160;";
        this.previousPageLabel = "<a href=\"javascript: \" class=\"btn_prev\" title=\"이전\" onclick=\"{0}({1}); return false;\">" +
                "<img src=\"/images/page_prev.png\" alt=\"이전으로\" /></a>&#160;";
        this.currentPageLabel = "<a href=\"javascript:\" class=\"on\">{0}</a>&#160;";
        this.otherPageLabel = "<a href=\"javascript:\" onclick=\"{0}({1}); return false;\">{2}</a>&#160;";
        this.nextPageLabel = "<a href=\"javascript:\" class=\"btn_next\" title=\"다음\" onclick=\"{0}({1}); return false;\">" +
                "<img src=\"/images/page_next.png\" alt=\"다음으로\" /></a>&#160;";
        this.lastPageLabel = "<a href=\"javascript:\" class=\"btn_last\" title=\"끝\" onclick=\"{0}({1}); return false;\">" +
                "<img src=\"/images/page_last.png\" alt=\"끝으로\" /></a>&#160;";
    }

    public String renderPagination(PaginationInfo paginationInfo, String jsFunction)
    {
        return super.renderPagination(paginationInfo, jsFunction);
    }
}
