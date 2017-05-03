package com.javarush.test.level27.lesson15.big01.ad;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Comparator;
import java.util.TreeMap;

public class StatisticAdvertisementManager
{
    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();

    public static StatisticAdvertisementManager getInstance()
    {
        return ourInstance;
    }

    private StatisticAdvertisementManager() {
    }


    public TreeMap<String, Integer> getActiveVideoSet()   //возвращает список активных роликов+кол-во оставшихся показов
    {
        TreeMap<String, Integer> resultMap = new TreeMap<>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o1.compareToIgnoreCase(o2);//чтобы игноировать при сортировке регистр
            }
        });

        for (Advertisement advertisement : storage.list())
        {
            if (advertisement.getHits() > 0)
            {
                resultMap.put(advertisement.getName(), advertisement.getHits());
            }
        }
        return resultMap;
    }

    public ArrayList<String> getArchivedVideoSet(){//возвращает список неактивных роликов
        ArrayList<String> resultList = new ArrayList<>();
        for (Advertisement advertisement : storage.list())
        {
            if (advertisement.getHits() == 0) {
                resultList.add(advertisement.getName());
            }
        }

        Collections.sort(resultList, new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o1.compareToIgnoreCase(o2); //чтобы игноировать при сортировке регистр
            }
        });
        return resultList;
    }
}

