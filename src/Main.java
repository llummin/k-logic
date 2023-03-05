import java.util.Scanner;
import java.util.Stack;

public class Main {

  static int gK = 5;
  static int gN = 1;
  static int gSize;
  static Stack<int[]> gStack = new Stack<>();

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    AdditionalFunc.printHat();
    int userOpinion = -1;

    while (userOpinion != 0) {
      System.out.print("Введите число k: ");
      gK = AdditionalFunc.getValue(15, "Введите число k: ");

      System.out.print("Введите число n (1 или 2): ");
      gN = AdditionalFunc.getValue(17, "Введите число k: ");

      gSize = (int) Math.pow(gK, gN);

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

        int[] result = gStack.pop();
        result = null;
      } catch (Exception e) {
        System.out.println("\nВведенные данные некорректны!\n");
      }

      System.out.print("\nДля продолжения работы нажмите 1, если хотите выйти - 0\nВведите: ");
      userOpinion = AdditionalFunc.getValue(16,
          "Для начала работы нажмите 1, если хотите выйти - 0\n");
    }
  }

  static boolean check(String str) {
    if (str.equals("")) {
      System.out.println("Введена пустая строка!\n");
      return false;
    }

    int openBackets = 0;
    int closeBackets = 0;
    boolean alert = false;
    int temp = -1;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '(') {
        openBackets++;
      } else if (c == ')') {
        alert = false;
        closeBackets++;
      } else if (AdditionalFunc.tryParse(c)) {
        alert = true;
      } else if (c == '*') {
        alert = false;
      } else if (c == 'y' && gN == 1) {
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
    if (openBackets != closeBackets) {
      System.out.println("Первичный осмотр показал проблему в скобках\n");
      return false;
    }
    return true;
  }

  static void analyze(String analyticForm) throws Exception {
    int indexStart = -1;
    int indexEnd = -1;

    indexStart = analyticForm.indexOf('(');

    while (indexStart != -1) {
      int countOpenBracket = 1;
      indexEnd = indexStart;
      while (countOpenBracket != 0) {
        indexEnd++;
        if (analyticForm.charAt(indexEnd) == '(') {
          countOpenBracket++;
        } else if (analyticForm.charAt(indexEnd) == ')') {
          countOpenBracket--;
        }
      }

      String new_form = analyticForm.substring(indexStart + 1, indexEnd);
      analyticForm =
          analyticForm.substring(0, indexStart) + analyticForm.substring(indexEnd + 1);

      analyze(new_form);

      indexStart = analyticForm.indexOf('(');
    }

    int indexMultiplacation = -1;
    indexMultiplacation = analyticForm.indexOf('*');

    if (indexMultiplacation != -1) {
      String frontPart = analyticForm.substring(0, indexMultiplacation);
      String endPart = analyticForm.substring(indexMultiplacation + 1, analyticForm.length());
      analyticForm = "*";
      if (frontPart.length() > 0) {
        analyze(frontPart);
      }
      if (endPart.length() > 0) {
        analyze(endPart);
      }
    }

    int index_negotiation = -1;
    index_negotiation = analyticForm.indexOf('-');

    if (index_negotiation != -1) {
      String end_part = analyticForm.substring(index_negotiation + 1, analyticForm.length());
      analyticForm = "-";
      if (end_part.length() > 0) {
        analyze(end_part);
      }
    }

    Calculate.calculate(analyticForm);
  }
}
