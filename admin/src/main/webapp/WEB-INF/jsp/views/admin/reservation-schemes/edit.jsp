<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <jsp:include page="./form.jsp">
            <jsp:param name="amenities" value="${amenities}"/>
            <jsp:param name="_csrf" value="${_csrf}"/>
            <jsp:param name="redirectTo" value="${redirectTo}"/>
            <jsp:param name="complexes" value="${complexes}"/>
            <jsp:param name="parentIdx" value="${parentIdx}"/>
            <jsp:param name="group" value="${group}"/>
            <jsp:param name="scheme" value="${scheme}"/>
        </jsp:include>
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <%@include file="./scripts.jsp"%>
    </tiles:putAttribute>
</tiles:insertDefinition>