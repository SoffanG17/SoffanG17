import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApiClientTest {

    // Tests for P1 below (remember to make multiple methods for multiple test cases)

    /**
     * Comments on a dummy-commit on a dummy-branch. A viable token in src/main/java/token.txt is required.
     */
    @Test
    void testCommentingWorking() {
        Exception exception = null;
        String message = "";
        try{
            ApiClient client = new ApiClient();
            client.comment("bef0bd260555810ab29497317ac5a6085585b8e0", "Automated testing commit, ignore this");
            client.stop();
        }catch (Exception e){
            exception = e;
            message = e.getMessage();
        }

        Assertions.assertNull(exception, message);
    }

    // Tests for P2 below (remember to make multiple methods for multiple test cases)

    /**
     * DOC
     */
    @Test
    void testInvalidCommitId() {
        Exception exeption = null;
        try{
            ApiClient client = new ApiClient();
            client.comment("fisk", "Automated testing commit, ignore this");
            client.stop();
        }catch (Exception e){
            exeption = e;
        }

        Assertions.assertNotNull(exeption, "Invalid commit-id didn't caouse a exception to be thrown<>>>>>");
    }

}
