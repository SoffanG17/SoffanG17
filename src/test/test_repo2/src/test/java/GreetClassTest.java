import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Bogus tests. Should give a method coverage of 1 out of 4, and 1 failing test and 1 successful test.
 */
public class GreetClassTest {

    @Test
    void testFail() {
        GreetClass gc = new GreetClass();
        Assertions.assertTrue(false);
    }

    @Test
    void testPass() {
        GreetClass gc = new GreetClass();
        Assertions.assertTrue(true);
    }
}
