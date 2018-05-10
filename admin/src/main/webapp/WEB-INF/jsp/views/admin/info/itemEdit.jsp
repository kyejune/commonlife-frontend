<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="info">
<tiles:putAttribute name="title">INFO 작성/편집</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- DataTables -->
    <a><!-- --></a>
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
</tiles:putAttribute>
<tiles:putAttribute name="contents">
    <c:choose>
        <c:when test="${itemType eq 'guide'}">
            <c:set value="Living Guide" var="itemTypeTxt"></c:set>
        </c:when>
        <c:when test="${itemType eq 'benefits'}">
            <c:set value="Benefits" var="itemTypeTxt"></c:set>
        </c:when>
        <c:otherwise>
            <c:set value="-" var="itemTypeTxt"></c:set>
        </c:otherwise>
    </c:choose>

    <!-- Section Title -->
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2><c:out value="${itemTypeTxt}" escapeXml="false">INFO 게시글</c:out> 편집</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    INFO
                </li>
                <li class="active">
                    <a><c:out value="${itemTypeTxt}" escapeXml="false">INFO 게시글</c:out> 편집</a>
                </li>
            </ol>
        </div>
        <div class="col-lg-2">
        </div>
    </div>

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-md-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><c:out value="${itemTypeTxt}" escapeXml="false">INFO 게시글</c:out> 작성/편집</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form action="#" class="form-horizontal" name="itemInfo" id="itemInfo">
                            <input type="hidden" name="itemIdx" value="${itemIdx}" />
                            <input type="hidden" name="itemType" value="${itemType}" />
                            <input type="hidden" name="itemUpdateYn" value="${itemUpdateYn}" />

                            <div class="form-group">
                                <label class="col-sm-2 control-label">제목 *</label>
                                <div class="col-sm-10" >
                                    <input type="text"
                                           name="itemNm"
                                           class="form-control"
                                           value="${itemInfo.itemNm}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">내용 *</label>
                                <div class="col-sm-10" >
                                <textarea name="desc"
                                          id="desc"
                                          cols="100"
                                          rows="5">${itemInfo.desc}</textarea>
                                </div>
                            </div>
                            <%--<div class="form-group">--%>
                                <%--<label class="col-sm-2 control-label">이미지</label>--%>
                                <%--<div class="col-sm-6" >--%>
                                    <%--<div class="form-group">--%>
                                        <%--<input type="file" id="image-selector" multiple accept="image/*">--%>
                                    <%--</div>--%>
                                    <%--<div id="thumbnails">--%>
                                        <%--<c:if test="${postInfo.postFiles != null}">--%>
                                            <%--<c:forEach var="image" items="${postInfo.postFiles}">--%>
                                                <%--<div class="thumbnail-viewer" data-image="/admin/postFiles/${image.postFileIdx}" >--%>
                                                    <%--<input type="hidden" name="postFile[]" value="${image.postFileIdx}">--%>
                                                    <%--<button class="delete">&times;</button>--%>
                                                <%--</div>--%>
                                            <%--</c:forEach>--%>
                                        <%--</c:if>--%>
                                    <%--</div>--%>
                                    <%--<p class="text-success">* '파일 선택'하면 이미지 업로드가 시작되고, 완료되면 화면에 표시됩니다. 이미지 업로드가 완료될 때 까지 기다리세요.</p>--%>
                                    <%--<p class="text-success">* 추천 이미지 비율 - 1.56:1 가로:세로, 예) 720x460, 1440x920</p>--%>
                                    <%--<p class="text-success">* 앱에서는 이미지가 해당 비율을 유지하여 표시됩니다.</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <div class="hr-line-dashed"></div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"></label>
                                <div class="col-sm-6" >
                                    <div class="btn btn-block btn-primary m-t" onclick="updateItem()">저장하기</div>
                                </div>
                                <div class="col-sm-2"></div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</tiles:putAttribute>
