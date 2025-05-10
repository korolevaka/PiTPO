import java.util.*;
import java.io.*;

public class Task2 {
    private static final int MAX_COLOR = 50;
    private static List<List<Integer>> graph;
    private static int[][] edgesCount;
    private static List<String> result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int T = Integer.parseInt(br.readLine().trim());
        for (int t = 1; t <= T; t++) {
            if (t > 1) pw.println(); // Пустая строка между тестами

            int N = Integer.parseInt(br.readLine().trim());
            graph = new ArrayList<>();
            for (int i = 0; i <= MAX_COLOR; i++) {
                graph.add(new ArrayList<>());
            }
            edgesCount = new int[MAX_COLOR + 1][MAX_COLOR + 1];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                graph.get(a).add(b);
                graph.get(b).add(a);
                edgesCount[a][b]++;
                edgesCount[b][a]++;
            }

            if (!isEulerian()) {
                pw.printf("CASE #%d\n", t);
                pw.println("impossiblee");
                continue;
            }

            result = new ArrayList<>();
            int startColor = findStartColor();
            findEulerianCircuit(startColor);

            if (result.size() != N) {
                pw.printf("CASE #%d\n", t);
                pw.println("impossiblee");
            } else {
                pw.printf("CASE #%d\n", t);
                for (String bead : result) {
                    pw.println(bead);
                }
            }
        }

        pw.flush();
        pw.close();
    }

    private static boolean isEulerian() {
        // Проверяем, все ли вершины имеют чётную степень
        for (int i = 1; i <= MAX_COLOR; i++) {
            if (graph.get(i).size() % 2 != 0) {
                return false;
            }
        }

        // Проверяем связность графа
        boolean[] visited = new boolean[MAX_COLOR + 1];
        int startColor = -1;
        for (int i = 1; i <= MAX_COLOR; i++) {
            if (!graph.get(i).isEmpty()) {
                startColor = i;
                break;
            }
        }

        if (startColor == -1) return false; // Нет рёбер

        dfsCheckConnected(startColor, visited);

        for (int i = 1; i <= MAX_COLOR; i++) {
            if (!graph.get(i).isEmpty() && !visited[i]) {
                return false;
            }
        }

        return true;
    }

    private static void dfsCheckConnected(int u, boolean[] visited) {
        visited[u] = true;
        for (int v : graph.get(u)) {
            if (!visited[v]) {
                dfsCheckConnected(v, visited);
            }
        }
    }

    private static int findStartColor() {
        for (int i = 1; i <= MAX_COLOR; i++) {
            if (!graph.get(i).isEmpty()) {
                return i;
            }
        }
        return 1;
    }

    private static void findEulerianCircuit(int u) {
        for (int v = 1; v <= MAX_COLOR; v++) {
            if (edgesCount[u][v] > 0) {
                edgesCount[u][v]--;
                edgesCount[v][u]--;
                findEulerianCircuit(v);
                result.add(u + " " + v);
            }
        }
    }
}
