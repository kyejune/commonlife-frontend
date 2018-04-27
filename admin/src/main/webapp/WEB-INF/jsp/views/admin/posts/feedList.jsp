<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="posts">
<tiles:putAttribute name="title">FEED 관리</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- DataTables -->
    <a><!-- --></a>
</tiles:putAttribute>
<tiles:putAttribute name="contents">
    <c:choose>
        <c:when test="${postType eq 'event'}">
            <c:set value="Event" var="postTypeTxt"></c:set>
        </c:when>
        <c:when test="${postType eq 'news'}">
            <c:set value="Notice" var="postTypeTxt"></c:set>
        </c:when>
        <c:otherwise>
            <c:set value="사용자 Feed" var="postTypeTxt"></c:set>
        </c:otherwise>
    </c:choose>



    <!-- Section Title -->
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2>FEED 관리</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    FEED 관리
                </li>
                <li class="active">
                    <a><c:out value="${postTypeTxt}" escapeXml="false">사용자 FEED</c:out>관리</a>
                </li>
            </ol>
        </div>
        <div class="col-lg-2">
        </div>
    </div>

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-md-8">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>FEED 목록</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>

                    <div class="ibox-content" style="">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>사용자</th>
                                    <th>내용</th>
                                    <th><a href="#" alt="Like 개수"><i class="fa fa-thumbs-o-up"></i></a></th>
                                    <th><a href="#" alt="이미지 포함 여부"><i class="fa fa-file-image-o"></i></a></th>
                                    <th>상태</th>
                                    <th>생성 일시</th>
                                    <th>변경 일시</th>
                                </tr>
                            </thead>
                            <c:choose>
                                <c:when test="${fn:length(postList) == 0}">
                                    <tbody>
                                    <tr>
                                        <td colspan="9"><p><dfn>조회된 결과가 없습니다.</dfn></p></td>
                                    </tr>
                                    </tbody>
                                </c:when>
                                <c:otherwise>
                                    <tbody>
                                    <c:forEach var="vo" items="${postList}" varStatus="status">
                                        <tr>
                                            <td>
                                                ${vo.postIdx}
                                            </td>
                                            <td>
                                                ${vo.user.userNm} / ${vo.user.usrId}
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:length(vo.content) < 1}" >
                                                        <a href="javascript:void(0)"
                                                           onclick="showPost(${vo.postIdx})">{내용 없음}</a>
                                                    </c:when>
                                                    <c:when test="${fn:length(vo.content) > 30}" >
                                                        <a href="javascript:void(0)"
                                                           onclick="showPost(${vo.postIdx})">
                                                        ${fn:substring(vo.content,0, 30)}...
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="javascript:void(0)"
                                                           onclick="showPost(${vo.postIdx})">
                                                        ${vo.content}
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${vo.likesCount < 1}" >
                                                        -
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${vo.likesCount}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:length(vo.postFiles) > 0}">
                                                        <i class="fa fa-file-image-o" alt="이미지 포함"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        -
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${vo.delYn eq 'Y'}">
                                                        <i class="fa fa-times "></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="fa fa-check-circle-o text-navy"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd HH:mm" var="sysDt"/>
                                                <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd HH:mm"/>
                                            </td>
                                            <td>
                                                <fmt:parseDate value="${vo.updDttm}" pattern="yyyy-MM-dd HH:mm" var="sysDt2"/>
                                                <fmt:formatDate value="${sysDt2}" pattern="yyyy.MM.dd HH:mm"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <!-- paginging -->
                                    <tr>
                                        <td colspan="8" >
                                            <div class="center-block">
                                                <ui:pagination paginationInfo="${paginateInfo}" jsFunction="fn_link_page"/>
                                            </div>
                                        </td>
                                    </tr>
                                    </tfoot>
                                </c:otherwise>
                            </c:choose>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>FEED 상세보기</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>

                    <div class="ibox-content" style="">
                        <div id="emptyPost" class="text-success font-bold text"><br>
                            <br><h3>'내용'을 클릭하여 Feed의 <br> 상세 정보를 볼 수 있습니다.</h3><br>
                        </div>
                        <div class="social-feed-box" id="showPost" style="display: none;">
                            <div class="social-avatar">
                                <a href="" class="pull-left">
                                    <img id="profile" alt="image" src="">
                                </a>
                                <div class="media-body">
                                    <a href="#" id="userNm"></a>
                                    <small class="text-muted" id="regDttm" ></small>
                                </div>
                            </div>

                            <div class="social-body">
                                <div id="content"></div>
                                <br>
                                <br>
                                <img id="postFile" src="#" class="img-responsive" style="display: none">
                                <br>
                                <br>
                            </div>
                            <div class="social-footer">
                                <div class="row">
                                    <label class="col-lg-6 control-label">
                                        <i class="fa fa-thumbs-up"></i>
                                    </label>
                                    <div class="col-lg-6">
                                        <div id="likesCount">0</div>
                                    </div>
                                </div>
                                <div class="row" id="rsvYn" style="display: none;">
                                    <label class="col-lg-6 control-label">
                                        <i class="fa fa-users"></i> 참여자
                                    </label>
                                    <div class="col-lg-6">
                                        <div id="rsvCountStatus">0</div>
                                    </div>
                                </div>
                                <div class="row" id="shareYn" style="display: none;">
                                    <label class="col-lg-6 control-label">
                                        <i class="fa fa-share"></i>
                                    </label>
                                    <div class="col-lg-6">
                                        공유 가능
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</tiles:putAttribute>
<tiles:putAttribute name="js">
    <script type="text/javascript">
        $(function () {
            $("#left_feed").addClass("active");
            $("#left_feed > .nav-second-level").addClass("in");

            <c:choose>
                <c:when test="${postType eq 'news'}">
                    $("#left_feed_notice").addClass("active");
                </c:when>
                <c:when test="${postType eq 'event'}">
                    $("#left_feed_event").addClass("active");
                </c:when>
                <c:otherwise>
                    $("#left_feed_feed").addClass("active");
                </c:otherwise>
            </c:choose>


            <c:if test="${error != null}" >
                alert("${error}");
                return;
            </c:if>
        })

        function fn_link_page( pageIndex ) {
            window.location.replace("feedList.do?pageNum=" + pageIndex);
        }

        function refreshList(){
            $("#manageReqForm").attr("action", "/admin/post/feedlist.do");
            $("#manageReqForm").submit();
        }

        function showPost( postIdx ) {
            var url;

            url  = '/admin/posts/' + postIdx;
            console.log( postIdx );
            console.log( url);

            $.ajax({
                url : url,
                type : 'get',
                success: function (rs) {
                    $("#profile").attr( "src", rs['user']['imgSrc'] );
                    $("#userNm").text( rs['user']['userNm'] );
                    $("#regDttm").text( rs['regDttm'] );
                    $("#content").text( rs['content'] );
                    $("#likesCount").text( rs['likesCount'] );

                    if( rs['rsvYn'] == "Y" ) {
                        var rsvCountStatus = rs['rsvCount'] + " / " + rs['rsvMaxCnt'];
                        $("#rsvCountStatus").text( rsvCountStatus );
                        $("#rsvYn").show();
                    }

                    if( rs['shareYn'] == "Y" ) {
                        $("#shareYn").show();
                    }

                    if( rs['postFiles'].length > 0 ) {
                        $("#postFile").attr("src", rs['postFiles'][0]["mediumPath"] );
                        $("#postFile").show();
                    }

                    $("#showPost").show();
                    $("#emptyPost").hide();

                    console.log( rs['user']['imgSrc'])
                    console.log(rs);
                },
                error : function(){
                    alert('해당 게시물을 가져올 수 없습니다.');
                    console.log('error');
                }
            });
        }
        //
        // function managersDetail(adminId, grpId){
        //     $("#manageReqForm > #adminId").val(adminId);
        //     $("#manageReqForm").attr("action", "/admin/managers/write.do?grpId=" + grpId);
        //     $("#manageReqForm").submit();
        // }
        //
        // function managerAdd(grpId){
        //     $("#manageReqForm").attr("action", "/admin/managers/write.do?create=true&grpId=" + grpId);
        //     $("#manageReqForm").submit();
        // }

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>