public class Logic {

  public static int gK;

  public static int unaryNegation(int x) {
    return x > 0 ? gK - x : 0;
  }

  public static int multiplication(int x, int y) {
    return x * y % gK;
  }
}