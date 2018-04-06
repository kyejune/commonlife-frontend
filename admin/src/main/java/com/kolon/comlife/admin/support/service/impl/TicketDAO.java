package com.kolon.comlife.admin.support.service.impl;

import com.kolon.comlife.admin.support.model.TicketFileInfo;;
import com.kolon.comlife.admin.support.model.TicketInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("ticketDAO")
public class TicketDAO {
    private static final Logger logger = LoggerFactory.getLogger(TicketDAO.class);

    @Resource
    private SqlSession sqlSession;

    /////////  Ticket  /////////

    public int countTicketList() {
        return sqlSession.selectOne( "Support.countTicketList" );
    }

    public List<TicketInfo> selectTicketList( Map params ) {
        return sqlSession.selectList("Support.selectTicketList", params );
    }

    public TicketInfo selectTicketByIdx( Map params ) {
        return sqlSession.selectOne("Support.selectTicketList", params );
    }


    /////////  Ticket File  /////////
    public TicketFileInfo getTicketFileByFileIdx( int ticketFileIdx ) {
        Map<String, Integer> selectParams = new HashMap<>();
        selectParams.put( "ticketFileIdx", ticketFileIdx );
        return sqlSession.selectOne( "Support.selectTicketFile", selectParams );
    }

    public TicketFileInfo getTicketFileByTiketId( int ticketIdx ) {
        Map<String, Integer> selectParams = new HashMap<>();
        selectParams.put( "ticketIdx", ticketIdx );
        return sqlSession.selectOne( "Support.selectTicketFile", selectParams );
    }


}

