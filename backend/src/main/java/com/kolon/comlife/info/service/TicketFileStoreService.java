package com.kolon.comlife.info.service;

import com.kolon.comlife.info.exception.OperationFailedException;
import com.kolon.comlife.info.model.TicketFileInfo;

public interface TicketFileStoreService {

    TicketFileInfo createTicketFile(byte[] inputData, String fileType) throws OperationFailedException;
}
