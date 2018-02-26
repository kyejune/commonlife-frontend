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
                    특별행사
                    <small>Version 1.0</small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 특별행사</a></li>
                    <li class="active">목록</li>
                </ol>
            </section>

            <form:form name="boardReqForm" id="boardReqForm" method="post" commandName="boardInfo">
            <!--//paging-->
            <form:hidden path="pageIndex"/>
            <form:hidden path="boardIdx"/>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box">
                            <div class="box-header" align="right">
                                <button class="btn btn-primary" onclick="boardWrite()">등록</button>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table id="mngList" class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>제목</th>
                                        <th>작성자</th>
                                        <th>공개유무</th>
                                        <th>이벤트기간</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="vo" items="${boardList}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${pagination.totalRecordCount - (pagination.recordCountPerPage * (pagination.currentPageNo - 1)) - status.count + 1}"/></td>
                                        <td><a href="javascript:boardDetail('${vo.boardIdx}')">${vo.title}</a></td>
                                        <td>${vo.regUserId}</td>
                                        <td>${vo.useYn}</td>
                                        <td>
                                            <fmt:parseDate value="${vo.openDt}" pattern="yyyy-MM-dd" var="openDt"/>
                                            <fmt:formatDate value="${openDt}" pattern="yyyy.MM.dd"/>
                                            ~
                                            <fmt:parseDate value="${vo.closeDt}" pattern="yyyy-MM-dd" var="closeDt"/>
                                            <fmt:formatDate value="${closeDt}" pattern="yyyy.MM.dd"/>

                                        </td>
                                    </tr>
                                    </c:forEach>

                                    <c:if test="${fn:length(boardList) == 0}">
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

                $("#left_li_menu_02").addClass("active");
            })


            function fn_link_page(pageIndex){
                $("#boardReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#boardReqForm").attr("action", "/manage/shop/special_shop_list.do");
                $("#boardReqForm").submit();
            }

            function boardDetail(boardIdx){
                $("#boardReqForm > #boardIdx").val(boardIdx);
//                $("#boardReqForm").attr("action", "/board/detail.do");
                $("#boardReqForm").attr("action", "/manage/shop/special_shop_write.do");
                $("#boardReqForm").submit();
            }

            function boardWrite(){
                $("#boardReqForm").attr("action", "/manage/shop/special_shop_write.do");
                $("#boardReqForm").submit();
            }

        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>