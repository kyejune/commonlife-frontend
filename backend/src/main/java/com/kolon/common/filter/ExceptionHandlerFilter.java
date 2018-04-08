package com.kolon.common.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    static final Logger logger = LoggerFactory.getLogger( ExceptionHandlerFilter.class );
    static final String errorMsg = "내부 오류가 발생하여 명령을 처리할 수 없습니다. 만약, 문제가 지속된다면 관리자에게 문의하세요.";

    public void doFilterInternal( HttpServletRequest   request,
                                  HttpServletResponse  response,
                                  FilterChain          filterChain) throws ServletException, IOException {
        SimpleErrorInfo errorResponse;
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            logger.error( ">>>" + e.getMessage() );
            // custom error response class used across my project
            errorResponse = new SimpleErrorInfo( ExceptionHandlerFilter.errorMsg );

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write( this.convertObjectToJson(errorResponse) );
            response.setContentType( MediaType.APPLICATION_JSON_VALUE );
        }
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
