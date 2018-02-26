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
                    EMAIL 수신 관리
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> EMAIL 수신 관리</a></li>
                    <li class="active">목록</li>
                </ol>
            </section>

            <form:form name="emailSendManageReqForm" id="emailSendManageReqForm" method="post" commandName="propertiesInfo">
            <!--//paging-->
            <form:hidden path="pageIndex"/>
            <form:hidden path="propKey"/>
            <form:hidden path="propValue"/>
            <form:hidden path="mode" value="UPD"/>

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
                                            <th>항목</th>
                                            <th>설명</th>
                                            <th>이메일</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="vo" items="${propertiesList}" varStatus="status">
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${fn:substring(vo.propKey,0,3) eq 'VOC'}">
                                                    고객의소리
                                                </c:when>
                                                <c:when test="${fn:substring(vo.propKey,0,3) eq 'ORD'}">
                                                    단제주문
                                                </c:when>
                                                <c:when test="${fn:substring(vo.propKey,0,3) eq 'LOC'}">
                                                    입점문의
                                                </c:when>
                                                <c:when test="${fn:substring(vo.propKey,0,3) eq 'PRO'}">
                                                    제휴문의
                                                </c:when>
                                                <c:when test="${fn:substring(vo.propKey,0,3) eq 'ETC'}">
                                                    기타문의
                                                </c:when>
                                                <c:otherwise>
                                                    &nbsp;
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${vo.propDesc}</td>
                                        <td>
                                            <input type="hidden" name="propKey" id="propKey" VALUE="${vo.propKey}">
                                            <input type="text" style="width:200px" name="propValue" id="propValue" VALUE="${vo.propValue}">
                                        </td>
                                    </tr>
                                    </c:forEach>

                                    <c:if test="${fn:length(propertiesList) == 0}">
                                    <tfoot>
                                        <tr>
                                            <td colspan="4"><p><dfn>조회된 결과가 없습니다.</dfn></p></td>
                                        </tr>
                                    </tfoot>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
                <div align="right">
                    <input class="btn btn-primary" type="button" value="수정" onclick="emailAllUpdate()" />
                </div>
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
        <script src='/js/common_check.js'></script>
        <script type="text/javascript">

            $(document).ready(function(){
                $('#mngList').DataTable({
                    'paging'      : false,
                    'lengthChange': false,
                    'searching'   : false,
                    'ordering'    : false,
                    'info'        : false,
                    'autoWidth'   : true
                })

                $("#left_li_menu_06").addClass("active");

                mergeRows(mngList, 1);
                mergeRows(mngList, 0);

            });
            $(function () {

            })

            //ajax start
            $(document).ajaxStart(function() {
                showLoadingMask();
            });

            // ajax stop
            $( document ).ajaxStop(function() {
                hideLoadingMask();
            });


            function fn_link_page(pageIndex){
                $("#emailSendManageReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#emailSendManageReqForm").attr("action", "/manage/emailSendManage/list.do");
                $("#emailSendManageReqForm").submit();
            }

            /**
             * 로딩 마스크를 보인다.
             */
            function showLoadingMask(flag) {
                /*
                $("#loading-mask").css({ width:$("body").width() + "px", height:$(document).height() + "px" });
                $("#loading-mask").show();

                if( String(flag) == "undefined"){
                    $("#loading-mask").focus();
                }else if (flag){
                    $("#loading-mask").focus();
                }
                */
                var width = 0;
                var height = 0;
                var left = 0;
                var top = 0;

                width = 150;
                height = 150;


                top = ( $(window).height() - height ) / 2 + $(window).scrollTop();
                left = ( $(window).width() - width ) / 2 + $(window).scrollLeft();


                if($("#div_ajax_load_image").length != 0) {
                    $("#div_ajax_load_image").css({
                        "top": top+"px",
                        "left": left+"px"
                    });
                    $("#div_ajax_load_image").show();
                }
                else {
                    $('body').append('<div id="div_ajax_load_image" style="position:absolute; top:' + top + 'px; left:' + left + 'px; width:' + width + 'px; height:' + height + 'px; z-index:9999;  filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="/images/ajax_loader6.gif" style="width:150px; height:150px;"></div>');
                }

            }

            /**
             * 로딩 마스크를 숨긴다.
             */
            function hideLoadingMask() {
                //$("#loading-mask").fadeOut("slow");
                $("#div_ajax_load_image").hide();
            }

            function emailAllUpdate(){
                var i = 0

                var jsonData = {};
                jsonData.applyList = [];

                $("input[name=propKey]").each(function(idx){
                    var strPropKey = $("input[name=propKey]:eq(" + i + ")").val() ;
                    var strPropValue = $("input[name=propValue]:eq(" + i + ")").val() ;

                    jsonData['applyList[' + i +'].propKey'] = strPropKey;
                    jsonData['applyList[' + i +'].propValue'] = strPropValue;

                    //console.log(strPropKey + ":" + strPropValue) ;

                    i++;
                });

                jsonData.mode = 'UPDATE';

                var modeMsg = "수정";

                if (confirm(modeMsg+" 하시겠습니까?")) {
                    $.ajax({
                        url : '/manage/emailSendManage/allUpdateProc.do?${_csrf.parameterName}=${_csrf.token}',
                        type : 'post',
                        data: jsonData,
                        success: function (rs) {
                            if (rs.result) {
                                alert(modeMsg+" 되었습니다.");
                                refreshList();
                            }else{
                                alert("실패하였습니다.");
                            }
                        },
                        error : function(){
                            alert('에러가 발생하였습니다.');
                            refreshList();
                            console.log('error');
                        }
                    });
                }

            }

            function emailUpdate(pramPropKeyId, paramPropValueId){
                var propKeyId        = trim($('#'+pramPropKeyId).val());
                var propValueId       = trim($('#'+paramPropValueId).val());

                /*
                if(propValueId == "" || typeof propValueId == "undefined"){
                    alert('Email를 입력해주세요.');
                    return;
                }
                */

                $("#emailSendManageReqForm > #propKey").val(propKeyId);
                $("#emailSendManageReqForm > #propValue").val(propValueId);

                var modeMsg = "";

                if(mode == "INS"){
                    modeMsg = "등록";
                }else{
                    modeMsg = "수정";
                }

                if (confirm(modeMsg+" 하시겠습니까?")) {
                    $.ajax({
                        url : '/manage/emailSendManage/proc.do',
                        type : 'post',
                        data: $("#emailSendManageReqForm").serialize(),
                        success: function (rs) {
                            if (rs.result) {
                                alert(modeMsg+" 되었습니다.");
                                refreshList();
                            }else{
                                alert("실패하였습니다.");
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