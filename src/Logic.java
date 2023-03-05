public class Logic {

  public static int g_k;

  public static int unaryNegation(int x) {
    return x > 0 ? g_k - x : 0;
  }

  public static int multiplicate(int x, int y) {
    return x * y % g_k;
  }
}