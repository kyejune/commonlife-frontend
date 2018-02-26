<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- DataTables -->
    <a><!-- --></a>
</tiles:putAttribute>
<tiles:putAttribute name="contents">
    <!-- Section Title -->
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2>관리자 관리</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    <a>관리자 관리</a>
                </li>
                <li class="active">
                    <strong>목록</strong>
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
                        <h5>관리자 생성</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content" style="">
                        <button type="button" class="btn btn-primary btn-rounded">슈퍼 관리자 생성</button>
                        <button type="button" class="btn btn-primary btn-rounded">현장 관리자 생성</button>
                    </div>
                </div>
            </div>
        </div>

        <form:form name="manageReqForm" id="manageReqForm" method="post" commandName="adminInfo">
            <!--//paging-->
            <form:hidden path="pageIndex"/>
            <form:hidden path="mngId"/>
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>관리자 목록</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>

                        <div class="ibox-content" style="">
                            <input type="text" class="form-control input-sm m-b-xs" id="filter" placeholder="Search in table">
                            <table class="footable table table-stripped tablet breakpoint footable-loaded" data-page-size="8" data-filter="#filter">
                                <thead>
                                    <tr>
                                        <th class="footable-visible footable-first-column footable-sortable">#<span class="footable-sort-indicator"></span></th>
                                        <th class="footable-visible footable-last-column footable-sortable ">관리자 아이디<span class="footable-sort-indicator"></span></th>
                                        <th class="">이름</th>
                                        <th class="">Email</th>
                                        <th class="">관리자 그룹</th>
                                        <th class="">지역관리자 현장</th>
                                        <th class="">설명</th>
                                        <th class="">사용유무</th>
                                        <th class="">등록일</th>
                                    </tr>
                                </thead>
                                <c:choose>
                                    <c:when test="${fn:length(managerList) == 0}">
                                        <tbody>
                                        <tr>
                                            <td colspan="9"><p><dfn>조회된 결과가 없습니다.</dfn></p></td>
                                        </tr>
                                        </tbody>
                                    </c:when>
                                    <c:otherwise>
                                        <tbody>
                                        <c:forEach var="vo" items="${managerList}" varStatus="status">
                                            <tr class="gradeA footable-even" style="">
                                                <td class="footable-visible footable-first-column">
                                                    <c:out value="${pagination.totalRecordCount - (pagination.recordCountPerPage * (pagination.currentPageNo - 1)) - status.count + 1}"/>
                                                </td>
                                                <td class="footable-visible footable-last-column">
                                                    <a href="javascript:void(0)" onclick="managersDetail('${vo.adminIdx}')">
                                                            ${vo.adminId}
                                                    </a>
                                                </td>
                                                <td class="center">${vo.adminNm}</td>
                                                <td class="center">${vo.adminEmail}</td>
                                                <td class="center">${vo.grpId}</td>
                                                <td class="center">지연관리자 현장</td>
                                                <td class="center">{TBA}</td>
                                                <td class="center" >${vo.useYn}</td>
                                                <td class="center" >
                                                    <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd hh:mm:ss" var="sysDt"/>
                                                    <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <!-- paginging -->

                                        <%--<div class="dataTables_paginate paging_simple_numbers" align="center">--%>
                                        <%--</div>--%>

                                        <tr>
                                            <td colspan="9" class="footable-visible">
                                                <ui:pagination paginationInfo="${pagination}" jsFunction="fn_link_page"/>
                                            </td>
                                        </tr>
                                        </tfoot>
                                    </c:otherwise>
                                </c:choose>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</tiles:putAttribute>
<tiles:putAttribute name="js">
    <!-- DataTables -->
    <script type="text/javascript">
        $(function () {
            $('#mngList').DataTable({
                'paging'      : false,
                'lengthChange': false,
                'searching'   : false,
                'ordering'    : false,
                'info'        : false,
                'autoWidth'   : true
            })

            $("#left_li_menu_05").addClass("active");
        })


        function fn_link_page(pageIndex){
            $("#manageReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

            refreshList();
        }

        function refreshList(){
            $("#manageReqForm").attr("action", "/admin/managers/list.do");
            $("#manageReqForm").submit();
        }

        function managersDetail(mngId){
            $("#manageReqForm > #mngId").val(mngId);
            $("#manageReqForm").attr("action", "/admin/managers/write.do");
            $("#manageReqForm").submit();
        }

        function managerAdd(){
            $("#manageReqForm").attr("action", "/admin/managers/write.do");
            $("#manageReqForm").submit();
        }

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>