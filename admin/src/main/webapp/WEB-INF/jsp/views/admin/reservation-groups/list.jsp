<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
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
                            <h5>예약 그룹 목록</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <form action="" class="form-inline text-right">
                                <div class="form-group">
                                    <div class="input-group">
                                        <select name="cmplxIdx" class="form-control">
                                            <option value="">현장 선택</option>
                                            <c:forEach var="complex" items="${complexes}">
                                                <option value="${complex.cmplxId}" <c:if test="${cmplxIdx == complex.cmplxId}"> selected </c:if> >
                                                    ${complex.cmplxGrp} | ${complex.cmplxNm}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-btn">
                                            <button class="btn btn-primary">필터</button>
                                        </span>
                                    </div>
                                </div>

                            </form>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th class="text-center">ID</th>
                                    <th class="text-center">현장</th>
                                    <th class="text-center">아이콘</th>
                                    <th class="text-center">그룹명</th>
                                    <th>설명</th>
                                    <th class="text-center">생성</th>
                                    <th class="text-center">보기</th>
                                    <th class="text-center">수정</th>
                                    <th class="text-center">삭제</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="group" items="${groups}">
                                    <tr>
                                        <td class="text-center">${group.idx}</td>
                                        <td class="text-center">${group.cmplxNm}</td>
                                        <td class="text-center">${group.icon}</td>
                                        <td class="text-center">${group.title}</td>
                                        <td>${group.summary}</td>
                                        <td class="text-center">${group.regDttm}</td>
                                        <td class="text-center">
                                            <a href="/admin/reservation-groups/show.do?idx=${group.idx}" class="btn btn-xs btn-white">보기</a>
                                        </td>
                                        <td class="text-center">
                                            <a href="/admin/reservation-groups/edit.do?idx=${group.idx}" class="btn btn-xs btn-info">수정</a>
                                        </td>
                                        <td class="text-center">
                                            <form action="/admin/reservation-groups/delete.do?idx=${group.idx}" method="post">
                                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                                <button class="btn btn-xs btn-danger">삭제</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="text-right">
                                <a href="/admin/reservation-groups/create.do" class="btn btn-primary">그룹 추가</a>
                            </div>
                        </div>
                    </div>

                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>빠른 예약 추가</h5>
                        </div>
                        <div class="ibox-content">
                            <c:if test="${cmplxIdx == 0}">
                                <div class="text-center">
                                    현장을 선택하시면 빠른 예약 목록이 표시됩니다.
                                </div>
                            </c:if>
                            <c:if test="${cmplxIdx != 0}">
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
                                            </td>
                                            <td class="text-center">
                                                <a href="/admin/reservation-schemes/edit.do?idx=${scheme.idx}" class="btn btn-xs btn-info">보기/수정</a>
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
                                    <a href="/admin/reservation-schemes/create.do?cmplxIdx=${cmplxIdx}&parentIdx=0" class="btn btn-white">빠른 예약 추가</a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>