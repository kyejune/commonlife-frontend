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
                    입점/제휴 문의
                    <!--<small>Version 1.0</small>-->
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 고객센터</a></li>
                    <li class="active">입점/제휴/기타</li>
                    <li class="active">목록</li>
                </ol>
            </section>

            <form:form name="boardReqForm" id="boardReqForm" method="post" commandName="boardInfo">
            <!--//paging-->
            <form:hidden path="pageIndex"/>
            <form:hidden path="boardIdx"/>
            <form:hidden path="boardType" value="${boardType}"/>
            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box">
                            <div class="box-header" align="right">
                                <!--
                                <h3 class="box-title">공지사항</h3>
                                -->
                                <!--
                                <button class="btn btn-primary" onclick="boardWrite()">글작성</button>
                                -->
                            </div>
                            <!-- /.box-header -->
                            <div class="dataTables_wrapper form-inline dt-bootstrap">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <div class="dataTables_length" id="example1_length">
                                                <label>
                                                    <form:select path="searchType1" id="searchType1" aria-controls="example1" class="form-control input-sm" style="width:100px">
                                                        <form:option value="" label="전체" />
                                                        <form:option value="TITLE" label="제목"/>
                                                        <form:option value="CONTENT" label="내용"/>
                                                        <form:option value="WRITE_NM" label="작성자"/>
                                                        <form:option value="WRITE_HP" label="연락처"/>
                                                        <form:option value="WRITE_EMAIL" label="EMAIL"/>
                                                    </form:select>
                                                </label>
                                                <input  style="width:200px" type="text" name="searchKeyword1" id="searchKeyword1" class="form-control input-sm" placeholder="" aria-controls="example1" value="${boardInfo.searchKeyword1}">
                                                <button class="btn btn-primary btn-inquiry" type="submit">조회</button>
                                            </div>
                                        </div>
                                    </div>
                                    <table id="mngList" class="table table-bordered table-striped">
                                        <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>구분</th>
                                            <th>제목</th>
                                            <th>작성자</th>
                                            <th>답변여부</th>
                                            <th>등록일</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="vo" items="${boardList}" varStatus="status">
                                        <tr>
                                            <td><c:out value="${pagination.totalRecordCount - (pagination.recordCountPerPage * (pagination.currentPageNo - 1)) - status.count + 1}"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${vo.boardType eq 'LOCATION'}">
                                                        입점
                                                    </c:when>
                                                    <c:when test="${vo.boardType eq 'PROMOTION'}">
                                                        제휴
                                                    </c:when>
                                                    <c:when test="${vo.boardType eq 'ETC'}">
                                                        기타
                                                    </c:when>
                                                    <c:otherwise>
                                                        &nbsp;
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><a href="javascript:boardDetail('${vo.boardIdx}')">${vo.title}</a></td>
                                            <td>${vo.writeNm}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${vo.replyCount > 0}">
                                                        Y
                                                    </c:when>
                                                    <c:otherwise>
                                                        N
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd HH:mm:ss" var="sysDt"/>
                                                <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd HH:mm"/>
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
                            </div>
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

                $("#left_li_menu_04").addClass("active");
                $("#left_li_menu_04_03").addClass("active");
            })


            function fn_link_page(pageIndex){
                $("#boardReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#boardReqForm").attr("action", "/manage/customer/etc_list.do");
                $("#boardReqForm").submit();
            }

            function boardDetail(boardIdx){
                $("#boardReqForm > #boardIdx").val(boardIdx);
                $("#boardReqForm").attr("action", "/manage/customer/etc_view.do");
                $("#boardReqForm").submit();
            }
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>