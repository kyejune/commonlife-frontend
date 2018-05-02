<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <!-- Bootstrap 3.3.7 -->
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="/resources/css/animate.css" rel="stylesheet">
    <link href="/resources/css/style.css" rel="stylesheet">
</head>

<body class="gray-bg login-page">

<div class="login-box">
    <div class="login-box-body">
        <form name="form" method="post">
            <div class="middle-box text-center loginscreen animated fadeInDown">
                <div>
                    <div>
                        <h1 class="logo-name"></h1>
                    </div>
                    <h3>
                        <img src="/resources/img/logo_intro.png" alt="">
                    </h3>
                    <p><strong class="bold-font">환영합니다.</strong></p>
                    <div class="m-t">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Username" required=""  id="mngId" name="mngId">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" placeholder="Password" required="" name="mngPw" id="mngPw">
                        </div>
                        <button type="input" class="btn btn-primary block full-width m-b" onclick="login()">로그인</button>

                        <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >

                    </div>
                    <p class="m-t"> <small></small></p>
                </div>
            </div>

            <c:if test="${param.fail == 'true'}">
                <div class="error">아이디 또는 비밀번호가 일치하지 않습니다.</div>
            </c:if>
        </form>
    </div>
</div>


<!-- Mainly scripts -->
<script src="/resources/js/jquery-3.1.1.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>

<script>
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

        form.action="<c:url value='/j_spring_security_check' />";
        form.submit();
    }
</script>
</body>
</html>