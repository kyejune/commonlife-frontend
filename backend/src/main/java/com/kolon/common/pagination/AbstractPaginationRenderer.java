package com.kolon.common.pagination;

import java.text.MessageFormat;

public class AbstractPaginationRenderer implements PaginationRenderer{
    public String firstPageLabel;
    public String previousPageLabel;
    public String currentPageLabel;
    public String otherPageLabel;
    public String nextPageLabel;
    public String lastPageLabel;

    public String renderPagination(PaginationInfo paginationInfo, String jsFunction)
    {
        StringBuffer strBuff = new StringBuffer();

        int firstPageNo = paginationInfo.getFirstPageNo();
        int firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
        int totalPageCount = paginationInfo.getTotalPageCount();
        int pageSize = paginationInfo.getPageSize();
        int lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
        int currentPageNo = paginationInfo.getCurrentPageNo();
        int lastPageNo = paginationInfo.getLastPageNo();

        //strBuff.append("<div class=\"paging\" style=\"width:530px\">");
        strBuff.append("<div id=\"paging\">");

        if (totalPageCount > pageSize) {
            strBuff.append("<p class=\"left\">");
            if (firstPageNoOnPageList > pageSize)
            {
                strBuff.append(MessageFormat.format(this.firstPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
                strBuff.append(MessageFormat.format(this.previousPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList - 1) }));
            }
            else
            {
                strBuff.append(MessageFormat.format(this.firstPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
                strBuff.append(MessageFormat.format(this.previousPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
            }
            strBuff.append("</p>");
        }
        strBuff.append("<p class=\"center\">");
        for (int i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
            if (i == currentPageNo) {
                strBuff.append(MessageFormat.format(this.currentPageLabel, new Object[] { Integer.toString(i) }));
            } else {
                strBuff.append(MessageFormat.format(this.otherPageLabel, new Object[] { jsFunction, Integer.toString(i), Integer.toString(i) }));
            }
        }
        strBuff.append("</p>");
        if (totalPageCount > pageSize) {
            strBuff.append("<p class=\"right\">");
            if (lastPageNoOnPageList < totalPageCount)
            {
                strBuff.append(MessageFormat.format(this.nextPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList + pageSize) }));
                strBuff.append(MessageFormat.format(this.lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
            }
            else
            {
                strBuff.append(MessageFormat.format(this.nextPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
                strBuff.append(MessageFormat.format(this.lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
            }
            strBuff.append("</p>");
        }
        strBuff.append("</div>");
        return strBuff.toString();
    }
}
