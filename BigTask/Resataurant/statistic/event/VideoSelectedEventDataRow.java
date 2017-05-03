package com.javarush.test.level27.lesson15.big01.statistic.event;

import com.javarush.test.level27.lesson15.big01.ad.Advertisement;

import java.util.Date;
import java.util.List;

public class VideoSelectedEventDataRow implements EventDataRow
{
    private List<Advertisement> optimalVideoSet; //список видео-роликов, отобранных для показа
    private long amount;                        //сумма денег в копейках
    private int totalDuration;                 // общая продолжительность показа отобранных рекламных роликов
    private Date currentDate;

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration){
        this.optimalVideoSet = optimalVideoSet;
        this.amount = amount;
        this.totalDuration = totalDuration;
        this.currentDate = new Date();
    }

    public long getAmount()
    {
        return amount;
    }

    @Override
    public EventType getType()
    {
        return EventType.SELECTED_VIDEOS;
    }

    @Override
    public Date getDate()
    {
        return currentDate;
    }

    @Override
    public int getTime()
    {
        return totalDuration;
    }
}


