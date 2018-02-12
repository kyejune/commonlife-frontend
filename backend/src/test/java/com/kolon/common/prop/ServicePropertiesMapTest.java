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

    @Test
    public void sampleReadPostPropertiesTest() throws Exception {
        final String POST_PROP_GROUP = "POST";
        final String POST_S3_ACCESS_KEY_PROP_KEY = "S3_ACCESS_KEY";
        final String POST_S3_ACCESS_SECRET_PROP_KEY = "S3_ACCESS_SECRET";
        final String POST_UP_S3_NAME_PROP_KEY = "UP_S3_NAME";
        final String POST_UP_S3_KEY_PREFIX_PROP_KEY = "UP_S3_KEY_PREFIX";
        final String POST_DN_S3_NAME_PROP_KEY = "DN_S3_NAME";
        final String POST_DN_S3_KEY_PREFIX_PROP_KEY = "DN_S3_KEY_PREFIX";

        KeyValueMap kvm;

        kvm = serviceProp.getByGroup( POST_PROP_GROUP );
        Assert.assertEquals( kvm.getValue( POST_S3_ACCESS_KEY_PROP_KEY ) , null );
        Assert.assertEquals( kvm.getValue( POST_S3_ACCESS_SECRET_PROP_KEY ) , null );
        Assert.assertEquals( kvm.getValue( POST_UP_S3_NAME_PROP_KEY ) , null );
        Assert.assertEquals( kvm.getValue( POST_UP_S3_KEY_PREFIX_PROP_KEY ) , null );
        Assert.assertEquals( kvm.getValue( POST_DN_S3_NAME_PROP_KEY ) , null );
        Assert.assertEquals( kvm.getValue( POST_DN_S3_KEY_PREFIX_PROP_KEY ) , null );

        return;
    }
}