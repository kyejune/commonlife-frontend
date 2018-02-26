<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <title>관리자 페이지 로그인</title>

    <!-- Bootstrap 3.3.7 -->
    <link type="text/css" rel="stylesheet" href="/resources/bootstrap/css/bootstrap.min.css" />
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/resources/font-awesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/resources/adminlte/css/AdminLTE.min.css">

    <!-- Ionicons -->
    <link rel="stylesheet" href="/resources/Ionicons/css/ionicons.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="/resources/iCheck/square/blue.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="/resources/html5shiv.js"></script>
    <script src="/resources/respond.min.js"></script>
    <![endif]-->

    <!-- Google Font -->
    <!--
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
    -->
</head>

<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="#">ADMIN</a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <!--
        <p class="login-box-msg">Sign in to start your session</p>
        -->

        <form:form name="form" method="post">
            <div class="form-group has-feedback">
                <input type="text" class="form-control" id="mngId" name="mngId" placeholder="ID">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" placeholder="Password" name="mngPw" id="mngPw">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">

                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <!--
                    <button onclick="login()" class="btn btn-primary btn-block btn-flat">Sign in</button>
                    -->
                    <input type="button" onclick="login()" class="btn btn-primary btn-block btn-flat" value="Sign in">
                </div>
                <!-- /.col -->
            </div>
            <c:if test="${param.fail == 'true'}">
                <div class="error">아이디 또는 비밀번호가 일치하지 않습니다.</div>
            </c:if>
        </form:form>

        <!--
        <div class="social-auth-links text-center">
            <p>- OR -</p>
            <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using
                Facebook</a>
            <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in using
                Google+</a>
        </div>
        <!--
        <!-- /.social-auth-links -->
        <!--
        <a href="#">I forgot my password</a><br>
        <a href="register.html" class="text-center">Register a new membership</a>
        -->
    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script type="text/javascript" src="/resources/jquery-1.12.1.min.js"></script>
<!-- Bootstrap -->
<script type="text/javascript" src="/resources/bootstrap/bootstrap.min.js"></script>
<!-- // Bootstrap -->
<script type="text/javascript" src="/resources/jquery.placeholder.min.js"></script>
<script>
    /*
    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });
    */

    $(document).ready(function() {
        $("#mngId").focus();
    });


    function login(){
        if($("#mngId").val() == ''){
            alert('아이디를 입력해주세요.');
            $("#mngId").focus();
            return;
        }

        if($("#mngPw").val() == ''){
            alert('비밀번호를 입력해주세요.');
            $("#mngPw").focus();
            return;
        }


        form.action="/j_spring_security_check";
        form.submit();
    }
</script>
</body>
</html>