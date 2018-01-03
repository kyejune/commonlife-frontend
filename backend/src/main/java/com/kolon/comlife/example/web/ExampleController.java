package com.kolon.comlife.example.web;

import com.kolon.comlife.example.model.ExampleInfo;
import com.kolon.comlife.example.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
            produces = MediaType.APPLICATION_JSON_VALUE )
    public List<ExampleInfo> getExampleInJson() {
        List<ExampleInfo> exampleInfoList = exampleService.getExampleList();

        for( ExampleInfo e : exampleInfoList ) {
            logger.debug(">> " + e);
        }
        return exampleInfoList;
    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE )
    public void setExampleInJson(@RequestBody ExampleInfo example) {
        exampleService.setExample(example);
        return;
    }

}