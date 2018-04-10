package com.kolon.comlife.imageStore.web;

import com.kolon.comlife.common.model.ErrorInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.example.web.ExampleController;
import com.kolon.comlife.imageStore.exception.ImageBase64Exception;
import com.kolon.comlife.imageStore.exception.ImageNotFoundException;
import com.kolon.comlife.imageStore.model.ImageBase64;
import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import com.kolon.comlife.postFile.service.exception.OperationFailedException;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

@RestController
@RequestMapping("/imageStore/")
public class ImageStoreController {
    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @Autowired
    ImageStoreService imageStoreService;

    // Response Header 생성. 현재는 MIME Type 을 지정하는 역할만을 수행한다.
    private HttpHeaders getFileTypeHeaders( String type ) {
        final HttpHeaders headers = new HttpHeaders();
        switch ( type.toLowerCase() ) {
            case "image/png" :
                headers.setContentType(MediaType.IMAGE_PNG);
                break;
            case "image/gif" :
                headers.setContentType(MediaType.IMAGE_GIF);
                break;
            case "image/jpg" :
            case "image/jpeg" :
                headers.setContentType(MediaType.IMAGE_JPEG);
                break;
            default :

        }

        return headers;
    }

    @CrossOrigin
    @PostMapping(
            value = "/{imageType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createImageWithType( MultipartHttpServletRequest request,
                                               @PathVariable("imageType") String imageType) {
        AuthUserInfo      currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        int               usrId = -1;
        ImageInfo         uploadedImageInfo;
        Iterator<String>  iter;
        MultipartFile     mpf;
        String fileName;
        String fileExt;

        if( currUser != null ) {
            logger.debug(">>> CmplxId: " + currUser.getCmplxId());
            logger.debug(">>> UserId: " + currUser.getUserId());
            logger.debug(">>> UsrId: " + currUser.getUsrId());

            usrId = currUser.getUsrId();
        }

        iter = request.getFileNames();
        if( !(iter.hasNext()) ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body( new SimpleErrorInfo("업로드할 파일이 없습니다.") );
        }

        // 첫번째 값만 체크
        mpf = request.getFile( iter.next() );
        logger.debug("MPF NAME>>>> " + mpf.getName() );

        if( !"file".equals( mpf.getName() ) ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleErrorInfo("'file'값이 존재하지 않습니다.") );
        }

        try {
            logger.debug("file length : " + mpf.getBytes().length);

            fileName = mpf.getOriginalFilename();
            logger.debug("file name : " + fileName );

            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            try {
                uploadedImageInfo = imageStoreService.createImage(
                                        mpf.getInputStream(),
                                        mpf.getSize(),
                                        imageType,
                                        fileExt, -1);
            } catch(OperationFailedException e) {
                logger.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body( new SimpleErrorInfo( e.getMessage() ) );
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body( new SimpleErrorInfo("업로드가 실패했습니다.") );
        }

        return ResponseEntity.status(HttpStatus.OK).body( uploadedImageInfo );
    }

    @CrossOrigin
    @PostMapping(
            value = "/{imageType}/b64",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createImageWithTypeByB64( HttpServletRequest request,
                                                    @PathVariable("imageType") String imageType,
                                                    @RequestBody HashMap<String, String> params ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        int          usrId;
        ImageInfo    uploadedImageInfo;
        String       base64;
        ImageBase64  imageBase64 = new ImageBase64();
        byte[]       imageBytes;

        if( currUser != null ) {
            logger.debug(">>> CmplxId: " + currUser.getCmplxId());
            logger.debug(">>> UserId: " + currUser.getUserId());
            logger.debug(">>> UsrId: " + currUser.getUsrId());

            usrId = currUser.getUsrId();
        }

        base64 = params.get( "file" );
        if( base64 == null ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleMsgInfo( "file은 필수 입력 항목입니다." ));
        }

        try {
            imageBase64.parseBase64(base64);
        } catch( ImageBase64Exception e ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleMsgInfo( e.getMessage() ));
        }

