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
            value = "/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ExampleInfo updateExampleInJson(@PathVariable("name") String name, @RequestBody ExampleInfo example) {
        // TODO: 원래는 update 로직을 타야 하지만 현재 pk가 없어서 where 조건을 걸기 곤란하므로 delete 후 새로 insert
        exampleService.deleteExample( exampleService.getExample( name ) );
        return exampleService.updateExample(example);
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