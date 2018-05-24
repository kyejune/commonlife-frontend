<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <li class="nav-header">

                <div class="dropdown profile-element">
                    <img class="nav-header__logo" src="/resources/img/logo_default.png" alt="">
                    <c:if test="${adminInfo.adminNm != null}">
                        <p class="nav-header__welcome">
                            이름 : <strong class="font-bold">${adminInfo.adminNm}</strong><br/>
                            권한 : <strong class="font-bold">${adminInfo.grpNm}</strong>
                            <%--<c:if test="${adminInfo.grpNm eq '현장'}">--%>
                                <%--<br/>담당현장 : ${adminInfo.cmplxNm}--%>
                            <%--</c:if>--%>
                        </p>
                    </c:if>
                </div>
                <div class="logo-element">
                    <img src="/resources/img/logo_small.png" alt="">
                </div>
            </li>
            <%--<li>--%>
                <%--<a href="?">--%>
                    <%--<i class="fa fa-th-large"></i>--%>
                    <%--<span class="nav-label">Main View</span></a>--%>
            <%--</li>--%>
            <li>
                <a href="#">
                    <i class="fa fa-tasks"></i>
                    <span class="nav-label">HOME</span><span class="fa arrow"></span>
                    <ul class="nav nav-second-level collapse">
                        <li><a href="/">DASHBOARD(준비중)</a></li>
                    </ul>
                </a>
            </li>
            <c:if test="${adminInfo.grpNm != '슈퍼'}">
            <li id="left_feed">
                <a href="#">
                    <i class="fa fa-coffee"></i>
                    <span class="nav-label">Community </span><span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li id="left_feed_event"><a href="/admin/posts/eventList.do">EVENT 관리</a></li>
                    <li id="left_feed_notice"><a href="/admin/posts/noticeList.do">NOTICE 관리</a></li>
                    <li id="left_feed_feed"><a href="/admin/posts/feedList.do">사용자 작성글 관리</a></li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-calendar"></i>
                    <span class="nav-label">Reservation <span class="fa arrow"></span></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li><a href="/admin/reservations/list.do">예약 현황 (목록형)</a></li>
                    <li><a href="/admin/reservations/calendar.do">예약 현황 (달력형)</a></li>
                    <%--<li><a href="#">예약 등록</a></li>--%>
                </ul>
            </li>
            <li id="left_user">
                <a href="#">
                    <i class="fa fa-user"></i>
                    <span class="nav-label">Member </span><span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li id="left_user_head"><a href="/admin/users/headList.do">세대주 목록</a></li>
                    <li id="left_user_user"><a href="/admin/users/userList.do">사용자 목록</a></li>
                </ul>
            </li>
            <li >
                <a href="#">
                    <i class="fa fa-laptop"></i>
                    <span class="nav-label">현장지원</span><span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li><a href="/admin/support/ticket/ticketList.do">Living Support 지원</a></li>
                </ul>
            </li>
            <%--<li id="left_info">--%>
                <%--<a href="#">--%>
                    <%--<i class="fa fa-table"></i>--%>
                    <%--<span class="nav-label">현장| INFO</span><span class="fa arrow"></span>--%>
                <%--</a>--%>
                <%--<ul class="nav nav-second-level collapse">--%>
                    <%--<li id="left_info_guide"><a href="/admin/info/guide/list.do">Living Guide 관리</a></li>--%>
                    <%--<li id="left_info_benefits"><a href="/admin/info/benefits/list.do">Benefits 관리</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>

            </c:if>
            <c:if test="${adminInfo.grpNm == '슈퍼'}">
            <li id="left_complex">
                <a href="#">
                    <i class="fa fa-cube"></i>
                    <span class="nav-label">현장 관리</span><span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li id="left_complex_category"><a href="/admin/complexes/category.do">현장 그룹</a></li>
                    <li id="left_complex_list"><a href="/admin/complexes/list.do">현장 등록</a></li>
                </ul>
            </li>
            </c:if>
            <li>
                <a href="#">
                    <i class="fa fa-calendar-o"></i>
                    <span class="nav-label">예약 관리<span class="fa arrow"></span></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li><a href="/admin/reservation-groups/list.do">예약 관리</a></li>
                    <li><a href="/admin/reservation-amenities/list.do">시설 옵션 관리</a></li>
                    <%--<li><a href="/admin/reservations/queue.do">장기 예약 신청 관리</a></li>--%>
                </ul>
            </li>
            <c:if test="${adminInfo.grpNm == '슈퍼'}">
            <li id="left_admin">
                <a href="#">
                    <i class="fa fa-id-badge"></i>
                    <span class="nav-label">관리자 관리</span><span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li id="left_admin_all"><a href="/admin/managers/list.do">전체 관리자 목록</a></li>
                    <li id="left_admin_super"><a href="/admin/managers/list.do?grpId=0">슈퍼관리자 관리</a></li>
                    <li id="left_admin_complex"><a href="/admin/managers/list.do?grpId=1">현장관리자 관리</a></li>
                    <%--<li id="left_admin_log"><a href="/admin/managers/log.do">작업 로그</a></li>--%>
                </ul>
            </li>
            </c:if>
            <%--<li id="left_properties">--%>
                <%--<a href="#">--%>
                    <%--<i class="fa fa-table"></i>--%>
                    <%--<span class="nav-label">슈퍼| 환경설정 관리</span><span class="fa arrow"></span>--%>
                <%--</a>--%>
                <%--<ul class="nav nav-second-level collapse">--%>
                    <%--<li id="left_properties_all"><a href="/admin/properties/list.do">Properties 관리</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>

        </ul>

    </div>
</nav>