        imageBytes = imageBase64.getByteData();
        try {
            uploadedImageInfo = imageStoreService.createImage(
                    new ByteArrayInputStream( imageBytes ),
                    imageBytes.length,
                    imageType,
                    imageBase64.getFileType(),
                    -1);
        } catch( OperationFailedException e ) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleMsgInfo( e.getMessage() ) );
        }

        return ResponseEntity.status(HttpStatus.OK).body( uploadedImageInfo );
    }

    @GetMapping( value = "/{id}" )
    public ResponseEntity getImageByIdx( @PathVariable( "id" ) int id ) {
        HttpHeaders         headers ;
        ImageInfo           imageInfo;

        try {
            imageInfo = imageStoreService.getImageByIdx( id );
        } catch ( ImageNotFoundException e ){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body( new SimpleErrorInfo( e.getMessage()) );
        } catch ( Exception e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body( new SimpleErrorInfo("이미지를 가져오는 과정에서 오류가 발생했습니다. "));
        }

        headers = getFileTypeHeaders( imageInfo.getMimeType() );
        logger.debug(">> " + headers.getContentType() );

        logger.debug(">>>> imageInfo"     + imageInfo);
        logger.debug(">>>> parentIdx:"    + imageInfo.getParentIdx());
        logger.debug(">>>> parentType:"   + imageInfo.getParentType());
        logger.debug(">>>> parentTypeNm:" + imageInfo.getParentTypeNm());
        logger.debug(">>>> filePath:"     + imageInfo.getFilePath());

        return new ResponseEntity(imageInfo.getImageByteArray(), headers, HttpStatus.OK);
    }

    @GetMapping( value = "/{id}/{size}" )
    public ResponseEntity getImageByIdxAndSize( @PathVariable( "id" ) int id, @PathVariable( "size" ) String sizeStr ) {
        HttpHeaders         headers ;
        ImageInfo           imageInfo;

        try {
            imageInfo = imageStoreService.getImageByIdxAndSize(id, sizeStr);
        } catch ( ImageNotFoundException e ){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body( new SimpleErrorInfo( e.getMessage()) );
        } catch ( Exception e ) {
            logger.error( e.getMessage() );
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body( new SimpleErrorInfo("이미지를 가져오는 과정에서 오류가 발생했습니다. "));
        }

        headers = getFileTypeHeaders( imageInfo.getMimeType() );
        logger.debug(">> " + headers.getContentType() );

        logger.debug(">>>> imageInfo"     + imageInfo);
        logger.debug(">>>> parentIdx:"    + imageInfo.getParentIdx());
        logger.debug(">>>> parentType:"   + imageInfo.getParentType());
        logger.debug(">>>> parentTypeNm:" + imageInfo.getParentTypeNm());
        logger.debug(">>>> filePath:"     + imageInfo.getFilePath());

        return new ResponseEntity(imageInfo.getImageByteArray(), headers, HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity deleteImageInfo( @PathVariable("id") int id ) {
//        PostFileInfo postFileInfo = postFileService.getPostFile( id );
//
//        AmazonS3 s3Client = getS3Client();
//
//        String key = postFileInfo.getFilePath();
//
//        s3Client.deleteObject(
//                new DeleteObjectRequest(
//                        serviceProp.getByKey(PROP_GROUP, S3_UPLOAD_BUCKET), key ) );
        return ResponseEntity
                    .status( HttpStatus.NOT_IMPLEMENTED)
                    .body(new SimpleErrorInfo("해당 기능은 지원하지 않습니다. "));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException5(IllegalArgumentException ex, HttpServletResponse response) throws IOException
    {
        ErrorInfo errorInfo = new ErrorInfo();

        errorInfo.setMsg( "잘못된 파라미터 입니다." );
        errorInfo.setReason( ex.getMessage() );

        return errorInfo;

    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo handleException5(IllegalStateException ex, HttpServletResponse response) throws IOException
    {
        ErrorInfo errorInfo = new ErrorInfo();

        errorInfo.setMsg( "파라미터 및 전달 값의 속성이 잘못되었습니다." );
        errorInfo.setReason( ex.getMessage() );

        return errorInfo;

    }
}
