<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row border-bottom">
    <nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
            <%--<form role="search" class="navbar-form-custom" method="post" action="#">--%>
                <%--<div class="form-group">--%>
                    <%--<input type="text" placeholder="Search for something..." class="form-control" name="top-search" id="top-search">--%>
                <%--</div>--%>
            <%--</form>--%>
        </div>
        <c:url var="logoutUrl" value="/j_spring_security_logout"/>
        <form id="logout-form" action="${logoutUrl}" method="post">
            <ul class="nav navbar-top-links navbar-right">
                <li>
                    <a href="#" onclick="document.getElementById('logout-form').submit();">
                        <i class="fa fa-sign-out"></i>Log out
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </a>
                </li>
            </ul>
        </form>
    </nav>
</div>
