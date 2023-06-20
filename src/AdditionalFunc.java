import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class AdditionalFunc {

  private static final int DEFAULT_GK = 17;
  private static final int DEFAULT_GN = 2;
  private static final int DEFAULT_GSIZE = 10;
  private static final Deque<int[]> DEFAULT_GSTACK = new LinkedList<>();

  public static boolean isNumeric(char ch) {
    String temp = String.valueOf(ch);
    try {
      Integer.parseInt(temp);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean isNumeric(String str, int[] a) {
    try {
      a[0] = Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static int getValue(int condition, String prompt) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      int userInput;
      try {
        userInput = scanner.nextInt();
      } catch (Exception e) {
        System.out.println("Ошибка! Пожалуйста, повторите ввод!\n");
        scanner.nextLine();
        System.out.print(prompt);
        continue;
      }

      switch (condition) {
        case 15:
          if (isValidInput(userInput, 100)) {
            return userInput;
          }
          System.out.println("Вы должны ввести целое число от 0 до 100!\n Повторите ввод: ");
          scanner.nextLine();
          break;
        case 16:
          if (isValidInput(userInput, 2)) {
            return userInput;
          }
          System.out.println("Вы должны ввести либо 0, либо 1!\n Повторите ввод: ");
          scanner.nextLine();
          break;
        case 17:
          if (isValidInput(userInput, 3)) {
            return userInput;
          }
          System.out.println("Вы должны ввести либо 1, либо 2!\n Повторите ввод: ");
          scanner.nextLine();
          break;
        default:
          return userInput;
      }
    }
  }

  private static boolean isValidInput(int input, int max) {
    return input > 0 && input < max;
  }

  public static void printHat() {
    System.out.println("Работу выполнил Елушев М.А, группа 4217");
    System.out.println("g = 17, n = 2.");
    System.out.println("Функция одного аргумента - (g + n - 1)(mod 6) + 1 = 1 ~ -x");
    System.out.println(
        "Функция двух аргументов - (g + n - 1)(mod 7) + 1 = 5 ~ x * y - Произведение по модулю k");
    System.out.println(
        "Стандартная форма представления - (g + n - 1)(mod 3) + 1 = 1 ~ Первая форма (аналог СДНФ)");
    System.out.println();
    System.out.println("Ввод функции: -x ~ Унарное отрицание, x*y ~ Произведение по модулю k");
  }

  public static void printResult(String expression) {
    int[] result = DEFAULT_GSTACK.peek();
    int temp = expression.length() % 2;
    int width = (expression.length() - temp) / 2;

    System.out.println("\nРезультат работы программы:\n");
    if (DEFAULT_GN == 1) {
      String headerFormat = "| X | %" + expression.length() + "s |\n";
      System.out.printf(headerFormat, expression);
      for (int i = 0; i < DEFAULT_GSIZE; i++) {
        String rowFormat = "| %d | %d";
        String row = String.format(rowFormat, i, result[i]);
        System.out.println(row + " ".repeat(Math.max(0, width)) + " |");
      }
    } else if (DEFAULT_GN == 2) {
      String headerFormat = "| X | Y | %" + expression.length() + "s |\n";
      System.out.printf(headerFormat, expression);
      for (int i = 0; i < DEFAULT_GSIZE; i++) {
        String rowFormat = "| %d | %d | %d";
        assert result != null;
        String row = String.format(rowFormat, 0, i % DEFAULT_GK, result[i]);
        System.out.println(row + " ".repeat(width) + " |");
      }
    }
  }

  public static void cleanFromSpace(StringBuilder str) {
    int indexStart = str.indexOf(" ");
    while (indexStart != -1) {
      str.deleteCharAt(indexStart);
      indexStart = str.indexOf(" ");
    }
  }
}