import java.util.Scanner;

public class Main {

  static int gK = 5;
  static int gN = 1;
  static int gSize;

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
        AdditionalFunc.printResult(input);

        Calculate.makeFirstForm();

        boolean check = false;
        SaveSet.saveSet(new boolean[]{check});

      } catch (Exception e) {
        System.out.println("\nВведенные данные некорректны!\n");
      }

      System.out.print("\nДля продолжения работы нажмите 1, если хотите выйти - 0\nВведите: ");
      userOpinion = AdditionalFunc.getValue(16,
          "Для начала работы нажмите 1, если хотите выйти - 0\n");
    }
  }

  static boolean check(String str) {
    if (str.isEmpty()) {
      System.out.println("Введена пустая строка!\n");
      return false;
    }

    int openParentheses = 0;
    int closeParentheses = 0;
    boolean alert = false;

    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '(') {
        openParentheses++;
      } else if (c == ')') {
        alert = false;
        closeParentheses++;
      } else if (AdditionalFunc.isNumeric(c)) {
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
    if (openParentheses != closeParentheses) {
      System.out.println("Первичный осмотр показал проблему в скобках\n");
      return false;
    }
    return true;
  }

  static void analyze(String analyticForm) throws Exception {
    int startIndex = -1;
    int endIndex = -1;

    startIndex = analyticForm.indexOf('(');

    while (startIndex != -1) {
      int openBracketCount = 1;
      endIndex = startIndex;
      while (openBracketCount != 0) {
        endIndex++;
        if (analyticForm.charAt(endIndex) == '(') {
          openBracketCount++;
        } else if (analyticForm.charAt(endIndex) == ')') {
          openBracketCount--;
        }
      }

      String newForm = analyticForm.substring(startIndex + 1, endIndex);
      analyticForm = analyticForm.substring(0, startIndex) + analyticForm.substring(endIndex + 1);

      analyze(newForm);

      startIndex = analyticForm.indexOf('(');
    }

    int multiplicationIndex = -1;
    multiplicationIndex = analyticForm.indexOf('*');

    if (multiplicationIndex != -1) {
      String frontPart = analyticForm.substring(0, multiplicationIndex);
      String endPart = analyticForm.substring(multiplicationIndex + 1);
      analyticForm = "*";
      if (!frontPart.isEmpty()) {
        analyze(frontPart);
      }
      if (!endPart.isEmpty()) {
        analyze(endPart);
      }
    }

    int negotiationIndex = -1;
    negotiationIndex = analyticForm.indexOf('-');

    if (negotiationIndex != -1) {
      String endPart = analyticForm.substring(negotiationIndex + 1);
      analyticForm = "-";
      if (!endPart.isEmpty()) {
        analyze(endPart);
      }
    }

    Calculate.calculate(analyticForm);
  }
}
