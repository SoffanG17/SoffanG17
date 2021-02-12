import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Bogus test. Should give a method coverage of 2 out of 4, and 1 successful test.
 */
public class MinimalClassTest{

    @Test
    void testGreetOne() {
        MinimalClass mc = new MinimalClass();
        mc.greetPartOne();
        Assertions.assertTrue(true);
    }
}
