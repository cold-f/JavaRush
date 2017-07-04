package com.javarush.test.level33.lesson15.big01.strategies;

import java.io.Serializable;

public class Entry implements Serializable{

    Long key;
    String value;
    Entry next;
    int hash;

    public Entry(int hash, Long key, String value, Entry next) {
        this.key = key;
        this.hash = hash;
        this.next = next;
        this.value = value;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (next != null ? next.hashCode() : 0);
        result = 31 * result + hash;
        return result;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", next=" + next +
                ", hash=" + hash +
                '}';
    }
}
