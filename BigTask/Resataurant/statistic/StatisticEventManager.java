package com.javarush.test.level27.lesson15.big01.statistic;

import com.javarush.test.level27.lesson15.big01.statistic.event.CookedOrderEventDataRow;
import com.javarush.test.level27.lesson15.big01.statistic.event.EventDataRow;
import com.javarush.test.level27.lesson15.big01.statistic.event.EventType;
import com.javarush.test.level27.lesson15.big01.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class StatisticEventManager
{
    private static StatisticEventManager ourInstance = new StatisticEventManager(); //синглетон
    public static StatisticEventManager getInstance()
    {
        return ourInstance;
    }
    private StatisticEventManager() {}

    private StatisticStorage storage = new StatisticStorage();


    public Map<Date, Double> getAdRevenue() //вытаскивает статистику по рекламе
    {
        Map<Date, Double> resultMap = new TreeMap<>(Collections.reverseOrder());
        for (EventDataRow event : storage.getData(EventType.SELECTED_VIDEOS))
        {
            Date date = dateToStringMidnight(event.getDate());
            VideoSelectedEventDataRow eventData = (VideoSelectedEventDataRow) event;
            if (resultMap.containsKey(date))
            {
                resultMap.put(date, resultMap.get(date) + (0.01d * (double) eventData.getAmount()));
            } else
            {
                resultMap.put(date, (0.01d * (double) eventData.getAmount()));
            }
        }
        return resultMap;
    }


    public Map<Date, Map<String, Integer>> getCookWorkload() //вытаскивает статитиску по поварам
    {
        Map<Date, Map<String, Integer>> resultMap = new TreeMap<>(Collections.reverseOrder());
        for (EventDataRow event : storage.getData(EventType.COOKED_ORDER))
        {
            Date date = dateToStringMidnight(event.getDate());
            CookedOrderEventDataRow eventData = (CookedOrderEventDataRow) event;
            int time = eventData.getTime();
            if (time==0)
                continue;
            if (time % 60 == 0) time = time / 60;
            else time = time / 60 + 1;
            if (resultMap.containsKey(date))
            {
                Map<String, Integer> cookInfo = resultMap.get(date);
                if (cookInfo.containsKey(eventData.getCookName()))
                {
                    cookInfo.put(eventData.getCookName(), cookInfo.get(eventData.getCookName()) + time);
                } else
                {
                    cookInfo.put(eventData.getCookName(), time);
                }
            } else
            {
                TreeMap<String, Integer> cookInfo = new TreeMap<>();
                cookInfo.put(eventData.getCookName(), time);
                resultMap.put(date, cookInfo);
            }
        }
        return resultMap;
    }




    private Date dateToStringMidnight(Date date) //приведение даты к полночи
     {
        GregorianCalendar roundedDate = new GregorianCalendar();
        roundedDate.setTime(date);
        roundedDate.set(Calendar.HOUR_OF_DAY, 0);
        roundedDate.set(Calendar.MINUTE, 0);
        roundedDate.set(Calendar.SECOND, 0);
        roundedDate.set(Calendar.MILLISECOND, 0);
        return roundedDate.getTime();
    }


    public void register(EventDataRow data)
    {
        if (data == null) return;
        storage.put(data);
    }



    private static class StatisticStorage  //хранилище статистики
    {
        private Map<EventType, List<EventDataRow>> eventTypeListMap = new HashMap<>();
        private StatisticStorage()
        {
            for (EventType eventType : EventType.values())
            {
                eventTypeListMap.put(eventType, new ArrayList<EventDataRow>());
            }
        }
        private void put(EventDataRow data)
        {
            if (data == null) return;
            eventTypeListMap.get(data.getType())
                    .add(data); //получаем нужный arraylist по типу события, и в него кладем наше событие
        }
        private List<EventDataRow> getData(EventType eventType)
        {
            return eventTypeListMap.get(eventType);
        }
    }
}


