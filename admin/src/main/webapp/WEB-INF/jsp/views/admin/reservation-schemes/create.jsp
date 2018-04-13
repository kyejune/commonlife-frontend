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
                            <h5>예약 틀 생성</h5>
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
                                    <label>소속 현장</label>
                                    <div>
                                        <c:forEach var="complex" items="${complexes}">
                                            <label class="radio-inline">
                                                <input type="radio" name="cmplxIdx" value="${complex.cmplxId}"
                                                       required <c:if test="${cmplxIdx == complex.cmplxId}"> checked </c:if> >
                                                    ${complex.cmplxNm}
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>접근 허용 현장</label>
                                    <div>
                                        <c:forEach var="complex" items="${complexes}">
                                            <label class="checkbox-inline">
                                                <input type="checkbox" name="allowCmplxIdxes[]" value="${complex.cmplxId}"
                                                       checked >
                                                    ${complex.cmplxNm}
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>
                                <c:if test="${parentIdx != 0}">
                                <div class="form-group">
                                    <label>예약 그룹</label>
                                    <div>
                                        <input type="hidden" name="parentIdx" value="${parentIdx}">
                                        <input type="text" class="form-control" readonly value="${group.title}">
                                    </div>
                                </div>
                                </c:if>
                                <div class="form-group">
                                    <label>이미지</label>
                                    <div class="form-group">
                                        <input type="file" id="image-selector" multiple accept="image/*">
                                    </div>
                                    <div id="thumbnails">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>예약 유형</label>
                                    <select name="reservationType" class="form-control">
                                        <option value="A">예약 A: 시간 단위 대여</option>
                                        <option value="B">예약 B: 일자 단위 대여</option>
                                        <option value="C">예약 C: 장기 대여</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>예약명</label>
                                    <input name="title" type="text" class="form-control" placeholder="예약명을 입력해주세요.">
                                </div>
                                <div class="form-group">
                                    <label>예약 개요</label>
                                    <input type="text" class="form-control" name="summary">
                                    <p class="text-success">* 목록에 표시되는 한줄짜리 예약 소개입니다.</p>
                                </div>
                                <div class="form-group">
                                    <label>예약 상세</label>
                                    <textarea name="description" rows="6" class="form-control"></textarea>
                                    <p class="text-success">* 예약 화면에서 표시되는 여러줄의 소개 내용입니다.</p>
                                </div>
                                <div class="form-group">
                                    <label>예약 가능일</label>
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            오늘로부터
                                        </span>
                                        <input type="number" name="activateDuration" class="form-control" value="7">
                                        <span class="input-group-addon">
                                            일 까지
                                        </span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>사용 포인트</label>
                                    <input type="number" name="point" class="form-control" value="0">
                                    <p class="text-success">* 정수로만 입력할 수 있습니다.</p>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-md-6 form-group">
                                        <label>개시일</label>
                                        <div class="input-group">
                                            <input type="text" name="startDt" class="form-control datepicker" data-format="YYYY-MM-DD">
                                            <span class="input-group-addon">일 부터</span>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-md-6 form-group">
                                        <label>마감일</label>
                                        <div class="input-group">
                                            <input type="text" name="endDt" class="form-control datepicker" data-format="YYYY-MM-DD">
                                            <span class="input-group-addon">일 까지 예약 목록에 표시됨</span>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <p class="text-success">* 설정된 날짜가 지나면 예약 목록상에서 사라집니다.</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-md-6 form-group">
                                        <label>여는 시각</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control datepicker" name="openHour" data-format="HH">
                                            <span class="input-group-addon">시 부터</span>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-md-6 form-group">
                                        <label>닫는 시각</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control datepicker" name="closeHour" data-format="HH">
                                            <span class="input-group-addon">시 까지 운영</span>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <p class="text-success">* 사용자는 이 시간대 사이에서만 예약을 신청할 수 있습니다.</p>
                                    </div>
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

                                <div class="form-group">
                                    <label>재고</label>
                                    <input type="number" name="inStock" class="form-control" value="0">
                                    <p class="text-success">* 0으로 설정하면 재고 체크 안함</p>
                                </div>

                                <div class="form-group">
                                    <label>최대 예약 수량</label>
                                    <input type="number" name="maxQty" class="form-control" value="0">
                                    <p class="text-success">* 0으로 설정하면 체크 안함</p>
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
                                    <label>포함사항</label>
                                    <textarea name="options" rows="3" class="form-control" placeholder="예) 세제, 비누, 수건 제공"></textarea>
                                </div>
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
                                <div class="form-group">
                                    <label>주의사항</label>
                                    <textarea name="precautions" rows="5" class="form-control" placeholder="예) 다음 분을 위해 종료 5분전에 정리를 부탁드립니다."></textarea>
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
        <script>
            $( function() {
                var HOST = 'http://localhost:8080';
                if( window.location.hostname !== 'localhost' ) {
                    HOST = 'https://clback.cyville.net';
                }

                $( '.datepicker' ).each( function( index, element ) {
                    var $element = $( element );
                    var params = {};
                    if( $element.data( 'format' ) ) {
                        params.format = $element.data( 'format' );
                    }
                    $element.datetimepicker( params );
                } );

                function createThumbnail( data ) {
                    var thumb = "<div class='thumbnail-viewer' style='background-image: url(" + ( HOST + "/imageStore/" + data.imageIdx ) + ");'>" +
                        "<button class='delete' type='button'>&times;</button>" +
                        "<input type='hidden' name='images[]' value='" + data.imageIdx + "'>" +
                        "</div>";
                    $( '#thumbnails' ).append( $( thumb ) )
                }

                function uploadImage( file ) {
                    var data = new FormData();
                    data.append( "file", file );
                    $.ajax( {
                        url: HOST + '/imageStore/resv',
                        type: 'POST',
                        data: data,
                        contentType: false,
                        processData: false
                    } )
                        .done( function( data ) {
                            createThumbnail( data );
                        } );
                }

                // 이미지 업로드
                $( '#image-selector' ).on( 'change', function( event ) {
                    _.each( event.currentTarget.files, function( file ) {
                        uploadImage( file );
                    } );
                } );

                // 이미지 삭제
                $( document ).on( 'click', '.thumbnail-viewer .delete', function( event ) {
                    $( event.currentTarget ).closest( '.thumbnail-viewer' ).remove();
                } );
            } );
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>