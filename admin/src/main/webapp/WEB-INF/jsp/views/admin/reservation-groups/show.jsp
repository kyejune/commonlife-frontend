<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 그룹 관리</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 그룹 관리</a>
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
                            <h5>예약 그룹 상세</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <table class="table cl-reservation-table">
                                <colgroup>
                                    <col>
                                    <col style="width: 180px;">
                                    <col style="width: 60px;">
                                    <col style="width: 200px;">
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th class="text-center"> </th>
                                    <th>그룹명</th>
                                    <th class="text-center">아이콘</th>
                                    <th class="text-center">현장</th>
                                    <th>설명</th>
                                    <th class="text-center">수정</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td class="text-center" style="vertical-align: middle;">
                                            <span class="label label-primary">Active</span>
                                        </td>
                                        <td class="">
                                            <strong style="font-size: 1.4em;">${group.title}</strong>
                                            <br>
                                            <small>
                                                Created
                                                <fmt:formatDate value="${group.regDttm}" pattern="yyyy.MM.dd"/>
                                            </small>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${group.icon == 'HOME'}">
                                                    <img src="/resources/img/reservations/r-icon-1-3x.png" style="width: 36px;" alt="HOME">
                                                </c:when>
                                                <c:when test="${group.icon == 'TOOL'}">
                                                    <img src="/resources/img/reservations/r-icon-2-3x.png" style="width: 36px;" alt="TOOL">
                                                </c:when>
                                                <c:when test="${group.icon == 'STORE'}">
                                                    <img src="/resources/img/reservations/r-icon-3-3x.png" style="width: 36px;" alt="STORE">
                                                </c:when>
                                                <c:when test="${group.icon == 'ETC'}">
                                                    <img src="/resources/img/reservations/r-icon-4-3x.png" style="width: 36px;" alt="ETC">
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">${selectedComplex.cmplxNm}</td>
                                        <td>${group.summary}</td>
                                        <td class="text-center">
                                            <a href="/admin/reservation-groups/edit.do?idx=${group.idx}" class="btn btn-xs btn-white">
                                                <i class="fa fa-pencil"></i> 수정
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>예약 목록</h5>
                        </div>
                        <div class="ibox-content">
                            <table class="table cl-reservation-table">
                                <colgroup>
                                    <col>
                                    <col style="width: 180px;">
                                    <col style="width: 60px;">
                                    <col>
                                    <col>
                                    <col>
                                    <col>
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th class="text-center"> </th>
                                    <th>예약명</th>
                                    <th class="text-center">아이콘</th>
                                    <th>예약 개요</th>
                                    <th class="text-center">예약 개시</th>
                                    <th class="text-center">예약 마감</th>
                                    <th class="text-center">보기/수정</th>
                                    <th class="text-center">삭제</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="scheme" items="${schemes}">
                                    <tr>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${ scheme.activate == 'yes' }">
                                                    <span class="label label-primary">Active</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-mute">Unactive</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <span style="font-size: 1.4em;">${scheme.title}</span>
                                            <br>
                                            <small>
                                                Created
                                                <fmt:formatDate value="${scheme.regDttm}" pattern="yyyy.MM.dd"/>
                                            </small>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${scheme.icon =='CLEANING'}">
                                                    <img src="/resources/img/reservations/rs-icon-2-3x.png" style="width: 36px;" alt="CLEANING">
                                                </c:when>
                                                <c:when test="${scheme.icon =='LAUNDRY'}">
                                                    <img src="/resources/img/reservations/rs-icon-1-3x.png" style="width: 36px;" alt="LAUNDRY">
                                                </c:when>
                                                <c:when test="${scheme.icon =='FOOD'}">
                                                    <img src="/resources/img/reservations/rs-icon-3-3x.png" style="width: 36px;" alt="FOOD">
                                                </c:when>
                                                <c:when test="${scheme.icon =='CARWASH'}">
                                                    <img src="/resources/img/reservations/rs-icon-4-3x.png" style="width: 36px;" alt="CARWASH">
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>${scheme.summary}</td>
                                        <td class="text-center">
                                                ${scheme.startDt}
                                        </td>
                                        <td class="text-center">
                                                ${scheme.endDt}
                                        </td>
                                        <td class="text-center">
                                            <a href="/admin/reservation-schemes/edit.do?idx=${scheme.idx}&cmplxIdx=${complexIdx}" class="btn btn-xs btn-white">
                                                <i class="fa fa-pencil"></i> 수정
                                            </a>
                                        </td>
                                        <td class="text-center">
                                            <form action="/admin/reservation-schemes/delete.do" method="post">
                                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                                <input type="hidden" name="idx" value="${scheme.idx}">
                                                <button class="btn btn-xs btn-danger">
                                                    <i class="fa fa-trash"></i> 삭제
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="form-group">
                        <button class="btn btn-white" role="button" onclick="history.back()">뒤로 가기</button>
                    </div>

                </div>
            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <style type="text/css">
            .cl-reservation-table th,
            .cl-reservation-table td {
                vertical-align: middle !important;
            }
        </style>
        <script>
            $( function() {
                $( '.type-group' ).hide();
                $( '.type-group-a' ).show();

                $( 'select[name=reservationType]' ).on( 'change', function( event ) {
                    var $select = $( event.currentTarget );
                    var type = $select.val().toLowerCase();

                    $( '.type-group' ).hide();
                    $( '.type-group-' + type ).show();
                } );
            } );
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>