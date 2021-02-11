import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CIServerTest{
    static String reportRepo1;
    static String reportRepo2;

    @BeforeAll
    static void testInit() {
        ContinuousIntegrationServer ci = new ContinuousIntegrationServer();
        reportRepo1 = ci.executeTests("./src/test/test_repo/");
        reportRepo2 = ci.executeTests("./src/test/test_repo2/");
    }

    // Tests for P1 below (remember to make multiple methods for multiple test cases)

    /**
     * DOC
     */
    @Test
    void testP1Something() {

        Assertions.assertTrue(true);
    }

    // Tests for P2 below (remember to make multiple methods for multiple test cases)

    /**
     * Ensure that the correct number of tests are run and that no tests fail unexpectedly for test repo 1.
     */
    @Test
    void testExecuteTestsTestRepo1TestReport() {
        final String testReport = """
            Test set: MinimalClassTest
            -------------------------------------------------------------------------------
            Tests run: 1, Failures: 0, Errors: 0, Skipped: 0""";
        Assertions.assertTrue(reportRepo1.contains(testReport));
    }

    /**
     * Ensure that the coverage report looks like it should for test repo 1.
     */
    @Test
    void testExecuteTestsTestRepo1CoverageReport() {
        final String coverageReport = """
            In the class MinimalClass the number of methods covered by tests is: 2,
            and the number of methods not covered by tests is: 2""";
        Assertions.assertTrue(reportRepo1.contains(coverageReport));
    }

    /**
     * Ensure that the correct number of tests are run and that the correct tests fail for test repo 2.
     */
    @Test
    void testExecuteTestsTestRepo2TestReport() {
        final String testReportPart1 = """
            Test set: GreetClassTest
            -------------------------------------------------------------------------------
            Tests run: 2, Failures: 1, Errors: 0, Skipped: 0""";
        final String testReportPart2 = """
            Test set: GreetFinishClassTest
            -------------------------------------------------------------------------------
            Tests run: 2, Failures: 0, Errors: 0, Skipped: 0""";
        final String testReportPart3 = "at GreetClassTest.testFail(GreetClassTest.java:12)";

        Assertions.assertTrue(reportRepo2.contains(testReportPart1));
        Assertions.assertTrue(reportRepo2.contains(testReportPart2));
        Assertions.assertTrue(reportRepo2.contains(testReportPart3));
    }

    /**
     * Since we can't generate coverage reports when a test fails, we make sure that no fake coverage reports
     * have been included.
     */
    @Test
    void testExecuteTestsTestRepo2CoverageReport() {
        final String coverageReportPart1 = "the number of methods covered by tests is:";
        final String coverageReportPart2 = "and the number of methods not covered by tests is:";
        Assertions.assertFalse(reportRepo2.contains(coverageReportPart1));
        Assertions.assertFalse(reportRepo2.contains(coverageReportPart2));
    }

    // Tests for P3 below (remember to make multiple methods for multiple test cases)

    /**
     * DOC
     */
    @Test
    void testP3Something() {

        Assertions.assertTrue(true);
    }

    // Tests for for history module below (remember to make multiple methods for multiple test cases)

    /**
     * DOC
     */
    @Test
    void testHistorySomething() {

        Assertions.assertFalse(false);
    }

    // Test for other things below (remember to make multiple methods for multiple test cases)

    /**
     * DOC
     */
    @Test
    void testSomethingRemaining() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {throw new IllegalArgumentException();});
    }

}
