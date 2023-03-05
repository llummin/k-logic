import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class SaveSet {

  static int g_k;
  static int g_n;
  static int g_size;
  static Stack<int[]> g_stack;

  public static void inputSet(String str, Vector<Integer> arr) throws Exception {
    int number = 0;
    String temp;
    int index_start = str.indexOf(' ');
    while (index_start != -1) {
      temp = str.substring(0, index_start);
      str = str.substring(index_start + 1);

      if (AdditionalFunc.tryParse(temp, new int[]{number})) {
        if (number > g_k - 1) {
          throw new Exception("В множестве найдено число больше k-1\n");
        }
        arr.add(number);
      }

      index_start = str.indexOf(' ');
      number = 500;
    }
    if (AdditionalFunc.tryParse(str, new int[]{number})) {
      if (number > g_k - 1) {
        throw new Exception("В множестве найдено число больше k-1\n");
      }
      arr.add(number);
    }
  }

  public static void saveSet(boolean[] save) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Введите через пробел множество: ");
    String str = scanner.nextLine();
    Vector<Integer> my_set = new Vector<>();
    try {
      inputSet(str, my_set);
      int[] res_table = g_stack.peek();

      int index;
      String temporary;

      if (g_n == 2) {
        int iterator = 0;
        int[][] new_arr = new int[g_k][];

        for (int i = 0; i < g_k; i++) {
          new_arr[i] = new int[g_k];
          for (int j = 0; j < g_k; j++) {
            new_arr[i][j] = res_table[iterator++];
          }
        }

        for (int i = 0; i < my_set.size(); i++) {
          for (int j = 0; j < my_set.size(); j++) {
            temporary = String.valueOf(new_arr[my_set.get(i)][my_set.get(j)]);
            index = str.indexOf(temporary);
            if (index == -1) {
              System.out.println("Функция не сохраняет данное множество!");
              save[0] = false;
              return;
            }
          }
        }

        for (int j = 0; j < g_k; j++) {
          new_arr[j] = null;
        }
        new_arr = null;
      } else {
        for (int i = 0; i < my_set.size(); i++) {
          temporary = String.valueOf(res_table[my_set.get(i)]);
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