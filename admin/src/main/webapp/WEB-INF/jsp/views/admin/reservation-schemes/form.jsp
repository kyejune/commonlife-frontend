<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
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
    <form action="" method="post">
        <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" >
        <input type="hidden" name="redirectTo" value="${redirectTo}">

        <div class="row">
            <div class="col-lg-12">

                <%--신규 작성일 경우에만 사전 설정을 불러올 수 있다.--%>
                <c:if test="${scheme == null}">
                <div class="ibox">
                    <div class="ibox-title">
                        <h5>사전 설정 불러오기</h5>
                    </div>
                    <div class="ibox-content">
                        <select class="form-control">
                            <option value="">등록된 사전 설정이 없습니다.</option>
                        </select>
                    </div>
                </div>
                </c:if>

                <div class="ibox">
                    <div class="ibox-title">
                        <h5>1. 기본정보 설정</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <%--좌측 섹션--%>
                            <div class="col-md-7">
                                <div class="form-group">
                                    <label>지점 설정</label>
                                    <select name="cmplxIdx" class="form-control">
                                    <c:forEach var="complex" items="${complexes}">
                                        <option value="${complex.cmplxId}" <c:if test="${cmplxIdx == complex.cmplxId}"> selected </c:if> >${complex.cmplxNm}</option>
                                    </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>예약 그룹</label>
                                    <c:if test="${parentIdx != 0}">
                                        <div>
                                            <input type="hidden" name="parentIdx" value="${parentIdx}">
                                            <input type="text" class="form-control" readonly value="${group.title}">
                                        </div>
                                    </c:if>
                                    <c:if test="${parentIdx == 0}">
                                        <div>
                                            <input type="text" class="form-control" readonly value="그룹 없음">
                                        </div>
                                    </c:if>
                                </div>
                                <div class="form-group">
                                    <label>접근 허용 현장</label>
                                    <div>
                                        <select name="allowCmplxIdxes[]" multiple data-placeholder="선택해주세요" class="chosen-select" tabindex="2">
                                            <c:forEach var="complex" items="${complexes}">
                                            <option value="${complex.cmplxId}" selected>${complex.cmplxNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>예약명</label>
                                    <input name="title" type="text" class="form-control" placeholder="예약명을 입력해주세요." value="${scheme.title}">
                                </div>
                                <div class="form-group">
                                    <label>예약 개요</label>
                                    <input type="text" class="form-control" name="summary" value="${scheme.summary}">
                                    <p class="text-success">* 목록에 표시되는 한줄짜리 예약 소개입니다.</p>
                                </div>
                                <div class="form-group">
                                    <label>예약 상세</label>
                                    <textarea name="description" rows="6" class="form-control">${scheme.description}</textarea>
                                    <p class="text-success">* 예약 화면에서 표시되는 여러줄의 소개 내용입니다.</p>
                                </div>
                                <div class="form-group">
                                    <label>사용 크레딧</label>
                                    <input type="number" name="point" class="form-control" value="<c:out default="0" value="${scheme.point}"/>">
                                    <p class="text-success">* 정수로만 입력할 수 있습니다.</p>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-md-9 form-group">
                                        <label>예약 가능일 설정 (예약시작일 to 종료일)</label>
                                        <div class="input-group">
                                            <input type="text" name="startDt" class="form-control datepicker" data-format="YYYY-MM-DD" value="<c:out default="" value="${scheme.startDt}"/>">
                                            <span class="input-group-addon">to</span>
                                            <input type="text" name="endDt" class="form-control datepicker" data-format="YYYY-MM-DD" value="<c:out default="" value="${scheme.endDt}"/>">
                                        </div>
                                        <p class="text-success">* 설정된 날짜가 지나면 예약 목록상에서 사라집니다.</p>
                                    </div>
                                    <div class="col-xs-12 col-md-3 form-group">
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
                                </div>
                            </div>
                            <%--좌측 세션 끝--%>

                            <%--우측 섹션--%>
                            <div class="col-md-5">
                                <div class="form-group">
                                    <label>아이콘 설정</label>

                                    <div class="row">
                                        <div class="col-xs-3 col-md-3 text-center">
                                            <label>
                                                <img src="/resources/img/reservations/rs-icon-2-3x.png" style="max-width: 100%;" alt="">
                                                <div>CLEANING</div>
                                                <div>
                                                    <input type="radio" name="icon" <c:if test="${scheme.icon == 'CLEANING'}"> checked </c:if> >
                                                </div>
                                            </label>
                                        </div>
                                        <div class="col-xs-3 col-md-3 text-center">
                                            <label>
                                                <img src="/resources/img/reservations/rs-icon-1-3x.png" style="max-width: 100%;" alt="">
                                                <div>LAUNDRY</div>
                                                <div>
                                                    <input type="radio" name="icon" <c:if test="${scheme.icon == 'LAUNDRY'}"> checked </c:if> >
                                                </div>
                                            </label>
                                        </div>
                                        <div class="col-xs-3 col-md-3 text-center">
                                            <label>
                                                <img src="/resources/img/reservations/rs-icon-3-3x.png" style="max-width: 100%;" alt="">
                                                <div>FOOD</div>
                                                <div>
                                                    <input type="radio" name="icon" <c:if test="${scheme.icon == 'FOOD'}"> checked </c:if> >
                                                </div>
                                            </label>
                                        </div>
                                        <div class="col-xs-3 col-md-3 text-center">
                                            <label>
                                                <img src="/resources/img/reservations/rs-icon-4-3x.png" style="max-width: 100%;" alt="">
                                                <div>CARWASH</div>
                                                <div>
                                                    <input type="radio" name="icon" <c:if test="${scheme.icon == 'CARWASH'}"> checked </c:if> >
                                                </div>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>이미지</label>
                                    <div class="form-group">
                                        <input type="file" id="image-selector" multiple accept="image/*">
                                    </div>
                                    <div id="thumbnails">
                                        <c:if test="${scheme.images != null}">
                                            <c:forEach var="image" items="${scheme.images.split(',')}">
                                                <div class="thumbnail-viewer" data-image="/imageStore/${image}">
                                                    <input type="hidden" name="images[]" value="${image}">
                                                    <button type="button" class="delete">&times;</button>
                                                </div>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>이 예약을 활성화</label>
                                    <div class="text-right">
                                        <input type="checkbox" class="js-switch" checked />
                                    </div>
                                </div>
                            </div>
                            <%--우측 섹션 끝--%>
                        </div>
                    </div>
                </div>

                <div class="ibox">
                    <div class="ibox-title">
                        <h5>2. 예약 속성 설정</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="tabs-container">
                            <ul class="nav nav-tabs">
                                <li class="active"><a data-toggle="tab" href="#tab-1">A. 일자 + 시간</a></li>
                                <li class=""><a data-toggle="tab" href="#tab-2">B. 기간</a></li>
                            </ul>
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-xs-12 col-md-2 col-md-push-10 form-group">
                                                <label>시간 사용 여부</label>
                                                <div>
                                                    <input type="checkbox" class="js-switch" checked />
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-md-10 col-md-pull-2 form-group">
                                                <p class="text-success">
                                                    * 특정 일자(1일 단위)와 시간 선택이 가능한 예약을 생성합니다.
                                                </p>
                                                <div class="row">
                                                    <div class="col-xs-12 col-md-6 form-group">
                                                        <div class="input-group">
                                                            <span class="input-group-addon">시작시간</span>
                                                            <input type="text" class="form-control datepicker" name="openTime" data-format="HH" value="<c:out default="" value="${scheme.openTime}"/>">
                                                            <span class="input-group-addon">시</span>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-md-6 form-group">
                                                        <div class="input-group">
                                                            <span class="input-group-addon">종료시간</span>
                                                            <input type="text" class="form-control datepicker" name="closeTime" data-format="HH" value="<c:out default="" value="${scheme.closeTime}"/>">
                                                            <span class="input-group-addon">시</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-xs-6">
                                                        <div>
                                                            <label>예약  가능 최대 시간</label>
                                                        </div>
                                                        <input class="touchspin" type="text" value="" name="demo1">
                                                        <p class="text-success">
                                                            * 최소 1시간 단위로 설정
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="tab-2" class="tab-pane">
                                    <div class="panel-body">
                                        <p class="text-success">
                                            * 특정 기간 선택이 가능한 예약을 생성합니다.
                                        </p>
                                        <div class="row">
                                            <div class="col-xs-12 col-md-2 col-md-push-10 form-group">
                                                <label>대기 가능 여부</label>
                                                <div>
                                                    <input type="checkbox" class="js-switch" checked />
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-md-10 col-md-pull-2 form-group">
                                                <div class="row">
                                                    <div class="col-xs-12 col-md-8 form-group">
                                                        <div>
                                                            <label>예약  가능 최대일</label>
                                                        </div>
                                                        <input class="touchspin" type="text" value="" name="demo1">
                                                        <p class="text-success">
                                                            * 최소 1시간 단위로 설정
                                                        </p>
                                                    </div>
                                                    <div class="col-xs-12 col-md-8 form-group">
                                                        <label>즉시 예약 가능 여부</label>
                                                        <div class="form-group">
                                                            <label class="radio-inline">
                                                                <input type="radio">
                                                                예
                                                            </label>
                                                            <label class="radio-inline">
                                                                <input type="radio">
                                                                아니오
                                                            </label>
                                                        </div>
                                                        <div class="input-group">
                                                            <span class="input-group-addon">현재일보다</span>
                                                            <input type="text" class="form-control">
                                                            <span class="input-group-addon">일 이후부터 예약 신청이 가능합니다.</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="ibox">
                    <div class="ibox-title">
                        <h5>3. 기타 기능 설정</h5>
                    </div>
                    <div class="ibox-content">

                        <div class="row">
                            <div class="col-xs-4 col-md-3">
                                재고 사용

                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" class="onoffswitch-checkbox" id="qty-toggle">
                                        <label class="onoffswitch-label" for="qty-toggle">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-4 col-md-3">
                                옵션 사용(List 선택형)

                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" class="onoffswitch-checkbox" id="options-toggle">
                                        <label class="onoffswitch-label" for="options-toggle">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-4 col-md-3">
                                TEXT 입력필드 사용

                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" class="onoffswitch-checkbox" id="field-toggle">
                                        <label class="onoffswitch-label" for="field-toggle">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="ibox-content" id="qty-section">
                        <label>재고 사용</label>
                        <div class="row">
                            <div class="col-xs-12 col-md-6 form-group">
                                <span>전체 재고 수</span>
                                <input class="touchspin" type="text" value="" name="">
                            </div>
                            <div class="col-xs-12 col-md-6 form-group">
                                <span>예약 가능 최대 수량</span>
                                <input class="touchspin" type="text" value="" name="">
                            </div>
                        </div>
                    </div>
                    <div class="ibox-content" id="options-section">
                        <label>옵션 사용(List 선택형)</label>
                        <div class="row">
                            <div class="col-xs-12 col-md-1 form-group" style="padding-top: 0.5em;">
                                옵션:
                            </div>
                            <div class="col-xs-12 col-md-11 form-group">
                                <div class="input-group form-group">
                                    <input type="text" class="form-control" placeholder="옵션명을 입력하세요" id="option-input">
                                    <div class="input-group-btn">
                                        <button class="btn btn-white" id="add-option-button" type="button">추가</button>
                                    </div>
                                </div>

                                <ul class="list-group" id="options-list">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="ibox-content" id="field-section">
                        <label>TEXT 입력필드 사용</label>
                        <input type="text" class="form-control" placeholder="필드명을 입력하세요. 예) 주차예약시 차량번호">
                    </div>
                </div>

                <div class="ibox">
                    <div class="ibox-title">
                        <h5>4. 기타 정보 입력</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group">
                            <label>포함사항</label>
                            <textarea name="options" rows="3" class="form-control" placeholder="예) 세제, 비누, 수건 제공"><c:out default="" value="${scheme.options}"/></textarea>
                        </div>
                        <div class="form-group">
                            <label>시설 옵션</label>
                            <div class="scheme-form__amenities">
                                <select name="amenities[]" multiple data-placeholder="선택해주세요" class="chosen-select">
                                    <c:forEach var="amenity" items="${amenities}">
                                        <option value="${amenity.idx}"
                                                data-img-src="/admin/reservation-amenities/icon.do?idx=${amenity.iconIdx}"
                                                <c:forEach var="e" items="${scheme.amenities}">
                                                    <c:if test="${e.idx == amenity.idx}"> selected </c:if>
                                                </c:forEach>
                                        >${amenity.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>주의사항</label>
                            <textarea name="precautions" rows="5" class="form-control" placeholder="예) 다음 분을 위해 종료 5분전에 정리를 부탁드립니다."><c:out default="" value="${scheme.precautions}"/></textarea>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block">전송</button>
                </div>

            </div>
        </div>
    </form>
</div>