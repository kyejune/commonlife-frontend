<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
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
            <h2>INFO - Living Support</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    INFO - Living Support 관리
                </li>
                <li class="active">
                    <a>Support 카테고리</a>
                </li>
            </ol>
        </div>
        <div class="col-lg-2">
        </div>
    </div>

    <div class="wrapper wrapper-content animated fadeInRight">
        <form:form name="boardReqForm" id="boardReqForm" method="post" commandName="categoryInfo">
            <form:hidden path="cmplxId" value="${cmplxId}"/>
        </form:form>

        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>Living Support 항목 설정</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content" style="">
                        <form class="form-horizontal">
                            todo: 1. COMPLEX_ID 선택 할 수 있는 콤보 박스 설치
                            <%--todo: 1. COMPLEX_ID 선택 할 수 있는 콤보 박스 설치 ~--%>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <!-- 미추가 항목 -->
            <div class="col-lg-6">
                <div class="ibox">
                    <div class="ibox-content" style="">
                        <h3>미배치 항목</h3>
                        <p class="small"><i class="fa fa-hand-o-up"></i> Living Support 카테고리를 우측의 '적용된 항목'으로 옮겨 App에 배치할 수 있습니다.</p>
                        <ul class="sortable-list connectList agile-list" id="srt_list_${complexConst.grpUncategorizedId}" value="${complexConst.grpUncategorizedId}">
                        </ul>
                    </div>
                </div>
            </div>
            <!-- 추가  -->
            <div class="col-lg-6">
                <div class="ibox">
                    <div class="ibox-content" style="">
                        <h3>적용된 항목</h3>
                        <p class="small"><i class="fa fa-hand-o-up"></i> 여기에 배치된 카테고리의 순서를 변경할 수 있습니다. 변경된 순서를 App에도 동일하게 반영됩니다.</p>
                        <ul class="sortable-list connectList agile-list" id="srt_list_${complexConst.grpMinganId}" value="${complexConst.grpMinganId}">
                        </ul>
                    </div>
                </div>
            </div>
        </div>
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

            // Exception check

            <c:if test="${error != null}">
                alert("${error}");
                return;
            </c:if>

            //  전체 리스트 가져오기
            var categoryList = null;
            <c:if test="${categoryList != null}">
                categoryList = JSON.parse('${categoryList}');
            </c:if>
            console.log(categoryList);


                <%--console.log("${vo.lvngSuptCateIdx}");--%>

                <%--&lt;%&ndash;<td> ${vo.cmplxGrpId}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td> ${vo.cmplxGrp}</td>&ndash;%&gt;--%>
                <%--categoryCard = '<li class="success-element" id="${vo.lvngSuptCateIdx}">${vo.lvngSuptCateIdx} - ${vo.cmplxId}' +--%>
                    <%--'<div class="agile-detail">' +--%>
                    <%--'<i class="fa fa-clock-o"></i> ---- ' +--%>
                    <%--'</div></li>';--%>
                <%--$("#srt_list_${vo.cmplxId}").append(cmplxCard);--%>

        });

        function fn_link_page(pageIndex){
            $("#boardReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

            refreshList();
        }

        function refreshList(){
            $("#boardReqForm").attr("action", "/manage/board/list.do");
            $("#boardReqForm").submit();
        }

        function boardDetail(boardIdx){
            $("#boardReqForm > #boardIdx").val(boardIdx);
//                $("#boardReqForm").attr("action", "/board/detail.do");
            $("#boardReqForm").attr("action", "/manage/board/write.do");
            $("#boardReqForm").submit();
        }

        function boardWrite(){
            $("#boardReqForm").attr("action", "/manage/board/write.do");
            $("#boardReqForm").submit();
        }


    </script>
</tiles:putAttribute>
</tiles:insertDefinition>