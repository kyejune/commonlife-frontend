package com.kolon.comlife.postFile.service;

import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.exception.OperationFailedException;

public interface PostFileStoreService {

    PostFileInfo createPostFile( byte[] inputData, String fileType ) throws OperationFailedException;
    byte[] getPostFile( PostFileInfo postFileInfo)  throws OperationFailedException;
    byte[] getPostFileBySize( PostFileInfo postFileInfo, String size )  throws OperationFailedException;
}
