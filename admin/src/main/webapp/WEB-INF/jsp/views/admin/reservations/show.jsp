<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 조회</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 조회</a>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>예약 정보</h5>
                        </div>
                        <div class="ibox-content">
                            <dl class="dl-horizontal">
                                <dt>예약명</dt>
                                <dd>${reservation.scheme.title}</dd>

                                <dt>예약일</dt>
                                <dd>
                                    ${reservation.startDt}
                                    <c:if test="${reservation.endDt != null}">
                                        부터 ${reservation.endDt} 까지
                                    </c:if>
                                </dd>

                                <c:if test="${reservation.startTime != null}">
                                    <dt>예약 시간</dt>
                                    <dd>
                                        ${reservation.startTime}
                                        <c:if test="${reservation.endTime != null}">
                                            부터 ${reservation.endTime} 까지
                                        </c:if>
                                    </dd>
                                </c:if>
                            </dl>
                        </div>
                    </div>
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>예약자 정보</h5>
                        </div>
                        <div class="ibox-content">
                            <c:if test="${reservation.user != null}">
                            <dl class="dl-horizontal">
                                <dt>사용자 이름</dt>
                                <dd>${reservation.user.userNm}</dd>

                                <dt>사용자 아이디</dt>
                                <dd>${reservation.user.userId}</dd>

                                <dt>현장</dt>
                                <dd>${reservation.user.cmplxNm}</dd>

                                <dt>동</dt>
                                <dd>${reservation.user.dong}</dd>

                                <dt>호</dt>
                                <dd>${reservation.user.ho}</dd>

                                <dt>연락처</dt>
                                <dd>${reservation.user.cell}</dd>
                            </dl>
                            </c:if>
                            <c:if test="${reservation.user == null}">
                                사용자 정보가 없습니다.
                            </c:if>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12">
                            <button class="btn btn-white" onclick="history.back()">뒤로가기</button>
                        </div>
                    </div>

                    <%--<div class="ibox float-e-margins">--%>
                        <%--<div class="ibox-title">--%>
                            <%--<h5>예약 목록</h5>--%>
                            <%--<div class="ibox-tools">--%>
                                <%--<a class="collapse-link">--%>
                                    <%--<i class="fa fa-chevron-up"></i>--%>
                                <%--</a>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="ibox-content" style="">--%>
                            <%--<h2>reservation</h2>--%>
                            <%--<c:if test="${not empty reservation['class'].declaredFields}">--%>
                                <%--<dl>--%>
                                    <%--<c:forEach var="field" items="${reservation['class'].declaredFields}">--%>
                                        <%--<c:catch>--%>
                                            <%--<dt>${field.name}</dt>--%>
                                            <%--<dd>${reservation[field.name]}</dd>--%>
                                        <%--</c:catch>--%>
                                    <%--</c:forEach>--%>
                                <%--</dl>--%>
                            <%--</c:if>--%>

                            <%--<h2>reservation.scheme</h2>--%>
                            <%--<c:if test="${not empty reservation.scheme['class'].declaredFields}">--%>
                                <%--<dl>--%>
                                    <%--<c:forEach var="field" items="${reservation.scheme['class'].declaredFields}">--%>
                                        <%--<c:catch>--%>
                                            <%--<dt>${field.name}</dt>--%>
                                            <%--<dd>${reservation.scheme[field.name]}</dd>--%>
                                        <%--</c:catch>--%>
                                    <%--</c:forEach>--%>
                                <%--</dl>--%>
                            <%--</c:if>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>