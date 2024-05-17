package ru.skypro.homework.utils;


/**
 * Обмен данными между методами
 * @param <T>
 */
public final class ValueFromMethod<T> {
    public final boolean RESULT;
    public final String MESSAGE;
    public final T VALUE;

    public ValueFromMethod(boolean result, String mes ) {
        RESULT = result;
        MESSAGE = mes;
        VALUE = null;
    }

    public ValueFromMethod(T value) {
        RESULT = true;
        MESSAGE = "ok";
        VALUE = value;
    }

    public T getValue() {
        return (T) VALUE;
    }
}
