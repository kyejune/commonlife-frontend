<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 관리</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 관리</a>
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
                            <h5>예약 생성</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <form action="" method="post">
                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                <div class="form-group">
                                    <label>예약명</label>
                                    <select name="parentIdx" class="form-control">
                                        <c:forEach var="scheme" items="${schemes}">
                                            <option value="${scheme.idx}">[${scheme.reservationType}타입] ${scheme.title}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>예약 상태</label>
                                    <select name="status" class="form-control">
                                        <option value="RESERVED">예약</option>
                                        <option value="PENDING">대기중</option>
                                        <option value="USER_CANCELED">사용자가 취소함</option>
                                        <option value="ADMIN_CANCELED">관리자가 취소함</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>예약일</label>
                                    <input type="text" name="startDt" class="form-control datepicker" data-format="YYYY-MM-DD">
                                </div>
                                <div class="form-group">
                                    <label>시작 시각</label>
                                    <input type="text" name="startTime" class="form-control datepicker" data-format="HH:ss">
                                </div>
                                <div class="form-group">
                                    <label>예약 종료일</label>
                                    <input type="text" name="endDt" class="form-control datepicker" data-format="YYYY-MM-DD">
                                </div>
                                <div class="form-group">
                                    <label>종료 시각</label>
                                    <input type="text" name="endTime" class="form-control datepicker" data-format="HH:ss">
                                </div>
                                <div class="form-group">
                                    <label>예약 수량</label>
                                    <input type="number" name="qty" class="form-control">
                                </div>
                                <div>
                                    <button class="btn btn-primary">전송</button>
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