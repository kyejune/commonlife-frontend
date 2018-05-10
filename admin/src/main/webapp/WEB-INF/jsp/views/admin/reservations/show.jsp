<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 조회</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 조회</a>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>예약 정보</h5>
                        </div>
                        <div class="ibox-content">
                            <dl class="dl-horizontal">
                                <dt>예약명</dt>
                                <dd>${reservation.scheme.title}</dd>

                                <c:if test="${reservation.scheme.reservationType.equals( 'A' )}">
                                    <dt>예약일</dt>
                                    <dd>
                                        ${reservation.startDt}
                                    </dd>
                                    <c:if test="${reservation.scheme.useTime.equals( 'yes' )}">
                                        <dt>시간</dt>
                                        <dd>
                                            <fmt:parseDate var="startTime" value="${reservation.startTime}" pattern="HH:mm:ss" />
                                            <fmt:formatDate value="${startTime}" pattern="HH:mm"/>
                                            부터
                                            <fmt:parseDate var="endTime" value="${reservation.endTime}" pattern="HH:mm:ss" />
                                            <fmt:formatDate value="${endTime}" pattern="HH:mm"/>
                                            까지
                                        </dd>
                                    </c:if>
                                </c:if>
                                <c:if test="${reservation.scheme.reservationType.equals( 'B' )}">
                                    <dt>예약일</dt>
                                    <dd>
                                        ${reservation.startDt}
                                        부터
                                        ${reservation.endDt}
                                        까지
                                    </dd>
                                </c:if>

                                <c:if test="${reservation.option != null}">
                                    <dt>옵션</dt>
                                    <dd>
                                        ${reservation.option.name}
                                    </dd>
                                </c:if>

                                <c:if test="${reservation.scheme.useQty.equals( 'yes' )}">
                                    <dt>수량</dt>
                                    <dd>
                                        ${reservation.qty}
                                    </dd>
                                </c:if>

                                <c:if test="${reservation.userMemo != null}">
                                    <dt>${reservation.scheme.fieldLabel}</dt>
                                    <dd>
                                        ${reservation.userMemo}
                                    </dd>
                                </c:if>
                            </dl>
                        </div>
                        <div class="ibox-content">
                            <div>
                                <label>이 예약의 상태를 변경</label>
                            </div>
                            <div>
                                <div class="btn-group" id="status-group">
                                    <form action="/admin/reservations/edit-queue.do" method="post">
                                        <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                        <input type="hidden" name="idx" value="${reservation.idx}">
                                        <input type="hidden" name="status" value="${reservation.status}">
                                        <div class="btn-group">
                                            <button class="btn btn-white" data-value="IN_QUEUE" type="submit">예약 대기</button>
                                            <button class="btn btn-white" data-value="RESERVED" type="submit">예약 확정</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>예약자 정보</h5>
                        </div>
                        <div class="ibox-content">
                            <c:if test="${reservation.user != null}">
                            <dl class="dl-horizontal">
                                <dt>사용자 이름</dt>
                                <dd>${reservation.user.userNm}</dd>

                                <dt>사용자 아이디</dt>
                                <dd>${reservation.user.userId}</dd>

                                <dt>현장</dt>
                                <dd>${reservation.user.cmplxNm}</dd>

                                <dt>동</dt>
                                <dd>${reservation.user.dong}</dd>

                                <dt>호</dt>
                                <dd>${reservation.user.ho}</dd>

                                <dt>연락처</dt>
                                <dd>${reservation.user.cell}</dd>
                            </dl>
                            </c:if>
                            <c:if test="${reservation.user == null}">
                                사용자 정보가 없습니다.
                            </c:if>
                        </div>
                    </div>

                    <div class="ibox">
                        <div class="ibox-content">
                            <div class="row">
                                <div class="col-xs-6">
                                    <button class="btn btn-white" onclick="history.back()">뒤로가기</button>
                                </div>
                                <div class="col-xs-6 text-right">
                                    <form action="/admin/reservations/delete.do" method="post" onsubmit="return confirm( '정말 삭제하시겠습니까?' )">
                                        <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                        <input type="hidden" name="idx" value="${reservation.idx}">
                                        <input type="hidden" name="redirectTo" value="${ redirectTo }">
                                        <button class="btn btn-danger">이 예약을 삭제</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%--<div class="ibox float-e-margins">--%>
                        <%--<div class="ibox-title">--%>
                            <%--<h5>예약 목록</h5>--%>
                            <%--<div class="ibox-tools">--%>
                                <%--<a class="collapse-link">--%>
                                    <%--<i class="fa fa-chevron-up"></i>--%>
                                <%--</a>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="ibox-content" style="">--%>
                            <%--<h2>reservation</h2>--%>
                            <%--<c:if test="${not empty reservation['class'].declaredFields}">--%>
                                <%--<dl>--%>
                                    <%--<c:forEach var="field" items="${reservation['class'].declaredFields}">--%>
                                        <%--<c:catch>--%>
                                            <%--<dt>${field.name}</dt>--%>
                                            <%--<dd>${reservation[field.name]}</dd>--%>
                                        <%--</c:catch>--%>
                                    <%--</c:forEach>--%>
                                <%--</dl>--%>
                            <%--</c:if>--%>

                            <%--<h2>reservation.scheme</h2>--%>
                            <%--<c:if test="${not empty reservation.scheme['class'].declaredFields}">--%>
                                <%--<dl>--%>
                                    <%--<c:forEach var="field" items="${reservation.scheme['class'].declaredFields}">--%>
                                        <%--<c:catch>--%>
                                            <%--<dt>${field.name}</dt>--%>
                                            <%--<dd>${reservation.scheme[field.name]}</dd>--%>
                                        <%--</c:catch>--%>
                                    <%--</c:forEach>--%>
                                <%--</dl>--%>
                            <%--</c:if>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script>
            $(function(){
                $( 'input[name=status]' ).val();
                $( '#status-group button' ).each( function( index, element ) {
                    var $element = $( element );
                    $element.removeClass().addClass( 'btn' );
                    if( $element.data( 'value' ) === $( 'input[name=status]' ).val() ) {
                        $element.addClass( 'btn-primary' );
                    }
                    else {
                        $element.addClass( 'btn-white' );
                    }
                } );
                $( '#status-group button' ).on( 'mouseenter', function( event ) {
                    var $button = $( event.currentTarget );
                    $( 'input[name=status]' ).val( $button.data( 'value' ) );
                } );
            });
        </script>

    </tiles:putAttribute>
</tiles:insertDefinition>