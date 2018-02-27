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
            <section class="content-header">
                <h1>
                    탑 배너 이벤트
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 덕평소식</a></li>
                    <li class="active">탑 배너 이벤트</li>
                    <li class="active">작성</li>
                </ol>
            </section>

            <form:form name="topBannerEventReqForm" id="topBannerEventReqForm" method="post" commandName="topBannerEventInfo" enctype="multipart/form-data">
            <!--//paging-->
            <form:hidden path="topBannerEventIdx"/>
            <!-- Main content -->
            <div class="box-header with-border">
                <h3 class="box-title"> </h3>
            </div>

            <div class="box-body">
                <div class="form-group">
                    <label>* 배너 이벤트 명</label>
                    <input type="text" id="title" name="title" class="form-control" placeholder="배너 이벤트 입력" value="${topBannerEventDetail.title}">
                </div>

                <div class="form-group">
                    <label>* 비고</label>
                    <input type="text" id="bigo" name="bigo" class="form-control" placeholder="비고 입력" value="${topBannerEventDetail.bigo}">
                </div>

                <div class="form-group">
                    <label>* 연결 된 이벤트 <a href="#" onclick="popupEveList()">[찾기]</a> </label><br>
                    <input type="text" id="linkTitle" name="linkTitle" onclick="popupEveList()" class="form-control" placeholder="연결 된 이벤트 선택" value="${topBannerEventDetail.title}" readonly>

                    <input type="hidden" id="linkType" name="linkType" value="${topBannerEventDetail.linkType}">
                    <input type="hidden" id="linkUrl" name="linkUrl" value="${topBannerEventDetail.linkUrl}">
                    <input type="hidden" id="linkBoardIdx" name="linkBoardIdx" value="${topBannerEventDetail.linkBoardIdx}">
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
                    <label for="atchFiles">* Web 배너 파일첨부 (이미지 크기 1280 x 80)</label>
                    <input type="file" name="atchFiles" id="atchFiles" onchange="webAtchFile(this);"/>
                    <span id="atchFilInput">
                        <c:forEach var="vo" items="${fileList}" varStatus="status">
                            <c:if test="${vo.fileType eq 'WEB'}">
                            <li id="viewAttFileList<c:out value="${status.count + 1}"/>">
                                <strong>
                                   웹 배너 이미지 : <a href="javascript:void(0)" onclick="fileDown('${vo.fileNm}','${vo.fileOrgNm}','${vo.filePath}')">${vo.fileOrgNm}</a>
                                </strong>
                                &nbsp;&nbsp;
                                <a href="javascript:void(0)" onclick="fileDel('${vo.fileIdx}','${vo.fileNm}','${vo.filePath}','viewAttFileList<c:out value="${status.count + 1}"/>');"><span>[삭제하기]</span></a>
                            </li>
                            </c:if>
                        </c:forEach>
                    </span>
                </div>
                <hr />
                <div class="form-group">
                    <label for="atchFiles2">* Mobile 배너 파일첨부 (이미지 크기 640x142)</label>
                    <input type="file" name="atchFiles2" id="atchFiles2" onchange="mobileAtchFile(this);"/>
                    <span id="atchFilInput2">
                        <c:forEach var="vo" items="${fileList}" varStatus="status">
                            <c:if test="${vo.fileType eq 'MOBILE'}">
                            <li id="viewAttFileList<c:out value="${status.count + 1}"/>">
                                <strong>
                                  모바일 배너 이미지 : <a href="javascript:void(0)" onclick="fileDown('${vo.fileNm}','${vo.fileOrgNm}','${vo.filePath}')">${vo.fileOrgNm}</a>
                                </strong>
                                &nbsp;&nbsp;
                                <a href="javascript:void(0)" onclick="fileDel('${vo.fileIdx}','${vo.fileNm}','${vo.filePath}','viewAttFileList<c:out value="${status.count + 1}"/>');"><span>[삭제하기]</span></a>
                            </li>
                            </c:if>
                        </c:forEach>
                    </span>
                </div>

                <div align="right">
                    <input class="btn btn-primary" type="button" value="목록" onclick="refreshList()" />
                    <c:choose>
                        <c:when test="${topBannerEventDetail.topBannerEventIdx > 0 }">
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
                <c:when test="${topBannerEventDetail.topBannerEventIdx > 0 }">
                    <input type="hidden" name="mode" id="mode" value="UPD">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="mode" id="mode" value="INS">
                    <input type="hidden" name="delYn" id="delYn" value="N">
                </c:otherwise>
            </c:choose>
            </form:form>

            <form:form name="topBannerEventListForm" id="topBannerEventListForm" method="post" commandName="topBannerEventInfo">
                <form:hidden path="pageIndex"/>
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

                <c:if test="${topBannerEventDetail != null }">
                $('input:radio[name=useYn]:input[value=${topBannerEventDetail.useYn}]').attr("checked", "checked");
                </c:if>

                $("#left_li_menu_03").addClass("active");
                $("#left_li_menu_03_04").addClass("active");

            })

            function popupEveList(){
                var popUrl = "/manage/topbannerevent/pop_event_list.do";	//팝업창에 출력될 페이지 URL
                var popOption = "width=640, height=480, resizable=yes, scrollbars=yes, status=no;";    //팝업창 옵션(optoin)
                var pop_title = "pop_event_list" ;

                window.open(popUrl, pop_title, popOption);

            }

            function topBannerEventCallBack(boardType, boardIdx, title){
                $("#linkTitle").val(title);
                $("#linkBoardIdx").val(boardIdx);
                $("#linkUrl").val("/notice/event_list.do");
                //$("#linkType").val("EVENT_PAGE");
                $("#linkType").val(boardType);

            }

            function fn_link_page(pageIndex){
                $("#topBannerEventReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#topBannerEventListForm").attr("action", "/manage/topbannerevent/list.do");
                $("#topBannerEventListForm").submit();
            }

            function webAtchFile(thisObj){
                var atchFile = $("#atchFiles")[0];
                var mbSize = Math.round(MAX_ATCH_FILE_SIZE / 1024 / 1024);

                if($.browser.msie){
                    var ieVersion = ieVersionReturn();
                    if(ieVersion >= 10){
                        //alert('IE10 입니다.');
                        for ( var i = 0 ; i < atchFile.files.length ; i++ ) {
                            if (atchFile.files[i].size > MAX_ATCH_FILE_SIZE) {
                                alert("첨부 가능한 최대 용량이 초과 하였습니다.\n파일 최대 사이즈는 " + mbSize + "MB 입니다.");
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
                                    return;
                                }

                                n = d.getTime();

                                html += '<li id="addAttFileList'+n+'"><strong>';
                                html += '<a href=\'javascript:void(0)\' onclick=\'fileDown("'+fileList[i].streFileNm+'","'+fileList[i].orginlFileNm+'","'+fileList[i].fileSubPath+'")\'>'+fileList[i].orginlFileNm+'</a>'
                                html += '</strong>'
                                html += '&nbsp;&nbsp;';
                                html += '<a href="javascript:void(0)" onclick="fileDel2(\'addAttFileList'+n+'\')"><span>[삭제하기]</span></a>';
                                /*
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileNm" value="' + fileList[i].streFileNm + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileOrgNm" value="'  + fileList[i].orginlFileNm + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].filePath" value="' + fileList[i].fileSubPath + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileSize" value="' + fileList[i].fileSize + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileType" value="WEB" />';
                                */
                                html += '<input type="hidden" name="fileNm" value="' + fileList[i].streFileNm + '" />';
                                html += '<input type="hidden" name="fileOrgNm" value="'  + fileList[i].orginlFileNm + '" />';
                                html += '<input type="hidden" name="filePath" value="' + fileList[i].fileSubPath + '" />';
                                html += '<input type="hidden" name="fileSize" value="' + fileList[i].fileSize + '" />';
                                html += '<input type="hidden" name="fileType" value="WEB" />';
                                html += "</li>";



                                $("#atchFilInput").html(html);
                            }
                        }
                    }
                });
            };

            function mobileAtchFile(thisObj){
                var atchFile = $("#atchFiles2")[0];
                var mbSize = Math.round(MAX_ATCH_FILE_SIZE / 1024 / 1024);

                if($.browser.msie){
                    var ieVersion = ieVersionReturn();
                    if(ieVersion >= 10){
                        //alert('IE10 입니다.');
                        for ( var i = 0 ; i < atchFile.files.length ; i++ ) {
                            if (atchFile.files[i].size > MAX_ATCH_FILE_SIZE) {
                                alert("첨부 가능한 최대 용량이 초과 하였습니다.\n파일 최대 사이즈는 " + mbSize + "MB 입니다.");
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
                            return;
                        }
                    }
                }

                $.ajaxFileUpload({
                    url: "/fileUpload",
                    type: "post",
                    dataType: "json",
                    fileElementId: "atchFiles2",
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
                                    return;
                                }

                                n = d.getTime();

                                html += '<li id="addAttFileList2'+n+'"><strong>';
                                html += '<a href=\'javascript:void(0)\' onclick=\'fileDown("'+fileList[i].streFileNm+'","'+fileList[i].orginlFileNm+'","'+fileList[i].fileSubPath+'")\'>'+fileList[i].orginlFileNm+'</a>'
                                html += '</strong>'
                                html += '&nbsp;&nbsp;';
                                html += '<a href="javascript:void(0)" onclick="fileDel2(\'addAttFileList2'+n+'\')"><span>[삭제하기]</span></a>';
                                html += '<input type="hidden" name="fileNm2" value="' + fileList[i].streFileNm + '" />';
                                html += '<input type="hidden" name="fileOrgNm2" value="'  + fileList[i].orginlFileNm + '" />';
                                html += '<input type="hidden" name="filePath2" value="' + fileList[i].fileSubPath + '" />';
                                html += '<input type="hidden" name="fileSize2" value="' + fileList[i].fileSize + '" />';
                                html += '<input type="hidden" name="fileType2" value="MOBILE" />';
                                /*
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileNm" value="' + fileList[i].streFileNm + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileOrgNm" value="'  + fileList[i].orginlFileNm + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].filePath" value="' + fileList[i].fileSubPath + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileSize" value="' + fileList[i].fileSize + '" />';
                                html += '<input type="hidden" name="atchFileList['+ i +'].fileType" value="MOBILE" />';
                                */
                                html += "</li>";

                                $("#atchFilInput2").html(html);
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


            function procIns(){
                var mode        = trim($('#mode').val());
                var title       = trim($('#title').val());
                var linkBoardIdx= trim($('#linkBoardIdx').val());


                var useYn      = $(':radio[name="useYn"]:checked').val();

                if(title == "" || typeof title == "undefined"){
                    alert('제목을 입력해주세요.');
                    return;
                }

                if(linkBoardIdx == "" || typeof linkBoardIdx == "undefined"){
                    alert('연결된 이벤트를 선택해주세요.');
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
                        url : '/manage/topbannerevent/proc.do',
                        type : 'post',
                        data: $("#topBannerEventReqForm").serialize(),
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
                        url : '/manage/topbannerevent/procFileDel.do?${_csrf.parameterName}=${_csrf.token}',
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
