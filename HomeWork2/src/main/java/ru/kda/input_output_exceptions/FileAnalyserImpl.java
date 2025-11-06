package ru.kda.input_output_exceptions;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileAnalyserImpl implements FileAnalyser {

    private final String filePath;
    private final String fileName;
    private List<String> lines;

    public FileAnalyserImpl(String filePath) {
        this.filePath = filePath;
        this.fileName = Paths.get(filePath).getFileName().toString();
        try {
            this.lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public int getRowsCount() {
        return lines.size();
    }

    @Override
    public int getLettersCount() {
        return lines.stream()
                .mapToInt(line -> {
                    int count = 0;
                    for (char c : line.toCharArray()) {
                        if (Character.isLetterOrDigit(c)) {
                            count++;
                        }
                    }
                    return count;
                })
                .sum();
    }

    @Override
    public Map<Character, Integer> getSymbolsStatistics() {
        Map<Character, Integer> statistics = new HashMap<>();
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                if (Character.isLetterOrDigit(c)) {
                    statistics.put(c, statistics.getOrDefault(c, 0) + 1);
                }
            }
        }
        return statistics;
    }

    @Override
    public List<Character> getTopNPopularSymbols(int n) {
        return getSymbolsStatistics().entrySet().stream()
                .sorted((e1, e2) -> {
                    int cmp = Integer.compare(e2.getValue(), e1.getValue());
                    if (cmp == 0) {
                        // При одинаковой частоте сортируем по символу в порядке вхождения
                        return Integer.compare(getFirstPosition(e1.getKey()), getFirstPosition(e2.getKey()));
                    }
                    return cmp;
                })
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    
    private int getFirstPosition(char c) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int pos = line.indexOf(c);
            if (pos != -1) {
                return i * 1000 + pos; // Грубая эвристика для сохранения порядка
            }
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public void saveSummary(String summaryFilePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(summaryFilePath))) {
            writer.println("fileName: " + getFileName());
            writer.println("rowsCount: " + getRowsCount());
            writer.println("totalSymbols: " + getLettersCount());
            
            Map<Character, Integer> stats = getSymbolsStatistics();
            writer.print("symbolsStatistics: {");
            if (!stats.isEmpty()) {
                writer.print(stats.entrySet().stream()
                        .map(entry -> "'" + entry.getKey() + "': " + entry.getValue())
                        .collect(Collectors.joining(", ")));
            }
            writer.println("}");

            List<Character> top3 = getTopNPopularSymbols(3);
            writer.print("top3PopularSymbols: ");
            writer.println(top3.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")));
        } catch (IOException e) {
            throw new RuntimeException("Error writing summary file: " + summaryFilePath, e);
        }
    }
}