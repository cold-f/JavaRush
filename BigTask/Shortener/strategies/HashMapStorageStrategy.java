package com.javarush.test.level33.lesson15.big01.strategies;

import java.util.HashMap;
import java.util.Map;

public class HashMapStorageStrategy implements StorageStrategy {

    private HashMap<Long, String> data = new HashMap<>();

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
        Long key = 0L;
            for (Map.Entry<Long, String> e : data.entrySet()) {
                Long tempKey = e.getKey();
                String tempValue = e.getValue();
                if ((tempValue.toString()).equals(value)) {
                   key = tempKey;
                }
            }
        return key;
    }

    @Override
    public String getValue(Long key) {
        if (data.containsKey(key))
            return data.get(key);
        else
            return null;
    }
}
