<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="manage">
    <tiles:putAttribute name="title">관리자</tiles:putAttribute>
    <tiles:putAttribute name="css">
        <!-- DataTables -->
        <link rel="stylesheet" href="/resources/datatables.net-bs/css/dataTables.bootstrap.min.css">
        <link rel="stylesheet" href="/resources/bootstrap-daterangepicker/daterangepicker.css">
        <link rel="stylesheet" href="/resources/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
    </tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <jsp:include page ="/WEB-INF/tiles/common/left.jsp" flush="true"/>
        <script type="text/javascript" src="/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>

        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    관리자 등록
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 관리자</a></li>
                    <li class="active">등록</li>
                </ol>
            </section>

            <form:form name="managerReqForm" id="managerReqForm" method="post" commandName="managerInfo">
            <!-- Main content -->
            <div class="box-header with-border">
                <h3 class="box-title"> </h3>
            </div>

            <div class="box-body">
                <div class="form-group">
                    <label>* 아이디</label>
                    <c:choose>
                        <c:when test="${managerDetail.mngId != null }">
                            <input type="text" id="mngId" name="mngId" class="form-control" readonly value="${managerDetail.mngId}">
                        </c:when>
                        <c:otherwise>
                            <input type="text" id="mngId" name="mngId" class="form-control" placeholder="아이디 입력">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <label>* 비밀번호</label>
                    <input type="password" id="mngPw" name="mngPw" class="form-control" placeholder="비밀번호 입력">
                </div>

                <div class="form-group">
                    <label>* 관리자명</label>
                    <input type="text" id="mngNm" name="mngNm" class="form-control" placeholder="관리자명 입력" value="${managerDetail.mngNm}">
                </div>

                <div class="form-group">
                    <label>* 이메일</label>
                    <input type="text" id="mngEmail" name="mngEmail" class="form-control" placeholder="EMAIL 입력" value="${managerDetail.mngEmail}">
                </div>

                <div class="form-group">
                    <label>* 비고</label>
                    <input type="text" id="bigo" name="bigo" class="form-control" placeholder="비고 입력" value="${managerDetail.bigo}">
                </div>

                <div class="form-group">
                    <label>* 사용여부</label>
                    <div class="radio">
                        <label>
                            <input type="radio" name="useYn" id="useYnTrue" value="Y" checked>
                            사용
                        </label>&nbsp;&nbsp;&nbsp;
                        <label>
                            <input type="radio" name="useYn" id="useYnFalse" value="N" >
                            사용안함
                        </label>
                    </div>
                </div>


                <div align="right">
                    <input class="btn btn-primary" type="button" value="목록" onclick="refreshList()" />
                    <c:choose>
                        <c:when test="${managerDetail.mngId != null }">
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
                <c:when test="${managerDetail.mngId != null }">
                    <input type="hidden" name="mode" id="mode" value="UPD">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="mode" id="mode" value="INS">
                    <input type="hidden" name="delYn" id="delYn" value="N">
                </c:otherwise>
            </c:choose>
            </form:form>
            <form:form name="managerListForm" id="managerListForm" method="post" commandName="managerInfo">
                <form:hidden path="pageIndex"/>
            </form:form>
        </div>
        <!-- /.content-wrapper -->
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script src='/js/common_check.js'></script>
        <script type="text/javascript">
            $(function () {

                <c:if test="${managerDetail != null }">
                $('input:radio[name=useYn]:input[value=${managerDetail.useYn}]').attr("checked", "checked");
                </c:if>

                $("#left_li_menu_05").addClass("active");

            })


            function fn_link_page(pageIndex){
                $("#managerReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#managerListForm").attr("action", "/manage/managers/list.do");
                $("#managerListForm").submit();
            }



            function procIns(){
                var mode        = trim($('#mode').val());
                var mngId       = trim($('#mngId').val());
                var mngPw       = trim($('#mngPw').val());
                var mngNm       = trim($('#mngNm').val());
                var mngEmail    = trim($('#mngEmail').val());
                var useYn       = $(':radio[name="useYn"]:checked').val();

                if(mngId == "" || typeof mngId == "undefined"){
                    alert('아이디를 입력해주세요.');
                    return;
                }

                <c:if test="${managerDetail == null }">
                if(mngPw == "" || typeof mngPw == "undefined"){
                    alert('비밀번호를 입력해주세요.');
                    return;
                }
                </c:if>

                if(mngNm == "" || typeof mngNm == "undefined"){
                    alert('관리자명을 입력해주세요.');
                    return;
                }

                if(mngEmail == "" || typeof mngEmail == "undefined"){
                    alert('이메일을 입력해주세요.');
                    return;
                }

                var modeMsg = "";

                if(mode == "INS"){
                    modeMsg = "등록";
                }else{
                    modeMsg = "수정";
                }

                if (confirm(modeMsg+" 하시겠습니까?")) {
                    $.ajax({
                        url : '/manage/managers/proc.do',
                        type : 'post',
                        data: $("#managerReqForm").serialize(),
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