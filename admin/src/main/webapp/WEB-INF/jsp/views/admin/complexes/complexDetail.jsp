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
                                                일반 주소
                                            </label>
                                            <div class="col-sm-9 form-control-static">
                                                <c:out value="${complexDetail.addr}" escapeXml="false">
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
                                            <div class="col-sm-9 form-control-static">
                                                <c:out value="${totalHomeHeadCount}" escapeXml="false">N/A</c:out>
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
                                                            <c:if test="${complexDetail.logoImgIdx > 0}">
                                                                <div class="thumbnail-viewer" data-image="${complexDetail.clLogoImgSrc}" >
                                                                    <input type="hidden" id="imageIdx" name="imageIdx" value="${complexDetail.logoImgIdx}">
                                                                </div>
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
                                                        <input type="file"
                                                               class="btn btn-block btn-outline btn-default"
                                                               id="image-selector"
                                                               multiple accept="image/*">
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
                                                <p class="text-success">1. '파일 선택'하면 이미지 업로드가 시작되고, 완료되면 화면에 표시됩니다. 이미지 업로드가 완료될 때까지 잠시만 기다리세요.</p>
                                                <p class="text-success">2. 이미지가 표시되면, '이미지 반영' 버튼을 클릭해서 서비스에 변경된 이미지를 반영하세요.</p>
                                                <p class="text-success"></p>
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
                                <ul>
                                    <ui>
                                        <li>상단 [현장 기본 정보]의 [현장 이름] 및 [일반 주소]는 사용자 App내에서 표시용도로 사용되지 않습니다. [현장 표시 이름] 및 [현장 표시 주소]를 별도로 설정하세요.<br></li>
                                        <li>사용자 App내 표시되는 텍스트의 길이의 제한이 있습니다. [현장 표시 이름] 및 [현장 표시 주소]을 설정할 때, 표시되는 길이를 유의하여 입력 바랍니다. *한글의 경우, 3~8자 추천*<br></li>
                                        <li>[현장 지도 연결 링크]는 <a href="https://map.naver.com/" target="_blank">NAVER 지도</a>에서 위치 검색 후, 해당 주소의 URL을 이용하시기 바랍니다. (예, 코오롱 : http://naver.me/xIxRmD3M)<br></li>
                                    </ui>
                                </ul>
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
                                            "Smart Home" 사용 설정
                                        </label>
                                        <div class="col-sm-3 form-control-static">
                                            <div>
                                                <label>
                                                    <input type="radio" value="Y"
                                                           id="iotUseYes" name="iotUseRadios">
                                                    사용함
                                                </label>
                                            </div>
                                            <div>
                                                <label>
                                                    <input type="radio" value="N"
                                                           id="iotUseNo" name="iotUseRadios">
                                                    사용하지 않음
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 form-control-static">
                                            <button class="btn btn-sm btn-primary "
                                                    onclick="updateCmplx('iotUse')"
                                                    type="button"><strong>업데이트</strong></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <ul>
                                    <li>사용자앱 내 [Smart Home]탭의 표시 여부를 설정합니다.</li>
                                    <li>"사용함" 선택시, 사용자앱 하단의 [Smart Home] 탭이 표시됩니다.</li>
                                    <li>실제 IOT 기능 지원여부와 상관없이 탭의 표시 여부 만을 결정합니다. 따라서, 본 현장의 IOT
                                        기능이 실제 지원하는지 여부는 'Kolon 베니트' 담당자에게 문의하시기 바랍니다.</li>
                                </ul>
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
                                        <div class="col-sm-3 form-control-static">
                                            <div>
                                                <label>
                                                    <input type="radio" value="Y"
                                                           id="reservationUseYes" name="reservationUseRadios">
                                                    사용함
                                                </label>
                                            </div>
                                            <div>
                                                <label>
                                                    <input type="radio" value="N"
                                                           id="reservationUseNo" name="reservationUseRadios">
                                                    사용하지 않음
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 form-control-static">
                                            <button class="btn btn-sm btn-primary "
                                                    onclick="updateCmplx('reservationUse')"
                                                    type="button"><strong>업데이트</strong></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <ul>
                                    <li>사용자앱 내 [Reservation]탭의 표시 여부를 설정합니다.</li>
                                    <li>"사용함" 선택시, 사용자앱 하단의 [Reservation] 탭이 표시됩니다.</li>
                                </ul>
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
                                        <div class="col-sm-3 form-control-static">
                                            <div>
                                                <label>
                                                    <input type="radio" value="Y"
                                                           id="feedWriteAllowYes" name="feedWriteAllowRadios">
                                                    사용함
                                                </label>
                                            </div>
                                            <div>
                                                <label>
                                                    <input type="radio" value="N"
                                                           id="feedWriteAllowNo" name="feedWriteAllowRadios">
                                                    사용하지 않음
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 form-control-static">
                                            <button class="btn btn-sm btn-primary "
                                                    onclick="updateCmplx('feedWriteAllow')"
                                                    type="button"><strong>업데이트</strong></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-horizontal">
                                    <ul>
                                        <li>사용자앱 내 [Community] - [Feed]의 글쓰기 가능 여부를 설정합니다.</li>
                                        <li>"사용함" 선택시, 사용자앱에서 [Community] - [Feed]의 하단 우측에 글쓰기 아이콘이 표시됩니다.</li>
                                        <li>"사용하지 않음" 선택시, 사용자앱에서 [Community] - [Feed]의 하단 우측에 글쓰기 아이콘 표시가 사라지며, 사용자는 글을 남길 수 없습니다.</li>
                                        <li>"사용하지 않음"으로 변경 후에도 기존에 작성한 사용자 글은 계속 표시되며, 사용자는 기존 본인의 글을 수정할 수 있습니다. 변경에 유의하시기 바랍니다.</li>
                                    </ul>
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
                                        <ul>
                                            <li>해당 현장의 INFO에 표시되는 아이콘(목록) 설정할 수 있습니다.</li>
                                        </ul>
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
                                        <ul>
                                            <li>[INFO] - [Living Support]내의 문의접수 분류를 설정할 수 있습니다.</li>
                                            <li>여기에서 설정된 항목이 문의 접수시에 사용자 앱에서 표시 됩니다.</li>
                                        </ul>
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

            console.log("${complexDetail.iotUseYn}");
            console.log("${complexDetail.reservationUseYn}");
            console.log("${complexDetail.feedWriteAllowYn}");
            // Radio 버튼 업데이트 (iotUseYn, reservationUseYn, feedWriteAllow)
            $('input:radio[name=iotUseRadios]:input[value="${complexDetail.iotUseYn}"]').attr("checked", "checked");
            $('input:radio[name=reservationUseRadios]:input[value="${complexDetail.reservationUseYn}"]').attr("checked", "checked");
            $('input:radio[name=feedWriteAllowRadios]:input[value="${complexDetail.feedWriteAllowYn}"]').attr("checked", "checked");

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
                // "<button class='delete' type='button'>&times;</button>" +
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


            if( id == 'cmplxNm') {
                value = $('#' + id).val();
                url = '/admin/complexes/' + cmplxId + '/name' + '?name=' + value +
                    '&${_csrf.parameterName}=${_csrf.token}';
            } else if ( id == 'cmplxAddr' ) {
                value = $('#' + id).val();
                url = '/admin/complexes/' + cmplxId + '/addr' + '?addr=' + value +
                    '&${_csrf.parameterName}=${_csrf.token}';
            } else if ( id == 'cmplxMapSrc' ) {
                value = $('#' + id).val();
                url = '/admin/complexes/' + cmplxId + '/mapSrc' + '?mapSrc=' + value +
                    '&${_csrf.parameterName}=${_csrf.token}';
            } else if ( id == 'iotUse' ) {
                value = $(':radio[name="iotUseRadios"]:checked').val();
                url = '/admin/complexes/' + cmplxId + '/iot' + '?useYn=' + value +
                    '&${_csrf.parameterName}=${_csrf.token}';
            } else if ( id == 'reservationUse' ) {
                value = $(':radio[name="reservationUseRadios"]:checked').val();
                url = '/admin/complexes/' + cmplxId + '/reservation' + '?useYn=' + value +
                    '&${_csrf.parameterName}=${_csrf.token}';
            } else if ( id == 'feedWriteAllow' ) {
                value = $(':radio[name="feedWriteAllowRadios"]:checked').val();
                url = '/admin/complexes/' + cmplxId + '/feed' + '?writeAllowYn=' + value +
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