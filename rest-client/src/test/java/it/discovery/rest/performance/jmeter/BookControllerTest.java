package it.discovery.rest.performance.jmeter;


import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.DslTestPlan;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class BookControllerTest {

    @Test
    void findAllBooks_success() throws IOException {
        DslTestPlan plan = testPlan(threadGroup(2500, 2,
                httpSampler("http://localhost:8080/api/books")), htmlReporter("target/reports"));
        TestPlanStats stats = plan.run();
        double errorPercentage = (double) stats.overall().errorsCount() / stats.overall().samplesCount();
        assertTrue(errorPercentage < 0.2);

    }
}
