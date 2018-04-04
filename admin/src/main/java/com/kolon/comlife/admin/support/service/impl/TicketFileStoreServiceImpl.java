package com.kolon.comlife.admin.support.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.kolon.comlife.admin.support.exception.OperationFailedException;
import com.kolon.comlife.admin.support.model.TicketFileInfo;
import com.kolon.comlife.admin.support.service.TicketFileStoreService;
import com.kolon.common.prop.ServicePropertiesMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;


@Service("ticketFileStoreService")
public class TicketFileStoreServiceImpl implements TicketFileStoreService {
    private static final Logger logger = LoggerFactory.getLogger(TicketFileStoreServiceImpl.class);

    private static final String TICKET_IMG_BASE_PATH = "origin/ticket/";  // "ticket" 

    private static final String PROP_GROUP         = "POST";
    private static final String S3_ACCESS_KEY      = "S3_ACCESS_KEY";
    private static final String S3_ACCESS_SECRET   = "S3_ACCESS_SECRET";

    // 업로드 버킷에 올라간 이미지는 리사이징 되어 다운로드 버킷에 저장됩니다.
    private static final String S3_UPLOAD_BUCKET   = "UP_S3_NAME";
    private static final String S3_DOWNLOAD_BUCKET = "DN_S3_NAME";

    @Autowired
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
        // todo: AWS region 정보 Properties로 옮길 것
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    /**
     * 업로드 원본 조회
     *
     * @param s3Client
     * @return ResponseEntity
     */
    private byte[] getFile( AmazonS3  s3Client,
                            String    bucket,
                            String    objectKey) throws OperationFailedException {
        byte[]      outputFile;
        S3Object object;
        InputStream objectData;

        // todo: 아래 예외처리를 다른 store에도 반영할 것
        try {
            object = s3Client.getObject( new GetObjectRequest( bucket, objectKey ) );
            objectData = object.getObjectContent();
        } catch( AmazonS3Exception e ) {
            if( e.getStatusCode() == org.apache.http.HttpStatus.SC_NOT_FOUND ){
                logger.error( e.getMessage() );
                throw new OperationFailedException("해당 이미지가 존재하지 않습니다.");
            } else {
                logger.error( e.getMessage() );
                throw new OperationFailedException("내부 오류로 이미지를 가져오는데 실패했습니다. 이유: " + e.getMessage());
            }
        }

        try {
            outputFile = IOUtils.toByteArray( objectData );
        } catch( IOException e ) {
            logger.error( e.getMessage() );
            throw new OperationFailedException("해당 이미지를 가져올 수 없습니다.");
        }

        return outputFile;
    }

    public byte[] getTicketFile( TicketFileInfo ticketFileInfo) throws OperationFailedException {
        byte[] outputFile;

        outputFile = this.getFile(
                getS3Client(),
                serviceProp.getByKey(PROP_GROUP, S3_UPLOAD_BUCKET),
                ticketFileInfo.getFilePath() );

        return outputFile;
    }

    public byte[] getTicketFileBySize( TicketFileInfo ticketFileInfo, String size ) throws OperationFailedException {
        byte[] outputFile;
        String objectPath;

        // 새로운 File Path를 가져오기
        objectPath = ticketFileInfo.getFilePath();
        objectPath = objectPath.replace( "origin/", "resize/" + size + "/" );

        outputFile = this.getFile(
                getS3Client(),
                serviceProp.getByKey(PROP_GROUP, S3_DOWNLOAD_BUCKET),
                objectPath );

        return outputFile;
    }
}
