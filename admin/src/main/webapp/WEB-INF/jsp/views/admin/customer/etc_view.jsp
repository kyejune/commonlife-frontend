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
                    입점/제휴 문의
                    <!--<small>Version 1.0</small>-->
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 고객센터</a></li>
                    <li class="active">입점/제휴/기타</li>
                    <li class="active">상세</li>
                </ol>
            </section>
            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box">
                            <div class="box-header" align="right">
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table id="mngList" class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>구분</th>
                                            <th colspan="3">
                                                <c:choose>
                                                    <c:when test="${boardDetail.boardType eq 'LOCATION'}">
                                                        입점
                                                    </c:when>
                                                    <c:when test="${boardDetail.boardType eq 'PROMOTION'}">
                                                        제휴
                                                    </c:when>
                                                    <c:when test="${boardDetail.boardType eq 'ETC'}">
                                                        기타
                                                    </c:when>
                                                    <c:otherwise>
                                                        &nbsp;
                                                    </c:otherwise>
                                                </c:choose>
                                            </th>
                                        </tr>
                                        <tr>
                                            <th>제목</th>
                                            <th colspan="3"><c:out value="${boardDetail.title}"/></th>
                                        </tr>
                                        <tr>
                                            <th>작성자</th>
                                            <th colspan="3"><c:out value="${boardDetail.writeNm}"/></th>
                                        </tr>
                                        <tr>
                                            <th>연락처</th>
                                            <th colspan="3"><c:out value="${boardDetail.writeHp}"/></th>
                                        </tr>
                                        <tr>
                                            <th>이메일</th>
                                            <th colspan="3"><c:out value="${boardDetail.writeEmail}"/></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <th>주문내용</th>
                                            <th colspan="3">
                                                <!-- 본문삽입 START -->
                                                <c:out value="${boardDetail.content}" escapeXml="false"/>
                                                <!-- 본문삽입//E -->
                                            </th>
                                        </tr>
                                        <tr>
                                            <th>첨부파일</th>
                                            <th colspan="3">
                                                <c:forEach var="vo" items="${fileList}" varStatus="status">
                                                    <a href="javascript:void(0)" onclick="fileDown('${vo.fileNm}','${vo.fileOrgNm}','${vo.filePath}')">${vo.fileOrgNm}</a>
                                                </c:forEach>
                                            </th>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer" align="right">
                                <button class="btn btn-primary" onclick="refreshList()">목록</button>
                            </div>
                        </div>
                        <!-- /.box -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </section>
            <!-- /.content -->
            <!-- ==================================================================================================== -->
            <!-- == 답변 리스트 시작 -->
            <!-- ==================================================================================================== -->
            <c:if test="${fn:length(replyList) > 0}">
                <section class="content-header">
                    <h3>
                        답변
                    </h3>
                </section>
                <section class="content">
                    <c:forEach var="vo" items="${replyList}" varStatus="status">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="box">

                                    <div class="box-header">
                                        <h3 class="box-title">
                                            <c:choose>
                                                <c:when test="${vo.replyType eq 'TEL'}">
                                                    통화기록
                                                </c:when>
                                                <c:when test="${vo.replyType eq 'EMAIL'}">
                                                    메일전송
                                                </c:when>
                                                <c:otherwise>
                                                    기타
                                                </c:otherwise>
                                            </c:choose>
                                        </h3>
                                    </div>
                                    <!-- /.box-header -->
                                    <div class="box-body">
                                        <table class="table table-bordered table-striped">
                                            <thead>
                                            <tr>
                                                <th>제목</th>
                                                <th colspan="3"><c:out value="${vo.title}"/></th>
                                            </tr>
                                            <tr>
                                                <th>답변일자</th>
                                                <th>
                                                    <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd HH:mm:ss" var="sysDt"/>
                                                    <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd"/>
                                                </th>
                                                <th>답변시간</th>
                                                <th>
                                                    <fmt:formatDate value="${sysDt}" pattern="HH:mm"/>
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <th>내용</th>
                                                <td colspan="3">
                                                    <!-- 본문삽입 START -->
                                                    <c:out value="${stringUtil.convertHtml(vo.content)}" escapeXml="false"/>
                                                    <!-- 본문삽입//E -->
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>첨부파일</th>
                                                <th colspan="3">
                                                    <c:if test="${vo.fileNm != null}">
                                                        <a href="javascript:void(0)" onclick="fileDown('${vo.fileNm}','${vo.fileOrgNm}','${vo.filePath}')">${vo.fileOrgNm}</a>
                                                    </c:if>
                                                </th>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>

                                </div>
                                <!-- /.box -->
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->
                    </c:forEach>

                </section>
                <!-- /.content -->
                <hr />
            </c:if>
            <!-- ==================================================================================================== -->
            <!-- == 답변 리스트 종료 -->
            <!-- ==================================================================================================== -->


            <!-- ==================================================================================================== -->
            <!-- == 답변 등록 시작 -->
            <!-- ==================================================================================================== -->
            <form:form name="replyBoardReqForm" id="replyBoardReqForm" method="post" commandName="boardInfo" enctype="multipart/form-data">
                <!--//paging-->
                <form:hidden path="boardIdx" value="${boardDetail.boardIdx}"/>
                <form:hidden path="boardType" value="VOC"/>
                <form:hidden path="mode" value="INS"/>
                <form:hidden path="delYn" value="N"/>

                <div class="box-body">
                    <div class="form-group">
                        <label>* 제목 &nbsp;&nbsp;&nbsp;
                            (답변유형 :
                            <input type="radio" name="replyType" value="EMAIL">&nbsp;메일전송 &nbsp;&nbsp;&nbsp;
                            <input type="radio" name="replyType" value="TEL">&nbsp;통화기록
                            )
                        </label>
                        <input type="text" id="title" name="title" class="form-control" placeholder="제목 입력" value="">
                    </div>
                    <div class="form-group">
                        <textarea name="content" id="content" rows="10" cols="100" style="width:100%; height:412px; display:none;"></textarea>

                        <script type="text/javascript">
                            var oEditors = [];

                            // 추가 글꼴 목록
                            //var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

                            nhn.husky.EZCreator.createInIFrame({
                                oAppRef: oEditors,
                                elPlaceHolder: "content",
                                sSkinURI: "/resources/smarteditor/SmartEditor2Skin_noimage.html",
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
                        <label for="atchFiles">* 파일첨부</label>
                        <input type="file" name="atchFiles" id="atchFiles" onchange="boardAtchFile(this);"/>
                        <span id="atchFilInput"></span>
                    </div>

                    <div align="right">
                        <input class="btn btn-primary" type="button" value="목록" onclick="refreshList()" />
                        <input class="btn btn-primary" type="button" value="답변등록" onclick="procIns()" />
                    </div>
                </div>
                <!-- /.content -->
                <!-- ==================================================================================================== -->
                <!-- == 답변 등록 종료 -->
                <!-- ==================================================================================================== -->
            </form:form>
            <form:form name="boardListForm" id="boardListForm" method="post" commandName="boardInfo">
                <form:hidden path="pageIndex"/>
            </form:form>
        </div>
        <!-- /.content-wrapper -->
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <!-- DataTables -->
        <script src='/js/common_check.js'></script>
        <script src='/js/ajaxfileupload.js'></script>
        <script type="text/javascript">
            $(window).load(function(e){
                $("#left_li_menu_04").addClass("active");
                $("#left_li_menu_04_03").addClass("active");
            });

            function fn_link_page(pageIndex){
                $("#boardListForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#boardListForm").attr("action", "/manage/customer/etc_list.do");
                $("#boardListForm").submit();
            }

            function boardDetail(boardIdx){
                $("#boardReqForm > #boardIdx").val(boardIdx);
                $("#boardReqForm").attr("action", "/manage/customer/etc_view.do");
                $("#boardReqForm").submit();
            }

            function fileDown(fileName, fileOrgName, filePath){
                var params = "?fileName="+fileName +"&fileOrgName="+fileOrgName + "&subPath="+ filePath;

                location.href = "/common/file/fileDownProc.do" + params;
            };

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

                                $("#atchFilInput").html(html);
                                $("#atchFiles").val("");
                            }
                        }
                    }
                });
            };

            function procIns(){
                var mode        = trim($('#mode').val());
                var title       = trim($('#title').val());
                var replyType   = $(':radio[name="replyType"]:checked').val();

                if(title == "" || typeof title == "undefined"){
                    alert('제목을 입력해주세요.');
                    return;
                }

                if(replyType == "" || typeof replyType == "undefined"){
                    alert('답변유형을 선택해 주세요.');
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

                var modeMsg = "답변등록";

                if (confirm(modeMsg+" 하시겠습니까?")) {
                    $.ajax({
                        url : '/manage/board/replyProc.do',
                        type : 'post',
                        data: $("#replyBoardReqForm").serialize(),
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
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>