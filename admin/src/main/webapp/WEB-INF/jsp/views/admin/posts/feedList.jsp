<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="posts">
<tiles:putAttribute name="title">FEED 관리</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- DataTables -->
    <a><!-- --></a>
</tiles:putAttribute>
<tiles:putAttribute name="contents">
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
                    <a>사용자 FEED 관리</a>
                </li>
            </ol>
        </div>
        <div class="col-lg-2">
        </div>
    </div>

    <div class="wrapper wrapper-content animated fadeInRight">

        <%--<div class="row">--%>
            <%--<div class="col-lg-12">--%>
                <%--<div class="ibox float-e-margins">--%>
                    <%--<div class="ibox-title">--%>
                        <%--<h5>관리자 생성</h5>--%>
                        <%--<div class="ibox-tools">--%>
                            <%--<a class="collapse-link">--%>
                                <%--<i class="fa fa-chevron-up"></i>--%>
                            <%--</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="ibox-content" style="">--%>
                        <%--<form class="form-horizontal">--%>
                            <%--<div class="form-group">--%>
                                <%--<label class="col-sm-2 control-label">--%>
                                    <%--<button type="button"--%>
                                            <%--class="btn btn-primary b-r-lg"--%>
                                            <%--onclick="managerAdd('~~~~~')">--%>
                                        <%--슈퍼 관리자 생성</button>--%>
                                <%--</label>--%>
                                <%--<div class="col-sm-10"> 설명 작성 ... </div>--%>
                            <%--</div>--%>
                        <%--</form>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

        <%--<form:form name="manageReqForm" id="manageReqForm" method="post" commandName="adminInfo">--%>
            <%--<!--//paging-->--%>
            <%--<form:hidden path="pageIndex"/>--%>
            <%--<form:hidden path="adminId"/>--%>
        <%--</form:form>--%>

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
                                                        {내용 없음}
                                                    </c:when>
                                                    <c:when test="${fn:length(vo.content) > 30}" >
                                                        ${fn:substring(vo.content,0, 30)}...
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${vo.content}
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
                                        <%--<td class="center">--%>
                                                <%--<c:choose>--%>
                                                    <%--<c:when test="${adminConst.adminGrpSuper == vo.grpId}">--%>
                                                        <%--<span class="badge-primary">--%>
                                                                <%--${vo.grpNm}--%>
                                                        <%--</span>--%>
                                                    <%--</c:when>--%>
                                                    <%--<c:when test="${adminConst.adminGrpComplex == vo.grpId}">--%>
                                                        <%--<span class="badge-success">--%>
                                                                <%--${vo.grpNm}--%>
                                                        <%--</span>--%>
                                                    <%--</c:when>--%>
                                                    <%--<c:otherwise>--%>
                                                        <%--<span class="badge-plain">--%>
                                                            <%-----%>
                                                        <%--</span>--%>
                                                    <%--</c:otherwise>--%>
                                                <%--</c:choose>--%>
                                            <%--</td>--%>
                                            <%--</c:if>--%>
                                            <%--<td>--%>
                                                <%--<c:choose>--%>
                                                    <%--<c:when test="${grpId == -1}">--%>
                                                        <%--${vo.adminId}--%>
                                                    <%--</c:when>--%>
                                                    <%--<c:otherwise>--%>
                                                        <%--<a href="javascript:void(0)" onclick="managersDetail('${vo.adminId}', ${vo.grpId})">--%>
                                                            <%--${vo.adminId}--%>
                                                        <%--</a>--%>
                                                    <%--</c:otherwise>--%>
                                                <%--</c:choose>--%>
                                            <%--</td>--%>
                                            <%--<td class="center">${vo.adminNm}</td>--%>
                                            <%--<td class="center">${vo.adminEmail}</td>--%>
                                            <%--<c:if test="${adminConst.adminGrpComplex == grpId}">--%>
                                                <%--<td class="center">${vo.cmplxNm}</td>--%>
                                            <%--</c:if>--%>
                                            <%--<td class="center"><p>${vo.desc}</p><br/></td>--%>
                                            <%--<td class="center" >--%>
                                                <%--<c:choose>--%>
                                                    <%--<c:when test="${vo.useYn == 'Y'}">--%>
                                                        <%--<i class="fa fa-check-circle text-navy" alt="사용"></i>--%>
                                                    <%--</c:when>--%>
                                                    <%--<c:when test="${vo.useYn == 'N'}">--%>
                                                        <%--<i class="fa fa-times" alt="사용안함"></i>--%>
                                                    <%--</c:when>--%>
                                                    <%--<c:otherwise>--%>
                                                        <%-----%>
                                                    <%--</c:otherwise>--%>
                                                <%--</c:choose>--%>
                                            <%--</td>--%>
                                            <%--<td class="center" >--%>
                                                <%--<fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd" var="sysDt"/>--%>
                                                <%--<fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd"/>--%>
                                            <%--</td>--%>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <!-- paginging -->
                                    <tr>
                                        <td colspan="8">
                                            <%--<ul class="pagination"></ul>--%>
                                                <ui:pagination paginationInfo="${paginateInfo}" jsFunction="fn_link_page"/>
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
            $("#left_feed_feed").addClass("active");

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