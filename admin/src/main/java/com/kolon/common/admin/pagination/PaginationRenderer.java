package com.kolon.common.admin.pagination;

public abstract interface PaginationRenderer
{
    public abstract String renderPagination(PaginationInfo paramPaginationInfo, String paramString);
}
