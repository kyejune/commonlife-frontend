package com.kolon.comlife.support.service;

import com.kolon.comlife.support.exception.OperationFailedException;
import com.kolon.comlife.support.model.TicketFileInfo;

public interface TicketFileStoreService {

    TicketFileInfo createTicketFile(byte[] inputData, String fileType) throws OperationFailedException;
}
