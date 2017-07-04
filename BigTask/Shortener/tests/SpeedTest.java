package com.javarush.test.level33.lesson15.big01.tests;

import com.javarush.test.level33.lesson15.big01.Helper;
import com.javarush.test.level33.lesson15.big01.Shortener;
import com.javarush.test.level33.lesson15.big01.strategies.HashBiMapStorageStrategy;
import com.javarush.test.level33.lesson15.big01.strategies.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.javarush.test.level33.lesson15.big01.Solution.getIds;
import static com.javarush.test.level33.lesson15.big01.Solution.getStrings;

public class SpeedTest {

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids) throws SQLException {//возвращает время в миллисек.необходимое  для получения идентификаторов для всех строк из strings
        Date d1 = new Date();
        Set<Long> IDs=  getIds(shortener, strings);
        Date d2 = new Date();
        return  d2.getTime() - d1.getTime();
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) throws SQLException {//возвращает время в миллисек. необходимое для получения строк для всех идентификаторов из ids
        Date d1 = new Date();
        Set<String> strings1 =  getStrings(shortener, ids);
        Date d2 = new Date();
        return d2.getTime() - d1.getTime();
    }

    @Test
    public void testHashMapStorage() throws SQLException {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        HashSet<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> ids1 = new HashSet<>();
        Set<Long> ids2 = new HashSet<>();

        long time1 = getTimeForGettingIds(shortener1, origStrings, ids1);
        long time2 = getTimeForGettingIds(shortener2, origStrings, ids2);

        Assert.assertTrue(time1>time2);

        long time11 = getTimeForGettingStrings(shortener1, ids1, new HashSet<String>());
        long time22 = getTimeForGettingStrings(shortener2, ids2, new HashSet<String>());

        Assert.assertEquals(time11, time22, 5);
    }
}
