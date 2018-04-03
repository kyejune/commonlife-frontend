package com.kolon.comlife.support.service.impl;

import com.kolon.comlife.support.model.TicketFileInfo;
import com.kolon.comlife.support.model.TicketInfo;
import com.kolon.comlife.support.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ticketService")
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDAO ticketDAO;

//    @Autowired
//    private TicketFileDAO postFileDAO;


    @Override
    public TicketInfo submitTicket(TicketInfo post) {
        return ticketDAO.insertSupportTicket(post);
    }

    @Override
    public TicketInfo submitTicketWithImage(TicketInfo newTicket, List<Integer> filesIdList, int usrId) {

        TicketInfo            retTicketInfo = null;
        List<TicketFileInfo>  fileInfoList;

        retTicketInfo = ticketDAO.insertSupportTicket( newTicket );

        if( (filesIdList != null) && (filesIdList.size() > 0) ) {
            logger.debug(">>> Ticket Files Count: " +  filesIdList.size());
            fileInfoList = ticketDAO.bindTicketToTicketFiles( retTicketInfo.getTktIdx(), filesIdList, usrId );
            retTicketInfo.setTicketFiles( fileInfoList );
        }

        return retTicketInfo;
    }

    @Override
    public TicketFileInfo setTicketFile(TicketFileInfo ticketFileInfo ) {
        return ticketDAO.setTicketFile( ticketFileInfo );
    }
}
