package com.kolon.comlife.support.service.impl;

import com.kolon.comlife.support.model.TicketInfo;
import com.kolon.comlife.support.service.TicketService;
import com.kolon.comlife.users.service.impl.UserDAO;
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

    @Autowired
    private UserDAO userDAO;

//    @Autowired
//    private TicketFileDAO postFileDAO;


    @Override
    public TicketInfo submitTicket(TicketInfo post) {
        return ticketDAO.insertSupportTicket(post);
    }

    @Override
    public TicketInfo submitTicketWithImage(TicketInfo newTicket, List<Integer> filesIdList, int usrId) {

        TicketInfo            retTicketInfo = null;
//        List<TicketFileInfo>  fileInfoList;
//
//        retTicketInfo = postDAO.insertTicket( newTicket );
//
//        if( (filesIdList != null) && (filesIdList.size() > 0) ) {
//            logger.debug(">>> Ticket Files Count: " +  filesIdList.size());
//            fileInfoList = postFileDAO.bindTicketToTicketFiles(retTicketInfo.getTicketIdx(), filesIdList, usrId);
//            retTicketInfo.setTicketFiles( fileInfoList );
//        }

        return retTicketInfo;
    }
}
