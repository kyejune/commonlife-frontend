package com.kolon.comlife.admin.post.service;

import com.kolon.comlife.admin.post.exception.OperationFailedException;
import com.kolon.comlife.admin.post.model.PostFileInfo;

public interface PostFileStoreService {

    PostFileInfo createPostFile(byte[] inputData, String fileType) throws OperationFailedException;
    byte[] getPostFile(PostFileInfo postFileInfo)  throws OperationFailedException;
    byte[] getPostFileBySize(PostFileInfo postFileInfo, String size)  throws OperationFailedException;

    String getImageFullPathByIdx(int imageIdx) ;
    String getImageFullPathByIdx(int imageIdx, String imageSizeSuffix) ;

}
