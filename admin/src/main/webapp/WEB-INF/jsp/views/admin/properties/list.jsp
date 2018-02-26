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
                    시스템정보 관리
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 시스템정보 관리</a></li>
                    <li class="active">목록</li>
                </ol>
            </section>

            <form:form name="propertiesReqForm" id="propertiesReqForm" method="post" commandName="propertiesInfo">
            <!--//paging-->
            <form:hidden path="pageIndex"/>
            <form:hidden path="propGroup"/>
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
                                        <th>그룹명</th>
                                        <th>코드명</th>
                                        <th>값</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="vo" items="${propertiesList}" varStatus="status">
                                    <tr>
                                        <td>${vo.propGroup}</td>
                                        <td>${vo.propKey}</td>
                                        <td>
                                            <input type="hidden" id="${vo.propGroup}_PROP_KEY_${vo.propKey}" VALUE="${vo.propKey}">
                                            <input type="text" style="width:100%" id="${vo.propGroup}_PROP_KEY_${vo.propKey}_VALUE" VALUE="${vo.propValue}">
                                        </td>
                                        <td>
                                            <input type="button" VALUE="수정" onclick="propUpdate('${vo.propGroup}','${vo.propGroup}_PROP_KEY_${vo.propKey}', '${vo.propGroup}_PROP_KEY_${vo.propKey}_VALUE')">
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
        <script src='/js/common_check.js'></script>
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

                $("#left_li_menu_07").addClass("active");
            })


            function fn_link_page(pageIndex){
                $("#propertiesReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#propertiesReqForm").attr("action", "/manage/properties/list.do");
                $("#propertiesReqForm").submit();
            }

            function propUpdate(propGroup, pramPropKeyId, paramPropValueId){
                var propKeyId        = trim($('#'+pramPropKeyId).val());
                var propValueId       = trim($('#'+paramPropValueId).val());

//                if(propValueId == "" || typeof propValueId == "undefined"){
//                    alert('값을 입력해주세요.');
//                    return;
//                }

                $("#propertiesReqForm > #propGroup").val(propGroup);
                $("#propertiesReqForm > #propKey").val(propKeyId);
                $("#propertiesReqForm > #propValue").val(propValueId);



                var modeMsg = "";

                if(mode == "INS"){
                    modeMsg = "등록";
                }else{
                    modeMsg = "수정";
                }

                if (confirm(modeMsg+" 하시겠습니까?")) {
                    $.ajax({
                        url : '/manage/properties/proc.do',
                        type : 'post',
                        data: $("#propertiesReqForm").serialize(),
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