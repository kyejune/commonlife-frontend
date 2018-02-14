package com.kolon.comlife.users;

import com.kolon.comlife.users.service.MobileUserService;
import com.kolon.comlife.users.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations = {"classpath*:spring/**/context-*.xml", "classpath*:spring/**/test-context-*.xml"})
public class MobileUserServiceTest {

    @Resource(name = "mobileUserService")
    MobileUserService mobileUserService;

    @Test
    public void unitTestSampleTest(){
        // When
        System.out.println("Unit-test");
        return;
    }
}