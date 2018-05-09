<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    /*어매니티 멀티 셀렉터*/
    .chosen-container-multi .chosen-choices, .chosen-container-single .chosen-single,
    .chosen-container-active.chosen-container-multi.chosen-with-drop .chosen-choices, .chosen-container-active.chosen-with-drop .chosen-single,
    .chosen-container .chosen-drop {
        border: 1px solid #e5e5e5;
        border-radius: 0;
    }

    /*어매니티 선택 항목*/
    .scheme-form__amenities .chose-image {
        width: 24px;
        max-height: 24px;
        background: #666;
        padding: 4px;
    }

    /*어매니티 선택 항목 내부 라벨*/
    .scheme-form__amenities .chosen-container-multi .chosen-choices li.search-choice {
        background: none;
        border: none;
    }

    /*어매니티 선택 항목 이미지*/
    .scheme-form__amenities .chosen-container-multi .chosen-choices li.search-choice img {
        margin-right: 0.5em;
        transform: translate( 0, -1px );
    }

    /*어매니티 선택 항목 닫기 버튼*/
    .scheme-form__amenities .chosen-container-multi .chosen-choices li.search-choice .search-choice-close {
        transform: translate( 0, 3px );
    }

    /*어매니티 목록 이미지*/
    .scheme-form__amenities .chose-image-list {
        width: 24px;
        max-height: 24px;
        background: #666;
        padding: 4px;
    }
</style>
<script>
    $( function() {
        var HOST = 'http://localhost:8080';
        if( window.location.hostname === 'cl-stage-admin.cyville.net' ) {
            HOST = 'https://cl-stage.cyville.net';
        }
        else if( window.location.hostname !== 'localhost' ) {
            HOST = 'https://clback.cyville.net';
        }

        // 멀티 셀렉트
        $('.chosen-select').chosen({width: "100%"});

        // 스위쳐
        $('.js-switch').each( function( index, element ) {
            new Switchery(element, { color: '#1AB394', size: 'small' });
        } );

        // 터치 스핀(스테퍼)
        $(".touchspin").TouchSpin({
            buttondown_class: 'btn btn-white',
            buttonup_class: 'btn btn-white'
        });

        // 데이트피커
        $( '.datepicker' ).each( function( index, element ) {
            var $element = $( element );
            var params = {};
            if( $element.data( 'format' ) ) {
                params.format = $element.data( 'format' );
            }
            $element.datetimepicker( params );
        } );

        // 옵션 토글
        $( '#qty-toggle' ).on( 'change', function( event ) {
            var toggle = $( event.currentTarget );
            var on = toggle.prop( 'checked' );
            var target = $( '#qty-section' );
            if( on ) {
                target.show();
            }
            else {
                target.hide();
            }
        } );
        $( '#options-toggle' ).on( 'change', function( event ) {
            var toggle = $( event.currentTarget );
            var on = toggle.prop( 'checked' );
            var target = $( '#options-section' );
            if( on ) {
                target.show();
            }
            else {
                target.hide();
            }
        } );
        $( '#field-toggle' ).on( 'change', function( event ) {
            var toggle = $( event.currentTarget );
            var on = toggle.prop( 'checked' );
            var target = $( '#field-section' );
            if( on ) {
                target.show();
            }
            else {
                target.hide();
            }
        } );

        $( '#qty-toggle' ).trigger( 'change' );
        $( '#options-toggle' ).trigger( 'change' );
        $( '#field-toggle' ).trigger( 'change' );

        // 옵션 리스트 생성 및 추가
        $( '#add-option-button' ).on( 'click', function( event ) {
            var value = $( '#option-input' ).val();
            $.ajax( {
                url: '/admin/api/reservation-scheme-options/',
                type: 'post',
                data: {
                    _csrf: $( 'meta[name=_csrf]' ).attr( 'content' ),
                    name: value
                }
            } )
                .done( function( data ) {
                    var item = $( '<li class="list-group-item">' + value +
                        '                                       <input type="hidden" name="optionIdx" value="' + data.idx + '">' +
                        '                                        <span class="pull-right">' +
                        '                                            <button class="btn btn-danger btn-xs" type="button">' +
                        '                                                &times;' +
                        '                                            </button>' +
                        '                                        </span>' +
                        '                                    </li>' );
                    $( '#options-list' ).append( item );
                    $( '#option-input' ).val( '' );
                } );

        } );

        $( document ).on( 'click', '#options-list .btn-danger', function( event ) {
            event.preventDefault();
            var button = $( event.currentTarget );
            button.closest( '.list-group-item' ).remove();
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

        // 썸네일 이미지 표시
        $( '.thumbnail-viewer' ).each( function( index, element ) {
            var $element = $( element );
            $element.css( 'backgroundImage', 'url(' + HOST + $element.data( 'image' ) + ')')
        } );

        // 전송 전 폼 검증 체크
        $( '#scheme-form' ).on( 'submit', function( event ) {
            // 예약명 유무
            if( !$( 'input[name=title]' ).val() ) {
                event.preventDefault();
                alert( '예약명은 필수 입력 항목입니다.' );
                return false;
            }
            // 예약 개요
            if( !$( 'input[name=summary]' ).val() ) {
                event.preventDefault();
                alert( '예약 개요는 필수 입력 항목입니다.' );
                return false;
            }
            // 예약 상세
            if( !$( 'textarea[name=description]' ).val() ) {
                event.preventDefault();
                alert( '예약 상세는 필수 입력 항목입니다.' );
                return false;
            }
            // 예약 가능일: 시작일
            if( !$( 'input[name=startDt]' ).val() ) {
                event.preventDefault();
                alert( '예약 가능일은 필수 입력 항목입니다.' );
                return false;
            }
            // 예약 가능일: 종료일
            if( !$( 'input[name=endDt]' ).val() ) {
                event.preventDefault();
                alert( '예약 가능일은 필수 입력 항목입니다.' );
                return false;
            }
            // 아이콘
            if( !$( 'input[name=icon]:checked' ).val() ) {
                event.preventDefault();
                alert( '아이콘은 필수 입력 항목입니다.' );
                return false;
            }
        } );
    } );
</script>