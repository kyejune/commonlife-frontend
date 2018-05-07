<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="posts">
<tiles:putAttribute name="title">Feed 작성/편집</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- DataTables -->
    <a><!-- --></a>
    <style type="text/css">
        .thumbnail-viewer {
            display: inline-block;
            position: relative;
            content: ' ';
            width: 120px;
            height: 120px;
            margin-right: 1em;
            background-size: cover;
            background-position: center;
        }

        .thumbnail-viewer button {
            position: absolute;
            right: 0;
            top: 0;
        }
    </style>
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
            <h2><c:out value="${postTypeTxt}" escapeXml="false">사용자 FEED</c:out> 편집</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    FEED 관리
                </li>
                <li class="active">
                    <a><c:out value="${postTypeTxt}" escapeXml="false">사용자 FEED</c:out> 편집</a>
                </li>
            </ol>
        </div>
        <div class="col-lg-2">
        </div>
    </div>

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-md-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><c:out value="${postTypeTxt}" escapeXml="false">사용자 FEED</c:out> 작성/편집</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form action="#" class="form-horizontal" name="postInfo" id="postInfo">
                            <input type="hidden" name="postIdx" value="${postIdx}" />
                            <input type="hidden" name="postType" value="${postType}" />
                            <input type="hidden" name="postUpdateYn" value="${postUpdateYn}" />

                            <%-- Event 전용 Form --%>
                            <c:if test="${postType eq 'event'}">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">제목 *</label>
                                    <div class="col-sm-10" >
                                        <input type="text"
                                               name="title"
                                               class="form-control"
                                               value="${postInfo.title}">
                                    </div>
                                </div>
                            </c:if>

                            <%-- 공통 Form --%>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">내용 *</label>
                                <div class="col-sm-10" >
                                <textarea name="content"
                                          id="content"
                                          cols="100"
                                          rows="5">${postInfo.content}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">이미지</label>
                                <div class="col-sm-6" >
                                    <div class="form-group">
                                        <input type="file" id="image-selector" multiple accept="image/*">
                                    </div>
                                    <div id="thumbnails">
                                        <c:if test="${postInfo.postFiles != null}">
                                            <c:forEach var="image" items="${postInfo.postFiles}">
                                                <div class="thumbnail-viewer" data-image="/admin/postFiles/${image.postFileIdx}" >
                                                    <input type="hidden" name="postFile[]" value="${image.postFileIdx}">
                                                    <button class="delete">&times;</button>
                                                </div>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                    <p class="text-success">* '파일 선택'하면 이미지 업로드가 시작되고, 완료되면 화면에 표시됩니다. 이미지 업로드가 완료될 때 까지 기다리세요.</p>
                                    <p class="text-success">* 추천 이미지 비율 - 1.56:1 가로:세로, 예) 720x460, 1440x920</p>
                                    <p class="text-success">* 앱에서는 이미지가 해당 비율을 유지하여 표시됩니다.</p>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <c:if test="${postType eq 'event'}">
                                <%-- Event 전용 Form --%>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">이벤트 기간</label>
                                    <div class="col-sm-10">
                                        <div class="input-group m-b">
                                            <input type="text"
                                                   class="form-control datepicker"
                                                   name="eventBeginDttm"
                                                   data-format="YYYY-MM-DD HH:mm"
                                                   value="${postInfo.eventBeginDttm}">
                                            <span class="input-group-addon"> 부터</span>
                                        </div>
                                        <div class="input-group">
                                            <input type="text"
                                                   class="form-control datepicker"
                                                   name="eventEndDttm"
                                                   data-format="YYYY-MM-DD HH:mm"
                                                   value="${postInfo.eventEndDttm}">
                                            <span class="input-group-addon"> 까지</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">이벤트 장소</label>
                                    <div class="col-sm-10">
                                        <input type="text"
                                               name="eventPlaceNm"
                                               class="form-control col-sm-10"
                                               value="${postInfo.eventPlaceNm}">
                                        <p class="text-success col-sm-12">* 구체적인 장소를 입력하세요. (예, 5층 커뮤니티 룸)</p>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">'참여 신청' 기능 설정 *</label>
                                    <div class="col-sm-10" >
                                        <div class="input-group">
                                            <c:choose>
                                                <c:when test="${postInfo.rsvYn eq 'Y'}">
                                                    <label class="radio-inline">
                                                        <input type="radio" name="rsvYn" value="Y" checked>
                                                        사용
                                                    </label>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="rsvYn" value="N" >
                                                        사용하지 않음
                                                    </label>
                                                </c:when>
                                                <c:otherwise>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="rsvYn" value="Y">
                                                        사용
                                                    </label>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="rsvYn" value="N" checked>
                                                        사용하지 않음
                                                    </label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">최대 참여 가능 인원</label>
                                    <div class="col-sm-10">
                                        <input type="number"
                                               name="rsvMaxCnt"
                                               class="form-control"
                                               min="0"
                                               value="${postInfo.rsvMaxCnt}" >
                                        <p class="text-success">* 참여 신청 기능을 이용하면, 선착순으로 참여 신청을 받을 수 있습니다.</p>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">'문의 안내' 표시 설정 *</label>
                                    <div class="col-sm-10" >
                                        <div class="input-group">
                                            <c:choose>
                                                <c:when test="${postInfo.inquiryYn eq 'Y'}">
                                                <label class="radio-inline">
                                                    <input type="radio" name="inquiryYn" value="Y" checked>
                                                    사용
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="inquiryYn" value="N"  >
                                                    사용하지 않음
                                                </label>
                                                </c:when>
                                                <c:otherwise>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="inquiryYn" value="Y" >
                                                        사용
                                                    </label>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="inquiryYn" value="N"  checked>
                                                        사용하지 않음
                                                    </label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">문의 방법 선택</label>
                                    <div class="col-sm-10" >
                                        <div class="input-group">
                                            <c:choose>
                                                <c:when test="${postInfo.inquiryType eq 'P'}">
                                                    <label class="radio-inline">
                                                        <input type="radio" name="inquiryType" value="E" >
                                                        이메일
                                                    </label>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="inquiryType" value="P" checked>
                                                        전화번호
                                                    </label>
                                                </c:when>
                                                <c:otherwise>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="inquiryType" value="E" checked>
                                                        이메일
                                                    </label>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="inquiryType" value="P">
                                                        전화번호
                                                    </label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">문의 정보</label>
                                    <div class="col-sm-10">
                                        <input type="text"
                                               name="inquiryInfo"
                                               class="form-control"
                                               value="${postInfo.inquiryInfo}">
                                        <p class="text-success">* 이메일 또는 전화번호를 선택합니다. 문의 정보에 이메일 주소나 연락처를
                                            입력하면, 이벤트 게시물을 통해 사용자가 문의할 수 있습니다. </p>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">외부 공유 기능 설정 *</label>
                                    <div class="col-sm-10" >
                                        <div class="input-group">
                                            <c:choose>
                                                <c:when test="${postInfo.shareYn eq 'Y'}">
                                                    <label class="radio-inline">
                                                        <input type="radio" name="shareYn" value="Y" checked>
                                                        공유 기능 활성화
                                                    </label>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="shareYn" value="N">
                                                        공유 기능 비활성
                                                    </label>
                                                </c:when>
                                                <c:otherwise>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="shareYn" value="Y">
                                                        공유 기능 활성화
                                                    </label>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="shareYn" value="N" checked>
                                                        공유 기능 비활성
                                                    </label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                            </c:if>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"></label>
                                <div class="col-sm-6" >
                                    <div class="btn btn-block btn-primary m-t" onclick="updatePost()">저장하기</div>
                                </div>
                                <div class="col-sm-2"></div>
                            </div>
                        </form>
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

            // initialze datetime picker
            $( '.datepicker' ).each( function( index, element ) {
                var $element = $( element );
                var params = {};
                if( $element.data( 'format' ) ) {
                    params.format = $element.data( 'format' );
                }
                $element.datetimepicker(    params );
            } );

            // 이미지 업로드
            $( '#image-selector' ).on( 'change', function( event ) {
                _.each( event.currentTarget.files, function( file ) {
                    uploadImage( file );
                } );
            } );

            // 이미지 삭제
            $( document ).on( 'click', '.thumbnail-viewer .delete', function( event ) {
                $( event.currentTarget ).closest( '.thumbnail-viewer' ).remove();
            } );

            // 썸네일 이미지 표시
            $( '.thumbnail-viewer' ).each( function( index, element ) {
                var $element = $( element );
                $element.css( 'backgroundImage', 'url(' + $element.data( 'image' ) + ')')
            } );
        });

        function createThumbnail( data ) {
            var thumb = "<div class='thumbnail-viewer' " +
                        "      style='background-image: url(" + ( "/admin/postFiles/" + data['postFileIdx'] ) + ");'>" +
                "<button class='delete' type='button'>&times;</button>" +
                "<input type='hidden' name='postFiles[]' value='" + data['postFileIdx'] + "'>" +
                "</div>";
            if( $('.thumbnail-viewer').length ) {
                $('.thumbnail-viewer').remove();
            }
            $( '#thumbnails' ).append( $( thumb ) );
        }

        function uploadImage( file ) {
            var data = new FormData();
            data.append( "file", file );
            $.ajax( {
                url: '/admin/postFiles/?${_csrf.parameterName}=${_csrf.token}',
                type: 'POST',
                data: data,
                contentType: false,
                processData: false
            } )
                .done( function( data ) {
                    console.log( data);
                    createThumbnail( data );
                } );
        }

        function updatePost() {
            var url;
            var type;
            <c:choose>
            <c:when test="${postUpdateYn eq 'Y'}">
            url = '/admin/posts/${postIdx}?${_csrf.parameterName}=${_csrf.token}';
            type = 'put';
            </c:when>
            <c:otherwise>
            url = '/admin/posts/proc.do?${_csrf.parameterName}=${_csrf.token}';
            type =  'post'
            </c:otherwise>
            </c:choose>

            var data = {};
            data['postFiles'] = new Array;

            var postFileIdx = 0;
            $.each( $("form[name=postInfo]").serializeArray(), function() {
                if( this.name == "postFiles[]" ) {
                    var postFile = {};
                    postFile['postFileIdx'] = this.value;
                    data['postFiles'].push( postFile );
                } else {
                    data[this.name] = this.value;
                }
            });

            console.log( data );

            $.ajax({
                type : type,
                url  : url,
                data : JSON.stringify(data),
                dataType : "json",
                contentType : "application/json; charset=UTF-8",
                error: function(xhr, status, error){
                    var respJson = $.parseJSON(xhr.responseText);
                    console.log( respJson    );
                    console.log( status );
                    alert( respJson['msg'] );
                },
                success : function(json){
                    alert('성공적으로 저장하였습니다');
                    <c:choose>
                        <c:when test="${postType eq 'news'}">
                            window.location.href = "noticeList.do";
                        </c:when>
                        <c:when test="${postType eq 'event'}">
                            window.location.href = "eventList.do";
                        </c:when>
                        <c:otherwise>
                            window.location.href = "feedList.do";
                        </c:otherwise>
                    </c:choose>
                }
            });
        }

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>