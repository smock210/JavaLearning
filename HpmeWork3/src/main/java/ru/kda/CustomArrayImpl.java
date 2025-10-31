package ru.kda;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class CustomArrayImpl implements CustomArray{

    private int size;
    private Object[] elements;

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ARRAY = {};

    /**
     * Конструктор по умолчанию. Создает пустой массив.
     */
    public CustomArrayImpl() {
        this.elements = EMPTY_ARRAY;
        this.size = 0;
    }

    /**
     * Конструктор с заданной начальной емкостью.
     * @param initialCapacity начальная емкость массива
     * @throws IllegalArgumentException если initialCapacity отрицательный
     */
    public CustomArrayImpl(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elements = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elements = EMPTY_ARRAY;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.size = 0;
    }

    /**
     * Конструктор, создающий массив из элементов коллекции.
     * @param collection коллекция элементов
     * @throws IllegalArgumentException если коллекция равна null
     */
    public CustomArrayImpl(Collection<Object> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection cannot be null");
        }

        if (collection.isEmpty()) {
            this.elements = EMPTY_ARRAY;
            this.size = 0;
        } else {
            this.elements = collection.toArray();
            this.size = elements.length;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(Object item) {
        ensureCapacity(size + 1);
        elements[size++] = item;
        return true;
    }

    @Override
    public boolean addAll(Object[] items) {
        if (items == null) {
            throw new IllegalArgumentException("Элемент массив не может быть null");
        }
        if (items.length == 0) {
            return false;
        }
        //увеличиваем размер массива, если не хватает
        ensureCapacity(size + items.length);
        //копируем элементы из массива items в массив
        System.arraycopy(items, 0, elements, size, items.length);
        //увеличиваем текущий размер массива
        size += items.length;
        return true;
    }

    @Override
    public boolean addAll(Collection items) {

        //проверяем, что переданный массив не null
        if (items == null) {
            throw new IllegalArgumentException("Элемент массив не может быть null");
        }
        //проверяем, что в массиве есть элементы
        if (items.isEmpty()) {
            return false;
        }
        //увеличиваем размер массива, если не хватает
        ensureCapacity(size + items.size());
        //копируем элементы из массива items в массив
        for (Object item : items) {
            elements[size++] = item;
        }
        return true;

    }

    @Override
    public boolean addAll(int index, Object[] items) {
        if (items == null) {
            //return false;
            throw new IllegalArgumentException("Элемент массив не может быть null");
        }
        if (items.length == 0) {
            return false;
        }
        arrayOfBound(index);
        // Увеличиваем размер массива, если не хватает
        ensureCapacity(size + items.length);
        // Сдвигаем существующие элементы вправо
        int numToMove = size - index;
        if (numToMove > 0) {
            System.arraycopy(elements, index, elements, index + items.length, numToMove);
        }

        // Копируем новые элементы
        System.arraycopy(items, 0, elements, index, items.length);
        size += items.length;
        return true;

    }

    private void arrayOfBound(int index) {
        if (index < 0 || index > elements.length) {
            throw new IndexOutOfBoundsException("Индекс выходит за границы массива");
        }
    }

    @Override
    public Object get(int index) {
        arrayOfBound(index);
        return elements[index];
    }

    @Override
    public Object set(int index, Object item) {
        arrayOfBound(index);
        //запоминаем старый элемент
        Object oldValue = elements[index];
        //заменяем элемент на новый
        elements[index] = item;
        return oldValue;
    }

    @Override
    public void remove(int index) {
        arrayOfBound(index);

        int numToMove = size - index - 1;
        // Сдвигаем элементы влево если это не последний элемент
        if (numToMove > 0) {
            System.arraycopy(elements, index + 1, elements, index, numToMove);
        }

        elements[--size] = null;

    }

    @Override
    public boolean remove(Object item) {
        if (item == null){
            return false;
        }
        int index = indexOf(item);
        if (index == -1){
            return false;
        } else {
            remove(index);
            return true;
        }
    }

    @Override
    public boolean contains(Object item) {
        if (item == null){
            return false;
        }
        int index = indexOf(item);
        return index != -1;
    }

    @Override
    public int indexOf(Object item) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(item, elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void ensureCapacity(int newElementsCount) {
        if (newElementsCount > elements.length) {
            int oldCapacity = elements.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1); // Увеличиваем на 50%
            if (newCapacity < newElementsCount) {
                newCapacity = newElementsCount;
            }
            if (newCapacity < DEFAULT_CAPACITY) {
                newCapacity = DEFAULT_CAPACITY;
            }
            elements = Arrays.copyOf(elements, newCapacity);
        }

    }

    @Override
    public int getCapacity() {
        return elements.length;

    }

    @Override
    public void reverse() {
        for (int i = 0; i < size / 2; i++) {
            Object temp = elements[i];
            int j = size - 1 - i;
            elements[i] = elements[j];
            elements[j] = temp;
        }

    }

    @Override
    public Object[] toArray() {
        if (size == 0){
            return new Object[0];
        } else {

            return Arrays.copyOf(elements, size);
        }

    }
    @Override
    public String toString() {
        if (size == 0) {
            return "[ ]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(" ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}
