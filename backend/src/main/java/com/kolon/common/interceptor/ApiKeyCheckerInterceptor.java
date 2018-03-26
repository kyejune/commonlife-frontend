package com.kolon.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// todo: IOT-41 개발 단계에서 임시적으로 사용하는 ApiKey 체커입니다. 사용자 인증이 연동되면 삭제하시기 바랍니다.
@Component
public class ApiKeyCheckerInterceptor extends HandlerInterceptorAdapter {

    protected static final Logger logger = LoggerFactory.getLogger(ApiKeyCheckerInterceptor.class);

    static private String API_KEY_NAME = "api_key";
    private String expectedApiKey;
    private String apiTestYN;

    public void setApiTestYN(String apiTestYN) {
        this.apiTestYN = apiTestYN;
    }

    public void setApiKey(String apiKey) { this.expectedApiKey = apiKey; }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception
    {

        // 1. 모든 OPTIONS 요청에 대해 api_key 체크하지 않음
        if ( request.getMethod().equals("OPTIONS") ) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 2. /status 에 대한 요청은 통과
        logger.debug(">>>> request path: " + request.getRequestURL());
        if ( request.getRequestURL().indexOf("/status/") > 0 ) {
            return true;
        } else if ( request.getRequestURL().indexOf("/postFiles/") > 0 ) {
            return true;
        }

        // 3. Header의 Api_key 체크
//        logger.debug( "apiTestYN>>>>>[" + this.apiTestYN + "]" );
        if( "Y".equals(this.apiTestYN) ) {
            logger.debug("API key check: ON");

            String apiKey = request.getHeader(API_KEY_NAME);


            if( expectedApiKey.equals(apiKey) == true ) {
                return true;
            }
            else {
                logger.debug("API key is mismatched.");

                response.setStatus( HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("Content-type", "application/json");
                response.getWriter().write("{\"msg\": \"api_key가 잘못되었습니다.\"}");
                response.getWriter().flush();
                response.getWriter().close();

                return false;
            }
        }
        else {
            logger.debug("API key check: OFF");
        }

        return true;
    }
}
