<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 현황</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 현황</a>
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
                            <h5>예약 목록</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <h2>reservation</h2>
                            <c:if test="${not empty reservation['class'].declaredFields}">
                                <dl>
                                    <c:forEach var="field" items="${reservation['class'].declaredFields}">
                                        <c:catch>
                                            <dt>${field.name}</dt>
                                            <dd>${reservation[field.name]}</dd>
                                        </c:catch>
                                    </c:forEach>
                                </dl>
                            </c:if>
                            <h2>reservation.scheme</h2>
                            <c:if test="${not empty reservation.scheme['class'].declaredFields}">
                                <dl>
                                    <c:forEach var="field" items="${reservation.scheme['class'].declaredFields}">
                                        <c:catch>
                                            <dt>${field.name}</dt>
                                            <dd>${reservation.scheme[field.name]}</dd>
                                        </c:catch>
                                    </c:forEach>
                                </dl>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>