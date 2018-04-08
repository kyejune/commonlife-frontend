package com.kolon.comlife.imageStore.web;

import com.kolon.comlife.common.model.ErrorInfo;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.example.web.ExampleController;
import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.PostFileService;
import com.kolon.comlife.postFile.service.PostFileStoreService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

@RestController
@RequestMapping("/imageStore/")
public class ImageStoreController {
    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @Autowired
    PostFileStoreService storeService;

    @Autowired
    ImageStoreService imageStoreService;

    @Resource(name = "postFileService")
    private PostFileService postFileService;


    // Response Header 생성. 현재는 MIME Type 을 지정하는 역할만을 수행한다.
    private HttpHeaders getFileTypeHeaders( String type ) {
        final HttpHeaders headers = new HttpHeaders();
        switch ( type ) {
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
        int               usrId;
        ImageInfo         uploadedImageInfo;
        byte[]            imageBytes;
        Iterator<String>  iter;
        MultipartFile     mpf;
        String fileName;
        String fileExt;

        // UpperCase로 변환
        imageType = imageType.toUpperCase();

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

        mpf = request.getFile( iter.next() );
        try {
            System.out.println("file length : " + mpf.getBytes().length);
            fileName = mpf.getOriginalFilename();
            System.out.println("file name : " + fileName );

            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

            try {
                uploadedImageInfo = imageStoreService.createImage( mpf.getBytes(), imageType, fileExt );
            } catch(OperationFailedException e) {
                logger.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body( new SimpleErrorInfo( e.getMessage() ) );
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body( new SimpleErrorInfo("업로드가 실패했습니다.") );
        }


//        try {
//            // Upload to S3
//            postFileInfo = storeService.createPostFile( imageBytes, fileType );
//            postFileInfo.setUsrId( usrId );
//
//            // 테이블 업데이트
//            postFileInfo = postFileService.setPostFile(postFileInfo);
//        } catch(OperationFailedException e) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body( new SimpleMsgInfo( "이미지 업로드가 실패하였습니다." ));
//        }

        return ResponseEntity.status(HttpStatus.OK).body( uploadedImageInfo );
    }


    @CrossOrigin
    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPostFile( HttpServletRequest request,
                                       @RequestBody HashMap<String, String> params ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        int          usrId;
        PostFileInfo postFileInfo;
        byte[]       imageBytes;

        logger.debug(">>> CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> UserId: " + currUser.getUserId());
        logger.debug(">>> UsrId: " + currUser.getUsrId());

        usrId = currUser.getUsrId();

        String base64 = params.get( "file" );

        if( base64 == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleMsgInfo( "file은 필수 입력 항목입니다." ));
        }

        String[] base64Components = base64.split(",");

        if (base64Components.length != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleMsgInfo( "잘못된 데이터입니다." ));
        }

        String base64Data = base64Components[0];
        String fileType = base64Data.substring(base64Data.indexOf('/') + 1, base64Data.indexOf(';'));
        String base64Image = base64Components[1];
        imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

        try {
            // Upload to S3
            postFileInfo = storeService.createPostFile( imageBytes, fileType );
            postFileInfo.setUsrId( usrId );

            // 테이블 업데이트
            postFileInfo = postFileService.setPostFile(postFileInfo);
        } catch(OperationFailedException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleMsgInfo( "이미지 업로드가 실패하였습니다." ));
        }

        return ResponseEntity.status(HttpStatus.OK).body( postFileInfo );
    }

    @GetMapping(
            value = "/{id}"
    )
    public ResponseEntity getPostFile( @PathVariable( "id" ) int id ) {
        byte[]       outputFile;
        HttpHeaders  headers;
        PostFileInfo postFileInfo;

        postFileInfo = postFileService.getPostFile( id );
        if( postFileInfo == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new SimpleErrorInfo("해당하는 이미지가 없습니다."));
        }
        logger.debug(">>>> postFileInfo" + postFileInfo);
        logger.debug(">>>> postIdx:" + postFileInfo.getPostIdx());
        logger.debug(">>>> filePath:" + postFileInfo.getFilePath());
        logger.debug(">>>> postFileIdx:" + postFileInfo.getPostFileIdx());

        try {
            outputFile = storeService.getPostFile( postFileInfo );
        } catch( OperationFailedException e ) {

            return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( null );
        }

        // Set Header
        headers = getFileTypeHeaders( postFileInfo.getMimeType() );

        return new ResponseEntity(outputFile, headers, HttpStatus.OK);
    }

    @GetMapping(
            value = "/{id}/{size}"
    )
    public ResponseEntity getPostSmallFile( @PathVariable( "id" ) int id, @PathVariable( "size" ) String size ) {
        byte[]       outputFile;
        HttpHeaders  headers;
        PostFileInfo postFileInfo;

        postFileInfo = postFileService.getPostFile( id );
        if( postFileInfo == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new SimpleErrorInfo("해당하는 이미지가 없습니다."));
        }
        logger.debug(">>>> postFileInfo" + postFileInfo);
        logger.debug(">>>> postIdx:" + postFileInfo.getPostIdx());
        logger.debug(">>>> filePath:" + postFileInfo.getFilePath());
        logger.debug(">>>> postFileIdx:" + postFileInfo.getPostFileIdx());

        try {
            outputFile = storeService.getPostFileBySize( postFileInfo, size );
        } catch( OperationFailedException e ) {
            return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( null );
        }

        // Set Header
        headers = getFileTypeHeaders( postFileInfo.getMimeType() );

        return new ResponseEntity(outputFile, headers, HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity deletePostFile( @PathVariable("id") int id ) {
        PostFileInfo postFileInfo = postFileService.getPostFile( id );
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
