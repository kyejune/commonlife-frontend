package com.kolon.comlife.reservation.web;

import com.kolon.comlife.common.model.SimpleErrorInfo;
import com.kolon.comlife.reservation.model.ReservationInfo;
import com.kolon.comlife.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.reservation.service.ReservationGroupService;
import com.kolon.comlife.reservation.service.ReservationSchemeService;
import com.kolon.comlife.reservation.service.ReservationService;
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
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/reservations/*")
public class ReservationController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Resource(name = "reservationService")
    ReservationService service;

    @Resource(name = "reservationGroupService")
    ReservationGroupService groupService;

    @Resource(name = "reservationSchemeService")
    ReservationSchemeService schemeService;

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
        Date startDttm;
        Date endDttm;

        try {
            startDttm = dateFormat.parse( info.getStartDt() + " " + info.getStartTime() );
        }
        catch ( ParseException e ) {
            logger.debug( "Parse Exception: " + e );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "시작 일시가 유효하지 않습니다." ) );
        }
        try {
            endDttm = dateFormat.parse( info.getEndDt() + " " + info.getEndTime() );
        }
        catch ( ParseException e ) {
            logger.debug( "Parse Exception: " + e );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "종료 일시가 유효하지 않습니다." ) );
        }

        Date now = new Date();

        if( startDttm.getTime() - now.getTime() < 0 ) {
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "시작 일시를 과거로 설정할 수 없습니다." ) );
        }

        if( endDttm.getTime() - startDttm.getTime() < 0 ) {
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "종료 일시가 시작 일시보다 우선할 수 없습니다." ) );
        }

        // TODO: 관리자가 최소 예약 시간을 설정할 필요가 있는지?
        if( endDttm.getTime() - startDttm.getTime() < ( 60 * 60 ) ) {
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "60분보다 작은 시간으로 예약할 수 없습니다." ) );
        }

        // TODO: 관리자가 예약 단위를 설정할 필요가 있는지?
        if( ( endDttm.getTime() - startDttm.getTime() ) % ( 60 * 60 ) != 0 ) {
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "60분보다 작은 단위로 예약할 수 없습니다." ) );
        }

        // TODO: 수량 체크 필요

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
        Date startDttm;
        Date endDttm;

        try {
            startDttm = dateFormat.parse( info.getStartDt() + " " + "00:00:00" );
        }
        catch ( ParseException e ) {
            logger.debug( "Parse Exception: " + e );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "시작 일시가 유효하지 않습니다." ) );
        }
        try {
            endDttm = dateFormat.parse( info.getEndDt() + " " + "23:59:59" );
        }
        catch ( ParseException e ) {
            logger.debug( "Parse Exception: " + e );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "종료 일시가 유효하지 않습니다." ) );
        }

        Date now = new Date();

        if( startDttm.getTime() - now.getTime() < 0 ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("시작 일시를 과거로 설정할 수 없습니다."));
        }

        if( endDttm.getTime() - startDttm.getTime() < 0 ) {
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "종료 일시가 시작 일시보다 우선할 수 없습니다." ) );
        }

        // TODO: 수량 체크 필요

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

        try {
            startDttm = dateFormat.parse( info.getStartDt() + " " + "00:00:00" );
        }
        catch ( ParseException e ) {
            logger.debug( "Parse Exception: " + e );
            return ResponseEntity
                    .status( HttpStatus.BAD_REQUEST )
                    .body( new SimpleErrorInfo( "시작 일시가 유효하지 않습니다." ) );
        }

        Date now = new Date();

        if( startDttm.getTime() - now.getTime() < 0 ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorInfo("시작 일시를 과거로 설정할 수 없습니다."));
        }

        // TODO: 예약 상태 체크 필요
        info.setStatus( ReservationInfo.RESERVED );

        service.create( info );

        return ResponseEntity
                .status( HttpStatus.OK )
                .body( new SimpleErrorInfo( "OK" ) );
    }

//    @CrossOrigin
//    @PostMapping(
//            value = "/",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity store(
//            HttpServletRequest request
//            , @RequestParam( value = "parentIdx", required = false ) int parentIdx
//            , @RequestParam( value = "usrId", required = false ) int usrId
//            , @RequestParam( value = "startDt", required = false ) String startDt
//            , @RequestParam( value = "startTime", required = false ) String startTime
//            , @RequestParam( value = "endDt", required = false ) String endDt
//            , @RequestParam( value = "endTime", required = false ) String endTime
//            , @RequestParam( value = "qty", required = false ) int qty
//    ) {
//        ReservationSchemeInfo scheme = schemeService.show( parentIdx );
//
//        // 반드시 스키마 정보가 있어야 한다
//        if( scheme == null ) {
//            return ResponseEntity
//                    .status( HttpStatus.BAD_REQUEST )
//                    .body( new SimpleErrorInfo( "예약 정보를 찾을 수 없습니다." ) );
//        }
//
//        ReservationInfo info = new ReservationInfo();
//        info.setParentIdx( parentIdx );
//        info.setUsrID( usrId );
//        info.setStartDt( startDt );
//        info.setStartTime( startTime );
//        info.setEndDt( endDt );
//        info.setEndTime( endTime );
//        info.setQty( qty );
//
//        // 스키마로부터 포인트와 금액 정보 내려받는다
//        // 스키마가 변경되어도 예약 당시의 포인트/금액 정보는 유지되어야 하기 때문에 별도로 기록해야 한다
//        info.setPoint( scheme.getPoint() );
//        info.setAmount( scheme.getAmount() );
//
//        switch ( scheme.getReservationType() ) {
//            case "A" :
//                return bookTypeA( scheme, info );
//            case "B" :
//                return bookTypeB( scheme, info );
//            case "C" :
//                return bookTypeC( scheme, info );
//            default :
//                return ResponseEntity
//                        .status( HttpStatus.BAD_REQUEST )
//                        .body( new SimpleErrorInfo( "알 수 없는 예약 유형입니다." ) );
//        }
//    }

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

        ReservationInfo info = new ReservationInfo();
        info.setUsrID( 14 ); // TODO: 사용자 아이디 입력
        info.setStatus( "RESERVED" );
        info.setStartDt( startDt );
        info.setStartTime( startTime );
        info.setEndDt( endDt );
        info.setEndTime( endTime );
        info.setQty( 1 );

        // 틀에서 내려받을 자료들
        info.setParentIdx( scheme.getIdx() );
        info.setPoint( scheme.getPoint() );
        info.setAmount( scheme.getAmount() );

        service.create( info );

        return ResponseEntity.status( HttpStatus.OK ).body( null );
    }
}
