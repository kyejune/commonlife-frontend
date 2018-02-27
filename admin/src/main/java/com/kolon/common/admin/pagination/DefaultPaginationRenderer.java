package com.kolon.common.admin.pagination;

public class DefaultPaginationRenderer
        extends AbstractPaginationRenderer
{
    public DefaultPaginationRenderer()
    {
//        this.firstPageLabel = "<li class=\"paginate_button previous \"><a href=\"#\" title=\"처음\" onclick=\"{0}({1}); return false;\">처음</a></li>&#160;";
//        this.previousPageLabel = "<li class=\"paginate_button previous \"><a href=\"#\" title=\"이전\" onclick=\"{0}({1}); return false;\">이전</a></li>&#160;";
//        this.currentPageLabel = "<li class=\"paginate_button active\"><a href=\"#\">{0}</a></li>&#160;";
//        this.otherPageLabel = "<li class=\"paginate_button \"><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a></li>&#160;";
//        this.nextPageLabel = "<li class=\"paginate_button next \"><a href=\"#\" title=\"다음\" onclick=\"{0}({1}); return false;\">다음</a></li>&#160;";
//        this.lastPageLabel = "<li class=\"paginate_button next \"><a href=\"#\" title=\"끝\" onclick=\"{0}({1}); return false;\">끝</a></li>&#160;";

        this.firstPageLabel = "<li class=\"footable-page-arrow \"><a href=\"#\" title=\"처음\" data-page=\"first\" onclick=\"{0}({1}); return false;\">«</a></li>&#160;";
        this.previousPageLabel = "<li class=\"footable-page-arrow \"><a href=\"#\" title=\"이전\" data-page=\"prev\" onclick=\"{0}({1}); return false;\">‹</a></li>&#160;";
        this.currentPageLabel = "<li class=\"footable-page active\"><a href=\"#\">{0}</a></li>&#160;";
        this.otherPageLabel = "<li class=\"footable-page \"><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a></li>&#160;";
        this.nextPageLabel = "<li class=\"footable-page-arrow \"><a href=\"#\" title=\"다음\" data-page=\"next\" onclick=\"{0}({1}); return false;\">›</a></li>&#160;";
        this.lastPageLabel = "<li class=\"footable-page-arrow \"><a href=\"#\" title=\"끝\" data-page=\"last\" onclick=\"{0}({1}); return false;\">»</a></li>&#160;";
    }

    public String renderPagination(PaginationInfo paginationInfo, String jsFunction)
    {
        return super.renderPagination(paginationInfo, jsFunction);
    }
}
