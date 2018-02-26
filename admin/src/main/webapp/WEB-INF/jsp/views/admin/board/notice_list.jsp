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
                    공지사항
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 덕평소식</a></li>
                    <li class="active">공지사항</li>
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
                                <!--
                                <h3 class="box-title">공지사항</h3>
                                -->
                                <button class="btn btn-primary" onclick="boardWrite()">글작성</button>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table id="mngList" class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>공지사항/보도자료</th>
                                        <th>제목</th>
                                        <th>작성자</th>
                                        <th>공개유무</th>
                                        <th>등록일</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="vo" items="${boardList}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${pagination.totalRecordCount - (pagination.recordCountPerPage * (pagination.currentPageNo - 1)) - status.count + 1}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${vo.boardType eq 'NOTICE'}">
                                                    공지사항
                                                </c:when>
                                                <c:when test="${vo.boardType eq 'PRESS'}">
                                                    보도자료
                                                </c:when>
                                                <c:otherwise>
                                                    미지정
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><a href="javascript:boardDetail('${vo.boardIdx}')">${vo.title}</a></td>
                                        <td>${vo.mngNm}</td>
                                        <td>${vo.useYn}</td>
                                        <td>
                                            <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd" var="sysDt"/>
                                            <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd"/>
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

                $("#left_li_menu_03").addClass("active");
                $("#left_li_menu_03_01").addClass("active");

            })


            function fn_link_page(pageIndex){
                $("#boardReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#boardReqForm").attr("action", "/manage/board/notice_list.do");
                $("#boardReqForm").submit();
            }

            function boardDetail(boardIdx){
                $("#boardReqForm > #boardIdx").val(boardIdx);
//                $("#boardReqForm").attr("action", "/board/detail.do");
                $("#boardReqForm").attr("action", "/manage/board/notice_write.do");
                $("#boardReqForm").submit();
            }

            function boardWrite(){
                $("#boardReqForm").attr("action", "/manage/board/notice_write.do");
                $("#boardReqForm").submit();
            }

        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>