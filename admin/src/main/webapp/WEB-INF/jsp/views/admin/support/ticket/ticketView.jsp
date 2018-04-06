<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
    <tiles:putAttribute name="title">티켓 관리</tiles:putAttribute>
    <tiles:putAttribute name="css">
        <!-- DataTables -->
        <link rel="stylesheet" href="/resources/datatables.net-bs/css/dataTables.bootstrap.min.css">
    </tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <script type="text/javascript" src="/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>

        <form:form name="boardReqForm" id="boardReqForm" method="post" commandName="ticketInfo" enctype="multipart/form-data" >
        <div class="content-wrapper">
            <div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-lg-10">
                    <h2>티켓 관리 - <label class="font-bold">${complexInfo.cmplxNm}</label></h2>
                    <ol class="breadcrumb">
                        <li>
                            <a href="/">Home</a>
                        </li>
                        <li>
                            Living Support
                        </li>
                        <li>
                            <a>티켓 관리</a>
                        </li>
                        <li class="active">
                            <a>티켓 답</a>
                        </li>
                    </ol>
                </div>
                <div class="col-lg-2">
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>티켓 기본 정보</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <div class="row">
                                <div class="col-lg-4 b-r">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="panel panel-default" >
                                                <div class="panel-heading">
                                                    사용자 문의 사항
                                                </div>
                                                <div class="panel-body">
                                                    <div class="scroll_content">
                                                            ${ticketInfo.content}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 b-r">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="panel panel-default">
                                                <div class="panel-body">
                                                    <div class="col-sm-3 b-r">
                                                        분류
                                                    </div>
                                                    <div class="col-sm-9">
                                                        <span class="badge badge-success">
                                                            ${ticketInfo.cateNm}
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <!-- 사용자 업로드한 이미지 위치 -->
                                        <div class="col-sm-12">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    사용자 첨부 이미지
                                                </div>
                                                <div class="panel-body">
                                                    <c:choose>
                                                        <c:when test="${ticketFileImgUrl != null}">
                                                             <img src="${ticketFileImgUrl}" class="img-responsive">
                                                        </c:when>
                                                        <c:otherwise>이미지 없음</c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--<div class="hr-line-dashed"></div>--%>
                                <div class="col-lg-4">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">문의 사용자 정보</div>
                                            </div>
                                            <div class="contact-box">
                                                <a href="#">
                                                    <div class="col-sm-4">
                                                        <div class="text-center">
                                                            <!-- todo: 사진 임시로... -->
                                                            <img alt="image" class="img-circle m-t-xs img-responsive" src="https://s3.ap-northeast-2.amazonaws.com/cl.dev.image.target/resize/m/profile/panda-649938_1280.jpg">
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-8">
                                                        <h3><strong>${userExtInfo.userNm}</strong></h3>
                                                        <address>
                                                            <c:if test="${userExtInfo.cellYn != null}">
                                                                <abbr title="Phone">P:</abbr> ${userExtInfo.cell}<br>
                                                            </c:if>
                                                            <c:if test="${userExtInfo.emailYn != null}">
                                                                <abbr title="Email">E:</abbr>
                                                                <a href="mailto:${userExtInfo.email}">${userExtInfo.email}</a>
                                                            </c:if>
                                                        </address>
                                                    </div>
                                                    <div class="clearfix"></div>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>현장관리자 티켓 지원 정보</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <div class="row">
                                <div class="col-sm-6 b-r">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-horizontal">
                                                <div class="form-group">
                                                    <label class="control-label col-sm-3 b-r">
                                                        티켓 완료 여부
                                                    </label>
                                                    <div class="form-control-static col-sm-3">
                                                        <label>
                                                            <input type="radio" checked="" value="option1" id="ticketSolveYes" name="ticketSolveRadios">
                                                            완료 됨
                                                        </label>
                                                        <label>
                                                            <input type="radio" checked="" value="option1" id="ticketSolveNo" name="ticketSolveRadios">
                                                            처리 중
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="hr-line-dashed"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <%--<div class="row">--%>

                                    <%--</div>--%>
                                    <div class="row">
                                        <label class="control-label col-sm-3 b-r">
                                            현장 관리자 코멘트
                                        </label>
                                        <div class="9">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <br>
                                            <div class="media-body">
                                                <p>
                                                <textarea class="form-control" placeholder="사용자의 상세한 요청사항과 처리 결과를 여기에 작성하세요..."></textarea>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-1">
                                        </div>
                                        <div class="col-sm-10">
                                            <button type="button" class="btn btn-block btn-outline btn-primary">코멘트 업데이트</button>
                                        </div>
                                        <div class="col-sm-1">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="hr-line-dashed"></div>
                                        <div class="col-sm-3  b-r">
                                            <label class="control-label">
                                                코멘트 히스토리
                                            </label>
                                        </div>
                                        <div class="9">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="scroll_content_admin_comment
                                                        border-top-bottom
                                                        border-left-right
                                                        p-sm">
                                                <c:choose>
                                                    <c:when test="${ticketInfo.adminComment == null || ticketInfo.adminComment == ''}"><i>내용 없음</i></c:when>
                                                    <c:otherwise>${ticketInfo.adminComment}</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="hr-line-dashed"></div>
                                        이전 현장 관리자 코멘트
                                        {{내용 업데이트 버튼}}
                                        <div class="hr-line-dashed"></div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    {{티켓처리 도움말 추가}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>



            <!--//paging-->
            <!-- Main content -->
            <div class="box-header with-border">
                <h3 class="box-title"> </h3>
            </div>

            <div class="box-body">

                <div class="form-group">
                    <label>* 공지사항/보도자료 선택</label>
                    <p/>
                    <label> 라벨
                    </label>&nbsp;&nbsp;&nbsp;            <%--<form:hidden path="boardIdx"/>--%>

                    <label>
                        <%--<form:radiobutton path="boardType" value="PRESS"/>보도자료--%>
                    </label>
                </div>
                <div class="form-group">
                    <label>* 제목</label>
                    <input type="text" id="title" name="title" class="form-control" placeholder="제목 입력" value="${boardDetail.title}">
                </div>
                <div class="form-group">
                    <label>* 내용  - (<span style="color:blue">'보도자료' 선택 시</span>, 하나의 URL 만 입력하세요. 입력된 값(URL)으로 링크가 설정됩니다.) </label>
                    <!--
                    <input type="text" class="form-control" placeholder="내용 입력">
                    -->
                    <textarea name="content" id="content" rows="10" cols="100" style="width:100%; height:412px;">${ticketInfo.content}</textarea>

                    <script type="text/javascript">

                        // todo: Editor 코드가 위치함
                    </script>
                </div>
                <div class="form-group">
                    <label>* 공개여부</label>
                    <div class="radio">
                        <label>
                            <input type="radio" name="useYn" id="useYnTrue" value="Y" checked>
                            공개
                        </label>&nbsp;&nbsp;&nbsp;
                        <label>
                            <input type="radio" name="useYn" id="useYnFalse" value="N" >
                            비공개
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="atchFiles">* 파일첨부</label>
                    <input type="file" name="atchFiles" id="atchFiles" multiple="multiple" onchange="boardAtchFile(this);"/>
                    <span id="atchFilInput">
                    </span>
                    <span id="atchFilInputView">
                        <%--<c:forEach var="vo" items="${fileList}" varStatus="status">--%>
                            <%--<li id="viewAttFileList<c:out value="${status.count + 1}"/>">--%>
                                <%--<strong>--%>
                                    <%--<a href="javascript:void(0)" onclick="fileDown('${vo.fileNm}','${vo.fileOrgNm}','${vo.filePath}')">${vo.fileOrgNm}</a>--%>
                                <%--</strong>--%>
                                <%--&nbsp;&nbsp;--%>
                                <%--<a href="javascript:void(0)" onclick="fileDel('${vo.fileIdx}','${vo.fileNm}','${vo.filePath}','viewAttFileList<c:out value="${status.count + 1}"/>');"><span>[삭제하기]</span></a>--%>
                            <%--</li>--%>
                        <%--</c:forEach>--%>
                    </span>
                    <!--
                    <p class="help-block">Example block-level help text here.</p>
                     -->
                </div>

                <div align="right">
                    <input class="btn btn-primary" type="button" value="목록" onclick="refreshList()" />
                    <%--<c:choose>--%>
                        <%--<c:when test="${boardDetail.boardIdx > 0 }">--%>
                            <%--<input class="btn btn-primary" type="button" value="수정" onclick="procIns()" />--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>--%>
                            <%--<input class="btn btn-primary" type="button" value="등록" onclick="procIns()" />--%>
                        <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
                </div>
            </div>
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
                // 문의 내용 - Scrollbar
                $('.scroll_content').slimscroll({
                    height: '150px'
                })

                $('.scroll_content_admin_comment').slimscroll({
                    height: '150px'
                })




                $("#left_li_menu_03").addClass("active");
                $("#left_li_menu_03_01").addClass("active");

            })


            function fn_link_page(pageIndex){
                $("#boardReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#boardListForm").attr("action", "/manage/board/notice_list.do");
                $("#boardListForm").submit();
            }

            function boardAtchFile(thisObj){
                var atchFile = $("#atchFiles")[0];
                var mbSize = Math.round(MAX_ATCH_FILE_SIZE / 1024 / 1024);

                if($.browser.msie){
                    var ieVersion = ieVersionReturn();
                    if(ieVersion >= 10){
                        //alert('IE10 입니다.');
                        for ( var i = 0 ; i < atchFile.files.length ; i++ ) {
                            if (atchFile.files[i].size > MAX_ATCH_FILE_SIZE) {
                                alert("첨부 가능한 최대 용량이 초과 하였습니다.\n파일 최대 사이즈는 " + mbSize + "MB 입니다.");
                                $('#atchFiles').val("");
                                return;
                            }
                        }
                    } else{
                        //alert('IE9 이하 입니다.');
                        // Server Side에서 체크..
                    }
                }else {
                    for ( var i = 0 ; i < atchFile.files.length ; i++ ) {
                        if (atchFile.files[i].size > MAX_ATCH_FILE_SIZE) {
                            alert("첨부 가능한 최대 용량이 초과 하였습니다.\n파일 최대 사이즈는 " + mbSize + "MB 입니다.");
                            $('#atchFiles').val("");
                            return;
                        }
                    }
                }

                $.ajaxFileUpload({
                    url: "/fileUpload",
                    type: "post",
                    dataType: "json",
                    fileElementId: "atchFiles",
                    error:function(xhr,status,e){       //에러 발생시 처리함수
                        console.log(xhr);
                        console.log("status : "+status);
                        console.log(e);
                    },
                    success: function (data, status) {
                        var json = $.parseJSON(data.substring(data.indexOf("{"), data.lastIndexOf("}") + 1));
                        //var jsonResult = $.parseJSON(data.substring(data.indexOf("{"), data.lastIndexOf("}") + 1));
                        //var json = jQuery.parseJSON(jsonResult);

                        if ( json.atchFileList.length > 0 ){
                            var fileList = json.atchFileList;
                            var html = "";

                            var d = new Date();
                            var n = d.getTime();

                            for ( var i = 0 ; i < fileList.length ; i++ ){
                                if(fileList[i].sttsCode == '405'){
                                    alert(fileList[i].actionMessage);
                                    $('#atchFiles').val("");
                                    return;
                                }

                                n = d.getTime();

                                html += '<li id="addAttFileList'+n+'"><strong>';
                                html += '<a href=\'javascript:void(0)\' onclick=\'fileDown("'+fileList[i].streFileNm+'","'+fileList[i].orginlFileNm+'","'+fileList[i].fileSubPath+'")\'>'+fileList[i].orginlFileNm+'</a>'
                                html += '</strong>'
                                html += '&nbsp;&nbsp;';
                                html += '<a href="javascript:void(0)" onclick="fileDel2(\'addAttFileList'+n+'\')"><span>[삭제하기]</span></a>';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileNm" value="' + fileList[i].streFileNm + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileOrgNm" value="'  + fileList[i].orginlFileNm + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].filePath" value="' + fileList[i].fileSubPath + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileSize" value="' + fileList[i].fileSize + '" />';
                                html += "</li>";

                                /*
                                html += '<input type="hidden" name="fileNm" value="' + fileList[i].streFileNm + '" />';
                                html += '<input type="hidden" name="fileOrgNm" value="'  + fileList[i].orginlFileNm + '" />';
                                html += '<input type="hidden" name="filePath" value="' + fileList[i].fileSubPath + '" />';
                                */

                                $("#atchFilInput").html(html);
                                $("#atchFiles").val("");
                            }
                        }
                    }
                });
            };


            function fillFile() {
                var fileName = $("#atchFiles").val();
                $("#atchFilesView").val(fileName);
            };

            function fileDown(fileName, fileOrgName, filePath){
                var params = "?fileName="+fileName +"&fileOrgName="+fileOrgName + "&subPath="+ filePath;

                location.href = "/common/file/fileDownProc.do" + params;
            };

            $(document).ready( function() {
//                if( getViewType() == 'PRESS') {
//                    switchPressView();
//                }

                // PRESS/NOTICE 모드 이벤트 핸들러 추가
                $('input[type=radio][name=boardType]').change(
                    function() {
                        boardType = $('input[name=boardType]:checked', '#boardReqForm').val()
                        if (boardType == "PRESS") {
                            switchPressView();
                        } else if (boardType == "NOTICE" ) {
                            switchNoticeView();
                        } else {
                            // ignore
                        }
                    });
            } );

            function getViewType() {
                return $('input[name=boardType]:checked', '#boardReqForm').val();
            }

            function switchPressView() {
                $( '#content' ).show();
                $( 'iframe' ).hide();
            }

            function switchNoticeView() {
                $( '#content' ).hide();
                $( 'iframe' ).show();
            }



            function procIns(){
                var mode        = trim($('#mode').val());
                var title       = trim($('#title').val());
                var useYn      = $(':radio[name="useYn"]:checked').val();

                if(title == "" || typeof title == "undefined"){
                    alert('제목을 입력해주세요.');
                    return;
                }
                // PRESS(보도자료)의 경우, textarea에서 값을 바로 가져옵니다
                if (  getViewType() == 'PRESS') {
                    url_text=$('#content').val().trim();
                    alert("'보도자료'의 링크로 아래 값이 설정됩니다.\n" + url_text);
                    $('#content').val(url_text);
                }
                else if( getViewType() == 'NOTICE' )
                {
                    oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
                    // 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("ir1").value를 이용해서 처리하면 됩니다.\
                }
                else
                {
                    alert("잘못된 문서 속성입니다. 공지사항/보도자료 값을 다시 확인하세요. 현재값: " + getViewType() );
                    return;
                }

                /*
                var fname = document.myForm.uploadFile.value;

                if(!checkUploadFile(fname)){
                    alert("파일명에 특수문자가 있습니다. 파일명은 영문,숫자,한글만 가능합니다.");
                    document.boardReqForm.uploadFile.value = "";
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
                        url : '/manage/board/proc.do',
                        type : 'post',
                        data: $("#boardReqForm").serialize(),
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


            function fileDel(fileIdx, fileName, filePath, objId){
                var jsonData = {};

                jsonData.fileIdx = fileIdx;
                jsonData.fileName = fileName;
                jsonData.filePath = filePath;

                if (confirm("첨부파일을 삭제 하시겠습니까?")) {
                    $.ajax({
                        url : '/manage/board/procFileDel.do?${_csrf.parameterName}=${_csrf.token}',
                        type : 'post',
                        data: jsonData,
                        success: function (rs) {
                            if (rs.result) {
                                alert("삭제 되었습니다.");
                                //$('#attFile').('');
                                $("#"+objId).remove();
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

            function fileDel2(objId) {
                $("#"+objId).remove();
                $('#atchFiles').val("");

                /*
                $("#atchFilInput").html('');
                $('#fileNm').val("");
                $('#fileOrgNm').val("");
                $('#filePath').val("");
                */
            };
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>