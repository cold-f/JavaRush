package com.javarush.test.level33.lesson15.big01.strategies;

public interface StorageStrategy {

    boolean containsKey(Long key); //должен вернуть true, если хранилище содержит переданный ключ.

    boolean containsValue(String value);//  должен вернуть true, если хранилище содержит переданное значение.

    void put(Long key, String value); // добавить в хранилище новую пару ключ -значение.

    Long getKey(String value);    // вернуть ключ для переданного значения.

    String getValue(Long key);  //вернуть значение для переданного ключа.
}
