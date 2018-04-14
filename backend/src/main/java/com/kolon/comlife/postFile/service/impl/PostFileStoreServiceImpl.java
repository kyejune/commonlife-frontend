package com.kolon.comlife.postFile.service.impl;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.PostFileStoreService;
import com.kolon.comlife.postFile.service.exception.OperationFailedException;
import com.kolon.common.prop.ServicePropertiesMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service("postFileStoreService")
public class PostFileStoreServiceImpl implements PostFileStoreService {
    private static final Logger logger = LoggerFactory.getLogger(PostFileStoreServiceImpl.class);

    private static final String POST_IMG_BASE_PATH = "origin/article/";

    private static final String PROP_GROUP         = "POST";
    private static final String S3_ACCESS_KEY      = "S3_ACCESS_KEY";
    private static final String S3_ACCESS_SECRET   = "S3_ACCESS_SECRET";

    // 업로드 버킷에 올라간 이미지는 리사이징 되어 다운로드 버킷에 저장됩니다.
    private static final String S3_UPLOAD_BUCKET   = "UP_S3_NAME";
    private static final String S3_DOWNLOAD_BUCKET = "DN_S3_NAME";

    private static final String FILE_SERVER_HOST   = "FILE_SERVER_HOST";

    private static final String DOWNLOAD_PATH = "/postFiles";

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
     * 업로드 원본 조회
     *
     * @param s3Client
     * @return ResponseEntity
     */
    private byte[] getFile( AmazonS3  s3Client,
                            String    bucket,
                            String    objectKey) throws OperationFailedException {
        byte[]      outputFile;
        S3Object    object;
        InputStream objectData;

        object = s3Client.getObject( new GetObjectRequest( bucket, objectKey ) );
        objectData = object.getObjectContent();

        try {
            outputFile = IOUtils.toByteArray( objectData );
        }
        catch( IOException e ) {
            logger.error( e.getMessage() );
            throw new OperationFailedException("해당 이미지를 가져올 수 없습니다.");
        }

        return outputFile;
    }


    public PostFileInfo createPostFile( byte[] inputData, String fileType ) throws OperationFailedException {

        PostFileInfo postFile = new PostFileInfo();
        InputStream stream = new ByteArrayInputStream( inputData );
        ObjectMetadata metadata = new ObjectMetadata();
        AmazonS3 s3Client = getS3Client();
        UUID uuid;
        String mimeType;
        
        uuid = UUID.randomUUID();
        String objectPath = POST_IMG_BASE_PATH + uuid.toString() + "." + fileType;

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
        postFile.setMimeType( metadata.getContentType() );
        postFile.setFilePath( objectPath );

        return postFile;
    }

    public byte[] getPostFile( PostFileInfo postFileInfo) throws OperationFailedException {
        byte[] outputFile;

        outputFile = this.getFile(
                        getS3Client(),
                        serviceProp.getByKey(PROP_GROUP, S3_UPLOAD_BUCKET),
                        postFileInfo.getFilePath() );

        return outputFile;
    }

    public byte[] getPostFileBySize( PostFileInfo postFileInfo, String size ) throws OperationFailedException {
        byte[] outputFile;
        String objectPath;

        // 새로운 File Path를 가져오기
        objectPath = postFileInfo.getFilePath();
        objectPath = objectPath.replace( "origin/", "resize/" + size + "/" );

        outputFile = this.getFile(
                        getS3Client(),
                        serviceProp.getByKey(PROP_GROUP, S3_DOWNLOAD_BUCKET),
                        objectPath );

        return outputFile;
    }

    public String getImageFullPathByIdx( int imageIdx ) {
        return serviceProp.getByKey(PROP_GROUP, FILE_SERVER_HOST) + DOWNLOAD_PATH + "/" + imageIdx;
    }

    public String getImageFullPathByIdx( int imageIdx, String imageSizeSuffix  ) {
        return serviceProp.getByKey(PROP_GROUP, FILE_SERVER_HOST) + DOWNLOAD_PATH + "/" + imageIdx + imageSizeSuffix;
    }


}
