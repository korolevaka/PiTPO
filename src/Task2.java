import java.util.*;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int K = scanner.nextInt();
        scanner.nextLine(); // Пропуск оставшейся части строки

        for (int k = 0; k < K; k++) {
            int n = scanner.nextInt(); // Количество черепах
            scanner.nextLine();

            // Чтение начального порядка черепах
            List<String> initial = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                initial.add(scanner.nextLine());
            }

            // Чтение желаемого порядка черепах
            List<String> desired = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                desired.add(scanner.nextLine());
            }

            List<String> moves = findMoves(initial, desired); // Минимальная последовательность перемещений

            System.out.println();
            for (String move : moves) {
                System.out.println(move);
            }

            if (k < K - 1) {
                System.out.println();
            }
        }
    }

    private static List<String> findMoves(List<String> initial, List<String> desired) {
        List<String> moves = new ArrayList<>(); // Список перемещений

        int desiredIndex = 0; // Индекс в желаемом порядке
        int initialIndex = 0; // Индекс в начальном порядке

        // Обрабатываем черепах снизу вверх желаемого порядка
        while (desiredIndex < desired.size()) {
            String currentDesired = desired.get(desiredIndex);

            // Ищем текущую черепаху в начальном порядке
            int pos = initial.size() - 1;
            while (pos >= 0 && !initial.get(pos).equals(currentDesired)) {
                pos--;
            }

            if (pos >= 0) {
                // Все черепахи выше найденной должны быть перемещены
                for (int i = pos - 1; i >= initialIndex; i--) {
                    moves.add(initial.get(i));
                }
                initialIndex = pos + 1;
            }
            desiredIndex++;
        }
        return moves;
    }
}
