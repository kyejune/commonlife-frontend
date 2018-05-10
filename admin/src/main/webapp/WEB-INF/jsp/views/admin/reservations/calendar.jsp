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

                            <form action="" class="form-inline form-group">
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
                                            <li><a href="#" data-value="reserved">전체목록</a></li>
                                            <li><a href="#" data-value="in_queue">대기목록</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-white">필터 적용</button>
                                </div>
                            </form>

                            <div id="calendar"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script>
            var events = [];
            <c:forEach var="reservation" items="${reservations}">
            events.push( {
                idx: '${reservation.idx}',
                title: '${reservation.scheme.title}',
                start: moment( '${reservation.startDt}' ).toDate(),
                end: moment( '${reservation.endDt}' ).toDate()
            } );
            </c:forEach>
            $( function() {
                $('#calendar').fullCalendar({
                    header: {
                        left: 'prev,next',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    },
                    events: events,
                    eventClick: function(calEvent, jsEvent, view) {
                        window.location.href = "/admin/reservations/show.do?idx=" + calEvent.idx;
                    }
                });
            } );

            $(function(){
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