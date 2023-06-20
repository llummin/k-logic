public class Logic {

  public static int gK;

  public static int unaryNegation(int input) {
    return input > 0 ? gK - input : 0;
  }

  public static int multiply(int num1, int num2) {
    return (num1 * num2) % gK;
  }
}