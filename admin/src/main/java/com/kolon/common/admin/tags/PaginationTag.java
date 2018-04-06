package com.kolon.common.admin.tags;

import com.kolon.comlife.common.paginate.DefaultPaginationManager;
import com.kolon.comlife.common.paginate.PaginateInfo;
import com.kolon.comlife.common.paginate.PaginationManager;
import com.kolon.comlife.common.paginate.PaginationRenderer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag
        extends TagSupport
{
    private static final long serialVersionUID = 1L;
    private PaginateInfo paginationInfo;
    private String type;
    private String jsFunction;

    public int doEndTag()
            throws JspException
    {
        try
        {
            JspWriter out = this.pageContext.getOut();

            WebApplicationContext ctx = RequestContextUtils.getWebApplicationContext(this.pageContext.getRequest(), this.pageContext.getServletContext());
            PaginationManager paginationManager;

            if (ctx.containsBean("paginationManager")) {
                paginationManager = (PaginationManager)ctx.getBean("paginationManager");
            } else {
                paginationManager = new DefaultPaginationManager();
            }
            PaginationRenderer paginationRenderer = paginationManager.getRendererType(this.type);

            String contents = paginationRenderer.renderPagination(this.paginationInfo, this.jsFunction);

            out.println(contents);

            return 6;
        }
        catch (IOException e)
        {
            throw new JspException();
        }
    }

    public void setJsFunction(String jsFunction)
    {
        this.jsFunction = jsFunction;
    }

    public void setPaginationInfo(PaginateInfo paginationInfo)
    {
        this.paginationInfo = paginationInfo;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}