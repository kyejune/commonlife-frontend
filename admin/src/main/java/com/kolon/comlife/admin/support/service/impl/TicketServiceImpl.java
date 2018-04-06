package com.kolon.comlife.admin.support.service.impl;

import com.kolon.comlife.admin.support.model.TicketFileInfo;
import com.kolon.comlife.admin.support.model.TicketInfo;
import com.kolon.comlife.admin.support.service.TicketService;
import com.kolon.comlife.common.paginate.PaginateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("ticketService")
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDAO ticketDAO;

    @Override
    public PaginateInfo getTicketList( int complexId, int pageNum, int recCntPerPage ) {
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
        params.put("cmplxId", Integer.valueOf( complexId ));

        ticketList = ticketDAO.selectTicketList( params );
        countTicket = ticketDAO.countTicketList();

        paginateInfo = new PaginateInfo();
        paginateInfo.setData( ticketList );
        paginateInfo.setCurrentPageNo( pageNum);
        paginateInfo.setRecordCountPerPage( recCntPerPage );
        paginateInfo.setPageSize( recCntPerPage );
        paginateInfo.setTotalRecordCount( countTicket );

        return paginateInfo;
    }

    public TicketInfo getTicket( int complexId, int tktIdx ) {
        Map          params;
        TicketInfo   ticketInfo;
        List         ticketFiles = new ArrayList();

        params = new HashMap();

        params.put("cmplxId", Integer.valueOf( complexId ));
        params.put("tktIdx", Integer.valueOf( tktIdx ));

        // Ticket 및 TicketFile 정보 가져오기
        ticketInfo = ticketDAO.selectTicketByIdx( params );
        ticketFiles.add( ticketDAO.getTicketFileByTiketId( tktIdx ));
        ticketInfo.setTicketFiles( ticketFiles );

        return ticketInfo;
    }

    @Override
    public TicketFileInfo getTicketFile(int id ) {

        return ticketDAO.getTicketFileByFileIdx( id );
    }


}
