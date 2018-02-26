<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="manage">
    <tiles:putAttribute name="title">메인관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <jsp:include page ="/WEB-INF/tiles/common/left.jsp" flush="true"/>

        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    식사메뉴
                    <!--<small>Version 1.0</small>-->
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 식사메뉴</a></li>
                    <li class="active">식사메뉴 디테일</li>
                </ol>
            </section>

            <!-- Main content -->

            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script type="text/javascript">
            $(function () {

                $("#left_li_menu_01").addClass("active");
            })
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>