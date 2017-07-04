package com.javarush.test.level33.lesson15.big01.strategies;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class DualHashBidiMapStorageStrategy implements StorageStrategy {

    private DualHashBidiMap data = new DualHashBidiMap();
    @Override
    public boolean containsKey(Long key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {
        return data.containsValue(value);
    }

    @Override
    public void put(Long key, String value) {
        data.put(key, value);

    }

    @Override
    public Long getKey(String value) {
        if (containsValue(value))
            return (Long) data.getKey(value);
        else
            return null;
    }

    @Override
    public String getValue(Long key) {
        if (containsKey(key))
            return (String) data.get(key);
        else
            return null;
    }
}
