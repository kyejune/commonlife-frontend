package com.kolon.common.pagination;

public abstract interface PaginationRenderer
{
    public abstract String renderPagination(PaginationInfo paramPaginationInfo, String paramString);
}
