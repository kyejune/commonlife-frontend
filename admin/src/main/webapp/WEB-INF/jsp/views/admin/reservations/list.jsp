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
                            <table class="table">
                                <thead>
                                <tr>
                                    <th class="text-center">Index</th>
                                    <th class="text-center">예약명</th>
                                    <th class="text-center">시작일</th>
                                    <th class="text-center">시작시각</th>
                                    <th class="text-center">종료일</th>
                                    <th class="text-center">종료시작</th>
                                    <th class="text-center">포인트</th>
                                    <th class="text-center">금액</th>
                                    <%--<th class="text-center">수량</th>--%>
                                    <th class="text-center">보기</th>
                                    <th class="text-center">삭제</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="reservation" items="${reservations}">
                                    <tr>
                                        <td class="text-center">${reservation.idx}</td>
                                        <td class="text-center">${reservation.scheme.title}</td>
                                        <td class="text-center">${reservation.startDt}</td>
                                        <td class="text-center">${reservation.startTime}</td>
                                        <td class="text-center">${reservation.endDt}</td>
                                        <td class="text-center">${reservation.endTime}</td>
                                        <td class="text-center">${reservation.point}</td>
                                        <td class="text-center">${reservation.amount}</td>
                                        <td class="text-center">
                                            <a href="/admin/reservations/show.do?idx=${reservation.idx}" class="btn btn-xs btn-white">보기</a>
                                        </td>
                                        <td class="text-center">
                                            <form action="/admin/reservations/delete.do" method="post">
                                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                                <input type="hidden" name="idx" value="${reservation.idx}">
                                                <button class="btn btn-xs btn-danger">삭제</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>