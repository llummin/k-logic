import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class SaveSet {

  static int gK;
  static int gN;
  static Stack<int[]> gStack;

  public static void inputSet(String str, Vector<Integer> arr) throws Exception {
    int number = 0;
    String temp;
    int indexStart = str.indexOf(' ');
    while (indexStart != -1) {
      temp = str.substring(0, indexStart);
      str = str.substring(indexStart + 1);

      if (AdditionalFunc.tryParse(temp, new int[]{number})) {
        if (number > gK - 1) {
          throw new Exception("В множестве найдено число больше k-1\n");
        }
        arr.add(number);
      }

      indexStart = str.indexOf(' ');
      number = 500;
    }
    if (AdditionalFunc.tryParse(str, new int[]{number})) {
      if (number > gK - 1) {
        throw new Exception("В множестве найдено число больше k-1\n");
      }
      arr.add(number);
    }
  }

  public static void saveSet(boolean[] save) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Введите через пробел множество: ");
    String str = scanner.nextLine();
    Vector<Integer> mySet = new Vector<>();
    try {
      inputSet(str, mySet);
      int[] resTable = gStack.peek();

      int index;
      String temporary;

      if (gN == 2) {
        int iterator = 0;
        int[][] newArr = new int[gK][];

        for (int i = 0; i < gK; i++) {
          newArr[i] = new int[gK];
          for (int j = 0; j < gK; j++) {
            newArr[i][j] = resTable[iterator++];
          }
        }

        for (int i = 0; i < mySet.size(); i++) {
          for (Integer integer : mySet) {
            temporary = String.valueOf(newArr[mySet.get(i)][integer]);
            index = str.indexOf(temporary);
            if (index == -1) {
              System.out.println("Функция не сохраняет данное множество!");
              save[0] = false;
              return;
            }
          }
        }

        for (int j = 0; j < gK; j++) {
          newArr[j] = null;
        }
      } else {
        for (Integer integer : mySet) {
          temporary = String.valueOf(resTable[integer]);
          index = str.indexOf(temporary);
          if (index == -1) {
            System.out.println("Функция не сохраняет данное множество!");
            save[0] = false;
            return;
          }
        }
      }
      System.out.println("Функция сохраняет данное множество!");
      save[0] = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}