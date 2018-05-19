package com.kolon.comlife.admin.imageStore.service;


import com.kolon.comlife.admin.imageStore.exception.ImageNotFoundException;
import com.kolon.comlife.admin.imageStore.model.ImageInfo;
import com.kolon.comlife.admin.support.exception.OperationFailedException;

import java.io.IOException;
import java.io.InputStream;

public interface ImageStoreService {

    ImageInfo createImage( InputStream  inputStream,
                           long         imageSize,
                           String       imageType,
                           String       fileExt,
                           int          usrId,
                           int          parentIdx )
            throws OperationFailedException;

    ImageInfo getImageByIdx( int idx ) throws IOException, ImageNotFoundException;

    ImageInfo getImageInfoByIdx( int idx ) throws ImageNotFoundException;

    ImageInfo getImageByIdxAndSize( int idx, String sizeStr ) throws IOException, ImageNotFoundException ;

    String getImageFullPathByIdx( int imageIdx );

    String getImageFullPathByIdx( int imageIdx, String imageSizeSuffix );

    ImageInfo updateImageParentIdx( int imageIdx, int parentIdx ) throws OperationFailedException;

}
