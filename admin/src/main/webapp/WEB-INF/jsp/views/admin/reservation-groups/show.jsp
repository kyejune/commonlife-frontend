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
                            <div>${group.idx}</div>
                            <div>${group.icon}</div>
                            <div>${group.title}</div>
                            <div>${group.summary}</div>
                        </div>
                    </div>
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>이 그룹의 예약 목록</h5>
                        </div>
                        <div class="ibox-content">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th class="text-center">ID</th>
                                    <th>예약명</th>
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
                                        <td class="text-center">${scheme.idx}</td>
                                        <td>${scheme.title}</td>
                                        <td>${scheme.summary}</td>
                                        <td class="text-center">
                                                ${scheme.startDt}
                                        </td>
                                        <td class="text-center">
                                                ${scheme.endDt}
                                        </td>ㄴ
                                        <td class="text-center">
                                            <a href="/admin/reservation-schemes/edit.do?idx=${scheme.idx}&cmplxIdx=${scheme.cmplxIdx}&parentIdx=${scheme.parentIdx}" class="btn btn-xs btn-white">보기/수정</a>
                                        </td>
                                        <td class="text-center">
                                            <form action="/admin/reservation-schemes/delete.do" method="post">
                                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                                <input type="hidden" name="idx" value="${scheme.idx}">
                                                <button class="btn btn-xs btn-danger">삭제</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="text-right">
                                <a href="/admin/reservation-schemes/create.do?cmplxIdx=${group.cmplxIdx}&parentIdx=${group.idx}" class="btn btn-primary">예약 추가</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
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