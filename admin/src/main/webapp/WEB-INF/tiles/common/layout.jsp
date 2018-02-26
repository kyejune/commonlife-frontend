<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="ko">
<head>
    <title>COMMONLife ADMIN - <tiles:insertAttribute name="title" /></title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="/resources/css/animate.css" rel="stylesheet">
    <link href="/resources/css/style.css" rel="stylesheet">
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
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="/resources/js/inspinia.js"></script>
    <script src="/resources/js/plugins/pace/pace.min.js"></script>

    <tiles:insertAttribute name="js" />
</body>
</html>
