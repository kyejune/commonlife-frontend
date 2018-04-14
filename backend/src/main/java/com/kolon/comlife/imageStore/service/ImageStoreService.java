package com.kolon.comlife.imageStore.service;

import com.kolon.comlife.imageStore.exception.ImageNotFoundException;
import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.postFile.service.exception.OperationFailedException;

import java.io.IOException;
import java.io.InputStream;

public interface ImageStoreService {

    ImageInfo createImage(InputStream inputStream, long imageSize, String imageType, String fileExt, int parentIdx)
            throws OperationFailedException;

    ImageInfo getImageByIdx( int idx ) throws IOException, ImageNotFoundException;

    ImageInfo getImageByIdxAndSize( int idx, String sizeStr ) throws IOException, ImageNotFoundException ;

    String getImageFullPathByIdx( int imageIdx );

    String getImageFullPathByIdx( int imageIdx, String imageSizeSuffix  );

}
