package ru.kda.lecture2.branching;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kda.lecture2.common.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MyBranchingTest {
    @Test
    public void testMaxInt_UtilsUtilFunc2False_ReturnsMaxValue() {
        // Arrange
        Utils utilsMock = Mockito.mock(Utils.class);
        when(utilsMock.utilFunc2()).thenReturn(false); // utilFunc2() возвращает false → циклы не выполняются

        MyBranching myBranching = new MyBranching(utilsMock);

        // Act
        int result = myBranching.maxInt(3, 5);

        // Assert
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

        // Act
        int result = myBranching.maxInt(3, 5);

        // Assert
        assertEquals(0, result); // По логике метода, возвращается 0 после всех итераций
        verify(utilsMock, times(1)).utilFunc2();
        verify(utilsMock, atLeastOnce()).utilFunc1("Hello");
    }

    @Test
    public void testMaxInt_UtilsUtilFunc2True_UtilFunc1True_ReturnsZeroEarly() {
        // Arrange
        Utils utilsMock = Mockito.mock(Utils.class);
        when(utilsMock.utilFunc2()).thenReturn(true);
        when(utilsMock.utilFunc1("Hello")).thenReturn(true); // utilFunc1() возвращает true → метод возвращает 0 сразу

        MyBranching myBranching = new MyBranching(utilsMock);

        // Act
        int result = myBranching.maxInt(3, 5);

        // Assert
        assertEquals(0, result);
        verify(utilsMock, times(1)).utilFunc2();
        verify(utilsMock, times(1)).utilFunc1("Hello");
    }
}
