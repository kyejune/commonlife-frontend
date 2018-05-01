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
        <c:when test="${userType eq 'user'}">
            <c:set value="사용자" var="userTypeTxt"></c:set>
        </c:when>
        <c:when test="${userType eq 'head'}">
            <c:set value="새대주" var="userTypeTxt"></c:set>
        </c:when>
        <c:otherwise>
            <c:set   value="" var="userTypeTxt"></c:set>
        </c:otherwise>
    </c:choose>

    <!-- Section Title -->
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2><c:out value="${userTypeTxt}" escapeXml="false">사용자</c:out> 목록</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    사용자 정보 관리
                </li>
                <li class="active">
                    <a><c:out value="${userTypeTxt}" escapeXml="false">사용자</c:out> 목록</a>
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
                        <h5><c:out value="${userTypeTxt}" escapeXml="false">사용자</c:out> 목록</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>

                    <div class="ibox-content" style="">
                        <div class="row">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>
                                            <c:choose>
                                                <c:when test="${userType eq 'user'}">지점/세대주</c:when>
                                                <c:otherwise>지점</c:otherwise>
                                            </c:choose>
                                        </th>
                                        <th>이름</th>
                                        <th>등록일</th>
                                        <th>동/호수</th>
                                        <th>이메일 주소</th>
                                    </tr>
                                </thead>
                                <c:choose>
                                    <c:when test="${fn:length(userList) == 0}">
                                        <tbody>
                                        <tr>
                                            <td colspan="9"><p><dfn>조회된 결과가 없습니다.</dfn></p></td>
                                        </tr>
                                        </tbody>
                                    </c:when>
                                    <c:otherwise>
                                        <tbody>
                                        <c:forEach var="vo" items="${userList}" varStatus="status">
                                            <tr>
                                                <td>
                                                    <p>${vo.cmplxNm}
                                                    <c:if test="${userType eq 'user'}">
                                                    <br><span class="font-bold">${vo.headNm}</span> 세대
                                                    </c:if>
                                                    </p>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${userType eq 'user'}">${vo.userNm}</c:when>
                                                        <c:otherwise>${vo.headNm}</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <fmt:parseDate value="${vo.regDt}" pattern="yyyy-MM-dd" var="sysDt"/>
                                                    <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd"/>
                                                </td>
                                                <td>
                                                           ${vo.dong}동 ${vo.ho}호
                                                </td>
                                                <td>
                                                    ${vo.email}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <!-- paginging -->
                                        <tr>
                                            <td colspan="5" >
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
            <%--<div class="col-md-4">--%>
                <%--<div class="ibox float-e-margins">--%>
                    <%--<div class="ibox-title">--%>
                        <%--<h5><c:out value="${userTypeTxt}" escapeXml="false">사용자</c:out> 상세보기</h5>--%>
                        <%--<div class="ibox-tools">--%>
                            <%--<a class="collapse-link">--%>
                                <%--<i class="fa fa-chevron-up"></i>--%>
                            <%--</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<div class="ibox-content" style="">--%>
                        <%--<input id="postIdx" type="hidden" value="-1" />--%>
                        <%--<div id="emptyPost" class="text-success font-bold text"><br>--%>
                            <%--<br><h3>'내용'을 클릭하여 Feed의 <br> 상세 정보를 볼 수 있습니다.</h3><br>--%>
                        <%--</div>--%>
                        <%--<div class="social-feed-box" id="showPost" style="display: none;">--%>
                            <%--<div class="social-avatar m-sm">--%>
                                <%--<a href="" class="pull-left">--%>
                                    <%--<img id="profile" alt="image" src="">--%>
                                <%--</a>--%>
                                <%--<div class="media-body">--%>
                                    <%--<a href="#" id="userNm"></a>--%>
                                    <%--<small class="text-muted" id="regDttm" ></small>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="social-body m-sm">--%>
                                <%--<div id="content" style="white-space: pre-line"></div>--%>
                                <%--<br>--%>
                                <%--<br>--%>
                                <%--<img id="postFile" src="#" class="img-responsive" style="display: none">--%>
                                <%--<br>--%>
                                <%--<br>--%>
                            <%--</div>--%>
                            <%--<div class="social-footer">--%>
                                <%--<div class="row">--%>
                                    <%--<label class="col-lg-6 control-label">--%>
                                        <%--<i class="fa fa-thumbs-up"></i>--%>
                                    <%--</label>--%>
                                    <%--<div class="col-lg-6">--%>
                                        <%--<div id="likesCount">0</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="row" id="rsvYn" style="display: none;">--%>
                                    <%--<label class="col-lg-6 control-label">--%>
                                        <%--<i class="fa fa-users"></i> 참여자--%>
                                    <%--</label>--%>
                                    <%--<div class="col-lg-6">--%>
                                        <%--<div id="rsvCountStatus">0</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="row" id="shareYn" style="display: none;">--%>
                                    <%--<label class="col-lg-6 control-label">--%>
                                        <%--<i class="fa fa-share"></i>--%>
                                    <%--</label>--%>
                                    <%--<div class="col-lg-6">--%>
                                        <%--공유 가능--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="row">--%>
                                    <%--<div class="hr-line-dashed"></div>--%>
                                    <%--<c:if test="${userType eq 'news' or userType eq 'event'}">--%>
                                            <%--<div class="col-lg-6 btn-sm" id="updateButton">--%>
                                                <%--<button type="button"--%>
                                                        <%--class="btn-xs btn btn-w-m btn-primary btn-outline"--%>
                                                        <%--onclick="editPost()">편집</button>--%>
                                            <%--</div>--%>
                                    <%--</c:if>--%>
                                    <%--&lt;%&ndash;<c:if test="${userType eq 'feed'}">&ndash;%&gt;--%>
                                    <%--<div class="col-lg-6 btn-sm"  id="postPrivateButton">--%>
                                        <%--<button type="button"--%>
                                                <%--class="btn-xs btn btn-w-m btn-danger btn-outline"--%>
                                                <%--onclick="">비공개</button>--%>
                                    <%--</div>--%>
                                    <%--<div class="col-lg-6 btn-sm"  id="postPublicButton">--%>
                                        <%--<button type="button"--%>
                                                <%--class="btn-xs btn btn-w-m btn-danger btn-outline"--%>
                                                <%--onclick="">공개</button>--%>
                                    <%--</div>--%>
                                    <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</tiles:putAttribute>
<tiles:putAttribute name="js">
    <script type="text/javascript">
        $(function () {
            $("#left_user").addClass("active");
            $("#left_user > .nav-second-level").addClass("in");

            <c:choose>
            <c:when test="${userType eq 'user'}">
            $("#left_user_user").addClass("active");
            </c:when>
            <c:when test="${userType eq 'head'}">
            $("#left_user_head").addClass("active");
            </c:when>
            <c:otherwise>
            $("#left_user_head").addClass("active");
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
            window.location.replace("?pageNum=" + pageIndex);
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
                <c:when test="${userType eq 'news'}">
                    window.location.href = "newNotice.do";
                </c:when>
                <c:when test="${userType eq 'event'}">
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

        <c:if test="${userType eq 'news' or userType eq 'event'}">
            // notice 및 event 만 관리자가 편집 가능
            function editPost() {
                var url = "feedEdit.do?postIdx=" + $("#postIdx").val();
                window.location.href = url;
            }
        </c:if>
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>