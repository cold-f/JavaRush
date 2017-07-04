package com.javarush.test.level33.lesson15.big01.tests;

import com.javarush.test.level33.lesson15.big01.Helper;
import com.javarush.test.level33.lesson15.big01.Shortener;
import com.javarush.test.level33.lesson15.big01.strategies.*;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class FunctionalTest {

    public void testStorage(Shortener shortener) throws SQLException {
        String string1 = Helper.generateRandomString();;
        String string2 = Helper.generateRandomString();;
        String string3 = string1;

        long ID1 = shortener.getId(string1);
        long ID2 = shortener.getId(string2);
        long ID3 = shortener.getId(string3);

        Assert.assertNotEquals(ID2, ID1);
        Assert.assertNotEquals(ID2, ID3);
        Assert.assertEquals(ID1, ID3);

        String string11 =  shortener.getString(ID1);
        String string22 =  shortener.getString(ID2);
        String string33 =  shortener.getString(ID3);

        Assert.assertEquals(string1, string11);
        Assert.assertEquals(string2, string22);
        Assert.assertEquals(string3, string33);
    }
    @Test
    public void testHashMapStorageStrategy() throws SQLException {
        HashMapStorageStrategy hashMapStorageStrategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(hashMapStorageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() throws SQLException {
        StorageStrategy ourHashMapStorageStrategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(ourHashMapStorageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testFileStorageStrategy() throws SQLException {
        FileStorageStrategy fileStorageStrategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(fileStorageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testHashBiMapStorageStrategy() throws SQLException {
        HashBiMapStorageStrategy hashBiMapStorageStrategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(hashBiMapStorageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testDualHashBidiMapStorageStrategy() throws SQLException {
        DualHashBidiMapStorageStrategy dualHashBidiMapStorageStrategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(dualHashBidiMapStorageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testOurHashBiMapStorageStrategy() throws SQLException {
        OurHashBiMapStorageStrategy ourHashBiMapStorageStrategy = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(ourHashBiMapStorageStrategy);
        testStorage(shortener);
    }
}
