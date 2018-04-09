package com.kolon.comlife.imageStore.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.exception.OperationFailedException;
import com.kolon.comlife.postFile.service.impl.PostFileStoreServiceImpl;
import com.kolon.common.prop.ServicePropertiesMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service("imageStoreService")
public class ImageStoreServiceImpl implements ImageStoreService {
    private static final Logger logger = LoggerFactory.getLogger(PostFileStoreServiceImpl.class);

    private static final String IMAGE_STORE_BASE_PATH = "origin/";

    private static final String PROP_GROUP         = "IMAGE_STORE";
    private static final String S3_ACCESS_KEY      = "S3_ACCESS_KEY";
    private static final String S3_ACCESS_SECRET   = "S3_ACCESS_SECRET";
    private static final String S3_BUCKET_REGION   = "S3_BUCKET_REGION"; // ex) "ap-northeast-2"

    // [업로드 버킷]에 업로드된 이미지는 lambda-image-resizer에 의해 리사이징 되어 [다운로드 버킷]에 비동기로 저장됩니다.
    private static final String S3_BUCKET_UPLOAD   = "S3_BUCKET_UPLOAD"; // ex) "cl.imagestore.origin"
    private static final String S3_BUCKET_DOWNLOAD = "S3_BUCKET_DOWNLOAD"; // ex) "cl.imagestore.target"

    @Resource(name = "servicePropertiesMap")
    ServicePropertiesMap serviceProp;

    @Autowired
    ImageInfoDAO imageInfoDAO;


    /**
     * AWS S3 Client 객체 생성
     *
     * @return AmazonS3
     */
    private AmazonS3 getS3Client() {
        logger.debug(">>>>>>>>>>>>>> serviceProp: " + serviceProp );
        logger.debug(">>>>>>>>>>>>>> serviceProp:S3_ACCESS_KEY: " + serviceProp.getByKey(PROP_GROUP, S3_ACCESS_KEY) );
        logger.debug(">>>>>>>>>>>>>> serviceProp:S3_ACCESS_SECRET: " + serviceProp.getByKey(PROP_GROUP, S3_ACCESS_SECRET) );
        AWSCredentials credentials = new BasicAWSCredentials(
                serviceProp.getByKey(PROP_GROUP, S3_ACCESS_KEY),
                serviceProp.getByKey(PROP_GROUP, S3_ACCESS_SECRET) );
        return AmazonS3ClientBuilder
                .standard()
                .withRegion( serviceProp.getByKey(PROP_GROUP, S3_BUCKET_REGION) )
                .withCredentials( new AWSStaticCredentialsProvider(credentials) )
                .build();
    }

    /**
     * 업로드 원본 조회
     *
     * @param s3Client
     * @return ResponseEntity
     */
    private InputStream getObjectInputStream( AmazonS3  s3Client,
                            String    bucket,
                            String    objectKey) {
        byte[]      outputFile;
        S3Object    object;
        InputStream objectData;

        object = s3Client.getObject( new GetObjectRequest( bucket, objectKey ) );
        objectData = object.getObjectContent();

//        try {
//            outputFile = IOUtils.toByteArray( objectData );
//        }
//        catch( IOException e ) {
//            logger.error( e.getMessage() );
//            throw new OperationFailedException("해당 이미지를 가져올 수 없습니다.");
//        }

        return objectData;
    }


    public ImageInfo createImage(InputStream inputStream, long imageSize, String imageType, String fileExt )
            throws OperationFailedException
    {
        ImageInfo       imageInfo = new ImageInfo();
        ObjectMetadata  metadata = new ObjectMetadata();
        AmazonS3        s3Client = getS3Client();
        UUID            uuid;
        String          fileName;
        String          objectPath;
        Integer         imageTypeIdx;

        // Validation
        if( !(ImageInfoUtil.isSupportedType( imageType )) ) {
            throw new OperationFailedException("지원하지 않는 타입 입니다. " + imageType);
        }

        imageTypeIdx = imageInfoDAO.getImageTypeByTypeNm( imageType );
        if( imageTypeIdx == null ) {
            throw new OperationFailedException("정의되지 않은 타입 입니다. " + imageType);
        }

        uuid       = UUID.randomUUID();
        fileName   = uuid.toString() + "." + fileExt;
        objectPath = IMAGE_STORE_BASE_PATH + ImageInfoUtil.getImageTypePath( imageType ) + fileName;
        logger.debug(">> object path of stored image: " + objectPath);

        // Execution - file upload
        try {
            metadata.setContentLength( imageSize );
            metadata.setContentType( "image/" + fileExt );

            s3Client.putObject(
                    new PutObjectRequest(
                            serviceProp.getByKey(PROP_GROUP, S3_BUCKET_UPLOAD), objectPath, inputStream, metadata ) );
        } catch( AmazonServiceException ae ) {
            logger.error(ae.getMessage());
            throw new OperationFailedException("업로드가 실패하였습니다.");
        }

        imageInfo.setMimeType( metadata.getContentType() );
        imageInfo.setFilePath( objectPath );
        imageInfo.setFileNm( fileName );
        imageInfo.setParentType( imageTypeIdx );
        imageInfo.setImageSize( imageSize );

        // Execution - table update
        imageInfo = imageInfoDAO.setImageFile( imageInfo );
        imageInfo.setImageType( imageType );

        return imageInfo;
    }

    public ImageInfo getImageByIdx( int idx ) throws IOException {
        ImageInfo    imageInfo;
        InputStream  inputStream;

        imageInfo = imageInfoDAO.getImageFile( idx );
        inputStream = this.getObjectInputStream( getS3Client(),
                                                 serviceProp.getByKey(PROP_GROUP, S3_BUCKET_UPLOAD),
                                                 imageInfo.getFilePath() );

        imageInfo.setImageByteArray( IOUtils.toByteArray( inputStream ) );

        return imageInfo;
    }
//
//    public byte[] getPostFileBySize( PostFileInfo postFileInfo, String size ) throws OperationFailedException {
//        byte[] outputFile;
//        String objectPath;
//
//        // 새로운 File Path를 가져오기
//        objectPath = postFileInfo.getFilePath();
//        objectPath = objectPath.replace( "origin/", "resize/" + size + "/" );
//
//        outputFile = this.getFile(
//                getS3Client(),
//                serviceProp.getByKey(PROP_GROUP, S3_BUCKET_DOWNLOAD),
//                objectPath );
//
//        return outputFile;
//    }
}
