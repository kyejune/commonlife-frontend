package com.kolon.comlife.example;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations = {"classpath*:spring/**/context-*.xml", "classpath*:spring/**/test-context-*.xml"})
public class ExampleUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(ExampleUnitTest.class);

    @Test
    public void unitTestSampleTest(){
        logger.info(">>> Run unitTestSampleTest()");
        return;
    }
}
