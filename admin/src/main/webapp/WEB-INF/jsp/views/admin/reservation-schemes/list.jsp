<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 관리</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 관리</a>
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
                            <table class="table">
                                <thead>
                                <tr>
                                    <th class="text-center">타입</th>
                                    <th>예약명</th>
                                    <th class="text-center">포인트</th>
                                    <th class="text-center">금액</th>
                                    <th class="text-center">개시일</th>
                                    <th class="text-center">개시시각</th>
                                    <th class="text-center">마감일</th>
                                    <th class="text-center">마감시각</th>
                                    <th class="text-center">
                                        예약 가능일
                                        <small title="오늘부터 예약이 가능한 일수입니다" style="cursor: pointer;">[?]</small>
                                    </th>
                                    <th class="text-center">주말 예약</th>
                                    <th class="text-center">수량 제한</th>
                                    <th class="text-center">화면 표시</th>
                                    <%--<th>보기</th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="scheme" items="${schemes}">
                                    <tr>
                                        <td class="text-center">${scheme.reservationType}</td>
                                        <td>${scheme.title}</td>
                                        <td class="text-center">${scheme.point}</td>
                                        <td class="text-center">${scheme.amount}</td>
                                        <td class="text-center">
                                            ${scheme.startDt}
                                        </td>
                                        <td class="text-center">
                                            ${scheme.startTime}
                                        </td>
                                        <td class="text-center">${scheme.endDt}</td>
                                        <td class="text-center">${scheme.endTime}</td>
                                        <td class="text-center">${scheme.activateDuration}일</td>
                                        <td class="text-center">${scheme.availableInWeekend}</td>
                                        <td class="text-center">${scheme.maxQty}</td>
                                        <td class="text-center">${scheme.delYn}</td>
                                        <%--<td>--%>
                                            <%--<a href="/admin/reservation-schemes/edit.do?idx=${scheme.idx}" class="btn btn-xs btn-success">수정</a>--%>
                                        <%--</td>--%>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="text-right">
                                <a href="/admin/reservation-schemes/create.do" class="btn btn-primary">예약 형식 추가</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>