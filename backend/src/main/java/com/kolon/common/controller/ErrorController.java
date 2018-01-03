package com.kolon.common.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * 에러 처리 공통 컨트롤러
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
@ControllerAdvice
@RequestMapping(value = "/error/*")
public class ErrorController {
    @RequestMapping(value = {"/ssoerror.*"}, method = RequestMethod.GET)
    public String loginPage(HttpSession session) {

        return "/error/ssoerror";
    }

//    /*
//	 * 시스템 에러 view (default 오류)
//	 */
//    @Value("#{common['errorSystemView']}")
//    private String errorSystemView;
//
//    /*
//     * 404 에러 view
//     */
//    @Value("#{common['error404View']}")
//    private String error404View;
//
//    /*
//     * 505 에러 view
//     */
//    @Value("#{common['error505View']}")
//    private String error505View;
//
//    /*
//     * 데이터 접근 에러 view
//     */
//    @Value("#{common['errorAccessView']}")
//    private String errorAccessView;
//
//    /*
//     * 세션 종료 에러 view
//     */
//    @Value("#{common['errorSessionView']}")
//    private String errorSessionView;
//
//    /*
//     * 권한 에러 view
//     */
//    @Value("#{common['errorAuthView']}")
//    private String errorAuthView;
//
//    @RequestMapping(value = "errorSystem.*")
//    public ModelAndView errorSystem() {
//        return new ModelAndView(errorSystemView);
//    }
//
//    @RequestMapping(value = "error404.*")
//    public ModelAndView error404() {
//        return new ModelAndView(error404View);
//    }
//
//    @RequestMapping(value = "error505.*")
//    public ModelAndView error505() {
//        return new ModelAndView(error505View);
//    }
//
//    @RequestMapping(value = "errorAccess.*")
//    public ModelAndView errorAccess() {
//        return new ModelAndView(errorAccessView);
//    }
//
//    @RequestMapping(value = "errorSession.*")
//    public ModelAndView errorSession() {
//        return new ModelAndView(errorSessionView);
//    }
//
//    @RequestMapping(value = "errorAuth.*")
//    public ModelAndView errorAuth() {
//        return new ModelAndView(errorAuthView);
//    }
}
