package com.kolon.comlife.common.paginate;


public abstract interface PaginationManager
{
    public abstract PaginationRenderer getRendererType(String paramString);
}
