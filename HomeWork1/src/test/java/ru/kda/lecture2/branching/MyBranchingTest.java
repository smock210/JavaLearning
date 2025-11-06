package ru.kda.lecture2.branching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kda.lecture2.common.Utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MyBranchingTest {

    private MyBranching myBranching;
    private Utils utilsMock;
    @BeforeEach
    public void setUp() {
        utilsMock = mock(Utils.class);
        myBranching = new MyBranching(utilsMock);
    }

    @Test
    public void testIfElseExample_WhenUtilFunc2ReturnsTrue() {
        when(utilsMock.utilFunc2()).thenReturn(true);
        boolean result = myBranching.ifElseExample();
        assertTrue(result);
    }

    @Test
    public void testIfElseExample_WhenUtilFunc2ReturnsFalse() {
        when(utilsMock.utilFunc2()).thenReturn(false);
        boolean result = myBranching.ifElseExample();
        assertFalse(result);
    }

    /**
     * Тест проверяет поведение метода {@link MyBranching#maxInt(int, int)} при условии,
     * что метод {@link Utils#utilFunc2()} возвращает false.
     * <p>
     * Ожидается, что:
     * - Метод {@code maxInt} вернёт максимальное значение из двух переданных аргументов (5),
     * - Метод {@code utilFunc2} будет вызван один раз,
     * - Метод {@code utilFunc1} не будет вызван.
     */
    @Test
    public void testMaxInt_UtilsUtilFunc2False_ReturnsMaxValue() {
        Utils utilsMock = Mockito.mock(Utils.class);
        when(utilsMock.utilFunc2()).thenReturn(false); // utilFunc2() возвращает false → циклы не выполняются
        MyBranching myBranching = new MyBranching(utilsMock);
        int result = myBranching.maxInt(3, 5);
        assertEquals(5, result); // Максимум из 3 и 5
        verify(utilsMock, times(1)).utilFunc2();
        verifyNoInteractions(utilsMock, Mockito.times(0), Mockito.eq("Hello")); // utilFunc1 не вызывался
    }

    @Test
    public void testMaxInt_UtilsUtilFunc2True_UtilFunc1False_LoopsAndReturnsMax() {
        // Arrange
        Utils utilsMock = Mockito.mock(Utils.class);
        when(utilsMock.utilFunc2()).thenReturn(true); // utilFunc2() возвращает true → циклы запускаются
        when(utilsMock.utilFunc1("Hello")).thenReturn(false); // utilFunc1() возвращает false → не прерывает

        MyBranching myBranching = new MyBranching(utilsMock);
        int result = myBranching.maxInt(3, 5);
        assertEquals(0, result); // По логике метода, возвращается 0 после всех итераций
        verify(utilsMock, times(1)).utilFunc2();
        verify(utilsMock, atLeastOnce()).utilFunc1("Hello");
    }

    @Test
    public void testMaxInt_UtilsUtilFunc2True_UtilFunc1True_ReturnsZeroEarly() {
        Utils utilsMock = Mockito.mock(Utils.class);
        when(utilsMock.utilFunc2()).thenReturn(true);
        when(utilsMock.utilFunc1("Hello")).thenReturn(true); // utilFunc1() возвращает true → метод возвращает 0 сразу

        MyBranching myBranching = new MyBranching(utilsMock);
        int result = myBranching.maxInt(3, 5);
        assertEquals(0, result);
        verify(utilsMock, times(1)).utilFunc2();
        verify(utilsMock, times(1)).utilFunc1("Hello");
    }


    @Test
    public void testSwitchExample_WhenIEquals0() {
        when(utilsMock.utilFunc2()).thenReturn(true);
        myBranching.switchExample(0);
        verify(utilsMock).utilFunc2(); // Вызов в default
        verify(utilsMock).utilFunc1("abc2"); // Вызов в default, т.к. utilFunc2() вернула true
        verifyNoMoreInteractions(utilsMock);
    }

    @Test
    public void testSwitchExample_WhenIEquals1() {
        myBranching.switchExample(1);
        verify(utilsMock).utilFunc1("abc"); // case 1
        verify(utilsMock).utilFunc2();     // case 2 (fall-through)
        verifyNoMoreInteractions(utilsMock);
    }

    @Test
    public void testSwitchExample_WhenIEquals2() {
        myBranching.switchExample(2);
        verify(utilsMock).utilFunc2(); // case 2
        verifyNoMoreInteractions(utilsMock);
    }
}
