package com.kolon.comlife.admin.support.service;

import com.kolon.comlife.admin.support.model.TicketFileInfo;
import com.kolon.comlife.common.model.PaginateInfo;

public interface TicketService {

    PaginateInfo getTicketList( int complxId, int pageNum, int recCntPerPage );

    TicketFileInfo getTicketFile(int id );

}
