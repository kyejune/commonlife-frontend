<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 시설 옵션 관리</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 시설 옵션 관리</a>
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
                            <h5>예약 시설 옵션 목록</h5>
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
                                    <th class="text-center">아이콘</th>
                                    <th class="text-center">이름</th>
                                    <th class="text-center">등록일시</th>
                                    <th class="text-center">최종수정</th>
                                    <th class="text-center">수정</th>
                                    <th class="text-center">삭제</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="amenity" items="${amenities}">
                                    <tr>
                                        <td class="text-center">
                                            <span style="background: #666666; padding: 4px 4px 7px;">
                                                <img src="/admin/reservation-amenities/icon.do?idx=${amenity.iconIdx}" style="width: 18px;" alt="">
                                            </span>
                                        </td>
                                        <td class="text-center">${amenity.name}</td>
                                        <td class="text-center">${amenity.regDttm}</td>
                                        <td class="text-center">${amenity.updDttm}</td>
                                        <td class="text-center">
                                            <a href="/admin/reservation-amenities/edit.do?idx=${amenity.idx}" class="btn btn-xs btn-white">수정</a>
                                        </td>
                                        <td class="text-center">
                                            <form action="/admin/reservation-amenities/delete.do?idx=${amenity.idx}" method="post">
                                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                                <button class="btn btn-xs btn-danger">삭제</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="text-right">
                                <a href="/admin/reservation-amenities/create.do" class="btn btn-primary">예약 시설 옵션 추가</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>