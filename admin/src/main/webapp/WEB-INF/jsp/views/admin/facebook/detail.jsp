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
        <script type="text/javascript" src="/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>

        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    페이스북
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 덕평소식</a></li>
                    <li class="active">페이스북</li>
                    <li class="active">상세</li>
                </ol>
            </section>

            <form:form name="facebookReqForm" id="facebookReqForm" method="post" commandName="faceBookInfo">
            <!--//paging-->
            <form:hidden path="pageIndex"/>
            <form:hidden path="idx"/>
            <!-- Main content -->
            <div class="box-header with-border">
                <h3 class="box-title"> </h3>
            </div>

            <div class="box-body">
                <div class="form-group">
                    <c:choose>
                        <c:when test="${faceBookDetail.link != ''}">
                            <label>* 링크주소 </label><br />
                            <a href="${faceBookDetail.link}" target="_blank">${faceBookDetail.link}</a>
                        </c:when>
                    </c:choose>
                </div>

                <div class="form-group">
                    <c:choose>
                        <c:when test="${faceBookDetail.fullPicture != ''}">
                            <label>* 이미지</label><br />
                            <img src="${faceBookDetail.fullPicture}">
                        </c:when>
                    </c:choose>
                </div>
                <div class="form-group">
                    <label>* 내용</label>

                    <textarea rows="10" cols="100" style="width:100%; height:412px;" readonly>${faceBookDetail.message}</textarea>
                </div>
                <div class="form-group">
                    <label>* 공개여부</label>
                    <div class="radio">
                        <label>
                            <input type="radio" name="isOpen" id="isOpenTrue" value="Y" checked>
                            공개
                        </label>&nbsp;&nbsp;&nbsp;
                        <label>
                            <input type="radio" name="isOpen" id="isOpenFalse" value="N" >
                            비공개
                        </label>
                    </div>
                </div>
                <div align="right">
                    <input class="btn btn-primary" type="button" value="목록" onclick="refreshList()" />
                    <c:choose>
                        <c:when test="${faceBookDetail.idx > 0 }">
                            <input class="btn btn-primary" type="button" value="수정" onclick="procIns()" />
                        </c:when>
                        <c:otherwise>
                            <input class="btn btn-primary" type="button" value="등록" onclick="procIns()" />
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <!-- /.content -->

            <c:choose>
                <c:when test="${faceBookDetail.idx > 0 }">
                    <input type="hidden" name="mode" id="mode" value="UPD">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="mode" id="mode" value="INS">
                    <input type="hidden" name="delYn" id="delYn" value="N">
                </c:otherwise>
            </c:choose>
            </form:form>
        </div>
        <!-- /.content-wrapper -->
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <!-- DataTables -->
        <script src="/resources/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="/resources/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
        <script src='/js/common_check.js'></script>
        <script src='/js/ajaxfileupload.js'></script>
        <script type="text/javascript">
            $(function () {
                $("#left_li_menu_03").addClass("active");
                $("#left_li_menu_03_05").addClass("active");

                <c:if test="${faceBookDetail != null }">
                $('input:radio[name=isOpen]:input[value=${faceBookDetail.isOpen}]').attr("checked", "checked");
                </c:if>

            })


            function fn_link_page(pageIndex){
                $("#facebookReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#facebookReqForm").attr("action", "/manage/facebook/list.do");
                $("#facebookReqForm").submit();
            }

            function procIns(){
                var mode        = trim($('#mode').val());
                var title       = trim($('#title').val());
                var isOpen      = $(':radio[name="isOpen"]:checked').val();

                /*
                if(title == "" || typeof title == "undefined"){
                    alert('제목을 입력해주세요.');
                    return;
                }
                */

                var modeMsg = "";

                if(mode == "INS"){
                    modeMsg = "등록";
                }else{
                    modeMsg = "수정";
                }

                if (confirm(modeMsg+" 하시겠습니까?")) {
                    $.ajax({
                        url : '/manage/facebook/proc.do',
                        type : 'post',
                        data: $("#facebookReqForm").serialize(),
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