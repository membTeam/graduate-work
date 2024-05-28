package ru.skypro.homework.utils;


import lombok.extern.log4j.Log4j;

/**
 * Обмен данными между методами
 * @param <T>
 */
@Log4j
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

    public static ValueFromMethod resultErr(String strErr) {
        log.error(strErr);
        return new ValueFromMethod(false, strErr);
    }

    public static ValueFromMethod resultOk() {
        return new ValueFromMethod(true, "ok");
    }
}
