package com.kolon.comlife.admin.support.service;


import com.kolon.comlife.admin.support.exception.OperationFailedException;
import com.kolon.comlife.admin.support.model.TicketFileInfo;

public interface TicketFileStoreService {

    byte[] getTicketFile( TicketFileInfo ticketFileInfo)  throws OperationFailedException;
    byte[] getTicketFileBySize( TicketFileInfo ticketFileInfo, String size )  throws OperationFailedException;

}
