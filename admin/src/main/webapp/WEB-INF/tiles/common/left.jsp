<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>

<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- LEFT MENU START
        ==========================================================================================================
         -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu" data-widget="tree">
            <li class="header">MAIN NAVIGATION</li>
            <!--
            <li class="active menu-open" id="left_li_menu_01">
            -->
            <li class="treeview" id="left_li_menu_01">
                <a href="#">
                    <i class="fa fa-files-o"></i>
                    <span>식사메뉴</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                        </span>
                </a>
                <ul class="treeview-menu">
                    <li id="left_li_menu_01_01"><a href="/manage/foodcourt/list.do"><i class="fa fa-circle-o"></i> 푸드코드</a></li>
                </ul>
            </li>
            <li id="left_li_menu_02">
                <a href="/manage/shop/special_shop_list.do">
                    <i class="fa fa-edit"></i>
                    <span>특별행사</span>
                </a>
            </li>
            <li class="treeview" id="left_li_menu_03">
                <a href="#">
                    <i class="fa fa-files-o"></i>
                    <span>덕평소식</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                        </span>
                </a>
                <ul class="treeview-menu">
                    <li id="left_li_menu_03_01"><a href="/manage/board/notice_list.do"><i class="fa fa-circle-o"></i> 공지사항</a></li>
                    <li id="left_li_menu_03_02"><a href="/manage/board/event_list.do"><i class="fa fa-circle-o"></i> 이벤트</a></li>
                    <li id="left_li_menu_03_03"><a href="/manage/board/win_list.do"><i class="fa fa-circle-o"></i> 당첨자공지</a></li>
                    <li id="left_li_menu_03_04"><a href="/manage/topbannerevent/list.do"><i class="fa fa-circle-o"></i> 탑 배너 이벤트</a></li>
                    <li id="left_li_menu_03_05"><a href="/manage/facebook/list.do"><i class="fa fa-circle-o"></i> 페이스북 관리</a></li>
                </ul>
            </li>

            <li class="treeview" id="left_li_menu_04">
                <a href="#">
                    <i class="fa fa-files-o"></i>
                    <span>고객센터</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                        </span>
                </a>
                <ul class="treeview-menu">
                    <li id="left_li_menu_04_00"><a href="/manage/customer/fna_list.do"><i class="fa fa-circle-o"></i> 자주하는 질문</a></li>
                    <li id="left_li_menu_04_01"><a href="/manage/customer/voc_list.do"><i class="fa fa-circle-o"></i> 고객의 소리</a></li>
                    <li id="left_li_menu_04_02"><a href="/manage/customer/order_list.do"><i class="fa fa-circle-o"></i> 단체주문</a></li>
                    <li id="left_li_menu_04_03"><a href="/manage/customer/etc_list.do"><i class="fa fa-circle-o"></i> 입점/제휴/기타 문의</a></li>
                </ul>
            </li>
            <li id="left_li_menu_05">
                <a href="/manage/managers/list.do">
                    <i class="fa fa-edit"></i>
                    <span>관리자</span>
                </a>
            </li>

            <li id="left_li_menu_06">
                <a href="/manage/emailSendManage/list.do">
                    <i class="fa fa-edit"></i>
                    <span>EMAIL수신관리</span>
                </a>
            </li>

            <li id="left_li_menu_07">
                <a href="/manage/properties/list.do">
                    <i class="fa fa-edit"></i>
                    <span>시스템정보 관리</span>
                </a>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>