<tiles:putAttribute name="js">
    <script type="text/javascript">
        $(function () {
            $("#left_info").addClass("active");
            $("#left_info > .nav-second-level").addClass("in");

            <c:choose>
                <c:when test="${itemType eq 'guide'}">
                    $("#left_info_guide").addClass("active");
                </c:when>
                <c:when test="${itemType eq 'benefits'}">
                    $("#left_info_benefits").addClass("active");
                </c:when>
                <c:otherwise>
                    $("#left_info_guide").addClass("active");
                </c:otherwise>
            </c:choose>

            <c:if test="${error != null}" >
                alert("${error}");
                return;
            </c:if>

            // initialze datetime picker
            $( '.datepicker' ).each( function( index, element ) {
                var $element = $( element );
                var params = {};
                if( $element.data( 'format' ) ) {
                    params.format = $element.data( 'format' );
                }
                $element.datetimepicker(    params );
            } );

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

            // 썸네일 이미지 표시
            $( '.thumbnail-viewer' ).each( function( index, element ) {
                var $element = $( element );
                $element.css( 'backgroundImage', 'url(' + $element.data( 'image' ) + ')')
            } );
        });

        function createThumbnail( data ) {
            var thumb = "<div class='thumbnail-viewer' " +
                        "      style='background-image: url(" + ( "/admin/postFiles/" + data['postFileIdx'] ) + ");'>" +
                "<button class='delete' type='button'>&times;</button>" +
                "<input type='hidden' name='postFiles[]' value='" + data['postFileIdx'] + "'>" +
                "</div>";
            if( $('.thumbnail-viewer').length ) {
                $('.thumbnail-viewer').remove();
            }
            $( '#thumbnails' ).append( $( thumb ) );
        }

        function uploadImage( file ) {
            var data = new FormData();
            data.append( "file", file );
            $.ajax( {
                url: '/admin/postFiles/?${_csrf.parameterName}=${_csrf.token}',
                type: 'POST',
                data: data,
                contentType: false,
                processData: false
            } )
                .done( function( data ) {
                    console.log( data);
                    createThumbnail( data );
                } );
        }

        function updateItem() {
            var url;
            var type;
            <c:choose>
            <c:when test="${itemUpdateYn eq 'Y'}">
            url = '/admin/info/' + "${itemType}" + '/items/${itemIdx}?${_csrf.parameterName}=${_csrf.token}';
            type = 'put';
            </c:when>
            <c:otherwise>
            url = '/admin/info/proc.do?${_csrf.parameterName}=${_csrf.token}';
            type =  'post'
            </c:otherwise>
            </c:choose>

            var data = {};
            // data['postFiles'] = new Array;

            var postFileIdx = 0;
            $.each( $("form[name=itemInfo]").serializeArray(), function() {
                // if( this.name == "postFiles[]" ) {
                //     var postFile = {};
                //     postFile['postFileIdx'] = this.value;
                //     data['postFiles'].push( postFile );
                // }
                // else
                data[this.name] = this.value;
            });

            console.log( data );

            $.ajax({
                type : type,
                url  : url,
                data : JSON.stringify(data),
                dataType : "json",
                contentType : "application/json; charset=UTF-8",
                error: function(xhr, status, error){
                    var respJson = $.parseJSON(xhr.responseText);
                    console.log( respJson    );
                    console.log( status );
                    alert( respJson['msg'] );
                },
                success : function(json){
                    alert('성공적으로 저장하였습니다');
                    <c:choose>
                        <c:when test="${itemType eq 'guide'}">
                            window.location.href = "/admin/info/guide/list.do";
                        </c:when>
                        <c:when test="${itemType eq 'benefits'}">
                            window.location.href = "/admin/info/benefits/list.do";
                        </c:when>
                        <c:otherwise>
                            window.location.href = "/admin/info/guide/list.do";
                        </c:otherwise>
                    </c:choose>
                }
            });
        }

    </script>
</tiles:putAttribute>
</tiles:insertDefinition>