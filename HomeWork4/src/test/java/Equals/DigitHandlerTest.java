package Equals;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import ru.kda.Equals.DigitHandler;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса DigitHandler")
public class DigitHandlerTest {

    @Test
    @DisplayName("Тест: сравнение с null должен вернуть false")
    public void testEqualsWithNull() {
        DigitHandler handler = new DigitHandler(10);
        assertFalse(handler.equals(null));
    }

    @Test
    @DisplayName("Тест: сравнение с самим собой должен вернуть true")
    public void testEqualsWithSelf() {
        DigitHandler handler = new DigitHandler(10);
        assertTrue(handler.equals(handler));
    }

    @Test
    @DisplayName("Тест: сравнение с объектом другого класса должен вернуть false")
    public void testEqualsWithDifferentClass() {
        DigitHandler handler = new DigitHandler(10);
        String other = "some string";
        assertFalse(handler.equals(other));
    }

    @Test
    @DisplayName("Тест: сравнение с эквивалентным объектом должен вернуть true")
    public void testEqualsWithEqualObject() {
        DigitHandler handler1 = new DigitHandler(10);
        DigitHandler handler2 = new DigitHandler(10);
        assertTrue(handler1.equals(handler2));
    }

    @Test
    @DisplayName("Тест: сравнение с объектом с другим значением должен вернуть false")
    public void testEqualsWithDifferentValue() {
        DigitHandler handler1 = new DigitHandler(10);
        DigitHandler handler2 = new DigitHandler(15);
        assertFalse(handler1.equals(handler2));
    }

    @Test
    @DisplayName("Тест: корректная работа hashCode при одинаковых значениях")
    public void testHashCodeForEqualObjects() {
        DigitHandler handler1 = new DigitHandler(10);
        DigitHandler handler2 = new DigitHandler(10);
        assertEquals(handler1.hashCode(), handler2.hashCode());
    }

    @Test
    @DisplayName("Тест: значение hashCode соответствует ожидаемой логике (value + 19)")
    public void testHashCodeCalculation() {
        DigitHandler handler = new DigitHandler(5);
        int expected = 5 + 19;
        assertEquals(expected, handler.hashCode());
    }

    @Test
    @DisplayName("Тест: граничные значения int — минимальное значение")
    public void testWithMinIntValue() {
        DigitHandler handler1 = new DigitHandler(Integer.MIN_VALUE);
        DigitHandler handler2 = new DigitHandler(Integer.MIN_VALUE);
        assertTrue(handler1.equals(handler2));
        assertEquals(handler1.hashCode(), handler2.hashCode());
    }

    @Test
    @DisplayName("Тест: граничные значения int — максимальное значение")
    public void testWithMaxIntValue() {
        DigitHandler handler1 = new DigitHandler(Integer.MAX_VALUE);
        DigitHandler handler2 = new DigitHandler(Integer.MAX_VALUE);
        assertTrue(handler1.equals(handler2));
        assertEquals(handler1.hashCode(), handler2.hashCode());
    }

    @Test
    @DisplayName("Тест: корректность работы getValue")
    public void testGetValue() {
        DigitHandler handler = new DigitHandler(42);
        assertEquals(42, handler.getValue());
    }
}