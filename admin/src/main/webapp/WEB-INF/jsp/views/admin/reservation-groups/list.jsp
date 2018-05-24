<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 리스트</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 리스트</a>
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
                            <h5>예약 그룹 목록</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <form action="" class="form-inline cl-filter-group">
                                <div class="form-group">
                                    <div class="btn-group" id="dropdown-complex">
                                        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">지점선택 <span class="caret"></span></button>
                                        <input type="hidden" name="complexIdx" value="${complexIdx}">
                                        <ul class="dropdown-menu">
                                            <li><a href="#">지점선택</a></li>
                                            <c:forEach var="complex" items="${complexes}">
                                                <li><a href="/admin/reservation-groups/list.do?complexIdx=${complex.cmplxId}" data-value="${complex.cmplxId}">${complex.cmplxNm}</a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-white">필터 적용</button>
                                </div>
                                <div class="form-group pull-right">
                                    <a href="/admin/reservation-groups/create.do" class="btn btn-info">새 그룹 생성</a>
                                    <a href="/admin/reservation-schemes/create.do" class="btn btn-warning">새로운 예약 생성</a>
                                </div>
                            </form>
                            <table class="table cl-reservation-table">
                                <colgroup>
                                    <col>
                                    <col style="width: 180px;">
                                    <col style="width: 60px;">
                                    <col style="width: 200px;">
                                    <col>
                                    <col>
                                    <col>
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th class="text-center"> </th>
                                    <th>그룹명</th>
                                    <th class="text-center">아이콘</th>
                                    <th class="text-center">현장</th>
                                    <th>설명</th>
                                    <th class="text-center">
                                        제공 서비스
                                    </th>
                                    <th class="text-center">수정</th>
                                    <th class="text-center">삭제</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="group" items="${groups}">
                                    <tr>
                                        <td class="text-center" style="vertical-align: middle;">
                                            <span class="label label-primary">Active</span>
                                        </td>
                                        <td class="">
                                            <strong style="font-size: 1.4em;">${group.title}</strong>
                                            <br>
                                            <small>
                                                Created
                                                <fmt:formatDate value="${group.regDttm}" pattern="yyyy.MM.dd"/>
                                            </small>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${group.icon == 'HOME'}">
                                                    <img src="/resources/img/reservations/r-icon-1-3x.png" style="width: 36px;" alt="HOME">
                                                </c:when>
                                                <c:when test="${group.icon == 'TOOL'}">
                                                    <img src="/resources/img/reservations/r-icon-2-3x.png" style="width: 36px;" alt="TOOL">
                                                </c:when>
                                                <c:when test="${group.icon == 'STORE'}">
                                                    <img src="/resources/img/reservations/r-icon-3-3x.png" style="width: 36px;" alt="STORE">
                                                </c:when>
                                                <c:when test="${group.icon == 'ETC'}">
                                                    <img src="/resources/img/reservations/r-icon-4-3x.png" style="width: 36px;" alt="ETC">
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">${group.cmplxNm}</td>
                                        <td>${group.summary}</td>
                                        <td class="text-center">
                                            <div class="btn-group cl-res-group__res-scheme-select">
                                                <button data-toggle="dropdown" class="btn btn-white btn-xs dropdown-toggle">서비스 <span class="length"></span> <span class="caret"></span></button>
                                                <ul class="dropdown-menu">
                                                    <c:forEach var="scheme" items="${allSchemes}">
                                                        <c:if test="${group.idx == scheme.parentIdx}">
                                                            <li data-value="${scheme.idx}"><a href="/admin/reservation-schemes/edit.do?idx=${scheme.idx}&cmplxIdx=${group.cmplxIdx}">${scheme.title}</a></li>
                                                        </c:if>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </td>
                                        <td class="text-center">
                                            <a href="/admin/reservation-groups/edit.do?idx=${group.idx}" class="btn btn-xs btn-white">
                                                <i class="fa fa-pencil"></i> 수정
                                            </a>
                                        </td>
                                        <td class="text-center">
                                            <form action="/admin/reservation-groups/delete.do?idx=${group.idx}" method="post">
                                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                                <button class="btn btn-xs btn-danger">
                                                    <i class="fa fa-trash"></i>
                                                    삭제
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>예약 목록</h5>
                        </div>
                        <div class="ibox-content">
                            <c:if test="${complexIdx == null || complexIdx.equals( '' )}">
                                <div class="text-center">
                                    현장을 선택하시면 예약 목록이 표시됩니다.
                                </div>
                            </c:if>
                            <c:if test="${complexIdx != null && !complexIdx.equals( '' )}">
                                <table class="table cl-reservation-table">
                                    <colgroup>
                                        <col>
                                        <col style="width: 180px;">
                                        <col style="width: 60px;">
                                        <col style="width: 200px;">
                                        <col>
                                        <col>
                                        <col>
                                        <col>
                                        <col>
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th class="text-center"> </th>
                                        <th>예약명</th>
                                        <th class="text-center">아이콘</th>
                                        <th class="text-center">현장</th>
                                        <th>예약 개요</th>
                                        <th class="text-center">예약 개시</th>
                                        <th class="text-center">예약 마감</th>
                                        <th class="text-center">보기/수정</th>
                                        <th class="text-center">삭제</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="scheme" items="${schemes}">
                                        <tr>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${ scheme.activate == 'yes' }">
                                                        <span class="label label-primary">Active</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="label label-mute">Unactive</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <span style="font-size: 1.4em;">${scheme.title}</span>
                                                <br>
                                                <small>
                                                    Created
                                                    <fmt:formatDate value="${scheme.regDttm}" pattern="yyyy.MM.dd"/>
                                                </small>
                                            </td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${scheme.icon =='CLEANING'}">
                                                        <img src="/resources/img/reservations/rs-icon-2-3x.png" style="width: 36px;" alt="CLEANING">
                                                    </c:when>
                                                    <c:when test="${scheme.icon =='LAUNDRY'}">
                                                        <img src="/resources/img/reservations/rs-icon-1-3x.png" style="width: 36px;" alt="LAUNDRY">
                                                    </c:when>
                                                    <c:when test="${scheme.icon =='FOOD'}">
                                                        <img src="/resources/img/reservations/rs-icon-3-3x.png" style="width: 36px;" alt="FOOD">
                                                    </c:when>
                                                    <c:when test="${scheme.icon =='CARWASH'}">
                                                        <img src="/resources/img/reservations/rs-icon-4-3x.png" style="width: 36px;" alt="CARWASH">
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                            <td class="text-center">
                                                ${selectedComplex.cmplxNm}
                                            </td>
                                            <td>${scheme.summary}</td>
                                            <td class="text-center">
                                                ${scheme.startDt}
                                            </td>
                                            <td class="text-center">
                                                ${scheme.endDt}
                                            </td>
                                            <td class="text-center">
                                                <a href="/admin/reservation-schemes/edit.do?idx=${scheme.idx}&cmplxIdx=${complexIdx}" class="btn btn-xs btn-white">
                                                    <i class="fa fa-pencil"></i> 수정
                                                </a>
                                            </td>
                                            <td class="text-center">
                                                <form action="/admin/reservation-schemes/delete.do" method="post">
                                                    <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                                    <input type="hidden" name="idx" value="${scheme.idx}">
                                                    <button class="btn btn-xs btn-danger">
                                                        <i class="fa fa-trash"></i> 삭제
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <%--<div class="text-right">--%>
                                    <%--<a href="/admin/reservation-schemes/create.do?cmplxIdx=${cmplxIdx}&parentIdx=0" class="btn btn-white">빠른 예약 추가</a>--%>
                                <%--</div>--%>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
    <style type="text/css">
        .cl-reservation-table th,
        .cl-reservation-table td {
            vertical-align: middle !important;
        }
    </style>
    <script>
        $( function() {
            // 드롭다운 설정
            $( '#dropdown-group li a' ).hide();
            $( '#dropdown-scheme li a' ).hide();

            function renderSchemeItems() {
                var complexIdx = $( 'input[name=complexIdx]' ).val();
                var groupIdx = $( 'input[name=groupIdx]' ).val();
                $( '#dropdown-scheme li a' ).hide();
                if( !complexIdx && !groupIdx ) {
                    return false;
                }
                $( '#dropdown-scheme li a' ).each( function ( idnex, element ) {
                    var $element = $( element );
                    if( groupIdx ) {
                        if( $element.data( 'complex' ) == complexIdx && $element.data( 'group' ) == groupIdx ) {
                            $element.show();
                        }
                    }
                    else {
                        if( $element.data( 'complex' ) == complexIdx ) {
                            $element.show();
                        }
                    }

                })
            }

            $(".cl-filter-group .dropdown-menu li a").on( 'click', function( event ){
                event.preventDefault();

                var item = $( event.currentTarget );
                item.parents(".btn-group").find('.btn').html( item.text() + ' <span class="caret"></span>' );
                item.parents(".btn-group").find('input').val( item.data('value') );

                var complexIdx = $( 'input[name=complexIdx]' ).val();

                if( complexIdx ) {
                    $( '#dropdown-group li a' ).hide();
                    $( '#dropdown-group li a[data-complex=' + complexIdx + ']' ).show();
                    $( '#dropdown-group li a.default-item' ).show();
                }

                renderSchemeItems();
            });

            if( $( 'input[name=complexIdx]' ).val() ) {
                $("#dropdown-complex li a[data-value=" + $( 'input[name=complexIdx]' ).val() + "]" ).trigger( 'click' );
            }

            if( $( 'input[name=groupIdx]' ).val() ) {
                $("#dropdown-group li a[data-value=" + $( 'input[name=groupIdx]' ).val() + "]" ).trigger( 'click' );
            }

            if( $( 'input[name=schemeIdx]' ).val() ) {
                $("#dropdown-scheme li a[data-value=" + $( 'input[name=schemeIdx]' ).val() + "]" ).trigger( 'click' );
            }

            if( $( 'input[name=reservationStatus]' ).val() ) {
                $("#dropdown-status li a[data-value=" + $( 'input[name=reservationStatus]' ).val() + "]" ).trigger( 'click' );
            }

            // 서비스 드롭박스 카운트
            $( '.cl-res-group__res-scheme-select' ).each( function( index, element ) {
                var $element = $( element );
                var lengthHolder = $element.find( 'span.length' );
                lengthHolder.text( $element.find( 'li' ).length );
            } );
        } );
    </script>
    </tiles:putAttribute>
</tiles:insertDefinition>