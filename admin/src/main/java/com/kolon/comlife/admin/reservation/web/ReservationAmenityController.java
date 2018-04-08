package com.kolon.comlife.admin.reservation.web;

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
import com.kolon.comlife.admin.reservation.model.ReservationAmenityIconInfo;
import com.kolon.comlife.admin.reservation.model.ReservationAmenityInfo;
import com.kolon.comlife.admin.reservation.service.ReservationAmenityIconService;
import com.kolon.comlife.admin.reservation.service.ReservationAmenityService;
import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.common.prop.ServicePropertiesMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Controller("reservationAmenityController")
@RequestMapping("admin/reservation-amenities/*")
public class ReservationAmenityController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationAmenityController.class);

    private static final String POST_IMG_BASE_PATH = "origin/reservation-amenity-icons/";

    private static final String PROP_GROUP         = "POST";
    private static final String S3_ACCESS_KEY      = "S3_ACCESS_KEY";
    private static final String S3_ACCESS_SECRET   = "S3_ACCESS_SECRET";

    private static final String S3_UPLOAD_BUCKET   = "UP_S3_NAME";
    private static final String S3_DOWNLOAD_BUCKET = "DN_S3_NAME";

    @Resource(name = "reservationAmenityService")
    private ReservationAmenityService service;

    @Resource(name = "reservationAmenityIconService")
    private ReservationAmenityIconService iconService;

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

    @RequestMapping(value = "list.do")
    public ModelAndView listReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
        List<ReservationAmenityInfo> amenities = service.index();

        mav.addObject( "amenities", amenities );

        return mav;
    }

    @RequestMapping(value = "create.do")
    public ModelAndView createReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
    ) {
//        List<ReservationSchemeInfo> schemes = schemeService.index( new HashMap() );
//        mav.addObject( "schemes", schemes );
        return mav;
    }

    @RequestMapping(value = "create.do"
            , method = RequestMethod.POST
    )
    public String storeReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "image" ) MultipartFile image
            , @RequestParam( value = "name" ) String name
            ) {

        AmazonS3 s3Client = getS3Client();
        UUID uuid;
        String ext = image.getContentType().split( "/" )[ 1 ];

        uuid = UUID.randomUUID();
        String objectPath = POST_IMG_BASE_PATH + uuid.toString() + "." + ext;
        ObjectMetadata metadata = new ObjectMetadata();

        // 멀티파트 파일을 인풋스트림으로 변환
        InputStream inputStream;
        try {
            inputStream =  new BufferedInputStream(image.getInputStream());
        }
        catch( IOException e ) {
            logger.error(e.getMessage());
            // throw new IOException("업로드가 실패하였습니다.");
            return "redirect:" + request.getHeader( "referer" );
        }

        // S3에 저장
        try {
            s3Client.putObject(
                    new PutObjectRequest(
                            serviceProp.getByKey(PROP_GROUP, S3_UPLOAD_BUCKET), objectPath, inputStream, metadata ) );
        } catch( AmazonServiceException ae ) {
            logger.error(ae.getMessage());
            // throw new OperationFailedException("업로드가 실패하였습니다.");
            return "redirect:" + request.getHeader( "referer" );
        }

        // 아이콘 데이터 저장
        ReservationAmenityIconInfo iconInfo = new ReservationAmenityIconInfo();
        iconInfo.setMimeType( image.getContentType() );
        iconInfo.setFileName( image.getOriginalFilename() );
        iconInfo.setFilePath( objectPath );
        ReservationAmenityIconInfo savedIcon = iconService.create( iconInfo );

        // 어매니티 데이터 저장
        ReservationAmenityInfo info = new ReservationAmenityInfo();
        info.setIconIdx( savedIcon.getIdx() );
        info.setName( name );

        service.create( info );

        return "redirect:" + "/admin/reservation-amenities/list.do";
    }

    @RequestMapping(value = "icon.do")
    public ResponseEntity getIconImage(
            @RequestParam(value = "idx") int idx
    ) {
        byte[]       outputFile;
        HttpHeaders headers;
        ReservationAmenityIconInfo icon = iconService.show( idx );

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

    @RequestMapping(value = "edit.do")
    public ModelAndView editReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , ModelAndView mav
            , HttpSession session
            , @RequestParam(value = "idx") int idx
    ) {
        ReservationAmenityInfo amenity = service.show( idx );
        mav.addObject( "amenity", amenity );
        return mav;
    }

    @RequestMapping(value = "edit.do"
            , method = RequestMethod.POST
    )
    public String updateReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "idx", defaultValue = "0") int idx
            , @RequestParam( value = "image", required = false) MultipartFile image
            , @RequestParam( value = "name" ) String name
    ) {
        ReservationAmenityInfo info = service.show( idx );
        info.setName( name );
        service.update( info );

        return "redirect:" + "/admin/reservation-amenities/list.do";
    }

    @RequestMapping(value = "delete.do"
            , method = RequestMethod.POST
    )
    public String deleteReservationScheme (
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @RequestParam( value = "idx", defaultValue = "0") int idx
    ) {
        ReservationAmenityInfo info = service.show( idx );
        service.delete( info );

        return "redirect:" + "/admin/reservation-amenities/list.do";
    }

}
