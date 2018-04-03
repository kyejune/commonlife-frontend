package com.kolon.comlife.support.service.impl;

import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.support.model.TicketFileInfo;
import com.kolon.comlife.support.model.TicketInfo;
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

    public TicketInfo insertSupportTicket(TicketInfo ticketInfo ) {
        sqlSession.insert( "Support.insertSupportTicket", ticketInfo );
        return sqlSession.selectOne( "Support.selectLatestTicket" );
    }


    public List<TicketFileInfo> bindTicketToTicketFiles(int ticketIdx, List<Integer> ticketFileIdxs, int usrId ) {
        Map<String, Object> params = new HashMap<>();
        params.put( "ticketIdx", ticketIdx );
        params.put( "ticketFileIdxs", ticketFileIdxs );
        params.put( "usrId", usrId );
        sqlSession.update( "Support.bindTicketToTicketFiles", params );
        return sqlSession.selectList( "Support.selectTicketFile", params );
    }

    public TicketFileInfo setTicketFile(TicketFileInfo ticketFileInfo ) {
        sqlSession.insert( "Support.insertTicketFile", ticketFileInfo );
        return sqlSession.selectOne( "Support.selectLatestTicketFile" );
    }

}

