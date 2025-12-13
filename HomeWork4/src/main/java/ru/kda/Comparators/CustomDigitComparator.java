package ru.kda.Comparators;

import java.util.Comparator;

public class CustomDigitComparator implements Comparator<Integer> {



    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1 == null || o2 == null) {
            throw new IllegalArgumentException("Numbers cannot be null");
        }

        boolean isO1 = o1 % 2 == 0;
        boolean isO2 = o2 % 2 == 0;

        // если четность чисел не совпала
        if (isO1 && !isO2) {
            return -1; // четное идет перед нечетным
        } else if (!isO1 && isO2) {
            return 1;  // нечетное идет после четного
        } else {
            // Если оба числа одинаковой четности, сохраняем исходный порядок
             return o1.compareTo(o2);
        }
    }
}
