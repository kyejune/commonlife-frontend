<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 현황</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 현황</a>
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
                            <h5>예약 목록</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <form action="" class="form-inline">
                                <div class="form-group">
                                    <c:if test="${adminInfo.cmplxId == 0}">
                                        <div class="btn-group" id="dropdown-complex">
                                            <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">지점선택 <span class="caret"></span></button>
                                            <input type="hidden" name="complexIdx" value="${complexIdx}">
                                            <ul class="dropdown-menu">
                                                <li><a href="#">지점선택</a></li>
                                                <c:forEach var="complex" items="${complexes}">
                                                    <li><a href="#complexIdx=${complex.cmplxId}" data-value="${complex.cmplxId}">${complex.cmplxNm}</a></li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </c:if>
                                    <c:if test="${adminInfo.cmplxId != 0}">
                                        <div class="btn-group" id="dropdown-complex">
                                            <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">지점선택 <span class="caret"></span></button>
                                            <input type="hidden" name="complexIdx" value="${complexIdx}">
                                            <ul class="dropdown-menu">
                                                <li><a href="#">지점선택</a></li>
                                                <c:forEach var="complex" items="${complexes}">
                                                    <c:if test="${adminInfo.cmplxId == complex.cmplxId}">
                                                        <li><a href="#complexIdx=${complex.cmplxId}" data-value="${complex.cmplxId}">${complex.cmplxNm}</a></li>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="form-group">
                                    <div class="btn-group" id="dropdown-group">
                                        <button data-toggle="dropdown" class="btn btn-success dropdown-toggle">그룹선택 <span class="caret"></span></button>
                                        <input type="hidden" name="groupIdx" value="${groupIdx}">
                                        <ul class="dropdown-menu">
                                            <li><a href="#" class="default-item">그룹선택</a></li>
                                            <c:forEach var="group" items="${groups}">
                                                <li><a href="#groupIdx=${group.idx}" data-complex="${group.cmplxIdx}" data-value="${group.idx}">${group.title}</a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="btn-group" id="dropdown-scheme">
                                        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle">예약 선택 <span class="caret"></span></button>
                                        <input type="hidden" name="schemeIdx" value="${schemeIdx}">
                                        <ul class="dropdown-menu">
                                            <c:forEach var="scheme" items="${schemes}">
                                                <li><a href="#schemeIdx=${scheme.idx}" data-complex="${scheme.cmplxIdx}" data-group="${scheme.parentIdx}" data-value="${scheme.idx}">${scheme.title}</a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="form-group pull-right">
                                    <div class="btn-group" id="dropdown-status">
                                        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle">전체목록 <span class="caret"></span></button>
                                        <input type="hidden" name="reservationStatus" value="${reservationStatus}">
                                        <ul class="dropdown-menu">
                                            <li><a href="#" data-value="">전체목록</a></li>
                                            <li><a href="#" data-value="IN_QUEUE">대기목록</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-white">필터 적용</button>
                                </div>
                            </form>

                            <table class="table" id="reservation-list">
                                <thead>
                                <tr>
                                    <th class="text-center">ID</th>
                                    <th class="text-center">신청회원</th>
                                    <th class="text-center">예약 지점</th>
                                    <th class="text-center">그룹</th>
                                    <th class="text-center">예약명</th>
                                    <th class="text-center">요청일</th>
                                    <th class="text-center">포인트</th>
                                    <th class="text-center">금액</th>
                                    <th class="text-center">상태</th>
                                    <th class="text-center">보기</th>
                                    <th class="text-center">삭제</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="reservation" items="${reservations}">
                                    <tr>
                                        <td class="text-center">${reservation.idx}</td>
                                        <td class="text-center">
                                            <a href="/admin/users/userList.do" class="btn btn-xs btn-success">
                                                <c:out value="${reservation.user.userNm}" default="- 정보 없음 -"/>
                                            </a>
                                        </td>
                                        <td class="text-center">${reservation.scheme.complex.cmplxNm}</td>
                                        <td class="text-center">${reservation.scheme.group.title}</td>
                                        <td class="text-center">${reservation.scheme.title}</td>
                                        <td class="text-center">
                                            <fmt:formatDate value="${reservation.regDttm}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td class="text-center">${reservation.point}</td>
                                        <td class="text-center">${reservation.amount}</td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${reservation.status.equals( 'RESERVED' )}">
                                                    예약확정
                                                </c:when>
                                                <c:when test="${reservation.status.equals( 'IN_QUEUE' )}">
                                                    예약대기
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <a href="/admin/reservations/show.do?idx=${reservation.idx}" class="btn btn-xs btn-info">예약 내역 보기</a>
                                        </td>
                                        <td class="text-center">
                                            <form action="/admin/reservations/delete.do" method="post" onsubmit="return confirm( '정말 삭제하시겠습니까?' )">
                                                <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
                                                <input type="hidden" name="idx" value="${reservation.idx}">
                                                <button class="btn btn-xs btn-danger">삭제</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script>

            $(function(){
                $('#reservation-list').footable();

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

                $(".dropdown-menu li a").on( 'click', function( event ){
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
            });
        </script>

    </tiles:putAttribute>
</tiles:insertDefinition>