package com.kolon.comlife.reservation.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.reservation.model.ReservationInfo;
import com.kolon.comlife.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.reservation.service.ReservationGroupService;
import com.kolon.comlife.reservation.service.ReservationSchemeService;
import com.kolon.comlife.reservation.service.ReservationService;
import com.kolon.common.model.AuthUserInfo;
import com.kolon.common.servlet.AuthUserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reservations/*")
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Resource(name = "reservationService")
    ReservationService service;

    @Resource(name = "reservationSchemeService")
    ReservationSchemeService schemeService;

    @Resource(name = "complexService")
    ComplexService complexService;

    SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    @CrossOrigin
    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(HttpServletRequest request) {

        return null;
    }

    /**
     * A 타입 예약
     * @param scheme
     * @param info
     * @return
     */
    private ResponseEntity bookTypeA( ReservationSchemeInfo scheme, ReservationInfo info ) {
//        Date startDttm;
//        Date endDttm;
//
//        try {
//            startDttm = dateFormat.parse( info.getStartDt() + " " + "00:00:00" );
//        }
//        catch ( ParseException e ) {
//            logger.debug( "Parse Exception: " + e );
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body( new SimpleErrorInfo( "시작 일시가 유효하지 않습니다." ) );
//        }
//        try {
//            endDttm = dateFormat.parse( info.getEndDt() + " " + "23:59:59" );
//        }
//        catch ( ParseException e ) {
//            logger.debug( "Parse Exception: " + e );
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body( new SimpleErrorInfo( "종료 일시가 유효하지 않습니다." ) );
//        }
//
//        Date now = new Date();
//
//        if( startDttm.getTime() - now.getTime() < 0 ) {
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body( new SimpleErrorInfo( "시작 일시를 과거로 설정할 수 없습니다." ) );
//        }
//
//        if( endDttm.getTime() - startDttm.getTime() < 0 ) {
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body( new SimpleErrorInfo( "종료 일시가 시작 일시보다 우선할 수 없습니다." ) );
//        }

        info.setStatus( ReservationInfo.RESERVED );

        service.create( info );

        return ResponseEntity
                .status( HttpStatus.OK )
                .body( new SimpleErrorInfo( "OK" ) );
    }

    /**
     * B 타입 예약
     * @param scheme
     * @param info
     * @return
     */
    private ResponseEntity bookTypeB( ReservationSchemeInfo scheme, ReservationInfo info ) {
//        Date startDttm;
//        Date endDttm;
//
//        try {
//            startDttm = dateFormat.parse( info.getStartDt() + " " + "00:00:00" );
//        }
//        catch ( ParseException e ) {
//            logger.debug( "Parse Exception: " + e );
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body( new SimpleErrorInfo( "시작 일시가 유효하지 않습니다." ) );
//        }
//        try {
//            endDttm = dateFormat.parse( info.getEndDt() + " " + "23:59:59" );
//        }
//        catch ( ParseException e ) {
//            logger.debug( "Parse Exception: " + e );
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body( new SimpleErrorInfo( "종료 일시가 유효하지 않습니다." ) );
//        }
//
//        Date now = new Date();
//
//        if( startDttm.getTime() - now.getTime() < 0 ) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(new SimpleErrorInfo("시작 일시를 과거로 설정할 수 없습니다."));
//        }
//
//        if( endDttm.getTime() - startDttm.getTime() < 0 ) {
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body( new SimpleErrorInfo( "종료 일시가 시작 일시보다 우선할 수 없습니다." ) );
//        }

        info.setStatus( ReservationInfo.RESERVED );

        service.create( info );

        return ResponseEntity
                .status( HttpStatus.OK )
                .body( new SimpleErrorInfo( "OK" ) );
    }

    /**
     * C 타입 예약
     * @param scheme
     * @param info
     * @return
     */
    private ResponseEntity bookTypeC( ReservationSchemeInfo scheme, ReservationInfo info ) {
        Date startDttm;

//        try {
//            startDttm = dateFormat.parse( info.getStartDt() + " " + "00:00:00" );
//        }
//        catch ( ParseException e ) {
//            logger.debug( "Parse Exception: " + e );
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body( new SimpleErrorInfo( "시작 일시가 유효하지 않습니다." ) );
//        }
//
//        Date now = new Date();
//
//        if( startDttm.getTime() - now.getTime() < 0 ) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(new SimpleErrorInfo("시작 일시를 과거로 설정할 수 없습니다."));
//        }

        info.setStatus( ReservationInfo.IN_QUEUE );

        service.create( info );

        return ResponseEntity
                .status( HttpStatus.OK )
                .body( new SimpleErrorInfo( "OK" ) );
    }

    @CrossOrigin
    @GetMapping(
            value = "/my",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity myReservations( HttpServletRequest request ) {
        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );

        // TODO: 로컬에서 인증 정보가 없으면 yunamkim(userId: 632)을 기본값으로 출력한다. 추후 수정 필요.
        int idx = 632;
        if( authUserInfo != null ) {
            idx = authUserInfo.getUsrId();
        }

        HashMap params = new HashMap();
        params.put( "userId", idx );
        List<ReservationInfo> reservations = service.index( params );

        // 아이디 리스트를 뽑아 스키마 출력
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for ( ReservationInfo e : reservations ) {
            ids.add( e.getParentIdx() );
        }

        if( ids.size() > 0 ) {
            HashMap schemeParams = new HashMap();
            schemeParams.put( "ids", ids );
            List<ReservationSchemeInfo> schemes = schemeService.index( schemeParams );

            // 스키마에서 현장 아이디를 뽑아서...
            ids = new ArrayList<Integer>();

            for ( ReservationSchemeInfo s : schemes ) {
                ids.add( s.getCmplxIdx() );
                for ( ReservationInfo r : reservations ) {
                    if( s.getIdx() == r.getParentIdx() ) {
                        r.setScheme( s );
                        continue;
                    }
                }
            }

            List<ComplexInfo> complexes = complexService.getComplexList();

            for ( ComplexInfo c : complexes) {
                for ( ReservationSchemeInfo s : schemes ) {
                    if( c.getCmplxId() == s.getCmplxIdx() ) {
                        s.setComplex( c );
                    }
                }
            }

        }

        return ResponseEntity.status( HttpStatus.OK ).body( reservations );
    }

    @CrossOrigin
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity show( HttpServletRequest request, @PathVariable("id") int id ) {
        ReservationInfo info = service.show( id );

        return ResponseEntity.status( HttpStatus.OK ).body( info );
    }

    @CrossOrigin
    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity store(
            HttpServletRequest request
            , @RequestBody HashMap<String, Object> params
    ) {
        int parentIdx = Integer.parseInt( params.get( "parentIdx" ).toString() );
        String startDt = params.get( "startDt" ).toString();
        String startTime = params.get( "startTime" ).toString();
        String endDt = params.get( "endDt" ).toString();
        String endTime = params.get( "endTime" ).toString();

        // 예약 틀
        ReservationSchemeInfo scheme = schemeService.show( parentIdx );

        AuthUserInfo authUserInfo = AuthUserInfoUtil.getAuthUserInfo( request );

        // TODO: 로컬에서 인증 정보가 없으면 yunamkim(userId: 632)을 기본값으로 출력한다. 추후 수정 필요.
        int idx = 632;
        if( authUserInfo != null ) {
            idx = authUserInfo.getUsrId();
        }

        ReservationInfo info = new ReservationInfo();
        info.setUsrID( idx );
        info.setStartDt( startDt );
        info.setStartTime( startTime );
        info.setEndDt( endDt );
        info.setEndTime( endTime );
        info.setQty( 1 );

        // 틀에서 내려받을 자료들
        info.setParentIdx( scheme.getIdx() );
        info.setPoint( scheme.getPoint() );
        info.setAmount( scheme.getAmount() );

        switch ( scheme.getReservationType() ) {
            case "A" :
                return bookTypeA( scheme, info );
            case "B" :
                return bookTypeB( scheme, info );
            case "C" :
                return bookTypeC( scheme, info );
            default :
                return ResponseEntity
                        .status( HttpStatus.BAD_REQUEST )
                        .body( new SimpleErrorInfo( "알 수 없는 예약 유형입니다." ) );
        }
    }
}
