package com.kolon.comlife.support.service.impl;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.kolon.comlife.support.exception.OperationFailedException;
import com.kolon.comlife.support.model.TicketFileInfo;
import com.kolon.comlife.support.service.TicketFileStoreService;
import com.kolon.common.prop.ServicePropertiesMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.UUID;

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


    public TicketFileInfo createTicketFile(byte[] inputData, String fileType ) throws OperationFailedException {

        TicketFileInfo  ticketFile = new TicketFileInfo();
        InputStream     stream   = new ByteArrayInputStream( inputData );
        ObjectMetadata  metadata = new ObjectMetadata();
        AmazonS3        s3Client = this.getS3Client();
        UUID uuid;
        String mimeType;

        uuid = UUID.randomUUID();
        String objectPath = TICKET_IMG_BASE_PATH + uuid.toString() + "." + fileType;

        try {
            s3Client.putObject(
                    new PutObjectRequest(
                            serviceProp.getByKey(PROP_GROUP, S3_UPLOAD_BUCKET), objectPath, stream, metadata ) );
        } catch( AmazonServiceException ae ) {
            logger.error(ae.getMessage());
            throw new OperationFailedException("업로드가 실패하였습니다.");
        }

        mimeType = "image/" + fileType.toLowerCase();
        metadata.setContentLength( inputData.length );
        metadata.setContentType( mimeType );
        ticketFile.setMimeType( metadata.getContentType() );
        ticketFile.setFilePath( objectPath );

        return ticketFile;
    }

}
