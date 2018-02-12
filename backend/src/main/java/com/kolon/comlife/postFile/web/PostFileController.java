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

    private String s3key = "AKIAJVCEPXZYSTVAITNA";
    private String s3secret = "08JvYxmY99x7qfOik/ECYQArSzHb+vqTyQY4iMKO";
    private String s3bucket = "commonlife-store";

    @Resource(name = "postFileService")
    private PostFileService postFileService;

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

        // TODO: key와 secret은 설정으로 이동시켜야 함
        AWSCredentials credentials = new BasicAWSCredentials( s3key, s3secret );
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        // TODO: 파일 저장 경로에 대한 고민 필요
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String key = timestamp.getTime() + "." + fileType;

        // TODO: bucket 이름은 설정으로 이동시켜야 함
        s3Client.putObject( new PutObjectRequest( s3bucket, key, stream, metadata ) );

        PostFileInfo postFile = new PostFileInfo();
        postFile.setBoardIdx( 0 );
        postFile.setFilePath( key );
        postFile.setFileType( metadata.getContentType() );

        PostFileInfo result = postFileService.setPostFile( postFile );

        return ResponseEntity.status(HttpStatus.OK).body( result );
    }

    @GetMapping(
            value = "/{id}"
    )
    public ResponseEntity getPostFile( @PathVariable( "id" ) int id ) {
        PostFileInfo postFileInfo = postFileService.getPostFile( id );

        // TODO: key와 secret은 설정으로 이동시켜야 함
        AWSCredentials credentials = new BasicAWSCredentials( s3key, s3secret );
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        String key = postFileInfo.getFilePath();

        S3Object object = s3Client.getObject( new GetObjectRequest( s3bucket, key ) );
        InputStream objectData = object.getObjectContent();

        try {
            byte[] byteArray = IOUtils.toByteArray( objectData );

            // Set headers
            final HttpHeaders headers = new HttpHeaders();
            switch ( postFileInfo.getFileType() ) {
                case "image/png" :
                    headers.setContentType(MediaType.IMAGE_PNG);
                    break;
                case "image/jpg" :
                case "image/jpeg" :
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    break;
                default :
            }

            return new ResponseEntity<byte[]> (byteArray, headers, HttpStatus.OK);
        }
        catch( IOException e ) {

        }

        return null;
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity deletePostFile( @PathVariable("id") int id ) {
        PostFileInfo postFileInfo = postFileService.getPostFile( id );

        // TODO: key와 secret은 설정으로 이동시켜야 함
        AWSCredentials credentials = new BasicAWSCredentials( s3key, s3secret );
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        String key = postFileInfo.getFilePath();

        s3Client.deleteObject( new DeleteObjectRequest( s3bucket, key ) );
        return null;
    }
}
