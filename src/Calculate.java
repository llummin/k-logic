import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.IntUnaryOperator;

public class Calculate {

  private static final int DEFAULT_GK = 17;
  private static final int DEFAULT_GN = 2;
  private static final int DEFAULT_GSIZE = 10;
  private static final Deque<int[]> DEFAULT_GSTACK = new LinkedList<>();

  public static void unaryNegationFunc() {
    int[] result = DEFAULT_GSTACK.pop();

    for (int i = 0; i < DEFAULT_GSIZE; i++) {
      result[i] = Logic.unaryNegation(result[i]);
    }

    DEFAULT_GSTACK.push(result);
  }

  public static void multiplyTables() {
    int[] firstTable = DEFAULT_GSTACK.pop();
    int[] secondTable = DEFAULT_GSTACK.pop();
    int[] result = new int[DEFAULT_GSIZE];

    for (int i = 0; i < DEFAULT_GSIZE; i++) {
      result[i] = Logic.multiply(firstTable[i], secondTable[i]);
    }
    DEFAULT_GSTACK.push(result);
  }

  public static void calculate(String symbol) throws InvalidSymbolException {
    if (symbol.charAt(0) == '*') {
      multiplyTables();
    } else if (symbol.charAt(0) == '-') {
      unaryNegationFunc();
    } else {
      int[] arr;
      int temp = 0;
      if (symbol.charAt(0) == 'x') {
        arr = generateArray(DEFAULT_GSIZE / DEFAULT_GK);
      } else if (symbol.charAt(0) == 'y') {
        arr = generateArray(i -> i % DEFAULT_GK);
      } else if (AdditionalFunc.isNumeric(symbol, new int[]{temp})) {
        arr = generateArray(temp);
      } else {
        throw new InvalidSymbolException("Invalid symbol: " + symbol);
      }
      DEFAULT_GSTACK.push(arr);
    }
  }

  private static int[] generateArray(int value) {
    int[] arr = new int[Calculate.DEFAULT_GSIZE];
    Arrays.fill(arr, value);
    return arr;
  }

  private static int[] generateArray(IntUnaryOperator operator) {
    int[] arr = new int[Calculate.DEFAULT_GSIZE];
    for (int i = 0; i < Calculate.DEFAULT_GSIZE; i++) {
      arr[i] = operator.applyAsInt(i);
    }
    return arr;
  }

  public static void makeFirstForm() {
    int[] resTable = DEFAULT_GSTACK.pop();
    System.out.print("Первая форма, аналог СДНФ: ");

    if (DEFAULT_GN == 1) {
      printSingleArgumentForm(resTable);
    } else {
      printDoubleArgumentForm(resTable);
    }

    System.out.println();
  }

  private static void printSingleArgumentForm(int[] resTable) {
    boolean printed = false;
    for (int i = 0; i < DEFAULT_GK; i++) {
      if (resTable[i] != 0) {
        printFormElement(resTable[i], i);
        printed = true;
      }
    }
    if (!printed) {
      System.out.println("В таблице все нули!");
    }
  }

  private static void printDoubleArgumentForm(int[] resTable) {
    boolean printed = false;
    int iterator = 0;

    for (int i = 0; i < DEFAULT_GK; i++) {
      for (int j = 0; j < DEFAULT_GK; j++) {
        if (resTable[iterator] != 0) {
          printFormElement(resTable[iterator], i, j);
          printed = true;
        }
        iterator++;
      }
    }

    if (!printed) {
      System.out.println("В таблице все нули!");
    }
  }

  private static void printFormElement(int value, int... arguments) {
    if (arguments.length > 0) {
      System.out.print(value > 0 ? value + "&" : "");
      for (int i = 0; i < arguments.length; i++) {
        System.out.print("J_" + arguments[i] + "(x)");
        if (i < arguments.length - 1) {
          System.out.print("&");
        }
      }
    }
  }
}