package com.kolon.comlife.imageStore.model;

import com.kolon.comlife.imageStore.exception.ImageBase64Exception;
import javax.xml.bind.DatatypeConverter;

public class ImageBase64 {

    String base64Data;
    String fileType;
    byte[] byteData;

    public ImageBase64() {
        this.byteData = null;
    }

    public void parseBase64(String imageBase64) throws ImageBase64Exception {
        String   base64Image;
        String[] base64Components;

        base64Components = imageBase64.split(",");
        if (base64Components.length != 2) {
            throw new ImageBase64Exception("잘못된 데이터입니다." );
        }

        this.base64Data = base64Components[0];
        this.fileType   = base64Data.substring(base64Data.indexOf('/') + 1, base64Data.indexOf(';'));
        base64Image = base64Components[1];
        this.byteData = DatatypeConverter.parseBase64Binary(base64Image);
    }

    public byte[] getByteData() {
        return this.byteData;
    }

    public String getFileType() {
        return fileType;
    }
}
