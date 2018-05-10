<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<tiles:insertDefinition name="info">
<tiles:putAttribute name="title">INFO</tiles:putAttribute>
<tiles:putAttribute name="css">
    <!-- DataTables -->
    <a><!-- --></a>
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
            <c:set value="" var="itemTypeTxt"></c:set>
        </c:otherwise>
    </c:choose>

    <!-- Section Title -->
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2><c:out value="${itemTypeTxt}" escapeXml="false">INFO 게시글</c:out> 관리</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    INFO
                </li>
                <li class="active">
                    <a><c:out value="${itemTypeTxt}" escapeXml="false">INFO 게시글</c:out> 관리</a>
                </li>
            </ol>
        </div>
        <div class="col-lg-2">
        </div>
    </div>

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-md-8">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><c:out value="${itemTypeTxt}" escapeXml="false">INFO 게시글</c:out> 목록</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>

                    <div class="ibox-content" style="">
                        <c:if test="${itemType eq 'guide' or itemType eq 'benefits'}">
                            <div class="row">
                                <div class="col-lg-12">
                                    <button class="btn btn-primary"
                                            onclick="newItem()">
                                        게시물 생성
                                    </button>
                                </div>
                            </div>
                        </c:if>
                        <div class="row">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>제목</th>
                                        <th>내용</th>
                                        <th><a href="#" alt="이미지 포함 여부"><i class="fa fa-file-image-o"></i></a></th>
                                        <th>상태</th>
                                        <th>작성자</th>
                                        <th>생성 일시</th>
                                        <th>변경 일시</th>
                                    </tr>
                                </thead>
                                <c:choose>
                                    <c:when test="${fn:length(itemList) == 0}">
                                        <tbody>
                                        <tr>
                                            <td colspan="8"><p><dfn>조회된 결과가 없습니다.</dfn></p></td>
                                        </tr>
                                        </tbody>
                                    </c:when>
                                    <c:otherwise>
                                        <tbody>
                                        <c:forEach var="vo" items="${itemList}" varStatus="status">
                                            <tr>
                                                <td>
                                                    ${vo.itemIdx}
                                                </td>
                                                <td>
                                                    <c:out value="${vo.itemNm}" escapeXml="false"></c:out>
                                                </td>
                                                <td>
                                                    <a href="javascript:void(0)"
                                                       onclick="showItem('${vo.cateId}', ${vo.itemIdx})">

                                                        <c:choose>
                                                            <c:when test="${fn:length(vo.desc) < 1}" >
                                                                <c:out value="{내용 없음}" escapeXml="false"></c:out>
                                                            </c:when>
                                                            <c:when test="${fn:length(vo.desc) > 30}" >
                                                                <c:out value="${fn:substring(vo.desc,0, 30)}..." escapeXml="false"></c:out>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:out value="${vo.desc}" escapeXml="false"></c:out>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </a>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${vo.imageIdx2 > 0}">
                                                            <i class="fa fa-file-image-o" alt="이미지 포함"></i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            -
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${vo.delYn eq 'Y'}">
                                                            <i class="fa fa-times "></i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="fa fa-check-circle-o text-navy"></i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:out value="${vo.adminNm}" escapeXml="false"></c:out>
                                                </td>
                                                <td>
                                                    <fmt:parseDate value="${vo.regDttm}" pattern="yyyy-MM-dd HH:mm" var="sysDt"/>
                                                    <fmt:formatDate value="${sysDt}" pattern="yyyy.MM.dd HH:mm"/>
                                                </td>
                                                <td>
                                                    <fmt:parseDate value="${vo.updDttm}" pattern="yyyy-MM-dd HH:mm" var="sysDt2"/>
                                                    <fmt:formatDate value="${sysDt2}" pattern="yyyy.MM.dd HH:mm"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <!-- paginging -->
                                        <tr>
                                            <td colspan="8" >
                                                <div class="center-block">
                                                    <ui:pagination paginationInfo="${paginateInfo}" jsFunction="fn_link_page"/>
                                                </div>
                                            </td>
                                        </tr>
                                        </tfoot>
                                    </c:otherwise>
                                </c:choose>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><c:out value="${itemTypeTxt}" escapeXml="false">INFO 게시글</c:out> 상세보기</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>

                    <div class="ibox-content" style="">
                        <input id="itemIdx" type="hidden" value="-1" />
                        <input id="itemType" type="hidden" value="" />
                        <div id="emptyItem" class="text-success font-bold text"><br>
                            <br><h3>'내용'을 클릭하여 <br> 상세 정보를 볼 수 있습니다.</h3><br>
                        </div>
                        <div class="social-feed-box" id="showItem" style="display: none;">
                            <div class="social-avatar m-sm">
                                <%--<a href="" class="pull-left">--%>
                                    <%--<img id="profile" alt="image" src="">--%>
                                <%--</a>--%>
                                <div class="media-body">
                                    <a href="#" id="adminNm"></a>
                                    <small class="text-muted" id="regDttm" ></small>
                                </div>
                            </div>
                            <div class="social-body m-sm">
                                <div id="desc" style="white-space: pre-line"></div>
                                <br>
                                <br>
                                <img id="imageIdx2" src="#" class="img-responsive" style="display: none">
                                <br>
                                <br>
                            </div>
                            <div class="social-footer">
                                <div class="row">
                                    <div class="hr-line-dashed"></div>
                                    <c:if test="${itemType eq 'guide' or itemType eq 'benefits'}">
                                            <div class="col-lg-6 btn-sm" id="updateButton">
                                                <button type="button"
                                                        class="btn-xs btn btn-w-m btn-primary btn-outline"
                                                        onclick="editItem()">편집</button>
                                            </div>
                                    </c:if>
                                    <%--<c:if test="${itemType eq 'feed'}">--%>
                                    <div class="col-lg-6 btn-sm"  id="itemPrivateButton">
                                        <button type="button"
                                                class="btn-xs btn btn-w-m btn-danger btn-outline"
                                                onclick="">비공개</button>
                                    </div>
                                    <div class="col-lg-6 btn-sm"  id="itemPublicButton">
                                        <button type="button"
                                                class="btn-xs btn btn-w-m btn-danger btn-outline"
                                                onclick="">공개</button>
                                    </div>
                                    <%--</c:if>--%>
                                </div>
                            </div>
                        </div>
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

            // initialze datetime picker
            $( '.datepicker' ).each( function( index, element ) {
                var $element = $( element );
                var params = {};
                if( $element.data( 'format' ) ) {
                    params.format = $element.data( 'format' );
                }
                $element.datetimepicker( params );
            } );

            <c:if test="${error != null}" >
            alert("${error}");
            return;
            </c:if>
        });

        function fn_link_page( pageIndex ) {
            window.location.replace("list.do?pageNum=" + pageIndex);
        }

        function refreshList(){
            location.reload();
        }

        function showItem( itemType, itemIdx ) {
            var url = '/admin/info/' + itemType + '/items/' + itemIdx;

            console.log( itemIdx );
            console.log( url);

            $.ajax({
                url : url,
                type : 'get',
                success: function (rs) {
                    $("#itemIdx").attr( "value", rs['itemIdx'] );
                    $("#itemType").attr( "value", rs['cateId'] );
                    // $("#profile").attr( "src", rs['user']['imgSrc'] );
                    // $("#userNm").text( rs['user']['userNm'] );
                    $("#regDttm").text( rs['regDttm'] );
                    $("#desc").text( rs['desc'] );

                    if( rs['delYn'] == "Y" ) {
                        $("#itemPrivateButton").hide();
                        $("#itemPublicButton").show();
                        $("#itemPublicButton").attr( "onclick", "makeItemPublic('" + rs['cateId'] + "'," + rs['itemIdx'] + ")" );
                    } else {
                        $("#itemPrivateButton").show();
                        $("#itemPublicButton").hide();
                        $("#itemPrivateButton").attr( "onclick", "makeItemPrivate('" + rs['cateId'] + "'," + rs['itemIdx'] + ")" );
                    }

                    if( rs['imageIdx2'] > 0 ) {
                        console.log( ">>>>>>>>>>> rs['imageIdx2'] " + rs['imageIdx2'] );
                        $("#imageIdx2").attr("src", "/imageStore/" + rs['imageIdx2'] + "/m" );
                        $("#imageIdx2").show();
                    } else {
                        $("#imageIdx2").hide();
                    }

                    $("#showItem").show();
                    $("#emptyItem").hide();

                    // console.log( rs['user']['imgSrc'])
                    console.log(rs);
                },
                error : function(){
                    alert('해당 게시물을 가져올 수 없습니다.');
                    console.log('error');
                }
            });
        }

        function newItem() {
            <c:choose>
                <c:when test="${itemType eq 'guide'}">
                    window.location.href = "/admin/info/newGuide.do";
                </c:when>
                <c:when test="${itemType eq 'benefits'}">
                    window.location.href = "/admin/info/newBenefits.do";
                </c:when>
            </c:choose>
        }

        function makeItemPrivate( itemType, itemIdx ) {
            var url;
            var ret;

            url  = '/admin/info/' +
                    itemType +
                    '/items/' +
                    itemIdx +
                    "/private" +
                    "?${_csrf.parameterName}=${_csrf.token}";

            console.log( itemIdx );
            console.log( url);

            ret = confirm("주의: 해당 게시물을 비공개 처리하시겠습니까? 비공개 처리된 게시물은 사용자에게 더이상 노출 되지 않습니다.");
            if( ret ) {
                ret = confirm("재확인 : 게시물을 비공개 처리하겠습니까?");
                if( ret ) {
                    $.ajax({
                        url : url,
                        type : 'put',
                        success: function (data, textStatus, xhr) {
                            console.log(data);
                            alert(data['msg']);
                            refreshList();
                        },
                        error : function(data, textStatus, xhr){
                            console.log(data);
                            alert(data['msg']);
                        }
                    });
                }
            }
        }

        function makeItemPublic( itemType, itemIdx ) {
            var url;
            var ret;

            url  = '/admin/info/' +
                    itemType +
                    '/items/' +
                    itemIdx +
                    "/public" +
                    "?${_csrf.parameterName}=${_csrf.token}";

            console.log( itemIdx );
            console.log( url);

            ret = confirm("주의: 해당 게시물을 공개로 변경하시겠습니까? 공개된 게시물은 사용자에게 바로 노출 됩니다.");
            if( ret ) {
                ret = confirm("재확인 : 게시물을 공개 상태로 변경하시겠습니까?");
                if( ret ) {
                    $.ajax({
                        url : url,
                        type : 'put',
                        success: function (data, textStatus, xhr) {
                            console.log(data);
                            alert(data['msg']);
                            refreshList();
                        },
                        error : function(data, textStatus, xhr){
                            console.log(data);
                            alert(data['msg']);
                        }
                    });
                }
            }
        }

        <c:if test="${itemType eq 'guide' or itemType eq 'benefits'}">
            function editItem() {
                var url = "edit.do?itemIdx=" + $("#itemIdx").val() + "&itemType=" + $("#itemType").val();
                window.location.href = url;
            }
        </c:if>
    </script>
</tiles:putAttribute>
</tiles:insertDefinition>