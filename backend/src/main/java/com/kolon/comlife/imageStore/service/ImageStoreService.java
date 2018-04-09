package com.kolon.comlife.imageStore.service;

import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.exception.OperationFailedException;

import java.io.IOException;
import java.io.InputStream;

public interface ImageStoreService {

    ImageInfo createImage( InputStream inputStream, long imageSize, String imageType, String fileExt )
            throws OperationFailedException;

    ImageInfo getImageByIdx( int idx ) throws IOException ;


}
