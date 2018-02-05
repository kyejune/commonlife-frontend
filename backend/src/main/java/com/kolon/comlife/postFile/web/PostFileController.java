package com.kolon.comlife.postFile.web;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kolon.comlife.example.web.ExampleController;
import com.kolon.comlife.postFile.model.PostFileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/postFile/*")
public class PostFileController {
    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostFileInfo> setPostFile( @RequestBody HashMap<String, String> params ) {
        String base64 = params.get( "file" );

        String[] base64Components = base64.split(",");

        if (base64Components.length != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String base64Data = base64Components[0];
        String fileType = base64Data.substring(base64Data.indexOf('/') + 1, base64Data.indexOf(';'));
        String base64Image = base64Components[1];
        byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

        InputStream stream = new ByteArrayInputStream( imageBytes );
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength( imageBytes.length );
        metadata.setContentType( "image/" + fileType );

        logger.info( ">>>>>> base64: " + base64 );
        return null;
    }
}
