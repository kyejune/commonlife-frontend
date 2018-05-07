package com.kolon.comlife.admin.post.web;

import com.kolon.comlife.admin.post.exception.OperationFailedException;
import com.kolon.comlife.admin.post.model.PostFileInfo;
import com.kolon.comlife.admin.post.service.PostFileService;
import com.kolon.comlife.admin.post.service.PostFileStoreService;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
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

import java.io.IOException;
import java.util.Iterator;

@RestController
@RequestMapping("admin/postFiles/*")
public class PostFileController {
    private static final Logger logger = LoggerFactory.getLogger(PostFileController.class);

    @Autowired
    PostFileStoreService storeService;

    @Autowired
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


//    @CrossOrigin
//    @PostMapping(
//            value = "/",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity createPostFileByB64( HttpServletRequest request,
//                                          @RequestBody HashMap<String, String> params ) {
//        int          usrId = -1; // 관리자 업로드 시, -1로 설정
//        PostFileInfo postFileInfo;
//        byte[]       imageBytes;
//
//        String base64 = params.get( "file" );
//
//        if( base64 == null ) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleMsgInfo( "file은 필수 입력 항목입니다." ));
//        }
//
//        String[] base64Components = base64.split(",");
//
//        if (base64Components.length != 2) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new SimpleMsgInfo( "잘못된 데이터입니다." ));
//        }
//
//        String base64Data = base64Components[0];
//        String fileType = base64Data.substring(base64Data.indexOf('/') + 1, base64Data.indexOf(';'));
//        String base64Image = base64Components[1];
//        imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
//
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
//
//        return ResponseEntity.status(HttpStatus.OK).body( postFileInfo );
//    }

    @CrossOrigin
    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPostFileByMultipart( MultipartHttpServletRequest request) {
        int              usrId = -1; // 관리자 업로드 시, -1로 설정
        PostFileInfo     postFileInfo;
        byte[]           imageBytes;
        MultipartFile    mpf;
        Iterator<String> iter;
        String           fileName;
        String           fileType;

        // 첫번째 값만 체크
        iter = request.getFileNames();
        if( !(iter.hasNext()) ) {
            return ResponseEntity.
                    status(HttpStatus.CONFLICT).
                    body( new SimpleErrorInfo("업로드할 파일이 없습니다.") );
        }

        mpf = request.getFile( iter.next() );
        logger.debug("MPF NAME>>>> " + mpf.getName() );

        if( !"file".equals( mpf.getName() ) ) {
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST).
                    body( new SimpleErrorInfo("'file'값이 존재하지 않습니다.") );
        }

        try {
            logger.debug("file length : " + mpf.getBytes().length);

            fileName = mpf.getOriginalFilename();
            logger.debug("file name : " + fileName);

            fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            imageBytes = mpf.getBytes();

            // Upload to S3
            postFileInfo = storeService.createPostFile(imageBytes, fileType);
            postFileInfo.setUsrId(usrId);

            // 테이블 업데이트
            postFileInfo = postFileService.setPostFile(postFileInfo);
        } catch ( OperationFailedException e ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleMsgInfo( "이미지 업로드가 실패하였습니다." ));
        } catch ( IOException e ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new SimpleMsgInfo( "이미지 업로드가 실패하였습니다. " ));
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
