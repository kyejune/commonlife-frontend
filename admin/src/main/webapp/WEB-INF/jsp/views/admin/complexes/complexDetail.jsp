<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="complexes">
<tiles:putAttribute name="title">현장/현장그룹 관리</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- DataTables -->
    <a><!-- --></a>
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
                                                {{총 세대 수}}
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
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-lg-6 b-r">
                                                <div class="row">
                                                    <label class="col-sm-6 control-label">
                                                        현장 로고 이미지
                                                    </label>
                                                    <div class="col-sm-6">
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="hr-line-dashed"></div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12 fileinput fileinput-new" data-provides="fileinput">
                                                        <span class="btn btn-block btn-default btn-file">
                                                            <span class="fileinput-new">이미지 선택 및 업로드</span>
                                                            <span class="fileinput-exists">이미지 변경</span>
                                                            <input type="file" name="..."/>
                                                        </span>
                                                        <span class="fileinput-filename"></span>
                                                        <a href="#" class="close fileinput-exists" data-dismiss="fileinput" style="float: none">×</a>
                                                    </div>
                                                </div>
                                                <%--<div class="row">--%>
                                                    <%--<div class="col-sm-12">--%>
                                                        <%--<div class="hr-line-dashed"></div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <button class="btn btn-block btn-default">이미지 반영</button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                {{오른쪽}}
                                            </div>
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            현장 표시 이름
                                        </label>
                                        <div class="col-sm-9 form-control-static">
                                            <input type="text" class="form-control" placeholder="역삼 Tree하우스...">
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            현장 표시 주소
                                        </label>
                                        <div class="col-sm-9 form-control-static">
                                            <input type="text" class="form-control" placeholder="서울... ">
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            현장 지도<br>연결 링크
                                        </label>
                                        <div class="col-sm-9 form-control-static">
                                            <input type="text" class="form-control" placeholder="http://www....">
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

        })


        function refreshList(){
            $("#complexReqForm").attr("action", "/admin/complexes/list.do?grpId=${grpId}");
            $("#complexReqForm").submit();
        }

        function managersDetail(adminId, grpId){
            $("#manageReqForm > #adminId").val(adminId);
            $("#manageReqForm").attr("action", "/admin/managers/write.do?grpId=" + grpId);
            $("#manageReqForm").submit();
        }

        function managerAdd(grpId){
            $("#complexReqForm").attr("action", "/admin/complexes/write.do?create=true&grpId=" + grpId);
            $("#complexReqForm").submit();
        }

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>