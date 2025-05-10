import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scenario = 1;

        while (true) {
            // Чтение количества городов и дорог
            int N = scanner.nextInt();
            int R = scanner.nextInt();

            // Проверка условия завершения
            if (N == 0 && R == 0) break;

            // Инициализация матрицы смежности
            int[][] graph = new int[N+1][N+1];
            for (int i = 0; i <= N; i++) {
                Arrays.fill(graph[i], 0);
            }

            // Чтение данных о дорогах
            for (int i = 0; i < R; i++) {
                int C1 = scanner.nextInt();
                int C2 = scanner.nextInt();
                int P = scanner.nextInt();
                graph[C1][C2] = Math.max(graph[C1][C2], P);
                graph[C2][C1] = Math.max(graph[C2][C1], P);
            }

            // Чтение параметров задачи
            int S = scanner.nextInt();
            int D = scanner.nextInt();
            int T = scanner.nextInt();

            // Поиск пути с максимальной минимальной пропускной способностью
            int maxMinCapacity = findMaxMinCapacity(graph, N, S, D);

            // Вычисление минимального количества поездок
            int trips = (int) Math.ceil((double)T / (maxMinCapacity - 1));

            System.out.println("\n Scenario #" + scenario);
            System.out.println("Minimum Number of Trips = " + trips);
            System.out.println();

            scenario++;
        }
    }

    // Метод для поиска максимальной минимальной пропускной способности между S и D
    private static int findMaxMinCapacity(int[][] graph, int N, int S, int D) {
        int[] capacities = new int[N+1];
        boolean[] visited = new boolean[N+1];

        Arrays.fill(capacities, 0);
        capacities[S] = Integer.MAX_VALUE;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        pq.offer(new int[]{S, capacities[S]});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int city = current[0];

            if (visited[city]) continue;
            visited[city] = true;

            if (city == D) break;

            for (int neighbor = 1; neighbor <= N; neighbor++) {
                if (graph[city][neighbor] > 0) {
                    int newCapacity = Math.min(capacities[city], graph[city][neighbor]);
                    if (newCapacity > capacities[neighbor]) {
                        capacities[neighbor] = newCapacity;
                        pq.offer(new int[]{neighbor, newCapacity});
                    }
                }
            }
        }

        return capacities[D];
    }
}