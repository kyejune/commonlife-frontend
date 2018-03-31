<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="clear">
                            <span class="block m-t-xs">
                                <strong class="font-bold">{ADMIN NAME}</strong>
                            </span>
                            <span class="text-muted text-xs block">
                                {ADMIN GROUP} <b class="caret"></b>
                            </span>
                        </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li><a href="#">Logout</a></li>
                    </ul>
                </div>
                <div class="logo-element">
                    IN+
                </div>
            </li>
            <li>
                <a href="?">
                    <i class="fa fa-th-large"></i>
                    <span class="nav-label">Main View</span></a>
            </li>
            <li id="left_complex">
                <a href="#">
                    <i class="fa fa-table"></i>
                    <span class="nav-label">현장 그룹 관리</span><span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li id="left_complex_category"><a href="/admin/complexes/category.do">현장 분류</a></li>
                    <li id="left_complex_list"><a href="/admin/complexes/list.do">개별 현장 관리</a></li>
                </ul>
            </li>
            <li id="left_admin">
                <a href="#">
                    <i class="fa fa-table"></i>
                    <span class="nav-label">관리자 관리</span><span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li id="left_admin_all"><a href="/admin/managers/list.do">전체 관리자 목록</a></li>
                    <li id="left_admin_super"><a href="/admin/managers/list.do?grpId=0">슈퍼관리자 관리</a></li>
                    <li id="left_admin_complex"><a href="/admin/managers/list.do?grpId=1">현장관리자 관리</a></li>
                    <li id="left_admin_log"><a href="/admin/managers/log.do">작업 로그</a></li>
                </ul>
            </li>
            <li id="left_properties">
                <a href="#">
                    <i class="fa fa-table"></i>
                    <span class="nav-label">환경설정 관리</span><span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li id="left_properties_all"><a href="/admin/properties/list.do">Properties 관리</a></li>
                </ul>
            </li>
            <li >
                <a href="#">
                    <i class="fa fa-table"></i>
                    <span class="nav-label">세대주/사용자 관리</span><span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse">
                    <li><a href="/admin/managers/list.do">세대주 목록</a></li>
                    <li><a href="/admin/managers/list.do">사용자 목록</a></li>
                </ul>
            </li>
            <li>
                <a href="#">
                    <i class="fa fa-calendar"></i>
                    <span class="nav-label">예약 관리<span class="fa arrow"></span></span>
                </a>
                <ul class="nav nav-second-level callapse">
                    <li><a href="/admin/reservation-schemes/list.do">예약 틀 관리</a></li>
                    <li><a href="/admin/reservation-amenities/list.do">예약 시설 옵션 관리</a></li>
                    <li><a href="/admin/reservations/list.do">예약 관리</a></li>
                </ul>
            </li>
        </ul>

    </div>
</nav>
