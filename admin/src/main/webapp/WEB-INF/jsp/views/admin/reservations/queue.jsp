<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
    <tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>장기 예약 신청 관리</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>장기 예약 신청 관리</a>
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
                                    <th class="text-center">사용자</th>
                                    <th class="text-center">예약일</th>
                                    <th class="text-center">포인트</th>
                                    <th class="text-center">금액</th>
                                    <th class="text-center">상태</th>
                                    <th class="text-center">변경</th>
                                        <%--<th class="text-center">수량</th>--%>
                                    <th class="text-center">삭제</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="reservation" items="${reservations}">
                                    <tr>
                                        <td class="text-center">${reservation.idx}</td>
                                        <td class="text-center">${reservation.scheme.title}</td>
                                        <td class="text-center">${reservation.usrID}</td>
                                        <td class="text-center">${reservation.startDt}</td>
                                        <td class="text-center">${reservation.point}</td>
                                        <td class="text-center">${reservation.amount}</td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${reservation.status == 'RESERVED'}">
                                                    예약 확정
                                                </c:when>
                                                <c:when test="${reservation.status == 'IN_QUEUE'}">
                                                    예약 대기
                                                </c:when>
                                                <c:when test="${reservation.status == 'ADMIN_CANCELED'}">
                                                    취소됨
                                                </c:when>
                                                <c:otherwise>
                                                    알 수 없는 상태
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <form action="/admin/reservations/edit-queue.do" method="post">
                                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                                <input type="hidden" name="idx" value="${reservation.idx}">
                                                <div class="input-group">
                                                    <select name="status" class="form-control input-sm">
                                                        <option value="">선택</option>
                                                        <option value="IN_QUEUE">예약 대기</option>
                                                        <option value="RESERVED">예약 확정</option>
                                                        <option value="ADMIN_CANCELED">취소됨</option>
                                                    </select>
                                                    <span class="input-group-btn">
                                                        <button class="btn btn-sm btn-white">변경</button>
                                                    </span>
                                                </div>
                                            </form>
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
    <tiles:putAttribute name="js">
        <script>
            var events = [];
            <c:forEach var="reservation" items="${reservations}">
            events.push( {
                title: '${reservation.scheme.title}',
                start: moment( '${reservation.startDt}' ).toDate(),
                end: moment( '${reservation.endDt}' ).toDate()
            } );
            </c:forEach>
            $( function() {
                $('#calendar').fullCalendar({
                    header: {
                        left: 'prev,next',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    },
                    events: events
                });
            } );
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>