package ru.kda.Equals;

public class DigitHandler {
    private final int value;

    public DigitHandler(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DigitHandler temp = (DigitHandler) o;
        return value == temp.value;

    }
    @Override
    public int hashCode() {
        //if (value == null) return 0;
        int total = 19;
        return value + total;
    }
}
