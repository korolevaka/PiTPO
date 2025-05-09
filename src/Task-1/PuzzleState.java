import java.util.*;

// Класс для представления состояния головоломки
class PuzzleState implements Comparable<PuzzleState> {
    int[][] board;
    int zeroRow, zeroCol;   // Позиция пустышки
    String path;            // Последовательность ходов до текущего состояния
    int cost;               // Кол-во шагов от начального состояния
    int estimatedCost;      // Оценка расстояния до цели

    static final int[][] GOAL = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    public PuzzleState(int[][] board, String path, int cost) {
        this.board = new int[4][4];
        for (int i = 0; i < 4; i++) {
            this.board[i] = Arrays.copyOf(board[i], 4);
        }
        this.path = path;
        this.cost = cost;
        this.estimatedCost = cost + heuristic(); // f = g + h

        findZero();
    }

    // Поиск позиции нуля
    private void findZero() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (board[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    return;
                }
    }

    // Проверка, достигнута ли цель
    public boolean isGoal() {
        return Arrays.deepEquals(board, GOAL);
    }

    // Сумма расстояний до целевых позиций
    private int heuristic() {
        int dist = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                int val = board[i][j];
                if (val != 0) {
                    int targetRow = (val - 1) / 4;
                    int targetCol = (val - 1) % 4;
                    dist += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        return dist;
    }

    // Получить строковое представление доски
    public String getBoardString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board)
            for (int val : row)
                sb.append(val).append(',');
        return sb.toString();
    }

    // Генерация соседних состояний
    public List<PuzzleState> getNeighbors() {
        List<PuzzleState> neighbors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};              // Направления
        int[] dy = {0, 0, -1, 1};
        char[] move = {'U', 'D', 'L', 'R'};

        for (int dir = 0; dir < 4; dir++) {
            int newRow = zeroRow + dx[dir];
            int newCol = zeroCol + dy[dir];
            if (inBounds(newRow, newCol)) {
                int[][] newBoard = new int[4][4];
                for (int i = 0; i < 4; i++)
                    newBoard[i] = Arrays.copyOf(board[i], 4);

                // Меняем местами пустышку и соседний элемент
                newBoard[zeroRow][zeroCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;

                neighbors.add(new PuzzleState(newBoard, path + move[dir], cost + 1));
            }
        }

        return neighbors;
    }

    // Проверка, в пределах ли координаты
    private boolean inBounds(int i, int j) {
        return i >= 0 && i < 4 && j >= 0 && j < 4;
    }

    public int compareTo(PuzzleState other) {
        return Integer.compare(this.estimatedCost, other.estimatedCost);
    }
}

