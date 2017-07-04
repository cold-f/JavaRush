package com.javarush.test.level33.lesson15.big01;

import com.javarush.test.level33.lesson15.big01.strategies.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Solution {
    public static Set<Long> getIds(Shortener shortener, Set<String> strings)  {//для переданного множества строк возвращает множество идентификаторов
        Set<Long> IDs= new HashSet<>();

        Iterator<String> iterator =  strings.iterator();
        while(iterator.hasNext()) {
            String string = iterator.next();
            Long ID = shortener.getId(string);
            IDs.add(ID);
        }
        return IDs;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys)  { //возвращает множество строк, которое соответствует переданному   множеству идентификаторов.
        Set<String> strings = new HashSet<>();

        Iterator<Long> iterator =  keys.iterator();
        while(iterator.hasNext()) {
            Long ID = iterator.next();
            String string = shortener.getString(ID);
            strings.add(string);
        }
        return strings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) { //тестирует работу переданной стратегии на определенном количестве  элементов elementsNumber
        Helper.printMessage(strategy.getClass().getSimpleName());

        Set<String> strings = new HashSet<>();
        for (long i = 0; i < elementsNumber; i++) {
            strings.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);

        Date d1 = new Date();
        Set<Long> IDs=  getIds(shortener, strings);
        Date d2 = new Date();
        Long vremyaGetIds = d2.getTime() - d1.getTime();
        Helper.printMessage("Время отработки метода getIds: "+vremyaGetIds);


        Date d3 = new Date();
        Set<String> strings1 =  getStrings(shortener, IDs);
        Date d4 = new Date();
        Long vremyaGetStrings = d4.getTime() - d3.getTime();
        Helper.printMessage("Время отработки метода getStrings: "+vremyaGetStrings);

        if (strings.equals(strings1))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");
    }

    public static void main(String[] args)  {
       // testStrategy(new HashMapStorageStrategy(), 100000);
       // testStrategy(new OurHashMapStorageStrategy(), 100000);
        //testStrategy(new FileStorageStrategy(), 100);
      //  testStrategy(new OurHashBiMapStorageStrategy(), 100000);
       // testStrategy(new HashBiMapStorageStrategy(), 100000);
       // testStrategy(new DualHashBidiMapStorageStrategy(), 1000);
        testStrategy(new MySQLStorageStrategy(), 100);
    }


}
