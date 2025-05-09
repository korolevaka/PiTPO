import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class FifteenPuzzleSolver {

    // Проверка разрешимости головоломки
    public static boolean isSolvable(PuzzleState state) {
        int[] flat = new int[16];
        int idx = 0;
        int zeroRowFromBottom = -1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                flat[idx] = state.board[i][j];
                if (state.board[i][j] == 0)
                    zeroRowFromBottom = 3 - i;
                idx++;
            }
        }

        int inversions = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = i + 1; j < 16; j++) {
                if (flat[i] != 0 && flat[j] != 0 && flat[i] > flat[j])
                    inversions++;
            }
        }
        return (inversions + zeroRowFromBottom) % 2 == 0;
    }


    // Основной метод для поиска пути
    public static String solve(int[][] initial) {
        PuzzleState start = new PuzzleState(initial, "", 0);

        if (!isSolvable(start)) {
            return null;
        }

        PriorityQueue<PuzzleState> open = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        open.add(start);

        while (!open.isEmpty()) {
            PuzzleState current = open.poll();

            if (current.isGoal()) {
                return current.path;
            }

            String boardKey = current.getBoardString();
            if (visited.contains(boardKey)) continue;
            visited.add(boardKey);

            for (PuzzleState neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor.getBoardString())) {
                    open.add(neighbor);
                }
            }
        }

        return null;
    }

    // Чтение ввода и вывод результата
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCases = Integer.parseInt(sc.nextLine().trim());

        for (int t = 0; t < testCases; t++) {
            int[][] board = new int[4][4];
            for (int i = 0; i < 4; i++) {
                String[] row = sc.nextLine().trim().split("\\s+");
                for (int j = 0; j < 4; j++) {
                    board[i][j] = Integer.parseInt(row[j]);
                }
            }

            String solution = solve(board);
            if (solution == null) {
                System.out.println("This puzzle is not solvable.");
            } else {
                System.out.println(solution);
            }
        }
    }
}

