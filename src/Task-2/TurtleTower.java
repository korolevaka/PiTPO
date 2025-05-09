import java.util.*;

public class TurtleTower {

    // Класс для хранения веса и силы черепахи
    static class Turtle {
        int weight;
        int strength;

        Turtle(int weight, int strength) {
            this.weight = weight;
            this.strength = strength;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Turtle> turtles = new ArrayList<>();

        // Чтение входных данных
        while (scanner.hasNextInt()) {
            int weight = scanner.nextInt();
            if (!scanner.hasNextInt()) break;
            int strength = scanner.nextInt();
            turtles.add(new Turtle(weight, strength));
        }

        // Сортировка черепах по силе, если силы равны — по весу
        turtles.sort(Comparator.comparingInt((Turtle t) -> t.strength)
                .thenComparingInt(t -> t.weight));

        int n = turtles.size();
        int INF = Integer.MAX_VALUE;

        int[] dp = new int[n + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        int maxHeight = 0;

        for (Turtle turtle : turtles) {
            for (int i = maxHeight; i >= 0; i--) {
                // Если можно добавить текущую черепаху сверху (нагрузка не превышает силу)
                if (dp[i] + turtle.weight <= turtle.strength) {
                    dp[i + 1] = Math.min(dp[i + 1], dp[i] + turtle.weight);
                    maxHeight = Math.max(maxHeight, i + 1);
                }
            }
        }

        System.out.println(maxHeight);
    }
}
