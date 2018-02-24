package com.kolon.common.admin.pagination;

public class DefaultPaginationRenderer
        extends AbstractPaginationRenderer
{
    public DefaultPaginationRenderer()
    {
//        this.firstPageLabel = "<a href=\"#\" class=\"before\" title=\"처음\" onclick=\"{0}({1}); return false;\">처음</a>&#160;";
//        this.previousPageLabel = "<a href=\"#\" class=\"paginate_button previous\" title=\"이전\" onclick=\"{0}({1}); return false;\"></a>&#160;";
//        this.currentPageLabel = "<a href=\"#\" class=\"paginate_button active\">{0}</a>&#160;";
//        this.otherPageLabel = "<a href=\"#\" class=\"paginate_button \" onclick=\"{0}({1}); return false;\">{2}</a>&#160;";
//        this.nextPageLabel = "<a href=\"#\" class=\"paginate_button next\" title=\"다음\" onclick=\"{0}({1}); return false;\"></a>&#160;";
//        this.lastPageLabel = "<a href=\"#\" class=\"next\" title=\"끝\" onclick=\"{0}({1}); return false;\">끝</a>&#160;";

        this.firstPageLabel = "<li class=\"paginate_button previous \"><a href=\"#\" title=\"처음\" onclick=\"{0}({1}); return false;\">처음</a></li>&#160;";
        this.previousPageLabel = "<li class=\"paginate_button previous \"><a href=\"#\" title=\"이전\" onclick=\"{0}({1}); return false;\">이전</a></li>&#160;";
        this.currentPageLabel = "<li class=\"paginate_button active\"><a href=\"#\">{0}</a></li>&#160;";
        this.otherPageLabel = "<li class=\"paginate_button \"><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a></li>&#160;";
        this.nextPageLabel = "<li class=\"paginate_button next \"><a href=\"#\" title=\"다음\" onclick=\"{0}({1}); return false;\">다음</a></li>&#160;";
        this.lastPageLabel = "<li class=\"paginate_button next \"><a href=\"#\" title=\"끝\" onclick=\"{0}({1}); return false;\">끝</a></li>&#160;";

    }

    public String renderPagination(PaginationInfo paginationInfo, String jsFunction)
    {
        return super.renderPagination(paginationInfo, jsFunction);
    }
}
