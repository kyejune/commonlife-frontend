package com.kolon.common.pagination;

public abstract interface PaginationManager
{
    public abstract PaginationRenderer getRendererType(String paramString);
}
