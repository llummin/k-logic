import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class SaveSet {

  static int gK;
  static Deque<int[]> gStack;

  public static void inputSet(String str, List<Integer> list) throws InvalidSetNumberException {
    int number = 0;
    String temp;
    int indexStart = str.indexOf(' ');

    while (indexStart != -1) {
      temp = str.substring(0, indexStart);
      str = str.substring(indexStart + 1);

      if (AdditionalFunc.isNumeric(temp, new int[]{number})) {
        if (number > gK - 1) {
          throw new InvalidSetNumberException("Число в наборе больше k-1");
        }
        list.add(number);
      }

      indexStart = str.indexOf(' ');
      number = 500;
    }

    if (AdditionalFunc.isNumeric(str, new int[]{number})) {
      if (number > gK - 1) {
        throw new InvalidSetNumberException("Число в наборе больше k-1");
      }
      list.add(number);
    }
  }

  public static void saveSet(boolean[] save) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Введите множество, разделенное пробелами: ");
    String input = scanner.nextLine();
    List<Integer> set = new ArrayList<>();
    try {
      inputSet(input, set);
      int[] resultTable = gStack.peek();

      if (checkFunctionSavesSet(resultTable, set, input)) {
        save[0] = false;
      }

      System.out.println(
          save[0] ? "Функция сохраняет это множество!" : "Функция не сохраняет это множество!");
    } catch (InvalidSetNumberException e) {
      System.out.println(e.getMessage());
    }
  }

  private static boolean checkFunctionSavesSet(int[] resultTable, List<Integer> set, String input) {
    for (Integer element : set) {
      assert resultTable != null;
      String temp = String.valueOf(resultTable[element]);
      int index = input.indexOf(temp);
      if (index == -1) {
        return true;
      }
    }
    return false;
  }
}