package com.kolon.comlife.iot;

import com.kolon.comlife.iot.model.IotModeOrAutomationListInfo;
import com.kolon.comlife.iot.service.IotControlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations = {"classpath*:spring/**/context-*.xml", "classpath*:spring/**/test-context-*.xml"})
public class IotControlServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(IotControlServiceTest.class);


//    @Resource(name = "mobileUserService")
//    MobileUserService mobileUserService;

    @Autowired
    IotControlService iotControlService;

    @Test
    public void unitTestSampleTest(){
        logger.info(">>> Run unitTestSampleTest()");
        return;
    }

    @Test
    public void iotControlServiceTest() throws Exception {
        logger.info(">>> Run iotControlServiceTest()");

        IotModeOrAutomationListInfo result;

        result = iotControlService.switchToMode(125, 1 , "CM01103") ;
        logger.info(">>> >>> result: " + result);

        return;
    }
}