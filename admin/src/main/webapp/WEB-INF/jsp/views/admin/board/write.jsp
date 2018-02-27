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
                    공지사항
                    <small>Version 1.0</small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 덕평소식</a></li>
                    <li class="active">공지사항</li>
                    <li class="active">작성</li>
                </ol>
            </section>

            <form:form name="boardReqForm" id="boardReqForm" method="post" commandName="boardInfo" enctype="multipart/form-data">
            <!--//paging-->
            <form:hidden path="pageIndex"/>
            <form:hidden path="boardIdx"/>
            <form:hidden path="boardType" value="${boardType}"/>
            <!-- Main content -->
            <div class="box-header with-border">
                <h3 class="box-title"> </h3>
            </div>

            <div class="box-body">
                <div class="form-group">
                    <label>* 제목</label>
                    <input type="text" id="title" name="title" class="form-control" placeholder="제목 입력" value="${boardDetail.title}">
                </div>
                <div class="form-group">
                    <label>* 내용</label>
                    <!--
                    <input type="text" class="form-control" placeholder="내용 입력">
                    -->
                    <textarea name="content" id="content" rows="10" cols="100" style="width:100%; height:412px; display:none;">
                            ${boardDetail.content}
                    </textarea>

                    <script type="text/javascript">
                        var oEditors = [];

                        // 추가 글꼴 목록
                        //var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

                        nhn.husky.EZCreator.createInIFrame({
                            oAppRef: oEditors,
                            elPlaceHolder: "content",
                            sSkinURI: "/resources/smarteditor/SmartEditor2Skin.html",
                            htParams : {
                                bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                                bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                                bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                                //bSkipXssFilter : true,		// client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
                                //aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
                                fOnBeforeUnload : function(){
                                    //alert("완료!");
                                }
                            }, //boolean
                            fOnAppLoad : function(){
                                //예제 코드
                                //oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
                            },
                            fCreator: "createSEditor2"
                        });

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
                    <span id="atchFilInput"></span>
                    <!--
                    <p class="help-block">Example block-level help text here.</p>
                     -->
                </div>

                <div align="right">
                    <input class="btn btn-primary" type="button" value="목록" onclick="refreshList()" />
                    <c:choose>
                        <c:when test="${boardDetail.boardIdx > 0 }">
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
                <c:when test="${boardDetail.boardIdx > 0 }">
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

                <c:if test="${boardDetail != null }">
                $('input:radio[name=useYn]:input[value=${boardDetail.useYn}]').attr("checked", "checked");
                </c:if>

            })


            function fn_link_page(pageIndex){
                $("#boardReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#boardReqForm").attr("action", "/manage/board/list.do");
                $("#boardReqForm").submit();
            }

            function boardAtchFile(thisObj){
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

                            for ( var i = 0 ; i < fileList.length ; i++ ){
                                html += '<li><strong>';
                                html += '<a href=\'javascript:void(0)\' onclick=\'fileDown("'+fileList[i].streFileNm+'","'+fileList[i].orginlFileNm+'","'+fileList[i].fileSubPath+'")\'>'+fileList[i].orginlFileNm+'</a>'
                                html += '</strong>'
                                html += '&nbsp;&nbsp;';
                                html += '<a href="javascript:void(0)" onclick="fileDel()"><span>[삭제하기]</span></a></ins>';
                                html += "</li>";


                                html += '<input type="hidden" name="atchFileList['+ i +'].fileNm" value="' + fileList[i].streFileNm + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileOrgNm" value="'  + fileList[i].orginlFileNm + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].filePath" value="' + fileList[i].fileSubPath + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileSize" value="' + fileList[i].fileSize + '" />';


                                /*
                                html += '<input type="hidden" name="fileNm" value="' + fileList[i].streFileNm + '" />';
                                html += '<input type="hidden" name="fileOrgNm" value="'  + fileList[i].orginlFileNm + '" />';
                                html += '<input type="hidden" name="filePath" value="' + fileList[i].fileSubPath + '" />';
                                */


                                $("#atchFilInput").html(html);
                            }
                        }
                    }
                });
            };


            function fileDel() {
                $("#atchFilInput").html('');
                $('#fileNm').val("");
                $('#fileOrgNm').val("");
                $('#filePath').val("");
            };

            function fillFile() {
                var fileName = $("#atchFiles").val();
                $("#atchFilesView").val(fileName);
            };

            function fileDown(fileName, fileOrgName, filePath){
                var params = "?fileName="+fileName +"&fileOrgName="+fileOrgName + "&subPath="+ filePath;

                location.href = "/common/file/fileDownProc.do" + params;
            };


            function procIns(){
                var mode        = trim($('#mode').val());
                var title       = trim($('#title').val());
                var useYn      = $(':radio[name="useYn"]:checked').val();

                if(title == "" || typeof title == "undefined"){
                    alert('제목을 입력해주세요.');
                    return;
                }

                oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.

                // 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("ir1").value를 이용해서 처리하면 됩니다.

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
                                alert("등록 되었습니다.");
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


            function fileDel(boardFileIdx, fileName){
                var jsonData = {};

                jsonData.boardFileIdx = boardFileIdx;
                jsonData.fileName = fileName;

                if (confirm("첨부파일을 삭제 하시겠습니까?")) {
                    $.ajax({
                        url : '/manage/notice/procFileDel?${_csrf.parameterName}=${_csrf.token}',
                        type : 'post',
                        data: jsonData,
                        success: function (rs) {
                            if (rs.result) {
                                alert("삭제 되었습니다.");
                                $('#attFile').html('');
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