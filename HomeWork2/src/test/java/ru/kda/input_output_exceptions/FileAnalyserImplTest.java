package ru.kda.input_output_exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FileAnalyserImplTest {

    @TempDir
    Path tempDir;

    private File testFile;
    private File summaryFile;
    private FileAnalyserImpl analyzer;

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("input.txt").toFile();
        summaryFile = tempDir.resolve("summary.txt").toFile();
    }

    @Test
    void testGetFileName() throws IOException {
        String content = "Hello!";
        writeToFile(testFile, content);
        
        analyzer = new FileAnalyserImpl(testFile.getAbsolutePath());
        assertEquals("input.txt", analyzer.getFileName());
    }
    @Test
    void testGetSymbolsStatistics() throws IOException {
        String content = "Hello!";
        writeToFile(testFile, content);
        
        analyzer = new FileAnalyserImpl(testFile.getAbsolutePath());
        Map<Character, Integer> expected = Map.of(
            'H', 1,
            'e', 1,
            'l', 2,
            'o', 1
        );
        assertEquals(expected, analyzer.getSymbolsStatistics());
    }


    @Test
    void testSaveSummary() throws IOException {
        String content = "Hello!";
        writeToFile(testFile, content);
        
        analyzer = new FileAnalyserImpl(testFile.getAbsolutePath());
        analyzer.saveSummary(summaryFile.getAbsolutePath());
        
        assertTrue(summaryFile.exists());
        
        String summaryContent = readFile(summaryFile);
        assertTrue(summaryContent.contains("fileName: input.txt"));
        assertTrue(summaryContent.contains("rowsCount: 1"));
        assertTrue(summaryContent.contains("totalSymbols: 5"));
    }

    @Test
    void testEmptyFile() throws IOException {
        writeToFile(testFile, "");
        
        analyzer = new FileAnalyserImpl(testFile.getAbsolutePath());
        assertEquals(0, analyzer.getRowsCount());
        assertEquals(0, analyzer.getLettersCount());
        assertTrue(analyzer.getSymbolsStatistics().isEmpty());
        assertTrue(analyzer.getTopNPopularSymbols(3).isEmpty());
    }

    @Test
    void testFileWithNumbers() throws IOException {
        String content = "abc123";
        writeToFile(testFile, content);
        
        analyzer = new FileAnalyserImpl(testFile.getAbsolutePath());
        Map<Character, Integer> expected = Map.of(
            'a', 1,
            'b', 1,
            'c', 1,
            '1', 1,
            '2', 1,
            '3', 1
        );
        assertEquals(expected, analyzer.getSymbolsStatistics());
    }

    @Test
    void testFileNotFound() {
        assertThrows(RuntimeException.class, () -> {
            new FileAnalyserImpl("/non/existent/file.txt");
        });
    }



    private void writeToFile(File file, String content) throws IOException {
        try (var writer = new java.io.PrintWriter(file)) {
            writer.print(content);
        }
    }
    
    private String readFile(File file) throws IOException {
        return new String(java.nio.file.Files.readAllBytes(file.toPath()));
    }
}