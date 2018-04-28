<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="posts">
<tiles:putAttribute name="title">Feed 작성/편집</tiles:putAttribute>
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
                        <h5>Event 작성/편집</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form action="" method="post" class="form-horizontal">
                            <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                            <%--<input type="hidden" name="redirectTo" value="${redirectTo}">--%>
                            <input type="hidden" name="postIdx" value="${postIdx}">

                            <div class="form-group">
                                <label class="col-sm-2 control-label">이벤트 기간</label>
                                <div class="col-sm-10">
                                    <div class="input-group m-b">
                                        <input type="text"
                                               class="form-control datepicker"
                                               name="eventBeginDttm"
                                               data-format="YYYY-MM-DD HH:mm"
                                               value="">
                                        <span class="input-group-addon"> 부터</span>
                                    </div>
                                    <div class="input-group">
                                        <input type="text"
                                               class="form-control datepicker"
                                               name="eventBeginDttm"
                                               data-format="YYYY-MM-DD HH:mm"
                                               value="">
                                        <span class="input-group-addon"> 까지</span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">이벤트 장소</label>
                                <div class="col-sm-10">
                                    <input type="text" name="eventPlaceNm" class="form-control col-sm-10" value="">
                                    <p class="text-success col-sm-12">* 구체적인 장소를 입력하세요. (예, 5층 커뮤니티 룸)</p>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">'참여 신청' 기능 설정</label>
                                <div class="col-sm-10" >
                                    <div class="input-group">
                                        <label class="radio-inline">
                                            <input type="radio" name="rsvYn" value="Y" checked>
                                            사용
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="rsvYn" value="N">
                                            사용하지 않음
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">최대 참여 가능 인원</label>
                                <div class="col-sm-10">
                                    <input type="number" name="maxRsvCnt" class="form-control" value="0" min="0">
                                    <p class="text-success">* 참여 신청 기능을 이용하면, 선착순으로 참여 신청을 받을 수 있습니다.</p>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">'문의 안내' 표시 설정</label>
                                <div class="col-sm-10" >
                                    <div class="input-group">
                                        <label class="radio-inline">
                                            <input type="radio" name="inquiryYn" value="Y" checked>
                                            사용
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="inquiryYn" value="N">
                                            사용하지 않음
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">문의 방법 선택</label>
                                <div class="col-sm-10" >
                                    <div class="input-group">
                                        <label class="radio-inline">
                                            <input type="radio" name="inquiryType" value="E" checked>
                                            이메일
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="inquiryType" value="P">
                                            전화번호
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">문의 정보</label>
                                <div class="col-sm-10">
                                    <input type="text" name="inquiryInfo" class="form-control" value="">
                                    <p class="text-success">* 이메일 또는 전화번호를 선택합니다. 문의 정보에 이메일 주소나 연락처를
                                        입력하면, 이벤트 게시물을 통해 사용자가 문의할 수 있습니다. </p>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">외부 공유 기능 설정</label>
                                <div class="col-sm-10" >
                                    <div class="input-group">
                                        <label class="radio-inline">
                                            <input type="radio" name="shareYn" value="Y">
                                            공유 기능 활성화
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="shareYn" value="N" checked>
                                            공유 기능 비활성
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">이벤트 설명</label>
                                <div class="col-sm-10" >
                                    <textarea name="content" id="content" cols="100" rows="5"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">이미지</label>
                                <div class="col-sm-6" >
                                    <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                                        <div class="form-control" data-trigger="fileinput">
                                            <i class="glyphicon glyphicon-file fileinput-exists"></i>
                                            <span class="fileinput-filename"></span>
                                        </div>
                                        <span class="input-group-addon btn btn-default btn-file">
                                            <span class="fileinput-new">선택하기</span>
                                            <span class="fileinput-exists">변경하기</span>
                                            <input type="file" name="..."/>
                                        </span>
                                        <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">취소하기</a>
                                    </div>
                                    <p class="text-success">* 추천 이미지 비율 - 1.56:1 가로:세로, 예) 720x460, 1440x920</p>
                                    <p class="text-success">* 앱에서는 이미지가 해당 비율을 유지하여 표시됩니다.</p>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"></label>
                                <div class="col-sm-6" >
                                    <button class="btn  btn-block btn-primary m-t">저장하기</button>
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
                $element.datetimepicker( params );
            } );
        })

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>