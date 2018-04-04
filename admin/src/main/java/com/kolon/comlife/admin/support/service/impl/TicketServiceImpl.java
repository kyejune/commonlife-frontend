package com.kolon.comlife.admin.support.service.impl;

import com.kolon.comlife.admin.support.model.TicketFileInfo;
import com.kolon.comlife.admin.support.model.TicketInfo;
import com.kolon.comlife.admin.support.service.TicketService;
import com.kolon.comlife.common.model.PaginateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("ticketService")
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public PaginateInfo getTicketList( int complxId, int pageNum, int recCntPerPage ) {
        int          offset;
        int          countTicket;
        int          totalPages;
        Map          params;
        PaginateInfo paginateInfo;
        List<TicketInfo> ticketList;

        params = new HashMap();

        offset = recCntPerPage * ( pageNum - 1 );
        params.put("limit", Integer.valueOf( recCntPerPage ));
        params.put("offset", Integer.valueOf( offset ));
        params.put("cmplxId", Integer.valueOf( complxId ));

        ticketList = ticketDAO.selectTicketList( params );
        countTicket = ticketDAO.countTicketList();

        totalPages = (int) Math.ceil( (double)countTicket / (double)pageNum );

        paginateInfo = new PaginateInfo();
        paginateInfo.setData( ticketList );
        paginateInfo.setCurrentPage( pageNum );
        paginateInfo.setPerPage( recCntPerPage );
        paginateInfo.setTotalPages( totalPages );

        return paginateInfo;
    }

    @Override
    public TicketFileInfo getTicketFile(int id ) {

        return ticketDAO.getTicketFile( id );
    }


}
