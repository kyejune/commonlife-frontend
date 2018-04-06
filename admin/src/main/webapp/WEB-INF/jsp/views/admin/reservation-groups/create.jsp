<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 그룹 관리</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 그룹 관리</a>
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
                            <h5>예약 그룹 생성</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <form action="" method="post">
                                <div class="form-group">
                                    <label>현장 선택</label>
                                    <div>
                                    <c:forEach var="complex" items="${complexes}">
                                        <label class="radio-inline">
                                            <input type="radio" name="cmplxIdx" value="${complex.cmplxId}" required>
                                            ${complex.cmplxNm}
                                        </label>
                                    </c:forEach>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>아이콘</label>
                                    <select name="icon" class="form-control">
                                        <option value="HOME">HOME</option>
                                        <option value="TOOL">TOOL</option>
                                        <option value="STORE">STORE</option>
                                        <option value="ETC">ETC</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>그룹명</label>
                                    <input type="text" class="form-control" name="title" required>
                                </div>
                                <div class="form-group">
                                    <label>한줄설명</label>
                                    <input type="text" class="form-control" name="summary" required>
                                </div>
                                <div class="form-group">
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
    </tiles:putAttribute>
</tiles:insertDefinition>