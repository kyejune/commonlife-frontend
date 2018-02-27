package com.kolon.common.admin.pagination;

import java.util.Map;

public class DefaultPaginationManager
        implements PaginationManager
{
    private Map<String, PaginationRenderer> rendererType;

    public void setRendererType(Map<String, PaginationRenderer> rendererType)
    {
        this.rendererType = rendererType;
    }

    public PaginationRenderer getRendererType(String type)
    {
        return (this.rendererType != null) && (this.rendererType.containsKey(type)) ?
                (PaginationRenderer)this.rendererType.get(type) : new DefaultPaginationRenderer();
    }
}

