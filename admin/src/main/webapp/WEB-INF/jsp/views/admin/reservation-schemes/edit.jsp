<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 틀 관리</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 틀 관리</a>
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
                            <h5>예약 틀 수정</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <form action="" method="post">
                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                <input type="hidden" name="redirectTo" value="${redirectTo}">
                                <div class="form-group">
                                    <label>현장</label>
                                    <div>
                                        <c:forEach var="complex" items="${complexes}">
                                            <c:if test="${scheme.cmplxIdx == complex.cmplxId}">
                                            <label class="radio-inline">
                                                <input type="radio" name="cmplxIdx" value="${complex.cmplxId}"
                                                       required  checked  >
                                                    ${complex.cmplxNm}
                                            </label>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>접근 허용 현장</label>
                                    <div>
                                        <c:forEach var="complex" items="${complexes}">
                                            <label class="checkbox-inline">
                                                <input type="checkbox" name="allowCmplxIdxes[]" value="${complex.cmplxId}"
                                                <c:if test="${cmplxIdx == complex.cmplxId}"> checked </c:if> >
                                                    ${complex.cmplxNm}
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>
                                <c:if test="${group != null}">
                                <div class="form-group">
                                    <label>예약 그룹</label>
                                    <div>
                                        <input type="hidden" name="parentIdx" value="${group.idx}">
                                        <input type="text" class="form-control" readonly value="${group.title}">
                                    </div>
                                </div>
                                </c:if>
                                <div class="form-group">
                                    <label>예약 유형</label>
                                    <select name="reservationType" class="form-control">
                                        <option value="A" <c:if test="${scheme.reservationType == 'A'}"></c:if> >예약 A: 시간 단위 대여</option>
                                        <option value="B" <c:if test="${scheme.reservationType == 'B'}"></c:if> >예약 B: 일자 단위 대여</option>
                                        <option value="C" <c:if test="${scheme.reservationType == 'C'}"></c:if> >예약 C: 장기 대여</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>예약명</label>
                                    <input name="title" type="text" class="form-control" placeholder="예약명을 입력해주세요." value="${scheme.title}">
                                </div>
                                <div class="form-group">
                                    <label>예약 개요</label>
                                    <textarea name="summary" rows="3" class="form-control">${scheme.summary}</textarea>
                                </div>
                                <div class="form-group">
                                    <label>예약 상세</label>
                                    <textarea name="description" rows="6" class="form-control">${scheme.description}</textarea>
                                </div>
                                <div class="form-group">
                                    <label>예약 가능일</label>
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            오늘로부터
                                        </span>
                                        <input type="number" name="activateDuration" class="form-control" value="${scheme.activateDuration}">
                                        <span class="input-group-addon">
                                            일 까지
                                        </span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>사용 포인트</label>
                                    <input type="number" name="point" class="form-control" value="${scheme.point}">
                                </div>
                                <div class="form-group">
                                    <label>금액</label>
                                    <input type="number" name="amount" class="form-control" value="${scheme.amount}">
                                    <p class="text-info">* 0으로 설정하면 금액 청구 비활성</p>
                                </div>
                                <div class="form-group">
                                    <label>예약 개시일</label>
                                    <input type="text" name="startDt" class="form-control datepicker" data-format="YYYY-MM-DD" value="${scheme.startDt}">
                                </div>
                                <div class="form-group">
                                    <label>예약 개시 시각</label>
                                    <input type="text" name="startTime" class="form-control datepicker" data-format="HH:mm" value="${scheme.startTime}">
                                </div>
                                <div class="form-group">
                                    <label>예약 마감일</label>
                                    <input type="text" name="endDt" class="form-control datepicker" data-format="YYYY-MM-DD" value="${scheme.endDt}">
                                </div>
                                <div class="form-group">
                                    <label>예약 마감 시각</label>
                                    <input type="text" name="endTime" class="form-control datepicker" data-format="HH:mm" value="${scheme.endTime}">
                                </div>
                                <div class="form-group">
                                    <label>주말 예약 가능</label>
                                    <div>
                                        <label class="radio-inline">
                                            <input type="radio" name="availableInWeekend" value="Y" checked>
                                            예
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="availableInWeekend" value="N">
                                            아니오
                                        </label>
                                    </div>
                                </div>

                                    <%--타입 A, B에서만 입력하는 필드들--%>
                                <div class="type-group type-group-a type-group-b">
                                    <div class="form-group">
                                        <label>재고</label>
                                        <input type="number" name="inStock" class="form-control" value="0">
                                        <p class="text-info">* 0으로 설정하면 재고 체크 안함</p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>최대 예약 수량</label>
                                    <input type="number" name="maxQty" class="form-control" value="0">
                                    <p class="text-info">* 0으로 설정하면 체크 안함</p>
                                </div>

                                <div class="form-group">
                                    <label>화면에 표시</label>
                                    <div>
                                        <label class="radio-inline">
                                            <input type="radio" name="delYn" value="N" checked>
                                            예
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="delYn" value="Y">
                                            아니오
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-group">
                                        <label>시설 옵션</label>
                                        <div>
                                            <c:forEach var="amenity" items="${amenities}">
                                                <label class="checkbox-inline">
                                                    <input type="checkbox" name="amenities[]" value="${amenity.idx}">
                                                    <span style="background: #666; padding: 2px; display: inline-block; margin-right: 0.5em;">
                                                        <img src="/admin/reservation-amenities/icon.do?idx=${amenity.iconIdx}" style="width: 18px;" alt="">
                                                    </span>
                                                        ${amenity.name}
                                                </label>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6">
                                        <button class="btn btn-primary">전송</button>
                                    </div>
                                    <div class="col-xs-6 text-right">
                                        <a href="/admin/reservations/create.do?groupIdx=${scheme.parentIdx}&schemeIdx=${scheme.idx}"
                                           class="btn btn-default">이 형식의 예약 추가</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script>
            $( function() {
                $( '.type-group' ).hide();
                $( '.type-group-a' ).show();

                $( 'select[name=reservationType]' ).on( 'change', function( event ) {
                    var $select = $( event.currentTarget );
                    var type = $select.val().toLowerCase();

                    $( '.type-group' ).hide();
                    $( '.type-group-' + type ).show();
                } );
            } );
        </script>
        <script>
            $( function() {
                $( '.datepicker' ).each( function( index, element ) {
                    var $element = $( element );
                    var params = {};
                    if( $element.data( 'format' ) ) {
                        params.format = $element.data( 'format' );
                    }
                    $element.datetimepicker( params );
                } );
            } );
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>