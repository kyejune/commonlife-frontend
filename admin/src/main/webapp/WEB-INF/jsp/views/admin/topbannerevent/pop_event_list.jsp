<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="ko">
<head>
    <title>덕평휴게소 ADMIN - 이벤트 리스트</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <!-- Bootstrap 3.3.7 -->
    <link type="text/css" rel="stylesheet" href="/resources/bootstrap/css/bootstrap.min.css" />
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/resources/font-awesome/css/font-awesome.min.css">

    <!-- Theme style -->
    <link rel="stylesheet" href="/resources/adminlte/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
           folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/resources/adminlte/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/resources/datatables.net-bs/css/dataTables.bootstrap.min.css">

    <script type="text/javascript" src="/js/jquery.js"></script>

    <script type="text/javascript" src="/resources/jquery-ui-1.11.4.min.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body >
<div class="wrapper">
    <div >
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                등록된 리스트 [연결할 공지/이벤트를 선택하세요.]
            </h1>
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
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_length" id="example1_length">
                                        <label>
                                            <form:select path="boardType" id="boardType" aria-controls="example1" class="form-control input-sm" style="width:100px" onchange="refreshList()">
                                                <form:option value="NOTICE" label="공지사항"/>
                                                <form:option value="EVENT" label="이벤트"/>
                                            </form:select>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <table id="mngList" class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>No</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>공개유무</th>
                                    <th>등록일</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="vo" items="${boardList}" varStatus="status">
                                <tr>
                                    <td><c:out value="${pagination.totalRecordCount - (pagination.recordCountPerPage * (pagination.currentPageNo - 1)) - status.count + 1}"/></td>
                                    <td>${vo.title}</td>
                                    <td>${vo.mngNm}</td>
                                    <td>${vo.useYn}</td>
                                    <td>
                                        <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd" var="sysDt"/>
                                        <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd"/>
                                    </td>
                                    <td>
                                        <button class="btn btn-primary" onclick='setEvent("${vo.boardType}", "${vo.boardIdx}", "${vo.title}")'>선택</button>
                                    </td>
                                </tr>
                                </c:forEach>

                                <c:if test="${fn:length(boardList) == 0}">
                                <tfoot>
                                    <tr>
                                        <td colspan="6"><p><dfn>조회된 결과가 없습니다. 이벤트를 먼저 등록해주세요.</dfn></p></td>
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
</div>
</body>

<script type="text/javascript" src="/resources/respond.min.js"></script>
<script type="text/javascript" src="/resources/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/jquery.placeholder.min.js"></script>
<!-- AdminLTE App -->
<script type="text/javascript" src="/resources/adminlte/js/adminlte.min.js"></script>
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
    })

    function setEvent(boardType, boardIdx, title){
        opener.topBannerEventCallBack(boardType, boardIdx, title);
        window.close();
    }

    function fn_link_page(pageIndex){
        $("#boardReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

        refreshList();
    }

    function refreshList(){
        $("#boardReqForm").attr("action", "/manage/topbannerevent/pop_event_list.do");
        $("#boardReqForm").submit();
    }

    function boardDetail(boardIdx){
        $("#boardReqForm > #boardIdx").val(boardIdx);
//                $("#boardReqForm").attr("action", "/board/detail.do");
        $("#boardReqForm").attr("action", "/manage/board/event_write.do");
        $("#boardReqForm").submit();
    }

</script>
