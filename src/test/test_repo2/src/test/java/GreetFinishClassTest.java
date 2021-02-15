import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Bogus tests. Should give a method coverage of 2 out of 2, and 2 successful tests.
 */
public class GreetFinishClassTest {
    @Test
    void testConstructor() {
        GreetFinishClass gfc = new GreetFinishClass();
        Assertions.assertTrue(true);
    }

    @Test
    void testGreetPartThree() {
        GreetFinishClass gc = new GreetFinishClass();
        gc.greetPartThree();
        Assertions.assertTrue(true);
    }
}
