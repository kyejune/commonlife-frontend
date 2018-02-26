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
                    푸드코트
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 식사메뉴</a></li>
                    <li class="active">푸드코트</li>
                    <li class="active">작성</li>
                </ol>
            </section>

            <form:form name="foodCourtReqForm" id="foodCourtReqForm" method="post" commandName="foodCourtInfo">
            <!--//paging-->
            <form:hidden path="foodCourtMasterIdx"/>
            <!-- Main content -->
            <div class="box-header with-border">
                <h3 class="box-title"> </h3>
            </div>

            <div class="box-body">
                <div class="form-group">
                    <label>* 제목</label>
                    <input type="text" id="title" name="title" class="form-control" placeholder="제목 입력" value="${foodCourtMasterDetail.title}">
                </div>
                <div class="form-group">
                    <label>* 비고</label>
                    <input type="text" id="bigo" name="bigo" class="form-control" placeholder="비고" value="${foodCourtMasterDetail.bigo}">
                </div>
                <div class="form-group">
                    <label>* HTML 입력</label>
                    <!--
                    <input type="text" class="form-control" placeholder="내용 입력">
                    -->
                    <textarea name="contentHtml" id="contentHtml" rows="10" cols="100" style="width:100%; height:412px; display:none;">
                            ${foodCourtMasterDetail.contentHtml}
                    </textarea>

                    <script type="text/javascript">
                        var oEditors = [];

                        // 추가 글꼴 목록
                        //var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

                        nhn.husky.EZCreator.createInIFrame({
                            oAppRef: oEditors,
                            elPlaceHolder: "contentHtml",
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
                            <input type="radio" name="openYn" id="openYnTrue" value="Y" checked>
                            공개
                        </label>&nbsp;&nbsp;&nbsp;
                        <label>
                            <input type="radio" name="openYn" id="openYnFalse" value="N" >
                            비공개
                        </label>
                    </div>
                </div>

                <div align="right">
                    <input class="btn btn-primary" type="button" value="목록" onclick="refreshList()" />
                    <input class="btn btn-primary" type="button" value="미리보기" onclick="preview()" />
                    <c:choose>
                        <c:when test="${foodCourtMasterDetail.foodCourtMasterIdx > 0 }">
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
                <c:when test="${foodCourtMasterDetail.foodCourtMasterIdx > 0 }">
                    <input type="hidden" name="mode" id="mode" value="UPD">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="mode" id="mode" value="INS">
                    <input type="hidden" name="delYn" id="delYn" value="N">
                </c:otherwise>
            </c:choose>
            </form:form>

            <form:form name="foodCourtListForm" id="foodCourtListForm" method="post" commandName="foodCourtInfo">
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
        <script type="text/javascript">
            $(function () {

                <c:if test="${boardDetail != null }">
                $('input:radio[name=openYn]:input[value=${boardDetail.useYn}]').attr("checked", "checked");
                </c:if>

                $("#left_li_menu_01").addClass("active");
                $("#left_li_menu_01_01").addClass("active");
            })


            function fn_link_page(pageIndex){
                $("#foodCourtReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#foodCourtListForm").attr("action", "/manage/foodcourt/list.do");
                $("#foodCourtListForm").submit();
            }

            function preview(){
                var popUrl = "/manage/foodcourt/preview/food1.do";	//팝업창에 출력될 페이지 URL

                var popOption = "width=100%, 100%, resizable=yes, scrollbars=yes, status=no;";    //팝업창 옵션(optoin)
//                window.open(popUrl,"",popOption);

                var pop_title = "PreviewFood1" ;

                oEditors.getById["contentHtml"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.

                window.open("", pop_title, popOption) ;

                var frmData = document.foodCourtReqForm ;
                frmData.target = pop_title;
                frmData.action = popUrl;

                frmData.submit() ;
            }

            function procIns(){
                var mode        = trim($('#mode').val());
                var title       = trim($('#title').val());
                var openYn      = $(':radio[name="openYn"]:checked').val();

                if(title == "" || typeof title == "undefined"){
                    alert('제목을 입력해주세요.');
                    return;
                }

                oEditors.getById["contentHtml"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.

                // 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("ir1").value를 이용해서 처리하면 됩니다.

                var modeMsg = "";

                if(mode == "INS"){
                    modeMsg = "등록";
                }else{
                    modeMsg = "수정";
                }

                if (confirm(modeMsg+" 하시겠습니까?")) {
                    $.ajax({
                        url : '/manage/foodcourt/proc.do',
                        type : 'post',
                        data: $("#foodCourtReqForm").serialize(),
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