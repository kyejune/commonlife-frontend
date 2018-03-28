<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="admin">
<tiles:putAttribute name="title">관리자 관리</tiles:putAttribute>
    <tiles:putAttribute name="contents">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>예약 시설 옵션 관리</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">Home</a>
                    </li>
                    <li>
                        예약 관리
                    </li>
                    <li class="active">
                        <a>예약 시설 옵션 관리</a>
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
                            <h5>예약 시설 옵션 생성</h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" style="">
                            <form action="" method="post">
                                <div class="form-group">
                                    <label class="radio-inline">
                                        <input type="radio" name="useIcon" value="Y" checked>
                                        아이콘
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="useIcon" value="N">
                                        이미지
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label>아이콘</label>
                                    <input type="text" name="iconClass" class="form-control" placeholder="예) fa fa-address-book">
                                    <p class="text-info" style="margin-top: 1rem;">
                                        * 사전에 등록된 아이콘 중 <code>&lt;i class="fa fa-address-book">&lt;/i></code> 같은 형태로 사용하는 css 클래스명을 입력합니다.
                                    </p>
                                </div>
                                <div class="form-group">
                                    <label>이미지</label>
                                    <input type="file" name="imagePath">
                                </div>
                                <div class="form-group">
                                    <label>시설 옵션명</label>
                                    <input type="text" name="name" class="form-control" placeholder="예) 샤워 부스">
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

        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>