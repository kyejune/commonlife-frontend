<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
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
                                    <c:if test="${cmplxIdx == complex.cmplxId}">
                                    <label class="radio-inline">
                                        <input type="radio" name="cmplxIdx" value="${complex.cmplxId}"
                                               required <c:if test="${cmplxIdx == complex.cmplxId}"> checked </c:if> >
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
                            <label>아이콘</label>
                            <select name="icon" class="form-control">
                                <option value="CLEANING" <c:if test="${scheme.icon == 'CLEANING'}"> selected </c:if> >CLEANING</option>
                                <option value="LAUNDRY" <c:if test="${scheme.icon == 'LAUNDRY'}"> selected </c:if> >LAUNDRY</option>
                                <option value="FOOD" <c:if test="${scheme.icon == 'FOOD'}"> selected </c:if> >FOOD</option>
                                <option value="CARWASH" <c:if test="${scheme.icon == 'CARWASH'}"> selected </c:if> >CARWASH</option>
                            </select>
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
                                            <button class="delete">&times;</button>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>예약 유형</label>
                            <div>
                                <label class="radio-inline">
                                    <input type="radio" name="reservationType" value="A" <c:if test="${scheme.reservationType == 'A'}"> checked </c:if> <c:if test="${scheme == null}"> checked </c:if> >
                                    A타입 (날짜+시간)
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="reservationType" value="B" <c:if test="${scheme.reservationType == 'B'}"> checked </c:if> >
                                    B타입 (기간+옵션)
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="reservationType" value="C" <c:if test="${scheme.reservationType == 'C'}"> checked </c:if> >
                                    C타입 (날짜)
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="reservationType" value="D" <c:if test="${scheme.reservationType == 'D'}"> checked </c:if> >
                                    D타입 (날짜+사용자 입력 필드)
                                </label>
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
                            <label>예약 가능일</label>
                            <div class="input-group">
                                <span class="input-group-addon">
                                    오늘로부터
                                </span>
                                <input type="number" name="activateDuration" class="form-control" value="<c:out default="7" value="${activateDuration.startDt}"/>">
                                <span class="input-group-addon">
                                    일 까지
                                </span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label>사용 포인트</label>
                            <input type="number" name="point" class="form-control" value="<c:out default="0" value="${scheme.point}"/>">
                            <p class="text-success">* 정수로만 입력할 수 있습니다.</p>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-md-6 form-group">
                                <label>개시일</label>
                                <div class="input-group">
                                    <input type="text" name="startDt" class="form-control datepicker" data-format="YYYY-MM-DD" value="<c:out default="" value="${scheme.startDt}"/>">
                                    <span class="input-group-addon">일 부터</span>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-6 form-group">
                                <label>마감일</label>
                                <div class="input-group">
                                    <input type="text" name="endDt" class="form-control datepicker" data-format="YYYY-MM-DD" value="<c:out default="" value="${scheme.endDt}"/>">
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
                                    <input type="text" class="form-control datepicker" name="openTime" data-format="HH" value="<c:out default="" value="${scheme.openTime}"/>">
                                    <span class="input-group-addon">시 부터</span>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-6 form-group">
                                <label>닫는 시각</label>
                                <div class="input-group">
                                    <input type="text" class="form-control datepicker" name="closeTime" data-format="HH" value="<c:out default="" value="${scheme.closeTime}"/>">
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
                            <input type="number" name="inStock" class="form-control" value="<c:out default="0" value="${scheme.inStock}"/>">
                            <p class="text-success">* 0으로 설정하면 재고 체크 안함</p>
                        </div>

                        <div class="form-group">
                            <label>최대 예약 수량</label>
                            <input type="number" name="maxQty" class="form-control" value="<c:out default="0" value="${scheme.maxQty}"/>">
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
                            <textarea name="options" rows="3" class="form-control" placeholder="예) 세제, 비누, 수건 제공"><c:out default="" value="${scheme.options}"/></textarea>
                        </div>
                        <div class="form-group">
                            <label>시설 옵션</label>
                            <div>
                                <c:forEach var="amenity" items="${amenities}">
                                    <label class="checkbox-inline">
                                        <input type="checkbox" name="amenities[]" value="${amenity.idx}"
                                        <c:forEach var="e" items="${scheme.amenities}">
                                            <c:if test="${e.idx == amenity.idx}"> checked </c:if>
                                        </c:forEach>
                                        >
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
                            <textarea name="precautions" rows="5" class="form-control" placeholder="예) 다음 분을 위해 종료 5분전에 정리를 부탁드립니다."><c:out default="" value="${scheme.precautions}"/></textarea>
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