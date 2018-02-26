<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="manage">
    <tiles:putAttribute name="title">관리자</tiles:putAttribute>
    <tiles:putAttribute name="css">
        <!-- DataTables -->
        <link rel="stylesheet" href="/resources/datatables.net-bs/css/dataTables.bootstrap.min.css">
    </tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <jsp:include page ="/WEB-INF/tiles/common/left.jsp" flush="true"/>


        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    페이스북 관리
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 덕평소식</a></li>
                    <li class="active">페이스북 관리</li>
                    <li class="active">목록</li>
                </ol>
            </section>

            <form:form name="facebookReqForm" id="facebookReqForm" method="post" commandName="faceBookInfo">
            <!--//paging-->
            <form:hidden path="pageIndex"/>
            <form:hidden path="idx"/>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box">
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table id="mngList" class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>내용</th>
                                        <th>타입</th>
                                        <th>공개유무</th>
                                        <th>페북 등록일</th>
                                        <th>시스템 등록일</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="vo" items="${facebookList}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${pagination.totalRecordCount - (pagination.recordCountPerPage * (pagination.currentPageNo - 1)) - status.count + 1}"/></td>
                                        <td>
                                            <a href="javascript:facebookDetail('${vo.idx}')">
                                                <c:choose>
                                                    <c:when test="${fn:length(vo.message) > 50}">
                                                        <c:out value="${fn:substring(vo.message,0,50)}"/>  ...
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${vo.message}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </a>
                                        </td>
                                        <td>${vo.type}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${vo.isOpen != null}">
                                                    ${vo.isOpen}
                                                </c:when>
                                                <c:otherwise>
                                                    N
                                                </c:otherwise>
                                            </c:choose>
                                        <td>
                                            <fmt:parseDate value="${fn:substring(vo.createdTime,0,10)}" pattern="yyyy-MM-dd" var="fbDt"/>
                                            <fmt:formatDate value="${fbDt}" pattern="yyyy.MM.dd"/>
                                        </td>
                                        <td>
                                            <fmt:parseDate value="${fn:substring(vo.sysRegDttm,0,10)}" pattern="yyyy-MM-dd" var="sysDt"/>
                                            <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd"/>
                                        </td>
                                    </tr>
                                    </c:forEach>

                                    <c:if test="${fn:length(facebookList) == 0}">
                                    <tfoot>
                                        <tr>
                                            <td colspan="5"><p><dfn>조회된 결과가 없습니다.</dfn></p></td>
                                        </tr>
                                    </tfoot>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.box-body -->

                            <!--paging-->

                            <div class="dataTables_paginate paging_simple_numbers" align="center">
                                <ui:pagination paginationInfo="${pagination}" jsFunction="fn_link_page"/>
                            </div>

                        </div>
                        <!-- /.box -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </section>
            <!-- /.content -->
            </form:form>
        </div>
        <!-- /.content-wrapper -->
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <!-- DataTables -->
        <script src="/resources/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="/resources/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
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

                $("#left_li_menu_03").addClass("active");
                $("#left_li_menu_03_05").addClass("active");

            })


            function fn_link_page(pageIndex){
                $("#facebookReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#facebookReqForm").attr("action", "/manage/facebook/list.do");
                $("#facebookReqForm").submit();
            }

            function facebookDetail(idx){
                $("#facebookReqForm > #idx").val(idx);
                $("#facebookReqForm").attr("action", "/manage/facebook/detail.do");
                $("#facebookReqForm").submit();
            }

            function facebookWrite(){
                $("#facebookReqForm").attr("action", "/manage/facebook/detail.do");
                $("#facebookReqForm").submit();
            }

        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>