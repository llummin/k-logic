import java.util.Stack;

public class Calculate {

  public static int gK;
  public static int gN;
  public static int gSize;
  public static Stack<int[]> gStack = new Stack<>();

  public static void unaryNegationFunc() {
    int[] result = gStack.pop();

    for (int i = 0; i < gSize; i++) {
      result[i] = Logic.unaryNegation(result[i]);
    }
    gStack.push(result);
  }

  public static void multiplicationFunc() {
    int[] resTable1 = gStack.pop();
    int[] resTable2 = gStack.pop();
    int[] result = new int[gSize];

    for (int i = 0; i < gSize; i++) {
      result[i] = Logic.multiplication(resTable1[i], resTable2[i]);
    }
    gStack.push(result);
  }

  public static void calculate(String symbol) throws Exception {
    if (symbol.charAt(0) == '*') {
      multiplicationFunc();
    } else if (symbol.charAt(0) == '-') {
      unaryNegationFunc();
    } else {
      int[] arr = null;
      if (symbol.charAt(0) == 'x') {
        arr = new int[gSize];
        for (int i = 0; i < gSize; i++) {
          arr[i] = i / (gSize / gK);
        }
      } else if (symbol.charAt(0) == 'y') {
        arr = new int[gSize];
        for (int i = 0; i < gSize; i++) {
          arr[i] = i % gK;
        }
      } else {
        int temp = 0;
        if (AdditionalFunc.tryParse(symbol, new int[]{temp})) {
          if (temp > gK - 1) {
            System.out.println("Найдено число больше k-1");
            throw new Exception();
          } else {
            arr = new int[gSize];
            for (int i = 0; i < gSize; i++) {
              arr[i] = temp;
            }
          }
        }
      }
      if (arr != null) {
        gStack.push(arr);
      }
    }
  }

  public static void makeFirstForm() {
    int[] resTable1 = gStack.pop();
    boolean printed = false;

    System.out.print("Первая форма, аналог СДНФ: ");

    if (gN == 1) {
      for (int i = 0; i < gK; i++) {
        if (resTable1[i] != 0) {
          if (printed) {
            System.out.print(" & ");
          }

          if (resTable1[i] != gK - 1) {
            System.out.print(resTable1[i] + "&");
          }

          System.out.print("J_" + i + "(x)");
          printed = true;
        }
      }
    } else {
      int iterator = 0;
      for (int i = 0; i < gK; i++) {
        for (int j = 0; j < gK; j++) {
          if (resTable1[iterator] != 0) {
            if (printed) {
              System.out.print(" & ");
            }

            if (resTable1[iterator] != gK - 1) {
              System.out.print(resTable1[iterator] + "&");
            }

            System.out.print("J_" + i + "(x)&" + "J_" + j + "(y)");
            printed = true;
          }
          iterator++;
        }
      }
    }

    if (!printed) {
      System.out.println("В таблице все нули!");
    }
    System.out.println();
  }
}