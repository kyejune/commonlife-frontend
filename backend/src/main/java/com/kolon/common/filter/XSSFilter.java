package com.kolon.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 시스템 XXSFilter
 * @author Cho Sin Deuck
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-07    조신득          최초 생성
 * </pre>
 */
public class XSSFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {

    }


    public void destroy() {

    }


    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)

            throws IOException, ServletException {
        //System.out.println("doFilter");
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request),
                response);

    }

}