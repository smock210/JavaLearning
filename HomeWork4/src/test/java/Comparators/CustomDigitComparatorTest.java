package Comparators;

import org.junit.jupiter.api.Test;
import ru.kda.Comparators.CustomDigitComparator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class CustomDigitComparatorTest {

    private final CustomDigitComparator comparator = new CustomDigitComparator();

    @Test
    public void testEvenBeforeOdd() {
        // Четное должно быть "меньше" нечетного
        assertEquals(-1, comparator.compare(2, 3));
        assertEquals(1, comparator.compare(3, 2));
    }

    @Test
    public void testEvenAndEven() {
        // При одинаковой четности — сравнение по значению
        assertEquals(-1, comparator.compare(2, 4));
        assertEquals(1, comparator.compare(6, 4));
        assertEquals(0, comparator.compare(4, 4));
    }

    @Test
    public void testOddAndOdd() {
        // При одинаковой четности — сравнение по значению
        assertEquals(-1, comparator.compare(3, 5));
        assertEquals(1, comparator.compare(7, 5));
        assertEquals(0, comparator.compare(5, 5));
    }

    @Test
    public void testNullInputThrowsException() {
        // Проверка на выброс исключения при null
        assertThrows(IllegalArgumentException.class, () -> comparator.compare(null, 1));
        assertThrows(IllegalArgumentException.class, () -> comparator.compare(1, null));
        assertThrows(IllegalArgumentException.class, () -> comparator.compare(null, null));
    }

    @Test
    public void testSortingList() {
        List<Integer> numbers = Arrays.asList(3, 8, 1, 4, 7, 2, 5, 6);
        List<Integer> expected = Arrays.asList(2, 4, 6, 8, 1, 3, 5, 7);

        numbers.sort(comparator);

        assertEquals(expected, numbers);
    }

    @Test
    public void testStableSortPreservesOrderForEqualElements() {
        // Проверка, что при равенстве (одинаковая четность и значение) порядок не меняется
        List<Integer> numbers = Arrays.asList(4, 2, 6, 1, 3, 5);
        List<Integer> expected = Arrays.asList(2, 4, 6, 1, 3, 5); // Исходный порядок чётных: 4,2,6 → после сортировки: 2,4,6

        numbers.sort(comparator);

        assertEquals(expected, numbers);
    }
}