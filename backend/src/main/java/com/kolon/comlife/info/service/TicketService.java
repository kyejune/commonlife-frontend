package com.kolon.comlife.info.service;

import com.kolon.comlife.info.model.TicketFileInfo;
import com.kolon.comlife.info.model.TicketInfo;

import java.util.List;

public interface TicketService {

    TicketInfo submitTicket(TicketInfo example);

    TicketInfo submitTicketWithImage(TicketInfo newTicket, List<Integer> fileInfo, int usrId);

    TicketFileInfo setTicketFile(TicketFileInfo ticketFileInfo );


}
