import java.util.*;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            List<int[]> rounds = new ArrayList<>();

            for (int i = 0; i < 13; i++) {
                if (!scanner.hasNextLine()) break;
                int[] dice = Arrays.stream(scanner.nextLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                rounds.add(dice);
            }

            if (rounds.size() == 13) {
                int[] scores = calculateBestScore(rounds);
                for (int score : scores) {
                    System.out.print(score + " ");
                }
            }
        }
        scanner.close();
    }

    // Оптимальное распределение очков
    private static int[] calculateBestScore(List<int[]> rounds) {
        int[] categoryScores = new int[13]; // Очки для каждой категории
        boolean[] usedCategories = new boolean[13]; // Флаги использованных категорий

        for (int i = 0; i < 13; i++) {
            int[] dice = rounds.get(i);
            categoryScores[i] = calculateMaxCategoryScore(dice, usedCategories);
        }

        int upperSectionScore = Arrays.stream(categoryScores, 0, 6).sum();
        int bonus = (upperSectionScore >= 63) ? 35 : 0;
        int totalScore = Arrays.stream(categoryScores).sum() + bonus;

        int[] result = Arrays.copyOf(categoryScores, 15);
        result[13] = bonus;
        result[14] = totalScore;
        return result;
    }

    private static int calculateMaxCategoryScore(int[] dice, boolean[] usedCategories) {
        int[] counts = new int[7];
        for (int die : dice) counts[die]++;

        List<Integer> availableScores = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            if (!usedCategories[i - 1]) {
                availableScores.add(i * counts[i]);
            }
        }

        if (!usedCategories[6]) availableScores.add(Arrays.stream(dice).sum()); // Шанс
        if (!usedCategories[7] && hasNOfAKind(counts, 3)) availableScores.add(Arrays.stream(dice).sum());
        if (!usedCategories[8] && hasNOfAKind(counts, 4)) availableScores.add(Arrays.stream(dice).sum());
        if (!usedCategories[9] && isShortStraight(dice)) availableScores.add(25);
        if (!usedCategories[10] && isLongStraight(dice)) availableScores.add(35);
        if (!usedCategories[11] && isFullHouse(counts)) availableScores.add(40);

        return availableScores.stream().max(Integer::compareTo).orElse(0);
    }

    private static boolean hasNOfAKind(int[] counts, int n) {
        for (int count : counts) {
            if (count >= n) return true;
        }
        return false;
    }

    private static boolean isShortStraight(int[] dice) {
        Set<Integer> unique = new HashSet<>();
        for (int die : dice) unique.add(die);
        return unique.containsAll(Arrays.asList(1, 2, 3, 4)) ||
                unique.containsAll(Arrays.asList(2, 3, 4, 5)) ||
                unique.containsAll(Arrays.asList(3, 4, 5, 6));
    }

    private static boolean isLongStraight(int[] dice) {
        Set<Integer> unique = new HashSet<>();
        for (int die : dice) unique.add(die);
        return unique.containsAll(Arrays.asList(1, 2, 3, 4, 5)) ||
                unique.containsAll(Arrays.asList(2, 3, 4, 5, 6));
    }

    private static boolean isFullHouse(int[] counts) {
        boolean hasThree = false, hasTwo = false;
        for (int count : counts) {
            if (count == 3) hasThree = true;
            if (count == 2) hasTwo = true;
        }
        return hasThree && hasTwo;
    }
}
