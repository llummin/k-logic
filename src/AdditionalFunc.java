import java.util.Scanner;
import java.util.Stack;

public class AdditionalFunc {

  public static int g_k;
  public static int g_n;
  public static int g_size;
  public static Stack<int[]> g_stack = new Stack<>();

  public static boolean tryParse(char ch) {
    try {
      String temp = String.valueOf(ch);
      Integer.parseInt(temp);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean tryParse(String str, int[] a) {
    try {
      a[0] = Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static int getValue(int condition, String string) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      int a;
      try {
        a = scanner.nextInt();
      } catch (Exception e) {
        System.out.println("Ошибка! Пожалуйста, повторите ввод! \n");
        scanner.nextLine();
        System.out.print(string);
        continue;
      }

      switch (condition) {
        case 15 -> {
          if ((a > 0) && (a < 100)) {
            return a;
          }
          System.out.println("Вы должны ввести натуральное число, меньше 100!\n Повторите ввод: ");
          scanner.nextLine();
          continue;
        }
        case 16 -> {
          if ((a == 0) || (a == 1)) {
            return a;
          }
          System.out.println("Вы должны ввести либо 0, либо 1\n");
          scanner.nextLine();
          continue;
        }
        case 17 -> {
          if ((a > 0) && (a < 3)) {
            return a;
          }
          System.out.println("Вы должны ввести либо 1, либо 2! Повторите ввод: ");
          scanner.nextLine();
          continue;
        }
        default -> {
          return a;
        }
      }
    }
  }

  public static void printHat() {
    System.out.println("Работу выполнил Елушев М.А, группа 4217");
    System.out.println("g = 17, n = 2.");
    System.out.println(
        "Функия одного аргумента - (g + n - 1)(mod 6) + 1 = 1 ~ -x    - Унарное отрицание");
    System.out.println(
        "Функция двух аргументов - (g + n - 1)(mod 7) + 1 = 5 ~ x * y - Произведение по модулю k");
    System.out.println(
        "Стандартная форма представления - (g + n - 1)(mod 3) + 1 = 1 ~ Первая форма (аналог СДНФ)\n\n");
    System.out.println("Ввод функции: -x ~ Унарное отрицание, x*y ~ Произведение по модулю k\n");
  }

  public static void printRes(String expression) {
    int[] result = g_stack.peek();
    int temp = expression.length() % 2;
    int width1 = (expression.length() + temp) / 2;
    int width2 = (expression.length() - temp) / 2;

    System.out.println("\nРезультат работы программы:\n");
    if (g_n == 1) {
      System.out.printf("| X | %-" + expression.length() + "s |\n", expression);
      for (int i = 0; i < g_size; i++) {
        System.out.printf("| %d | %-" + width1 + "d%-" + width2 + "s |\n", i, result[i], "");
      }

    } else if (g_n == 2) {
      System.out.printf("| X | Y | %-" + expression.length() + "s |\n", expression);
      for (int i = 0; i < g_size; i++) {
        System.out.printf("| %d | %d | %-" + width1 + "d%-" + width2 + "s |\n", i / g_k, i % g_k,
            result[i], "");
      }

    }
  }

  public static void cleanFromSpace(StringBuilder str) {
    int index_start = str.indexOf(" ");
    while (index_start != -1) {
      str.deleteCharAt(index_start);
      index_start = str.indexOf(" ");
    }
  }
}