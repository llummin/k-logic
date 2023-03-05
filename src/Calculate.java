import java.util.Stack;

public class Calculate {

  public static int g_k;
  public static int g_n;
  public static int g_size;
  public static Stack<int[]> g_stack = new Stack<>();

  public static void unaryNegationFunc() {
    int[] result = g_stack.pop();

    for (int i = 0; i < g_size; i++) {
      result[i] = Logic.unaryNegation(result[i]);
    }

    g_stack.push(result);
  }

  public static void multiplicationFunc() {
    int[] res_table1 = g_stack.pop();
    int[] res_table2 = g_stack.pop();
    int[] result = new int[g_size];

    for (int i = 0; i < g_size; i++) {
      result[i] = Logic.multiplication(res_table1[i], res_table2[i]);
    }

    g_stack.push(result);
  }

  public static void calculate(String symbol) throws Exception {
    if (symbol.charAt(0) == '*') {
      multiplicationFunc();
    } else if (symbol.charAt(0) == '-') {
      unaryNegationFunc();
    } else {
      int[] arr = null;
      if (symbol.charAt(0) == 'x') {
        arr = new int[g_size];
        for (int i = 0; i < g_size; i++) {
          arr[i] = i / (g_size / g_k);
        }
      } else if (symbol.charAt(0) == 'y') {
        arr = new int[g_size];
        for (int i = 0; i < g_size; i++) {
          arr[i] = i % g_k;
        }
      } else {
        int temp = 0;
        if (AdditionalFunc.tryParse(symbol, new int[]{temp})) {
          if (temp > g_k - 1) {
            System.out.println("Найдено число больше k-1");
            throw new Exception();
          } else {
            arr = new int[g_size];
            for (int i = 0; i < g_size; i++) {
              arr[i] = temp;
            }
          }
        }
      }
      if (arr != null) {
        g_stack.push(arr);
      }
    }
  }

  public static void makeFirstForm() {
    int[] res_table1 = g_stack.pop();
    boolean printed = false;

    System.out.print("Первая форма, аналог СДНФ: ");

    if (g_n == 1) {
      for (int i = 0; i < g_k; i++) {
        if (res_table1[i] != 0) {
          if (printed) {
            System.out.print(" & ");
          }

          if (res_table1[i] != g_k - 1) {
            System.out.print(res_table1[i] + "&");
          }

          System.out.print("J_" + i + "(x)");
          printed = true;
        }
      }
    } else {
      int iterator = 0;
      for (int i = 0; i < g_k; i++) {
        for (int j = 0; j < g_k; j++) {
          if (res_table1[iterator] != 0) {
            if (printed) {
              System.out.print(" & ");
            }

            if (res_table1[iterator] != g_k - 1) {
              System.out.print(res_table1[iterator] + "&");
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