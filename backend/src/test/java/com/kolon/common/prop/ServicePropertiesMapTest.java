package com.kolon.common.prop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations = {"classpath*:spring/**/context-*.xml", "classpath*:spring/**/test-context-*.xml"})
public class ServicePropertiesMapTest {
    private static final Logger logger = LoggerFactory.getLogger(ServicePropertiesMapTest.class);

    @Resource(name = "servicePropertiesMap")
    ServicePropertiesMap serviceProp;

    @Test
    public void unitTestSampleTest() throws Exception {

        KeyValueMap kvm = null;

//        serviceProp.reload();
        kvm = serviceProp.getByGroup( "example" );
        Assert.assertEquals( kvm.getValue("key1") , "value11" );
        Assert.assertEquals( kvm.getValue("key2") , "value12" );
        kvm = serviceProp.getByGroup( "example2" );
        Assert.assertEquals( kvm.getValue("key1") , "value21" );
        Assert.assertEquals( kvm.getValue("key2") , "value22" );

        return;
    }
}