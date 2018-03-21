package com.kolon.comlife.example;

import com.kolon.comlife.example.model.ExampleInfo;
import com.kolon.comlife.example.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations = {"classpath*:spring/**/context-*.xml", "classpath*:spring/**/test-context-*.xml"})
public class TranactionRollbackTest {
    private static final Logger logger = LoggerFactory.getLogger(TranactionRollbackTest.class);

    @Autowired
    ExampleService exampleService;

    @Test
    public void unitTestSampleTest(){
        logger.info(">>> Run unitTestSampleTest()");
        return;
    }

    @Test
    public void transactionRollbackTest() throws Exception {
        logger.info(">>> Run transaction rollback()");

        ExampleInfo info = new ExampleInfo();
        info.setName("trans-rollback?");

        exampleService.transactionTestExample(info, true);

        return;
    }
}
