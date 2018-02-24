package com.kolon.comlife.postFile.web;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.kolon.comlife.common.model.SimpleMsgInfo;
import com.kolon.comlife.example.web.ExampleController;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.PostFileService;
import com.kolon.common.prop.KeyValueMap;
import com.kolon.common.prop.ServicePropertiesMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    private String s3key;
    private String s3secret;
    private String writeBucket;
    private String readBucket;

    private KeyValueMap kvm;

    @Resource(name = "postFileService")
    private PostFileService postFileService;
    @Resource(name = "servicePropertiesMap")
    ServicePropertiesMap serviceProp;

    /**
     * Properties 로드
     *
     * @return KeyValueMap
     */
    private KeyValueMap getKvm() {
        if( kvm == null ) {
            kvm = serviceProp.getByGroup( "POST" );
            s3key = kvm.getValue( "S3_ACCESS_KEY" );
            s3secret = kvm.getValue( "S3_ACCESS_SECRET" );
            writeBucket = kvm.getValue( "UP_S3_NAME" );
            readBucket = kvm.getValue( "DN_S3_NAME" );
        }
        return kvm;
    }

    /**
     * AWS S3 Client 객체 생성
     *
     * @return AmazonS3
     */
    private AmazonS3 getS3Client() {
        getKvm();
        AWSCredentials credentials = new BasicAWSCredentials( s3key, s3secret );
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
        S3Object object = s3Client.getObject( new GetObjectRequest( writeBucket, key ) );
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
        S3Object object = s3Client.getObject( new GetObjectRequest( readBucket, key ) );
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
    public ResponseEntity setPostFile( @RequestBody HashMap<String, String> params ) {
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

        // TODO: 파일 저장 경로에 대한 고민 필요
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String key = "origin/article/" + timestamp.getTime() + "." + fileType;

        s3Client.putObject( new PutObjectRequest( writeBucket, key, stream, metadata ) );

        PostFileInfo postFile = new PostFileInfo();
        postFile.setPostIdx( 0 );
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
        return getOriginFile( getS3Client(), postFileInfo );
    }

    @GetMapping(
            value = "/{id}/{size}"
    )
    public ResponseEntity getPostSmallFile( @PathVariable( "id" ) int id, @PathVariable( "size" ) String size ) {
        PostFileInfo postFileInfo = postFileService.getPostFile( id );
        return getResizeFile( getS3Client(), postFileInfo, size );
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity deletePostFile( @PathVariable("id") int id ) {
        PostFileInfo postFileInfo = postFileService.getPostFile( id );

        AmazonS3 s3Client = getS3Client();

        String key = postFileInfo.getFilePath();

        s3Client.deleteObject( new DeleteObjectRequest( writeBucket, key ) );
        return null;
    }
}
