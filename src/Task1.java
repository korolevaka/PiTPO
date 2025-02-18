import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите число 1 больше 0: ");
            int i = scanner.nextInt();
            if (i <= 0) break;
            System.out.println("Введите число 2 больше 0: ");
            int j = scanner.nextInt();
            if (j <= 0) break;
            int min = Math.min(i, j);
            int max = Math.max(i, j);
            int maxCycleLength = 0;

            for (int num = min; num <= max; num++) {
                int cycleLength = getCycleLength(num);
                maxCycleLength = Math.max(maxCycleLength, cycleLength);
            }

            System.out.println(i + " " + j + " " + maxCycleLength + "\n");
        }
    }

    private static int getCycleLength(int n) {
        int length = 1;
        while (n != 1) {
            if (n % 2 == 0) {
                n /= 2;
            } else {
                n = 3 * n + 1;
            }
            length++;
        }
        return length;
    }
}
