import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            int n = Integer.parseInt(parts[0]);
            int totalSums = n * (n - 1) / 2;

            if (parts.length != totalSums + 1) {
                System.out.println("Impossibleeee");
                continue;
            }

            int[] pairSums = new int[totalSums];
            for (int i = 0; i < totalSums; i++) {
                pairSums[i] = Integer.parseInt(parts[i + 1]);
            }

            Arrays.sort(pairSums);

            boolean found = false;

            for (int i = 2; i < totalSums && !found; i++) {
                int sum = pairSums[0] + pairSums[1] - pairSums[i];
                if (sum % 2 != 0) continue;

                int a = sum / 2;
                int b = pairSums[0] - a;
                int c = pairSums[1] - a;

                int[] result = new int[n];
                result[0] = a;
                result[1] = b;
                result[2] = c;

                List<Integer> expectedSums = new ArrayList<>();
                expectedSums.add(result[0] + result[1]);
                expectedSums.add(result[0] + result[2]);
                expectedSums.add(result[1] + result[2]);

                for (int idx = 3; idx < n; idx++) {
                    Integer next = findNext(result, idx, pairSums, expectedSums);
                    if (next == null) {
                        break;
                    }
                    result[idx] = next;
                    for (int j = 0; j < idx; j++) {
                        expectedSums.add(result[j] + next);
                    }
                }

                if (expectedSums.size() == pairSums.length) {
                    Collections.sort(expectedSums);
                    boolean ok = true;
                    for (int k = 0; k < pairSums.length; k++) {
                        if (!expectedSums.get(k).equals(pairSums[k])) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        Arrays.sort(result);
                        for (int x : result) System.out.print(x + " ");
                        System.out.println();
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("Impossibleeee");
            }
        }
    }

    // Подбор следующего элемента массива, проверяя на соответствие pair-суммам
    private static Integer findNext(int[] current, int len, int[] originalSums, List<Integer> expectedSums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int s : originalSums) freq.put(s, freq.getOrDefault(s, 0) + 1);
        for (int s : expectedSums) freq.put(s, freq.get(s) - 1);

        List<Integer> possible = new ArrayList<>(freq.keySet());
        Collections.sort(possible);

        for (int candidate : possible) {
            if (freq.get(candidate) == null || freq.get(candidate) <= 0) continue;

            int x = candidate - current[0];
            boolean valid = true;
            Map<Integer, Integer> temp = new HashMap<>(freq);

            for (int i = 0; i < len; i++) {
                int pairSum = current[i] + x;
                if (temp.getOrDefault(pairSum, 0) <= 0) {
                    valid = false;
                    break;
                }
                temp.put(pairSum, temp.get(pairSum) - 1);
            }

            if (valid) return x;
        }
        return null;
    }
}
