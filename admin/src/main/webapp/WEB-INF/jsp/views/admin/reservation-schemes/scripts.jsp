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

        // 썸네일 이미지 표시
        $( '.thumbnail-viewer' ).each( function( index, element ) {
            var $element = $( element );
            $element.css( 'backgroundImage', 'url(' + HOST + $element.data( 'image' ) + ')')
        } );
    } );
</script>