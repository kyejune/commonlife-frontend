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
    public ExampleInfo getExample( String name ) {
        return exampleDAO.selectExample( name );
    }

    @Override
    public List<ExampleInfo> getExampleList() {
        return exampleDAO.selectExampleList();
    }

    @Override
    public ExampleInfo setExample(ExampleInfo example) {
        return exampleDAO.insertExample(example);
    }

    @Override
    public int updateExample(ExampleInfo example) {
        return exampleDAO.updateExample(example);
    }

    @Override
    public void deleteExample(ExampleInfo example) {
        exampleDAO.deleteExample(example);
        return;
    }
}
