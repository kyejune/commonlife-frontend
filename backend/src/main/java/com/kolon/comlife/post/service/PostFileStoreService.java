package com.kolon.comlife.post.service;

import com.kolon.comlife.post.model.PostFileInfo;
import com.kolon.comlife.post.exception.OperationFailedException;

public interface PostFileStoreService {

    PostFileInfo createPostFile( byte[] inputData, String fileType ) throws OperationFailedException;
    byte[] getPostFile( PostFileInfo postFileInfo)  throws OperationFailedException;
    byte[] getPostFileBySize( PostFileInfo postFileInfo, String size )  throws OperationFailedException;

    String getImageFullPathByIdx( int imageIdx ) ;
    String getImageFullPathByIdx( int imageIdx, String imageSizeSuffix  ) ;

}
