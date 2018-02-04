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
                return false;
            }
        }
        else {
            logger.debug("API key check: OFF");
        }

        return true;
    }
}