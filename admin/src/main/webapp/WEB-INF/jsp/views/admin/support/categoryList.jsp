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
            <h2>Living Support 항목 설정 - ${complexInfo.cmplxNm}</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    현장 관리
                </li>
                <li>
                    개별 현장 관리
                </li>
                <li>
                    현장 상세 정보
                </li>
                <li class="active">
                    <a>Living Support 항목 설정</a>
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
                            <h3 >유의 사항</h3>
                            <p>
                            todo: 1. 현재 현장에 대한 정보를 표시
                            </p>
                            <p>
                            - App내 [INFO] - [Living Support]의 항목을 표시하고 순서를 변경할 수 있습니다.
                            - 항목간 순서를 변경한 이후, '업데이트'클릭하면 반영됩니다.
                            </p>
                            <%-- todo: 1. COMPLEX_ID 현재 현장에 대한 정보를 표시 --%>

                            <h3 >변경 사항 적용</h3>
                            <p>
                                <button type="button"
                                        class="btn btn-block btn-outline btn-primary"
                                        onclick="javascript:procIns();" >변경 사항 적용</button>
                            </p>
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
                        <ul class="sortable-list connectList agile-list" id="card_set_N" >
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
                        <ul class="sortable-list connectList agile-list" id="card_set_Y" >
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
        var cardSetN = null;
        var cardSetY = null;

        $(function () {
            $('.footable').footable();

            $("#left_complex").addClass("active");
            $("#left_complex > .nav-second-level").addClass("in");
            $("#left_complex_list").addClass("active");

            <c:if test="${error != null}">
                alert("${error}");
                return;
            </c:if>

            //  전체 리스트 가져오기
            var categoryList = null;
            <c:if test="${categoryList != null}">
                categoryList = JSON.parse('${categoryList}');
            </c:if>

            $.each(categoryList, function(i, obj) {
                console.log(">>>>> " + obj.cateNm);

                //use obj.id and obj.name here, for example:
                categoryCard = '<li class="success-element" id="' + obj.lvngSuptCateIdx + '">' +
                                    "항목 이름 : " + "<label class='font-bold'>" + obj.cateNm + "</label>" +
                                    '<div class="agile-detail">' +
                                    '<i class="fa fa-clock-o"></i> ' + obj.desc +
                                    '</div>' +
                                '</li>';
                $( "#card_set_" +  obj.setYn ).append(categoryCard);
                console.log("added! " + i + " // " +  + " // " + obj.setYn );
            });

            $("#card_set_N, #card_set_Y").sortable({
                connectWith: ".connectList",
                update: function( event, ui ) {
                    cardSetN = $( "#card_set_N" ).sortable( "toArray" );
                    cardSetY = $( "#card_set_Y" ).sortable( "toArray" );
                    console.log( "CARD_SET_NO: " + window.JSON.stringify(cardSetN) );
                    console.log( "CARD_SET_YES: " + window.JSON.stringify(cardSetY) );
                }
            }).disableSelection();
        });

        function procIns() {
            if( cardSetN == null && cardSetY == null ) {
                alert('업데이트할 내용이 없습니다.');
                return;
            }

            var updateList = [];

            $.each( cardSetN, function(i, obj) {
                var e = new Object();
                e['dispOrder'] = 9999;  // 해당 항목 순서는 제일 뒤로
                e['delYn'] = 'Y';     // 해당 항목 삭제
                e['lvngSuptCateIdx'] = obj;
                updateList.push(e);
            });

            $.each( cardSetY, function(i, obj) {
                var e = new Object();
                e['dispOrder'] = i + 1;     // dispOrder는 1부터 시작
                e['delYn'] = 'N';           // 해당 항목 삭제
                e['lvngSuptCateIdx'] = obj;
                updateList.push(e);
            });

            console.log(updateList);

            if( confirm("변경 사항을 적용하시겠습니까?") ) {
                $.ajax({
                    url : '/admin/support/procIns.do?cmplxId=${cmplxId}',
                    type : 'post',
                    dataType: 'json',
                    data: window.JSON.stringify(updateList),
                    contentType:"application/json; charset=UTF-8",
                    success: function (rs) {
                        if (rs.msg) {
                            alert(rs.msg);
                        }else{
                            alert("등록 되었습니다.");
                        }
                    },
                    error : function(){
                        alert('에러가 발생하였습니다.');
                        console.log('error');
                    }
                });
            }
        }

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>