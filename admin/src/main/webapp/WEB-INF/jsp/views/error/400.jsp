<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>입력 값이 잘못되었습니다.</title>
</head>
<body>
    <div style="position:absolute; top:50%; left:50%; margin:-176px 0 0 -264px; border:solid 1px black;">
        <p>
            <a href="/">잘못 된 입력입니다. 400</a>
            <br/>
            <label>상세 정보 : ${msg}</label>
        </p>
        <p>
            <a href="javascript:history.go(-1);">뒤로가기</a>
        </p>
    </div>
</body>
</html>