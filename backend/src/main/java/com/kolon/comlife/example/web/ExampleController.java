package com.kolon.comlife.example.web;

import com.kolon.comlife.example.model.ExampleInfo;
import com.kolon.comlife.example.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

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
            produces = "application/json")
    public ExampleInfo getExampleInJson() {
        ExampleInfo info = exampleService.getExample();
        return info;
    }

    @PostMapping(
            value = "/",
            produces = "application/json")
    public void setExampleInJson(@RequestBody ExampleInfo example) {
        exampleService.setExample(example);
        return;
    }

}