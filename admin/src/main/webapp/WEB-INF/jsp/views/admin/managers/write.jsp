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
                <c:choose>
                    <c:when test="${managerDetail.grpId == adminConst.adminGrpSuper}">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                '슈퍼 관리자' 등록 및 수정
                            </div>
                            <div class="ibox-content" style="">
                                {슈퍼 관리자의 등록과 관련 된 내용을 여기에 입력 합니다.}
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${managerDetail.grpId == adminConst.adminGrpComplex}">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                '현장 관리자' 등록 및 수정
                            </div>
                            <div class="ibox-content" style="">
                                {현장 관리자의 등록과 관련 된 내용을 여기에 입력 합니다.}
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                            </div>
                            <div class="ibox-content" style="">
                                --- 미지정 된 관리자 ---
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
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
                                        <c:when test="${mode == 'INS'}">
                                            <input type="text" id="adminId" name="adminId" class="form-control" placeholder="아이디 입력">
                                        </c:when>
                                        <c:when test="${mode == 'UPD'}">
                                            <input type="text" id="adminId" name="adminId" class="form-control" readonly value="${managerDetail.adminId}">
                                        </c:when>
                                        <c:otherwise>
                                            {잘못된 접근 입니다}
                                        </c:otherwise>
                                    </c:choose>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">비밀번호</label>
                                    <div class="col-sm-10">
                                        {todo:비밀번호 재입력 화면을 추가해야 함}
                                        <p/>
                                        <input type="password" id="adminPw" name="adminPw" class="form-control" placeholder="비밀번호 입력">
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
                            <c:if test="${managerDetail.grpId == adminConst.adminGrpComplex}">
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">담당 현장</label>
                                    <div class="col-sm-10">
                                        <select class="form-control m-b" name="cmplxId" id="cmplxId"  value="132">
                                            <c:forEach var="vo" items="${cmplxList}" varStatus="status">
                                                <option value="${vo.cmplxId}">${vo.cmplxGrp} | ${vo.cmplxNm} / ${vo.cmplxId} / ${vo.cmplxGrpId} </option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" id="cmplxId" name="cmplxId" class="form-control" placeholder="담당 현장 입력" value="${managerDetail.cmplxNm}">--%>
                                    </div>
                                </div>
                            </c:if>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">설명</label>
                                    <div class="col-sm-10">
                                        <input type="text" id="desc" name="desc" class="form-control" placeholder="해당 관리자에 대한 설명을 입력하세요." value="${managerDetail.desc}">
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">사용유무</label>
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
                                        <c:choose>
                                            <c:when test="${mode == 'INS'}">
                                                <input class="btn btn-primary" type="button" value="등록" onclick="procIns()" />
                                            </c:when>
                                            <c:when test="${mode == 'UPD'}">
                                                <input class="btn btn-primary" type="button" value="수정" onclick="procIns()" />
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-danger" type="button" value="{잘못된 접근 입니다}"  />
                                            </c:otherwise>
                                        </c:choose>
                                        <input class="btn btn-default" type="button" value="취소" onclick="refreshList()" />
                                    </div>
                                </div>
                            </div>
                            <!-- /.content -->
                            <input type="hidden" name="mode" id="mode" value="${mode}">
                            <form:hidden path="grpId"/>
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
            <c:choose>
                <c:when test="${mode == 'UPD'}">
            $('input:radio[name=useYn]:input[value=${managerDetail.useYn}]').attr("checked", "checked");
                </c:when>
            </c:choose>

            $("#left_admin").addClass("active");
            <c:choose>
                <c:when test="${adminConst.adminGrpSuper == managerDetail.grpId}">
                    $("#left_admin_super").addClass("active");
                </c:when>
                <c:when test="${adminConst.adminGrpComplex == managerDetail.grpId}">
                    $('#cmplxId').val(${managerDetail.cmplxId});
                    $("#left_admin_complex").addClass("active");
                </c:when>
            </c:choose>
        })


        function fn_link_page(pageIndex){
            $("#managerReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

            refreshList();
        }

        function refreshList(){
            $("#managerListForm").attr("action", "/admin/managers/list.do?grpId=${managerDetail.grpId}");
            $("#managerListForm").submit();
        }


        function procIns(){
            var mode          = trim($('#mode').val());
            var adminId       = trim($('#adminId').val());
            var adminPw       = trim($('#adminPw').val());
            var adminMn       = trim($('#adminMn').val());
            var adminEmail    = trim($('#adminEmail').val());
            var useYn       = $(':radio[name="useYn"]:checked').val();

            if(adminId == "" || typeof adminId == "undefined"){
                alert('아이디를 입력해주세요.');
                return;
            }

            <c:if test="${managerDetail == null }">
            if(adminPw == "" || typeof adminPw == "undefined"){
                alert('비밀번호를 입력해주세요.');
                return;
            }
            </c:if>

            if(adminNm == "" || typeof adminNm == "undefined"){
                alert('관리자명을 입력해주세요.');
                return;
            }

            if(adminEmail == "" || typeof adminEmail == "undefined"){
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
                    dataType: "json",
                    url : '/admin/managers/proc.do',
                    type : 'post',
                    data: $("#managerReqForm").serialize(),
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