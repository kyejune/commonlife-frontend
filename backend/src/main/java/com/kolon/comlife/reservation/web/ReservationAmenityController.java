package com.kolon.comlife.reservation.web;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.reservation.model.ReservationAmenityIconInfo;
import com.kolon.comlife.reservation.model.ReservationInfo;
import com.kolon.comlife.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.reservation.service.ReservationAmenityIconService;
import com.kolon.comlife.reservation.service.ReservationSchemeService;
import com.kolon.comlife.reservation.service.ReservationService;
import com.kolon.common.model.AuthUserInfo;
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
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reservation-amenities/*")
public class ReservationAmenityController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationAmenityController.class);

    private static final String POST_IMG_BASE_PATH = "origin/reservation-amenity-icons/";

    private static final String PROP_GROUP         = "POST";
    private static final String S3_ACCESS_KEY      = "S3_ACCESS_KEY";
    private static final String S3_ACCESS_SECRET   = "S3_ACCESS_SECRET";

    private static final String S3_UPLOAD_BUCKET   = "UP_S3_NAME";
    private static final String S3_DOWNLOAD_BUCKET = "DN_S3_NAME";

    @Resource(name = "reservationAmenityIconService")
    private ReservationAmenityIconService iconService;

    @Resource(name = "servicePropertiesMap")
    ServicePropertiesMap serviceProp;

    SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

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

    @CrossOrigin
    @GetMapping(
            value = "/{id}/icon"
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(
            HttpServletRequest request
            , @PathVariable("id") int id
    ) {
        byte[]       outputFile;
        HttpHeaders headers;
        ReservationAmenityIconInfo icon = iconService.show( id );

        if( icon == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new SimpleErrorInfo("해당하는 이미지가 없습니다."));
        }

        AmazonS3 s3Client = getS3Client();
        S3Object object;
        InputStream objectData;

        object = s3Client.getObject( new GetObjectRequest( serviceProp.getByKey(PROP_GROUP, S3_UPLOAD_BUCKET), icon.getFilePath() ) );
        objectData = object.getObjectContent();

        try {
            outputFile = IOUtils.toByteArray( objectData );
        }
        catch( IOException e ) {
            logger.error( e.getMessage() );
            return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( null );
        }

        headers = new HttpHeaders();
        switch ( icon.getMimeType() ) {
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

        return new ResponseEntity(outputFile, headers, HttpStatus.OK);
    }
}
