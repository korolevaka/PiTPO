import java.util.*;
import java.io.*;

public class Task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Чтение словаря до пустой строки
        List<String> dictionary = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            dictionary.add(line.toLowerCase());
        }

        // Построение графа связей между словами
        Map<String, List<String>> graph = buildGraph(dictionary);

        // Обработка пар слов
        boolean firstOutput = true;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] pair = line.split(" ");
            if (pair.length != 2) continue;

            String start = pair[0].toLowerCase();
            String end = pair[1].toLowerCase();

            // Проверка наличия слов в словаре
            if (!graph.containsKey(start) || !graph.containsKey(end)) {
                if (!firstOutput) System.out.println();
                System.out.println("No solution.");
                firstOutput = false;
                continue;
            }

            // Поиск кратчайшего пути
            List<String> path = findShortestPath(graph, start, end);

            // Вывод результата
            if (!firstOutput) System.out.println();
            firstOutput = false;

            if (path == null) {
                System.out.println("No solution.");
            } else {
                for (String word : path) {
                    System.out.println(word);
                }
            }
        }
    }

    // Строит граф связей между словами одинаковой длины
    private static Map<String, List<String>> buildGraph(List<String> dictionary) {
        Map<String, List<String>> graph = new HashMap<>();

        // Группируем слова по длине
        Map<Integer, List<String>> wordsByLength = new HashMap<>();
        for (String word : dictionary) {
            int len = word.length();
            wordsByLength.putIfAbsent(len, new ArrayList<>());
            wordsByLength.get(len).add(word);
        }

        // Для каждой группы слов одинаковой длины строим связи
        for (List<String> words : wordsByLength.values()) {
            for (int i = 0; i < words.size(); i++) {
                String word1 = words.get(i);
                graph.putIfAbsent(word1, new ArrayList<>());

                for (int j = i + 1; j < words.size(); j++) {
                    String word2 = words.get(j);
                    if (isDoublet(word1, word2)) {
                        graph.get(word1).add(word2);
                        graph.putIfAbsent(word2, new ArrayList<>());
                        graph.get(word2).add(word1);
                    }
                }
            }
        }
        return graph;
    }

    // Проверяет, являются ли слова дублетами (отличаются на 1 букву)
    private static boolean isDoublet(String a, String b) {
        if (a.length() != b.length()) return false;

        int differences = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                differences++;
                if (differences > 1) return false;
            }
        }
        return differences == 1;
    }

    // Поиск кратчайшего пути между словами
    private static List<String> findShortestPath(Map<String, List<String>> graph,
                                                 String start, String end) {
        if (start.equals(end)) {
            return Collections.singletonList(start);
        }

        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);

                    if (neighbor.equals(end)) {
                        return reconstructPath(parentMap, end);
                    }
                }
            }
        }

        return null;
    }

    // Восстанавливает путь от конечного слова к начальному
    private static List<String> reconstructPath(Map<String, String> parentMap, String end) {
        LinkedList<String> path = new LinkedList<>();
        String current = end;

        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        return path;
    }
}
