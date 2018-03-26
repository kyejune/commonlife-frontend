package com.kolon.comlife.postFile.web;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.example.web.ExampleController;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.PostFileService;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.prop.KeyValueMap;
import com.kolon.common.prop.ServicePropertiesMap;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;

@RestController
@RequestMapping("/postFiles/*")
public class PostFileController {
    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    private static final String POST_IMG_BASE_PATH = "origin/article/";

    private static final String PROP_GROUP         = "POST";
    private static final String S3_ACCESS_KEY      = "S3_ACCESS_KEY";
    private static final String S3_ACCESS_SECRET   = "S3_ACCESS_SECRET";

    // 업로드 버킷에 올라간 이미지는 리사이징 되어 다운로드 버킷에 저장됩니다.
    private static final String S3_UPLOAD_BUCKET   = "UP_S3_NAME";
    private static final String S3_DOWNLOAD_BUCKET = "DN_S3_NAME";


    @Resource(name = "postFileService")
    private PostFileService postFileService;
    @Resource(name = "servicePropertiesMap")
    ServicePropertiesMap serviceProp;

    /**
     * AWS S3 Client 객체 생성
     *
     * @return AmazonS3
     */
    private AmazonS3 getS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(
                                                serviceProp.getByKey(PROP_GROUP, S3_ACCESS_KEY),
                                                serviceProp.getByKey(PROP_GROUP, S3_ACCESS_SECRET) );
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    /**
     * Response Header 생성. 현재는 MIME Type 을 지정하는 역할만을 수행한다.
     *
     * @param type
     * @return HttpHeaders
     */
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

    /**
     * 업로드 원본 조회
     *
     * @param s3Client
     * @param postFileInfo
     * @return ResponseEntity
     */
    private ResponseEntity getOriginFile( AmazonS3 s3Client, PostFileInfo postFileInfo ) {
        String key = postFileInfo.getFilePath();
        S3Object object = s3Client.getObject(
                                new GetObjectRequest(
                                        serviceProp.getByKey(PROP_GROUP, S3_UPLOAD_BUCKET), key ) );
        InputStream objectData = object.getObjectContent();
        try {
            byte[] byteArray = IOUtils.toByteArray( objectData );

            // Set headers
            final HttpHeaders headers = getFileTypeHeaders( postFileInfo.getMimeType() );

            return new ResponseEntity<byte[]> (byteArray, headers, HttpStatus.OK);
        }
        catch( IOException e ) {
            return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( null );
        }
    }

    /**
     * 리사이즈 이미지 조회
     *
     * @param s3Client
     * @param postFileInfo
     * @param size
     * @return ResponseEntity
     */
    private ResponseEntity getResizeFile( AmazonS3 s3Client, PostFileInfo postFileInfo, String size ) {
        String key = postFileInfo.getFilePath();
        key = key.replace( "origin/", "resize/" + size + "/" );
        S3Object object = s3Client.getObject(
                                new GetObjectRequest(
                                        serviceProp.getByKey(PROP_GROUP, S3_DOWNLOAD_BUCKET), key ) );
        InputStream objectData = object.getObjectContent();
        try {
            byte[] byteArray = IOUtils.toByteArray( objectData );

            // Set headers
            final HttpHeaders headers = getFileTypeHeaders( postFileInfo.getMimeType() );

            return new ResponseEntity<byte[]> (byteArray, headers, HttpStatus.OK);
        }
        catch( IOException e ) {
            return getOriginFile( s3Client, postFileInfo );
        }
    }

    @CrossOrigin
    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setPostFile( HttpServletRequest request,
                                       @RequestBody HashMap<String, String> params ) {
        AuthUserInfo currUser = AuthUserInfoUtil.getAuthUserInfo( request );

        logger.debug(">>> CmplxId: " + currUser.getCmplxId());
        logger.debug(">>> UserId: " + currUser.getUserId());
        logger.debug(">>> UsrId: " + currUser.getUsrId());

        int usrId = currUser.getUsrId();

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
        byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

        InputStream stream = new ByteArrayInputStream( imageBytes );
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength( imageBytes.length );
        metadata.setContentType( "image/" + fileType );

        AmazonS3 s3Client = getS3Client();

        // TODO: 파일 저장 경로 변경 및 파일 이름 변경 - IOT-62
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String key = POST_IMG_BASE_PATH + timestamp.getTime() + "." + fileType;

        s3Client.putObject(
                new PutObjectRequest(
                        serviceProp.getByKey(PROP_GROUP, S3_UPLOAD_BUCKET), key, stream, metadata ) );

        PostFileInfo postFile = new PostFileInfo();
        postFile.setUsrId( usrId );
        postFile.setFilePath( key );
        postFile.setMimeType( metadata.getContentType() );

        PostFileInfo result = postFileService.setPostFile( postFile );

        return ResponseEntity.status(HttpStatus.OK).body( result );
    }

    @GetMapping(
            value = "/{id}"
    )
    public ResponseEntity getPostFile( @PathVariable( "id" ) int id ) {
        PostFileInfo postFileInfo = postFileService.getPostFile( id );

        if( postFileInfo == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new SimpleErrorInfo("해당하는 이미지가 없습니다."));
        }

        logger.debug(">>>> postFileInfo" + postFileInfo);
        logger.debug(">>>> postIdx:" + postFileInfo.getPostIdx());
        logger.debug(">>>> filePath:" + postFileInfo.getFilePath());
        logger.debug(">>>> postFileIdx:" + postFileInfo.getPostFileIdx());

        return getOriginFile( getS3Client(), postFileInfo );
    }

    @GetMapping(
            value = "/{id}/{size}"
    )
    public ResponseEntity getPostSmallFile( @PathVariable( "id" ) int id, @PathVariable( "size" ) String size ) {
        PostFileInfo postFileInfo = postFileService.getPostFile( id );

        if( postFileInfo == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new SimpleErrorInfo("해당하는 이미지가 없습니다."));
        }

        logger.debug(">>>> postFileInfo" + postFileInfo);
        logger.debug(">>>> postIdx:" + postFileInfo.getPostIdx());
        logger.debug(">>>> filePath:" + postFileInfo.getFilePath());
        logger.debug(">>>> postFileIdx:" + postFileInfo.getPostFileIdx());

        return getResizeFile( getS3Client(), postFileInfo, size );
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity deletePostFile( @PathVariable("id") int id ) {
        PostFileInfo postFileInfo = postFileService.getPostFile( id );

        AmazonS3 s3Client = getS3Client();

        String key = postFileInfo.getFilePath();

        s3Client.deleteObject(
                new DeleteObjectRequest(
                        serviceProp.getByKey(PROP_GROUP, S3_UPLOAD_BUCKET), key ) );
        return null;
    }
}
