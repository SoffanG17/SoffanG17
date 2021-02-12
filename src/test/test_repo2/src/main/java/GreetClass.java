
/**
 A classic exclamation.
 */
public class GreetClass {

    public static void main(String[] args) {
        GreetClass gc = new GreetClass();
        GreetFinishClass gfc = new GreetFinishClass();
        gc.greetPartOne();
        gc.greetPartTwo();
        gfc.greetPartThree();
    }

    public void greetPartOne() {
        System.out.print("Hello");
    }

    public void greetPartTwo() {
        System.out.print(" world");
    }
}
