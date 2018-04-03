package com.kolon.comlife.support.service.impl;

import com.kolon.comlife.support.model.TicketInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("ticketDAO")
public class TicketDAO {
    private static final Logger logger = LoggerFactory.getLogger(TicketDAO.class);

    @Resource
    private SqlSession sqlSession;

    public TicketInfo insertSupportTicket(TicketInfo post) {
        sqlSession.insert( "Support.insertSupportTicket", post );
        return sqlSession.selectOne( "Support.selectLatestTicket" );
    }
}

