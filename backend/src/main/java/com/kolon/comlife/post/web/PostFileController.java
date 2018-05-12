package com.kolon.comlife.post.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.post.model.PostFileInfo;
import com.kolon.comlife.post.service.PostFileService;
import com.kolon.comlife.post.service.PostFileStoreService;
import com.kolon.comlife.post.exception.OperationFailedException;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;

@RestController
@RequestMapping("/postFiles/*")
public class PostFileController {
    private static final Logger logger = LoggerFactory.getLogger(PostFileController.class);

    @Autowired
    PostFileStoreService storeService;

    @Resource(name = "postFileService")
    private PostFileService postFileService;


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
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPostFile( HttpServletRequest request,
                                       @RequestBody HashMap<String, String> params ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );
        int          usrId;
        PostFileInfo postFileInfo;
        byte[]       imageBytes;

        if( currUser != null ) {
            logger.debug(">>> CmplxId: " + currUser.getCmplxId());
            logger.debug(">>> UserId: " + currUser.getUserId());
            logger.debug(">>> UsrId: " + currUser.getUsrId());

            usrId = currUser.getUsrId();
        } else {
            usrId = -1;
        }

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
}
