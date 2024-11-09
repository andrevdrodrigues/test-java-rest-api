package base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    private static Logger log = Logger.getAnonymousLogger();

    @BeforeAll
    public static void init(){
        log.info(" ---- Starting ToDo Tests ---- ");
    }

    @AfterAll
    public void tearDown() {
        log.info(" ---- Finishing ToDo Tests ---- ");
    }

}
