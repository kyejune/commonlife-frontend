<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="ko">
<head>
    <title>COMMONLife ADMIN - <tiles:insertAttribute name="title" /></title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%--// CSRF--%>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="/resources/css/plugins/jasny/jasny-bootstrap.min.css" rel="stylesheet">

    <!-- FooTable -->
    <link href="/resources/css/plugins/footable/footable.core.css" rel="stylesheet">
    <link href="/resources/css/plugins/datapicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="/resources/css/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
    <link href="/resources/css/plugins/fullcalendar/fullcalendar.print.css" rel="stylesheet">

    <%--chosen--%>
    <link href="/resources/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link href="/resources/css/plugins/chosen/image-select.css" rel="stylesheet">
    <link href="/resources/css/plugins/chosen/flat.css" rel="stylesheet">

    <%--Switchery--%>
    <link href="/resources/css/plugins/switchery/switchery.css" rel="stylesheet">

    <%--Touchspin--%>
    <link href="/resources/css/plugins/touchspin/jquery.bootstrap-touchspin.min.css" rel="stylesheet">

    <link href="/resources/css/animate.css" rel="stylesheet">
    <link href="/resources/css/style.css" rel="stylesheet">
    <link href="/resources/css/commonlife.css" rel="stylesheet">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <tiles:insertAttribute name="css" />
    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body>
    <div id="wrapper">
        <tiles:insertAttribute name="header"/>
        <div id="page-wrapper" class="gray-bg">
            <tiles:insertAttribute name="contents-header"/>
            <tiles:insertAttribute name="contents"/>
            <tiles:insertAttribute name="footer"/>
        </div>
    </div>
    <!-- Mainly scripts -->
    <script src="/resources/js/underscore-min.js"></script>
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/plugins/moment/moment.js"></script>
    <script src="/resources/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="/resources/js/plugins/jasny/jasny-bootstrap.min.js"></script>

    <%--chosen--%>
    <script src="/resources/js/plugins/chosen/chosen.jquery.js"></script>
    <script src="/resources/js/plugins/chosen/image-select.jquery.js"></script>

    <%--Switchery--%>
    <script src="/resources/js/plugins/switchery/switchery.js"></script>

    <%--Touchspin--%>
    <script src="/resources/js/plugins/touchspin/jquery.bootstrap-touchspin.min.js"></script>

    <!-- FooTable -->
    <script src="/resources/js/plugins/footable/footable.all.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="/resources/js/inspinia.js"></script>
    <script src="/resources/js/plugins/pace/pace.min.js"></script>
    <script src="/resources/js/plugins/datapicker/bootstrap-datetimepicker.min.js"></script>
    <script src="/resources/js/plugins/fullcalendar/fullcalendar.min.js"></script>

    <tiles:insertAttribute name="js" />
</body>
</html>
