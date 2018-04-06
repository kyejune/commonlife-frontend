<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
    <tiles:putAttribute name="title">관리자</tiles:putAttribute>
    <tiles:putAttribute name="css">
        <!-- DataTables -->
        <link rel="stylesheet" href="/resources/datatables.net-bs/css/dataTables.bootstrap.min.css">
    </tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <!-- Section Title -->
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>티켓 관리 - <label class="font-bold">${complexInfo.cmplxNm}</label></h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        Living Support
                    </li>
                    <li class="active">
                        <a>티켓 관리</a>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
            </div>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-body">
                            <table id="ticketList" class="table table-bordered table-striped table-hover dataTable">
                                <thead>
                                <tr>
                                    <th>No</th>
                                    <th>완료 여부</th>
                                    <th>분류</th>
                                    <th>작성자</th>
                                    <th>작성 내용</th>
                                    <th>등록 시간</th>
                                    <th>변경 시간</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="vo" items="${ticketList}" varStatus="status">
                                <tr>
                                    <td> ${vo.tktIdx}</td>
                                    <td> ${vo.respYn}</td>
                                    <td> ${vo.cateNm}</td>
                                    <td> ${vo.userNm}</td>
                                    <td>
                                        <a href="ticketView.do?tktIdx=${vo.tktIdx}&pageNum=${paginateInfo.currentPageNo}">
                                                ${vo.content}
                                        </a>
                                    </td>
                                    <td>
                                        <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd HH:mm" var="regDttm"/>
                                        <fmt:formatDate value="${regDttm}" pattern="yyyy.MM.dd HH:mm"/>
                                    </td>
                                    <td>
                                        <fmt:parseDate value="${vo.updDttm}" pattern="yyyy-MM-dd HH:mm" var="updDttm"/>
                                        <fmt:formatDate value="${updDttm}" pattern="yyyy.MM.dd HH:mm"/>
                                    </td>
                                </tr>
                                </c:forEach>

                                <c:if test="${fn:length(ticketList) == 0}">
                                <tfoot>
                                    <tr>
                                        <td colspan="7"><p><dfn>조회된 결과가 없습니다.</dfn></p></td>
                                    </tr>
                                </tfoot>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.box-body -->

                        <!--paging-->
                        <div class="dataTables_paginate paging_simple_numbers" align="center">
                            <ui:pagination paginationInfo="${paginateInfo}" jsFunction="fn_link_page"/>
                        </div>

                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.content -->
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <!-- DataTables -->
        <script src="/resources/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="/resources/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
        <script type="text/javascript">
            $(function () {
                // $('#mngList').DataTable({
                //     'paging'      : false,
                //     'lengthChange': false,
                //     'searching'   : false,
                //     'ordering'    : false,
                //     'info'        : false,
                //     'autoWidth'   : true
                // })
                //
                // $("#left_li_menu_03").addClass("active");
                // $("#left_li_menu_03_02").addClass("active");
            })


            function fn_link_page( pageIndex) {
                window.location.replace("ticketList.do?pageNum=" + pageIndex);

//                 $("#boardReqForm > #pageIndex").val(pageIndex);
// //                $("#pageIndex").val(pageIndex);
//
//                 refreshList();
            }

            function refreshList(){
                location.reload();
            }

            function boardDetail(boardIdx){
                // $("#boardReqForm > #boardIdx").val(boardIdx);
//                $("#boardReqForm").attr("action", "/board/detail.do");
//                 $("#boardReqForm").attr("action", "/manage/board/event_write.do");
//                 $("#boardReqForm").submit();
            }

            function boardWrite(){
                // $("#boardReqForm").attr("action", "/manage/board/event_write.do");
                // $("#boardReqForm").submit();
            }

        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>