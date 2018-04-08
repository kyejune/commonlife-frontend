package com.kolon.comlife.imageStore.service;

import com.kolon.comlife.imageStore.model.ImageInfo;
import com.kolon.comlife.postFile.service.exception.OperationFailedException;

public interface ImageStoreService {

    ImageInfo createImage(byte[] inputData, String imageType, String fileExt ) throws OperationFailedException;

}
