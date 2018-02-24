package com.kolon.common.admin.pagination;

public abstract interface PaginationManager
{
    public abstract PaginationRenderer getRendererType(String paramString);
}
