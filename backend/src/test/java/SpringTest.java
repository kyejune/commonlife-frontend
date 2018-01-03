import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations = "classpath:context-*.xml")
public class SpringTest {
    @Test
    public void unitTestSampleTest(){
        // When
        System.out.println("Unit-test");
        return;
    }
}