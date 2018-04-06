package com.kolon.comlife.admin.support.service;

import com.kolon.comlife.admin.support.model.TicketFileInfo;
import com.kolon.comlife.admin.support.model.TicketInfo;
import com.kolon.comlife.common.paginate.PaginateInfo;

public interface TicketService {

    PaginateInfo getTicketList( int complxId, int pageNum, int recCntPerPage );

    TicketInfo getTicket(int complexId, int tktIdx );

    TicketFileInfo getTicketFile(int id );

}
