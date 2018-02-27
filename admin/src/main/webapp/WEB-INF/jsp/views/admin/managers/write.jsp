<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">

<tiles:putAttribute name="title">관리자</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- CSS -->
    <a></a>
</tiles:putAttribute>

<tiles:putAttribute name="contents">
    <!-- Section Title -->
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2>관리자 등록</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    <a>관리자 관리</a>
                </li>
                <li class="active">
                    <strong>관리자 등록/수정</strong>
                </li>
            </ol>
        </div>
        <div class="col-lg-2">

        </div>
    </div>

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>관리자 정보</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content" style="">
                        <form:form class="form-horizontal" name="managerReqForm" id="managerReqForm" method="post" commandName="adminInfo">
                            <div class="box-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">아이디</label>
                                    <div class="col-sm-10">
                                    <c:choose>
                                        <c:when test="${managerDetail.adminId != null }">
                                            <input type="text" id="adminId" name="adminId" class="form-control" readonly value="${managerDetail.adminId}">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" id="adminId" name="adminId" class="form-control" placeholder="아이디 입력">
                                        </c:otherwise>
                                    </c:choose>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">비밀번호</label>
                                    <div class="col-sm-10">
                                        <input type="password" id="mngPw" name="mngPw" class="form-control" placeholder="비밀번호 입력">
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">관리자명</label>
                                    <div class="col-sm-10">
                                        <input type="text" id="adminNm" name="adminNm" class="form-control" placeholder="관리자명 입력" value="${managerDetail.adminNm}">
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">이메일</label>
                                    <div class="col-sm-10">
                                        <input type="text" id="adminEmail" name="adminEmail" class="form-control" placeholder="EMAIL 입력" value="${managerDetail.adminEmail}">
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">비고</label>
                                    <div class="col-sm-10">
                                        <input type="text" id="desc" name="desc" class="form-control" placeholder="비고 입력" value="${managerDetail.desc}">
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">사용여부</label>
                                    <div class="col-sm-10">
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
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <div class="col-sm-4 col-sm-offset-2">
                                        <input class="btn btn-primary" type="button" value="목록" onclick="refreshList()" />
                                        <c:choose>
                                            <c:when test="${managerDetail.adminId != null }">
                                                <input class="btn btn-primary" type="button" value="수정" onclick="procIns()" />
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-primary" type="button" value="등록" onclick="procIns()" />
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            <!-- /.content -->
                            <c:choose>
                                <c:when test="${managerDetail.adminId != null }">
                                    <input type="hidden" name="mode" id="mode" value="UPD">
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="mode" id="mode" value="INS">
                                    <input type="hidden" name="delYn" id="delYn" value="N">
                                </c:otherwise>
                            </c:choose>
                        </form:form>
                        <form:form name="managerListForm" id="managerListForm" method="post" commandName="adminInfo">
                            <form:hidden path="pageIndex"/>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
            $("#managerListForm").attr("action", "/admin/managers/list.do");
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
                    url : '/admin/managers/proc.do',
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