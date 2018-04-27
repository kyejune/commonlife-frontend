<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="complexes">
<tiles:putAttribute name="title">현장/현장그룹 관리</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- DataTables -->
    <a><!-- --></a>
</tiles:putAttribute>
<tiles:putAttribute name="contents">
    <!-- Section Title -->
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2>개별 현장 관리</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    현장 관리
                </li>
                <li class="active">
                    <a>개별 현장 관리</a>
                </li>
            </ol>
        </div>
        <div class="col-lg-2">
        </div>
    </div>

    <div class="wrapper wrapper-content animated fadeInRight">
        <form:form name="complexReqForm" id="complexReqForm" method="post" commandName="complexInfo">
            <!--//paging-->
            <%--<form:hidden path="pageIndex"/>--%>
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>현장 목록</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>

                        <div class="ibox-content" style="">
                            <%--<input type="text" class="form-control input-sm m-b-xs" id="filter" placeholder="Search in table">--%>
                            <table class="footable table table-stripped toggle-arrow-tiny" data-page-size="10" data-filter="#filter">
                                <thead>
                                    <tr>
                                        <th data-toggle="true" data-sort-ignore="true">#</th>
                                        <th >현장 이름</th>
                                        <th >현장 그룹</th>
                                        <th class="" data-sort-ignore="true">현장 주소</th>
                                    </tr>
                                </thead>
                                <c:choose>
                                    <c:when test="${fn:length(complexList) == 0}">
                                        <tbody>
                                        <tr>
                                            <td colspan="9"><p><dfn>조회된 결과가 없습니다.</dfn></p></td>
                                        </tr>
                                        </tbody>
                                    </c:when>
                                    <c:otherwise>
                                        <tbody>
                                        <c:forEach var="vo" items="${complexList}" varStatus="status">
                                            <tr>
                                                <td> ${vo.rnum} </td>
                                                <td><a href="complexDetail.do?cmplxId=${vo.cmplxId}">${vo.cmplxNm}</a></td>
                                                <td> ${vo.cmplxGrp}</td>
                                                <td> ${vo.addr}</td>
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

            $("#left_complex").addClass("active");
            $("#left_complex > .nav-second-level").addClass("in");
            $("#left_complex_list").addClass("active");
            <%--<c:choose>--%>
                <%--<c:when test="${adminConst.adminGrpSuper == grpId}">--%>
                    <%--$("#left_admin_super").addClass("active");--%>
                <%--</c:when>--%>
                <%--<c:when test="${adminConst.adminGrpComplex == grpId}">--%>
                    <%--$("#left_admin_complex").addClass("active");--%>
                <%--</c:when>--%>
                <%--<c:otherwise>--%>
                    <%--$("#left_admin_all").addClass("active");--%>
                <%--</c:otherwise>--%>
            <%--</c:choose>--%>
        })


//         function fn_link_page(pageIndex){
//             $("#complexReqForm > #pageIndex").val(pageIndex);
// //                $("#pageIndex").val(pageIndex);
//
//             refreshList();
//         }

        function refreshList(){
            $("#complexReqForm").attr("action", "/admin/complexes/list.do?grpId=${grpId}");
            $("#complexReqForm").submit();
        }

        function managersDetail(adminId, grpId){
            $("#complexReqForm > #adminId").val(adminId);
            $("#complexReqForm").attr("action", "/admin/complexes/write.do?grpId=" + grpId);
            $("#complexReqForm").submit();
        }

        function managerAdd(grpId){
            $("#complexReqForm").attr("action", "/admin/complexes/write.do?create=true&grpId=" + grpId);
            $("#complexReqForm").submit();
        }

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>