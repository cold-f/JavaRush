package com.javarush.test.level33.lesson15.big01;

import com.javarush.test.level33.lesson15.big01.strategies.StorageStrategy;

public class Shortener {

    private Long lastId = 0L;//Это поле будет отвечать за последнее значение идентификатора, которое было использовано при добавлении новой строки в хранилище
    private StorageStrategy storageStrategy;


    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public synchronized Long getId(String string)  { //будет возвращать идентификатор id для заданной строки.
        if (storageStrategy.containsValue(string)){
            return storageStrategy.getKey(string);
        }
        else {
            lastId++;
            storageStrategy.put(lastId, string);
            return lastId;
        }
    }


    public synchronized String getString(Long id)  { //будет возвращать строку для заданного  идентификатора или null, если передан неверный идентификатор.
        return storageStrategy.getValue(id);
    }

}
