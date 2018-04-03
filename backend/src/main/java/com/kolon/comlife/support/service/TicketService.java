package com.kolon.comlife.support.service;

import com.kolon.comlife.support.model.TicketInfo;

import java.util.List;

public interface TicketService {

    TicketInfo submitTicket(TicketInfo example);

    TicketInfo submitTicketWithImage(TicketInfo newTicket, List<Integer> fileInfo, int usrId);

}
