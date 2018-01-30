package com.kolon.comlife.example.web;

import com.kolon.comlife.example.model.ExampleInfo;
import com.kolon.comlife.example.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Example Controller
 */
@RestController
@RequestMapping("/example/*")
public class ExampleController {
    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @Resource(name = "exampleService")
    private ExampleService exampleService;

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExampleInfo> getExampleInJson() {
        List<ExampleInfo> exampleInfoList = exampleService.getExampleList();

        for (ExampleInfo e : exampleInfoList) {
            logger.debug(">> " + e);
        }
        return exampleInfoList;
    }

//    @PostMapping(
//            value = "/",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ExampleInfo setExampleInJson(@RequestBody ExampleInfo example) {
//        return exampleService.setExample(example);
//    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExampleInfo> setExampleInJson(@RequestBody ExampleInfo example) {
        ExampleInfo info;

        try {
            info = exampleService.setExample(example);
        } catch (org.springframework.dao.DuplicateKeyException dupKeyEx) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        // @ResponseBody 대신 ResponseEntity로 HttpStatus 및 body를 함께 반환 할 수 있습니다
        // 참조 : https://stackoverflow.com/questions/16232833/how-to-respond-with-http-400-error-in-a-spring-mvc-responsebody-method-returnin
        logger.debug(">> " + info);
        return ResponseEntity.status(HttpStatus.CREATED).body(info);
    }

    @GetMapping(
            value = "/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ExampleInfo getExampleOneInJson(@PathVariable("name") String name) {
        return exampleService.getExample(name);
    }


    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExampleInfo> updateExampleInJson(@PathVariable("id") int id,
                                                           @RequestBody ExampleInfo example) {

        // PUT에 대한 예외 처리 참조
        // 참조 : https://stackoverflow.com/questions/2342579/http-status-code-for-update-and-delete
        int updatedCount = 0;

        try {
            example.setId(id);
            updatedCount = exampleService.updateExample(example);
        } catch (org.springframework.dao.DuplicateKeyException dupKeyEx) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        if( updatedCount == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping(
            value = "/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteExampleInJson(@PathVariable("name") String name) {
        ExampleInfo example = exampleService.getExample( name );
        exampleService.deleteExample(example);
        return;
    }

}