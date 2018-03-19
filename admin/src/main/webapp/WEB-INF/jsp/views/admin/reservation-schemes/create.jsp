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
                                <div class="form-group">
                                    <label>예약 유형</label>
                                    <select name="type" class="form-control">
                                        <option value="A">예약 A</option>
                                        <option value="B">예약 B</option>
                                        <option value="C">예약 C</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>예약명</label>
                                    <input name="title" type="text" class="form-control" placeholder="예약명을 입력해주세요.">
                                </div>
                                <div class="form-group">
                                    <label>예약 개요</label>
                                    <textarea name="summary" rows="3" class="form-control"></textarea>
                                </div>
                                <div class="form-group">
                                    <label>예약 상세</label>
                                    <textarea name="description" rows="6" class="form-control"></textarea>
                                </div>
                                <div class="form-group">
                                    <label>화면에 표시</label>
                                    <div>
                                        <input type="checkbox" name="is_open" value="Y">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>이미지</label>
                                    <div>
                                        <input type="file">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>최대 수량</label>
                                    <input type="number" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>사용 크레딧</label>
                                    <input type="number" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>금액</label>
                                    <input type="number" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>예약 시작일</label>
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
</tiles:insertDefinition>