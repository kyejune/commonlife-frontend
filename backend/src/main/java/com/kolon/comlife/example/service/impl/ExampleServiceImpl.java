package com.kolon.comlife.example.service.impl;

import com.kolon.comlife.example.model.ExampleInfo;
import com.kolon.comlife.example.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 */
@Service("exampleService")
public class ExampleServiceImpl implements ExampleService {
    public static final Logger logger = LoggerFactory.getLogger(ExampleServiceImpl.class);

    @Resource(name = "exampleDAO")
    private ExampleDAO exampleDAO;

    @Override
    public ExampleInfo getExample() {
        return exampleDAO.selectExample();
    }

    @Override
    public List<ExampleInfo> getExampleList() {
        return exampleDAO.selectExampleList();
    }

    @Override
    public void setExample(ExampleInfo example) {
        exampleDAO.insertExample(example);
        return;
    }
}
