<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="complexes">
<tiles:putAttribute name="title">현장/현장그룹 관리</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- DataTables -->
    <a><!-- --></a>

    <!-- Toastr style -->
    <link href="/resources/css/plugins/toastr/toastr.min.css" rel="stylesheet">

</tiles:putAttribute>
<tiles:putAttribute name="contents">
    <!-- Section Title -->
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2>현장 분류</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    현장/현장그룹 관리
                </li>
                <li class="active">
                    <a>현장 목록</a>
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
                <!-- Uncategorized -->
                <div class="col-lg-4">
                    <div class="ibox">
                        <div class="ibox-content" style="">
                            <h3>미분류</h3>
                            <p class="small"><i class="fa fa-hand-o-up"></i> 현장그룹 간에 현장을 드래그하여 이동하세요.</p>
                            <ul class="sortable-list connectList agile-list" id="uncategorized" value="${complexConst.grpUncategorizedId}">
                                <li class="warning-element" id="test_1">
                                    Simply dummy text of the printing and typesetting industry.
                                    <div class="agile-detail">
                                        <a href="#" class="pull-right btn btn-xs btn-white">Tag</a>
                                        <i class="fa fa-clock-o"></i> 12.10.2015
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- COMMNONLife  -->
                <div class="col-lg-4">
                    <div class="ibox">
                        <div class="ibox-content" style="">
                            <h3>민간</h3>
                            <p class="small"><i class="fa fa-hand-o-up"></i> 현장그룹 간에 현장을 드래그하여 이동하세요.</p>
                            <ul class="sortable-list connectList agile-list" id="mingan" value="${complexConst.grpMinganId}">
                                <li class="warning-element" id="test_2">
                                    Simply dummy text of the printing and typesetting industry.
                                    <div class="agile-detail">
                                        <a href="#" class="pull-right btn btn-xs btn-white">Tag</a>
                                        <i class="fa fa-clock-o"></i> 12.10.2015
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- 민간 -->
                <div class="col-lg-4">
                    <div class="ibox">
                        <div class="ibox-content" style="">
                            <h3>공공</h3>
                            <p class="small"><i class="fa fa-hand-o-up"></i> 현장그룹 간에 현장을 드래그하여 이동하세요.</p>
                            <ul class="sortable-list connectList agile-list" id="gonggong" value="${complexConst.grpGonggongId}">
                                <li class="info-element" id="test_3">
                                    Simply dummy text of the printing and typesetting industry.
                                    <div class="agile-detail">
                                        <a href="#" class="pull-right btn btn-xs btn-white">Tag</a>
                                        <i class="fa fa-clock-o"></i> 12.10.2015
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</tiles:putAttribute>
<tiles:putAttribute name="js">


    <!-- jquery UI -->
    <script src="/resources/js/plugins/jquery-ui/jquery-ui.min.js"></script>
    <!-- Touch Punch - Touch Event Support for jQuery UI -->
    <script src="/resources/js/plugins/touchpunch/jquery.ui.touch-punch.min.js"></script>

    <script type="text/javascript">
        $(function () {
            $('.footable').footable();

            $("#left_complex").addClass("active");
            $("#left_complex > .nav-second-level").addClass("in");
            $("#left_complex_category").addClass("active");
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

            //  전체 리스트 가져오기
            var cmplxCard;
            <c:forEach var="vo" items="${complexList}" varStatus="status">
            <%--<td> ${vo.cmplxGrpId}</td>--%>
            <%--<td> ${vo.cmplxGrp}</td>--%>
            cmplxCard = '<li class="success-element" id="${vo.cmplxId}">${vo.cmplxNm}' +
                '<div class="agile-detail">' +
                '<i class="fa fa-clock-o"></i> ${vo.addr}' +
                '</div></li>';
            $("#uncategorized").append(cmplxCard);
            </c:forEach>

            $("#uncategorized, #mingan, #gonggong").sortable({
                connectWith: ".connectList",
                receive: function( event, ui ) {
                    var cmplxGrpId  = $(event.target).attr('value');
                    var cmplxId = ui.item.attr('id');

                    console.log(cmplxGrpId);
                    console.log(cmplxId);
                    // ui.item.attr('data-id');

                    procIns( cmplxId, cmplxGrpId);

                    var todo = $( "#uncategorized" ).sortable( "toArray" );
                    var inprogress = $( "#mingan" ).sortable( "toArray" );
                    var completed = $( "#gonggong" ).sortable( "toArray" );
                    console.log("ToDo: " + window.JSON.stringify(todo) + "<br/>" +
                        "In Progress: " + window.JSON.stringify(inprogress) + "<br/>" +
                        "Completed: " + window.JSON.stringify(completed));
                }
            }).disableSelection();
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

        function procIns(cmplxId, cmplxGrpId){
            if (confirm("업데이트 하시겠습니까?")) {
                var data = {};
                data['cmplxId'] = cmplxId;
                data['cmplxGrpId'] = cmplxGrpId;

                console.log(data);

                $.ajax({
                    dataType: "json",
                    url : '/admin/complexes/updateCategory.do',
                    type : 'PUT',
                    data: data,
                    success: function (rs) {
                        if (rs.result) {
                            alert(modeMsg+" 되었습니다.");
                            refreshList();
                        }else{
                            alert(rs.msg);
                        }
                    },
                    error : function(jqxhr){
                        var respBody = jQuery.parseJSON(jqxhr.responseText);
                        console.log(respBody);
                        alert(modeMsg + "에 실패하였습니다.\n\n - 원인: " + respBody.msg);
                    }
                });
            }
        }




    </script>
</tiles:putAttribute>
</tiles:insertDefinition>