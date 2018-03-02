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
                        <form class="form-horizontal">
                            <c:choose>
                                <c:when test="${adminConst.adminGrpSuper == grpId}">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">
                                            <button type="button"
                                                    class="btn btn-primary b-r-lg"
                                                    onclick="managerAdd(${adminConst.adminGrpSuper})">
                                                슈퍼 관리자 생성</button>
                                        </label>
                                        <div class="col-sm-10"> 설명 작성 ... </div>
                                    </div>
                                </c:when>
                                <c:when test="${adminConst.adminGrpComplex == grpId}">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">
                                            <button type="button"
                                                    class="btn btn-success b-r-lg"
                                                    onclick="managerAdd(${adminConst.adminGrpComplex})">
                                                현장 관리자 생성</button>
                                        </label>
                                        <div class="col-sm-10"> 설명 작성 ... </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge-plain">
                                        -
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <form:form name="manageReqForm" id="manageReqForm" method="post" commandName="adminInfo">
            <!--//paging-->
            <form:hidden path="pageIndex"/>
            <form:hidden path="adminId"/>
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
                            <table class="footable table table-stripped toggle-arrow-tiny" data-page-size="10" data-filter="#filter">
                                <thead>
                                    <tr>
                                        <th data-toggle="true" data-sort-ignore="true">#</th>
                                        <th data-sort-ignore="true">관리자 그룹</th>
                                        <th data-sort-ignore="true">관리자 아이디</th>
                                        <th class="" data-sort-ignore="true">이름</th>
                                        <th class="" data-sort-ignore="true">Email</th>
                                        <c:if test="${grpId != null && adminConst.adminGrpComplex == grpId}">
                                            <th class="" data-sort-ignore="true">담당 현장</th>
                                        </c:if>
                                        <th data-hide="all" data-sort-ignore="true">설명</th>
                                        <th class="" data-sort-ignore="true">사용유무</th>
                                        <th class="" data-sort-ignore="true">등록일</th>
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
                                            <tr>
                                                <td>
                                                    ${vo.rnum}
                                                </td>
                                                <td class="center">
                                                    <c:choose>
                                                        <c:when test="${adminConst.adminGrpSuper == vo.grpId}">
                                                            <span class="badge-primary">
                                                                    ${vo.grpNm}
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${adminConst.adminGrpComplex == vo.grpId}">
                                                            <span class="badge-success">
                                                                    ${vo.grpNm}
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge-plain">
                                                                -
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <a href="javascript:void(0)" onclick="managersDetail('${vo.adminId}', ${vo.grpId})">
                                                            ${vo.adminId}
                                                    </a>
                                                </td>
                                                <td class="center">${vo.adminNm}</td>
                                                <td class="center">${vo.adminEmail}</td>
                                                <c:if test="${adminConst.adminGrpComplex == grpId}">
                                                    <td class="center">${vo.cmplxNm}</td>
                                                </c:if>
                                                <td class="center"><p>${vo.desc}</p><br/></td>
                                                <td class="center" >
                                                    <c:choose>
                                                        <c:when test="${vo.useYn == 'Y'}">
                                                            <i class="fa fa-check-circle text-navy" alt="사용"></i>
                                                        </c:when>
                                                        <c:when test="${vo.useYn == 'N'}">
                                                            <i class="fa fa-times" alt="사용안함"></i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="center" >
                                                    <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd" var="sysDt"/>
                                                    <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <!-- paginging -->
                                        <tr>
                                            <c:choose>
                                                <c:when test="${adminConst.adminGrpComplex == grpId}">
                                                    <td colspan="8">
                                                </c:when>
                                                <c:otherwise>
                                                    <td colspan="7">
                                                </c:otherwise>
                                            </c:choose>
                                                        <ul class="pagination"></ul>
                                                    <%--<ui:pagination paginationInfo="${pagination}" jsFunction="fn_link_page"/>--%>
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
    <script type="text/javascript">
        $(function () {
            $('.footable').footable();

            $("#left_admin").addClass("active");
            <c:choose>
                <c:when test="${adminConst.adminGrpSuper == grpId}">
                    $("#left_admin_super").addClass("active");
                </c:when>
                <c:when test="${adminConst.adminGrpComplex == grpId}">
                    $("#left_admin_complex").addClass("active");
                </c:when>
                <c:otherwise>
                    $("#left_admin_all").addClass("active");
                </c:otherwise>
            </c:choose>
        })


        function fn_link_page(pageIndex){
            $("#manageReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

            refreshList();
        }

        function refreshList(){
            $("#manageReqForm").attr("action", "/admin/managers/list.do?grpId=${grpId}");
            $("#manageReqForm").submit();
        }

        function managersDetail(adminId, grpId){
            $("#manageReqForm > #adminId").val(adminId);
            $("#manageReqForm").attr("action", "/admin/managers/write.do?grpId=" + grpId);
            $("#manageReqForm").submit();
        }

        function managerAdd(grpId){
            $("#manageReqForm").attr("action", "/admin/managers/write.do?create=true&grpId=" + grpId);
            $("#manageReqForm").submit();
        }

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>