<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="complexes">
<tiles:putAttribute name="title">현장/현장그룹 관리</tiles:putAttribute>
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
    <!-- Section Title -->
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2>현장 상세 정보 - <label class="font-bold">${complexDetail.cmplxNm}</label></h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    현장 관리
                </li>
                <li>
                    개별 현장 관리
                </li>
                <li class="active">
                    <a>현장 상세 정보</a>
                </li>
            </ol>
        </div>
        <div class="col-lg-2">
        </div>
    </div>

    <form:form class="wrapper wrapper-content animated fadeInRight">
        <!------ 해당 현장 상세 정보 시작 ------>
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>현장 기본 정보</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-lg-6 b-r">
                                <%--<div class="ibox-content" style="">--%>
                                    <div class="form-horizontal">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                현장 이름
                                            </label>
                                            <div class="col-sm-9 form-control-static">
                                                <c:out value="${complexDetail.cmplxNm}" escapeXml="false">
                                                    N/A
                                                </c:out>
                                            </div>
                                        </div>

                                        <div class="hr-line-dashed"></div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                현장 그룹
                                            </label>
                                            <div class="col-sm-9 form-control-static">
                                                <c:out value="${complexDetail.cmplxGrpType}" escapeXml="false">
                                                    N/A
                                                </c:out>
                                            </div>
                                        </div>
                                        <div class="hr-line-dashed"></div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                일반 주소
                                            </label>
                                            <div class="col-sm-9 form-control-static">
                                                <c:out value="${complexDetail.addr}" escapeXml="false">
                                                    N/A
                                                </c:out>
                                            </div>
                                        </div>
                                        <%--<div class="hr-line-dashed"></div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label class="col-sm-4 control-label">--%>
                                                <%--상세 주소--%>
                                            <%--</label>--%>
                                            <%--<div class="col-sm-8">--%>
                                                <%--<c:out value="${complexDetail.addrDtl}" escapeXml="false">--%>
                                                    <%--N/A--%>
                                                <%--</c:out>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    </div>
                                <%--</div>--%>
                            </div>
                            <div class="col-lg-6">
                                <%--<div class="ibox-content" style="">--%>
                                    <div class="form-horizontal">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                총 세대 수
                                            </label>
                                            <div class="col-sm-9 media-">
                                                <c:out value="${cmplexDetail.unitCnt}" escapeXml="false">N/A</c:out>
                                            </div>
                                        </div>
                                        <div class="hr-line-dashed"></div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                서비스 <br>가입자 수
                                            </label>
                                            <div class="col-sm-9 form-control-static">
                                                <c:out value="${totalUserCount}" escapeXml="false">
                                                    N/A
                                                </c:out>
                                            </div>
                                        </div>
                                        <%--<div class="hr-line-dashed"></div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label class="col-sm-3 control-label">--%>
                                                <%--{{ SLOT 1 }}--%>
                                            <%--</label>--%>
                                            <%--<div class="col-sm-9 form-control-static">--%>
                                                <%--{{ SLOT 1 }}--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                        <%--<div class="hr-line-dashed"></div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label class="col-sm-3 control-label">--%>
                                                <%--{{ SLOT 2 }}--%>
                                            <%--</label>--%>
                                            <%--<div class="col-sm-9 form-control-static">--%>
                                                <%--{{ SLOT 2 }}--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    </div>
                                <%--</div>--%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!------ 해당 현장 상세 정보 끝 ------>
        <!------ APP 표시 정보 설정 시작 ------>
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>APP 표시 정보 설정</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content" style="">
                        <div class="row">
                            <div class="col-lg-6 b-r">
                                <div class="form-horizontal">
                                    <!--
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            현장 지역
                                        </label>
                                        <div class="col-sm-9 form-control-static">
                                            <select class="form-control m-b" name="account">
                                                <c:forEach var="vo" items="${regionList}" varStatus="status">
                                                    <option>${vo.rgnNm}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    -->
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-lg-6 b-r">
                                                <div class="row">
                                                    <label class="col-sm-6 control-label">
                                                        현장 로고 이미지
                                                    </label>
                                                    <div class="col-sm-6">
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
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="hr-line-dashed"></div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12" >
                                                        <input type="file" id="image-selector" multiple accept="image/*">
                                                    </div>
                                                </div>
                                                <%--<div class="row">--%>
                                                    <%--<div class="col-sm-12">--%>
                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <button type="button"
                                                                class="btn btn-block btn-outline btn-default"
                                                                onclick="updateImage()">이미지 반영</button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <p class="text-success">* '파일 선택'하면 이미지 업로드가 시작되고, 완료되면 화면에 표시됩니다. 이미지 업로드가 완료될 때 까지 기다리세요.</p>
                                                <p class="text-success">* 추천 이미지 비율 - 1:1 가로:세로, 예) 720x720, 1440x1440</p>
                                                <p class="text-success">* 앱에서는 이미지가 해당 비율을 유지하여 표시됩니다.</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            현장 표시 이름
                                        </label>
                                        <div class="col-sm-7 form-control-static">
                                            <input type="text"
                                                   id="cmplxNm"
                                                   class="form-control"
                                                   placeholder="역삼 Tree하우스..."
                                                   value="<c:out value="${complexDetail.clCmplxNm}" escapeXml="false"></c:out>">
                                        </div>
                                        <div class="col-sm-2 form-control-static">
                                            <button class="btn btn-sm btn-primary"
                                                    onclick="updateCmplx('cmplxNm')"
                                                    type="button"><strong>업데이트</strong></button>
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            현장 표시 주소
                                        </label>
                                        <div class="col-sm-7 form-control-static">
                                            <input type="text"
                                                   id="cmplxAddr"
                                                   class="form-control"
                                                   placeholder="서울... "
                                                   value="<c:out value="${complexDetail.clCmplxAddr}" escapeXml="false"></c:out>">
                                        </div>
                                        <div class="col-sm-2 form-control-static">
                                            <button class="btn btn-sm btn-primary"
                                                    onclick="updateCmplx('cmplxAddr')"
                                                    type="button"><strong>업데이트</strong></button>
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            현장 지도<br>연결 링크
                                        </label>
                                        <div class="col-sm-7 form-control-static">
                                            <input type="text"
                                                   id="cmplxMapSrc"
                                                   class="form-control"
                                                   placeholder="http://www...."
                                                   value="<c:out value="${complexDetail.clMapSrc}" escapeXml="false"></c:out>">
                                        </div>
                                        <div class="col-sm-2 form-control-static">
                                            <button class="btn btn-sm btn-primary"
                                                    onclick="updateCmplx('cmplxMapSrc')"
                                                    type="button"><strong>업데이트</strong></button>
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                </div>
                            </div>
                            <div class="col-lg-6 b-r">
                                {{right}}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!------ APP 표시 정보 설정 끝 ------>
        <!------ 해당 현장 관리자 목록 시작 ------>
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>관리자 목록</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>

                    <div class="ibox-content" style="">
                        <input type="text" class="form-control input-sm m-b-xs" id="filter" placeholder="Search in table">
                        <table class="footable table table-stripped toggle-arrow-tiny" data-page-size="10" data-filter="#filter">
                            <thead>
                            <tr>
                                <th data-toggle="true" data-sort-ignore="true">ID</th>
                                <th data-sort-ignore="true">관리자 아이디</th>
                                <th class="" data-sort-ignore="true">이름</th>
                                <th class="" data-sort-ignore="true">Email</th>
                                <th data-hide="all" data-sort-ignore="true">설명</th>
                                <th class="" data-sort-ignore="true">사용유무</th>
                                <th class="" data-sort-ignore="true">등록일</th>
                            </tr>
                            </thead>
                            <c:choose>
                                <c:when test="${fn:length(managerList) == 0}">
                                    <tbody>
                                    <tr>
                                        <td colspan="8"><p><dfn>조회된 결과가 없습니다.</dfn></p></td>
                                    </tr>
                                    </tbody>
                                </c:when>
                                <c:otherwise>
                                    <tbody>
                                    <c:forEach var="vo" items="${managerList}" varStatus="status">
                                        <tr>
                                            <td>${vo.adminIdx}</td>
                                            <td>
                                                <a href="javascript:void(0)" onclick="managersDetail('${vo.adminId}', ${vo.grpId})">
                                                        ${vo.adminId}
                                                </a>
                                            </td>
                                            <td class="center">${vo.adminNm}</td>
                                            <td class="center">${vo.adminEmail}</td>
                                            <td class="center"><p>${vo.desc}</p><br/></td>
                                            <td class="center" >
                                                <c:choose>
                                                    <c:when test="${vo.useYn == 'Y'}">
                                                        <i class="fa fa-check-circle text-navy" alt="사용"></i>
                                                    </c:when>
                                                    <c:when test="${vo.useYn == 'N'}">
                                                        <i class="fa fa-times" alt="사용안함"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        -
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="center" >
                                                <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd" var="sysDt"/>
                                                <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <!-- paginging -->
                                    <tr>
                                        <td colspan="7">
                                            <ul class="pagination"></ul>
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
        <!------ 해당 현장 관리자 목록 끝 ------>
        <!------ [APP] 설정 시작 ------>
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>[APP] 설정</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content" style="">
                        <div class="row">
                            <div class="col-lg-6 b-r">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-6 control-label">
                                            "HOME IoT" 사용 설정
                                        </label>
                                        <div class="col-sm-6 form-control-static">
                                            <div>
                                                <label>
                                                    <input type="radio" checked="" value="option1" id="homeIotUseYes" name="homeIotUseRadios">
                                                    사용함
                                                </label>
                                            </div>
                                            <div>
                                                <label>
                                                    <input type="radio" value="option2" id="homeIotUseNo" name="homeIotUseRadios">
                                                    사용하지 않음
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        {{[APP 설정-"HOME IoT" 사용설정 Right}}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                            <div class="hr-line-dashed"></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6 b-r">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-6 control-label">
                                            "Reservation" 사용 설정
                                        </label>
                                        <div class="col-sm-6 form-control-static">
                                            <div>
                                                <label>
                                                    <input type="radio" checked="" value="option1" id="resvUseYes" name="resvUseRadios">
                                                    사용함
                                                </label>
                                            </div>
                                            <div>
                                                <label>
                                                    <input type="radio" value="option2" id="resvUseNo" name="resvUseRadios">
                                                    사용하지 않음
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        {{[APP 설정-"Reservation" 사용설정 Right}}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!------ [APP] 설정 끝 ------>

        <!------ [APP - COMMUNITY] 설정 시작 ------>
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>[APP - COMMUNITY] 설정</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content" style="">
                        <div class="row">
                            <div class="col-lg-6 b-r">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-6 control-label">
                                            사용자 글쓰기 여부
                                        </label>
                                        <div class="col-sm-6 form-control-static">
                                            <div>
                                                <label>
                                                    <input type="radio" checked="" value="option1" id="feedWriteAllowYes" name="feedWriteAllowRadios">
                                                    사용함
                                                </label>
                                            </div>
                                            <div>
                                                <label>
                                                    <input type="radio" value="option2" id="feedWriteAllowNo" name="feedWriteAllowRadios">
                                                    사용하지 않음
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        {{[APP - COMMUNITY] 설정 Right}}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!------ [APP - COMMUNITY] 설정 끝 ------>

        <!------ [APP - INFO] 설정 시작 ------>
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>[APP - INFO] 설정</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content" style="">
                        <div class="row">
                            <div class="col-lg-6 b-r">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-6 control-label">
                                            [INFO] 목록 관리
                                        </label>
                                        <div class="col-sm-6 form-control-static">
                                            <button
                                                    onclick="window.open('/admin/info/categoryList.do?cmplxId=${complexDetail.cmplxId}')"
                                                    type="button"
                                                    class="btn btn-block btn-outline btn-default">
                                                새로운 창에서 열기
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        {{[INFO] 목록 관리 Right}}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="hr-line-dashed"></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6 b-r">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-6 control-label">
                                            [Living Support] 목록 관리
                                        </label>
                                        <div class="col-sm-6 form-control-static">
                                            <button
                                                    onclick="window.open('/admin/support/categoryList.do?cmplxId=${complexDetail.cmplxId}')"
                                                    type="button"
                                                    class="btn btn-block btn-outline btn-default">
                                                새로운 창에서 열기
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        {{[[Living Support] 목록 관리 Right}}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!------ [APP - INFO] 설정 끝 ------>

    </form:form>
    <form:form name="manageReqForm" id="manageReqForm" method="post" commandName="managerInfo">
        <form:hidden path="adminId"/>
    </form:form>
</tiles:putAttribute>
<tiles:putAttribute name="js">
    <script type="text/javascript">
        $(function () {
            $('.footable').footable();

            $("#left_complex").addClass("active");
            $("#left_complex > .nav-second-level").addClass("in");
            $("#left_complex_list").addClass("active");

            // 썸네일 - 이미지 업로드
            $( '#image-selector' ).on( 'change', function( event ) {
                _.each( event.currentTarget.files, function( file ) {
                    uploadImage( file );
                } );
            } );

            // 썸네일 - 이미지 삭제
            $( document ).on( 'click', '.thumbnail-viewer .delete', function( event ) {
                $( event.currentTarget ).closest( '.thumbnail-viewer' ).remove();
            } );

            // 썸네일 - 이미지 표시
            $( '.thumbnail-viewer' ).each( function( index, element ) {
                var $element = $( element );
                $element.css( 'backgroundImage', 'url(' + $element.data( 'image' ) + ')')
            } );
        })

        function createThumbnail( data ) {
            var thumb =
                "<div class='thumbnail-viewer' " +
                "      style='background-image: url(" + ( "/admin/imageStore/" + data['imageIdx'] ) + ");'>" +
                "<button class='delete' type='button'>&times;</button>" +
                "<input type='hidden' id='imageIdx' name='imageIdx' value='" + data['imageIdx'] + "'>" +
                "</div>";
            if( $('.thumbnail-viewer').length ) {
                $('.thumbnail-viewer').remove();
            }
            $( '#thumbnails' ).append( $( thumb ) );
        }

        function uploadImage( file ) {
            var data = new FormData();
            data.append( "file", file );
            $.ajax({
                url: '/admin/imageStore/complex/?${_csrf.parameterName}=${_csrf.token}',
                type: 'POST',
                data: data,
                contentType: false,
                processData: false,
                success: function (rs) {
                    if( rs ) {
                        console.log( rs );
                        createThumbnail( rs );
                    }else{
                        alert(rs.msg);
                    }
                },
                error : function(jqxhr){
                    var respBody = jQuery.parseJSON(jqxhr.responseText);
                    console.log(respBody);
                    alert("이미지 업로드가 실패하였습니다.");
                }
            });
        }

        function updateImage( ) {
            var url;
            var type;
            var cmplxId = ${complexDetail.cmplxId};
            var imageIdx = $('#imageIdx').val();
            console.log( cmplxId );
            console.log( imageIdx );


            type = 'PUT';
            url = '/admin/imageStore/' + imageIdx +
                  '?parentIdx=' + cmplxId +
                  '&${_csrf.parameterName}=${_csrf.token}';

                  console.log(url);

            $.ajax( {
                    url: url,
                    type: type,
                    contentType: false,
                    processData: false,
                    success: function (rs) {
                        if (rs) {
                            alert("업데이트 되었습니다.");
                            refreshList();
                        } else {
                            alert("실패하였습니다.");
                        }
                    },
                    error: function () {
                        alert('에러가 발생하였습니다.');
                        console.log('error');
                    }
                } );
        }

        function updateCmplx( id ) {
            var url;
            var type    = 'PUT';
            var cmplxId = <c:out value="${complexDetail.cmplxId}" escapeXml="false">-1</c:out>;
            var value;

            value = $('#' + id).val();
            if( id == 'cmplxNm') {
                url = '/admin/complexes/' + cmplxId + '/name' + '?name=' + value +
                    '&${_csrf.parameterName}=${_csrf.token}';
            } else if ( id == 'cmplxAddr' ) {
                url = '/admin/complexes/' + cmplxId + '/addr' + '?addr=' + value +
                    '&${_csrf.parameterName}=${_csrf.token}';
            } else if ( id == 'cmplxMapSrc' ) {
                url = '/admin/complexes/' + cmplxId + '/mapSrc' + '?mapSrc=' + value +
                    '&${_csrf.parameterName}=${_csrf.token}';
            } else {
                return;
            }

            console.log(url);

            $.ajax({
                url: url,
                type: type,
                contentType: false,
                processData: false,
                success: function (rs) {
                    if (rs) {
                        alert("업데이트 되었습니다.");
                        refreshList();
                    } else {
                        alert("실패하였습니다.");
                        refreshList();
                    }
                },
                error: function (req, status, error) {
                    alert('에러가 발생하였습니다.');
                    console.log( error );
                    refreshList();
                }
            })
        }

        function refreshList(){
            location.reload();
        }

        function managersDetail(adminId, grpId){
            $("#manageReqForm > #adminId").val(adminId);
            $("#manageReqForm").attr("action", "/admin/managers/write.do?grpId=" + grpId);
            $("#manageReqForm").submit();
        }

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>