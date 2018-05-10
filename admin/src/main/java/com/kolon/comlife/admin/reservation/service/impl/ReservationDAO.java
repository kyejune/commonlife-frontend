package com.kolon.comlife.admin.reservation.service.impl;

import com.kolon.comlife.admin.reservation.model.ReservationInfo;
import com.kolon.comlife.admin.reservation.model.ReservationSchemeInfo;
import com.kolon.comlife.admin.reservation.model.ReservationSchemeOptionInfo;
import com.kolon.comlife.admin.reservation.string.Reservation;
import com.kolon.comlife.admin.users.model.UserExtInfo;
import com.kolon.comlife.admin.users.model.UserInfo;
import com.kolon.comlife.admin.users.service.impl.UserDAO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository("reservationDAO")
public class ReservationDAO {
    @Resource
    private SqlSession sqlSession;

    @Resource(name="reservationSchemeDAO")
    private ReservationSchemeDAO schemeDAO;

    @Resource(name="reservationSchemeOptionDAO")
    private ReservationSchemeOptionDAO optionDAO;

    @Resource(name="userDAO")
    private UserDAO userDAO;

    public List<ReservationInfo> index() {
        return sqlSession.selectList( "Reservation.index" );
    }

    public List<ReservationInfo> index( HashMap params ) {
        return sqlSession.selectList( "Reservation.index", params );
    }

    public ReservationInfo show( int idx ) {
        HashMap params = new HashMap<String, Object>();
        params.put("idx", idx);
        ReservationInfo reservation = sqlSession.selectOne("Reservation.show", params);

        ReservationSchemeInfo scheme = schemeDAO.show( reservation.getParentIdx() );
        reservation.setScheme( scheme );

        UserExtInfo user = userDAO.selectUserExtInfoByUsrId( reservation.getUsrID() );
        reservation.setUser( user );

        if( reservation.getOptionIdx() > 0 ) {
            ReservationSchemeOptionInfo option = optionDAO.show( reservation.getOptionIdx() );
            reservation.setOption( option );
        }

        return reservation;
    }

    public int create( ReservationInfo info ) {
        return sqlSession.insert( "Reservation.create", info );
    }

    public int update( ReservationInfo info ) {
        return sqlSession.update("Reservation.update", info);
    }

    public int delete( ReservationInfo info ) {
        return sqlSession.delete("Reservation.delete", info);
    }

    public List<ReservationInfo> queue() {
        return sqlSession.selectList( "Reservation.queue" );
    }

    public int updateStatus( ReservationInfo info ) {
        return sqlSession.update( "Reservation.updateStatus", info );
    }
}
