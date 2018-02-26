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
                    특별행사
                    <!--<small>Version 1.0</small>-->
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 특별행사</a></li>
                    <li class="active">작성</li>
                </ol>
            </section>

            <form:form name="boardReqForm" id="boardReqForm" method="post" commandName="boardInfo" enctype="multipart/form-data">
            <!--//paging-->
            <form:hidden path="boardIdx"/>
            <form:hidden path="boardType" value="SPECIAL_SHOP"/>
            <!-- Main content -->
            <div class="box-header with-border">
                <h3 class="box-title"> </h3>
            </div>

            <div class="box-body">
                <div class="form-group">
                    <label>* 행사명</label>
                    <input type="text" id="title" name="title" class="form-control" placeholder="행사명" value="${boardDetail.title}">
                </div>
                <div class="form-group">
                    <label>* 설명</label>
                    <input type="text" id="content" name="content" class="form-control" placeholder="설명입력" value="${boardDetail.content}">
                </div>
                <div class="form-group">
                    <!-- +++++++++++++++(이벤트기간)+++++++++++++++ -->

                    <script type="text/javascript">
                        $(function () {
                            $("#openDt").datepicker({

                                dateFormat : "yy-mm-dd",
                                monthNamesShort : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                                dayNamesMin : ['일', '월', '화', '수', '목', '금', '토'],
                                changeMonth : true,
                                changeYear : true,
                                yearRange : "c-70:c+70",
                                showMonthAfterYear : true,
                                autoclose : true
                            });

                            $("#closeDt").datepicker({

                                dateFormat : "yy-mm-dd",
                                monthNamesShort : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                                dayNamesMin : ['일', '월', '화', '수', '목', '금', '토'],
                                changeMonth : true,
                                changeYear : true,
                                yearRange : "c-70:c+70",
                                showMonthAfterYear : true,
                                autoclose : true
                            });
                        });
                    </script>
                    <label>* 기간</label>
                    <br>
                    <div class="input-daterange input-group" id="datepicker">
                        <input type="text" id="openDt" name="openDt" value="${boardDetail.openDt}" class="input-sm form-control" name="start" />
                        <span class="input-group-addon">to</span>
                        <input type="text" id="closeDt" name="closeDt" value="${boardDetail.closeDt}" class="input-sm form-control" name="end" />
                    </div>

                </div>
                <div class="form-group">
                    <label>* 메인게시</label>
                    <div class="radio">
                        <label>
                            <input type="radio" name="useYn" id="useYnTrue" value="Y" checked>
                            게시
                        </label>&nbsp;&nbsp;&nbsp;
                        <label>
                            <input type="radio" name="useYn" id="useYnFalse" value="N" >
                            비게시
                        </label>
                    </div>
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

            <form:form name="boardListForm" id="boardListForm" method="post" commandName="boardInfo">
                <form:hidden path="pageIndex"/>
            </form:form>
        </div>
        <!-- /.content-wrapper -->
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <!-- DataTables -->
        <script src="/resources/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="/resources/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
        <script src="/resources/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
        <script src='/js/common_check.js'></script>
        <script type="text/javascript">
            $(function () {
                <c:if test="${boardDetail != null }">
                $('input:radio[name=useYn]:input[value=${boardDetail.useYn}]').attr("checked", "checked");
                </c:if>

                $("#left_li_menu_02").addClass("active");
            })


            function fn_link_page(pageIndex){
                $("#boardReqForm > #pageIndex").val(pageIndex);
//                $("#pageIndex").val(pageIndex);

                refreshList();
            }

            function refreshList(){
                $("#boardListForm").attr("action", "/manage/shop/special_shop_list.do");
                $("#boardListForm").submit();
            }


            function procIns(){
                var mode        = trim($('#mode').val());
                var title       = trim($('#title').val());
                var useYn      = $(':radio[name="useYn"]:checked').val();

                if(title == "" || typeof title == "undefined"){
                    alert('제목을 입력해주세요.');
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


        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>