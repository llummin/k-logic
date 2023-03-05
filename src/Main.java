import java.util.Scanner;
import java.util.Stack;

public class Main {

  static int g_k = 5;
  static int g_n = 1;
  static int g_size;
  static Stack<int[]> g_stack = new Stack<int[]>();

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    AdditionalFunc.printHat();
    int user_opinion = -1;

    while (user_opinion != 0) {
      System.out.print("Введите число k: ");
      g_k = AdditionalFunc.getValue(15, "Введите число k: ");

      System.out.print("Введите число n (1 или 2): ");
      g_n = AdditionalFunc.getValue(17, "Введите число k: ");

      g_size = (int) Math.pow(g_k, g_n);

      sc.nextLine();
      System.out.print("Введите функцию: ");
      String input = sc.nextLine();

      if (input.equals("exit")) {
        break;
      }

      AdditionalFunc.cleanFromSpace(new StringBuilder(input));

      if (!check(input)) {
        System.out.println(
            "\nНеудачный ввод!\nЦикл будет перезапущен, для выхода в вводе функции напишите \"exit\"\n\n");
        continue;
      }

      try {
        analyze(input);
        AdditionalFunc.printRes(input);

        Calculate.makeFirstForm();

        boolean check = false;
        SaveSet.saveSet(new boolean[]{check});

        int[] result = g_stack.pop();
        result = null;
      } catch (Exception e) {
        System.out.println("\nВведенные данные некорректны!\n");
      }

      System.out.print("\nДля продолжения работы нажмите 1, если хотите выйти - 0\nВведите: ");
      user_opinion = AdditionalFunc.getValue(16,
          "Для начала работы нажмите 1, если хотите выйти - 0\n");
    }
  }

  static boolean check(String str) {
    if (str.equals("")) {
      System.out.println("Введена пустая строка!\n");
      return false;
    }

    int open_backets = 0;
    int close_backet = 0;
    boolean alert = false;
    int temp = -1;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '(') {
        open_backets++;
      } else if (c == ')') {
        alert = false;
        close_backet++;
      } else if (AdditionalFunc.tryParse(c)) {
        alert = true;
      } else if (c == '*') {
        alert = false;
      } else if (c == 'y' && g_n == 1) {
        System.out.println("Обнаружен y при n = 1!\n");
        return false;
      } else if (c == 'x' || c == 'y' || c == '-') {
        if (alert) {
          System.out.println("После числа возможно лишь '*' или ')'\n");
          return false;
        }
      } else {
        System.out.println("Найден неопознанный символ!\n");
        return false;
      }
    }
    if (open_backets != close_backet) {
      System.out.println("Первичный осмотр показал проблему в скобках\n");
      return false;
    }
    return true;
  }

  static void analyze(String analytic_form) throws Exception {
    int index_start = -1;
    int index_end = -1;

    index_start = analytic_form.indexOf('(');

    while (index_start != -1) {
      int count_open_bracket = 1;
      index_end = index_start;
      while (count_open_bracket != 0) {
        index_end++;
        if (analytic_form.charAt(index_end) == '(') {
          count_open_bracket++;
        } else if (analytic_form.charAt(index_end) == ')') {
          count_open_bracket--;
        }
      }

      String new_form = analytic_form.substring(index_start + 1, index_end);
      analytic_form =
          analytic_form.substring(0, index_start) + analytic_form.substring(index_end + 1);

      analyze(new_form);

      index_start = analytic_form.indexOf('(');
    }

    int index_multiplacation = -1;
    index_multiplacation = analytic_form.indexOf('*');

    if (index_multiplacation != -1) {
      String front_part = analytic_form.substring(0, index_multiplacation);
      String end_part = analytic_form.substring(index_multiplacation + 1, analytic_form.length());
      analytic_form = "*";
      if (front_part.length() > 0) {
        analyze(front_part);
      }
      if (end_part.length() > 0) {
        analyze(end_part);
      }
    }

    int index_negotiation = -1;
    index_negotiation = analytic_form.indexOf('-');

    if (index_negotiation != -1) {
      String end_part = analytic_form.substring(index_negotiation + 1, analytic_form.length());
      analytic_form = "-";
      if (end_part.length() > 0) {
        analyze(end_part);
      }
    }

    Calculate.calculate(analytic_form);
  }
}
