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
            <h2><c:out value="${postTypeTxt}" escapeXml="false">사용자 FEED</c:out> 관리</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    FEED 관리
                </li>
                <li class="active">
                    <a><c:out value="${postTypeTxt}" escapeXml="false">사용자 FEED</c:out> 관리</a>
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
                        <h5><c:out value="${postTypeTxt}" escapeXml="false">사용자 FEED</c:out> 목록</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>

                    <div class="ibox-content" style="">
                        <c:if test="${postType eq 'event' or postType eq 'news'}">
                            <div class="row">
                                <div class="col-lg-12">
                                    <button class="btn btn-primary"
                                            onclick="newPost()">
                                        게시물 생성

                                    </button>
                                </div>
                            </div>
                        </c:if>
                        <div class="row">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>사용자</th>
                                        <c:if test="${postType eq 'event'}">
                                        <th>제목</th>
                                        </c:if>
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
                                                    <c:if test="${vo.adminYn eq 'Y'}">
                                                        <i class="fa fa-star text-warning"></i>
                                                    </c:if>
                                                    ${vo.user.userNm}
                                                </td>
                                                <c:if test="${postType eq 'event'}">
                                                <td>
                                                    ${vo.title}
                                                </td>
                                                </c:if>
                                                <td>
                                                    <a href="javascript:void(0)"
                                                       onclick="showPost(${vo.postIdx})">

                                                        <c:choose>
                                                            <c:when test="${fn:length(vo.content) < 1}" >
                                                                <c:out value="{내용 없음}" escapeXml="false"></c:out>
                                                            </c:when>
                                                            <c:when test="${fn:length(vo.content) > 30}" >
                                                                <c:out value="${fn:substring(vo.content,0, 30)}..." escapeXml="false"></c:out>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:out value="${vo.content}" escapeXml="false"></c:out>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </a>
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
                                            <c:choose>
                                                <c:when test="${postType eq 'event'}">
                                                <td colspan="9" >
                                                </c:when>
                                                <c:otherwise>
                                                <td colspan="8" >
                                                </c:otherwise>
                                            </c:choose>
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
            </div>
            <div class="col-md-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-titile">
                        <h5><c:out value="${postTypeTxt}" escapeXml="false">사용자 FEED</c:out> 상세보기</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>

                    <div class="ibox-content" style="">
                        <input id="postIdx" type="hidden" value="-1" />
                        <div id="emptyPost" class="text-success font-bold text"><br>
                            <br><h3>'내용'을 클릭하여 Feed의 <br> 상세 정보를 볼 수 있습니다.</h3><br>
                        </div>
                        <div class="social-feed-box" id="showPost" style="display: none;">
                            <div class="social-avatar m-sm">
                                <a href="" class="pull-left">
                                    <img id="profile" alt="image" src="">
                                </a>
                                <div class="media-body">
                                    <a href="#" id="userNm"></a>
                                    <small class="text-muted" id="regDttm" ></small>
                                </div>
                            </div>
                            <div class="social-body m-sm">
                                <div id="content" style="white-space: pre-line"></div>
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
                                <div class="row">
                                    <div class="hr-line-dashed"></div>
                                    <c:if test="${postType eq 'news' or postType eq 'event'}">
                                            <div class="col-lg-6 btn-sm" id="updateButton">
                                                <button type="button"
                                                        class="btn-xs btn btn-w-m btn-primary btn-outline"
                                                        onclick="editPost()">편집</button>
                                            </div>
                                    </c:if>
                                    <%--<c:if test="${postType eq 'feed'}">--%>
                                    <div class="col-lg-6 btn-sm"  id="postPrivateButton">
                                        <button type="button"
                                                class="btn-xs btn btn-w-m btn-danger btn-outline"
                                                onclick="">비공개</button>
                                    </div>
                                    <div class="col-lg-6 btn-sm"  id="postPublicButton">
                                        <button type="button"
                                                class="btn-xs btn btn-w-m btn-danger btn-outline"
                                                onclick="">공개</button>
                                    </div>
                                    <%--</c:if>--%>
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

            // initialze datetime picker
            $( '.datepicker' ).each( function( index, element ) {
                var $element = $( element );
                var params = {};
                if( $element.data( 'format' ) ) {
                    params.format = $element.data( 'format' );
                }
                $element.datetimepicker( params );
            } );

            <c:if test="${error != null}" >
            alert("${error}");
            return;
            </c:if>
        });

        function fn_link_page( pageIndex ) {
            window.location.replace("feedList.do?pageNum=" + pageIndex);
        }

        function refreshList(){
            location.reload();
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
                    $("#postIdx").attr( "value", rs['postIdx'] );
                    $("#profile").attr( "src", rs['user']['imgSrc'] );
                    $("#userNm").text( rs['user']['userNm'] );
                    $("#regDttm").text( rs['regDttm'] );
                    $("#content").text( rs['content'] );
                    $("#likesCount").text( rs['likesCount'] );

                    if( rs['delYn'] == "Y" ) {
                        $("#postPrivateButton").hide();
                        $("#postPublicButton").show();
                        $("#postPublicButton").attr( "onclick", "makePostPublic(" + rs['postIdx'] + ")" );
                    } else {
                        $("#postPrivateButton").show();
                        $("#postPublicButton").hide();
                        $("#postPrivateButton").attr( "onclick", "makePostPrivate(" + rs['postIdx'] + ")" );
                    }

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
                    } else {
                        $("#postFile").hide();
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

        function newPost() {
            <c:choose>
                <c:when test="${postType eq 'news'}">
                    window.location.href = "newNotice.do";
                </c:when>
                <c:when test="${postType eq 'event'}">
                    window.location.href = "newEvent.do";
                </c:when>
            </c:choose>
        }

        function makePostPrivate( postIdx ) {
            var url;
            var ret;

            url  = '/admin/posts/' + postIdx + "/private" + "?${_csrf.parameterName}=${_csrf.token}";
            console.log( postIdx );
            console.log( url);

            ret = confirm("주의: 해당 게시물을 비공개 처리하시겠습니까? 비공개 처리된 게시물은 사용자에게 더이상 노출 되지 않습니다.");
            if( ret ) {
                ret = confirm("재확인 : 게시물을 비공개 처리하겠습니까?");
                if( ret ) {
                    $.ajax({
                        url : url,
                        type : 'put',
                        success: function (data, textStatus, xhr) {
                            console.log(data);
                            alert(data['msg']);
                            refreshList();
                        },
                        error : function(data, textStatus, xhr){
                            console.log(data);
                            alert(data['msg']);
                        }
                    });
                }
            }
        }

        function makePostPublic( postIdx ) {
            var url;
            var ret;

            url  = '/admin/posts/' + postIdx +  "/public" + "?${_csrf.parameterName}=${_csrf.token}";
            console.log( postIdx );
            console.log( url);

            ret = confirm("주의: 해당 게시물을 공개로 변경하시겠습니까? 공개된 게시물은 사용자에게 바로 노출 됩니다.");
            if( ret ) {
                ret = confirm("재확인 : 게시물을 공개 상태로 변경하시겠습니까?");
                if( ret ) {
                    $.ajax({
                        url : url,
                        type : 'put',
                        success: function (data, textStatus, xhr) {
                            console.log(data);
                            alert(data['msg']);
                            refreshList();
                        },
                        error : function(data, textStatus, xhr){
                            console.log(data);
                            alert(data['msg']);
                        }
                    });
                }
            }
        }

        <c:if test="${postType eq 'news' or postType eq 'event'}">
            // notice 및 event 만 관리자가 편집 가능
            function editPost() {
                var url = "feedEdit.do?postIdx=" + $("#postIdx").val();
                window.location.href = url;
            }
        </c:if>
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>