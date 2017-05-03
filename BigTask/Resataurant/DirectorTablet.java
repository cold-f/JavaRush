package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.ad.StatisticAdvertisementManager;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet
{
    private DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

    public void printAdvertisementProfit()  //какую сумму заработали на рекламе, сгруппировать по дням
    {
        double total = 0d;
        for (Map.Entry<Date, Double> entry : StatisticEventManager.getInstance().getAdRevenue().entrySet())
        {
            double profit = entry.getValue();
            ConsoleHelper.writeMessage(String.format("%s - %.2f", df.format(entry.getKey()), profit));
            total += profit;
        }
        ConsoleHelper.writeMessage(String.format("Total - %.2f", total));
    }

    public void printCookWorkloading() //загрузка (рабочее время) повара, сгруппировать по дням
    {
        for (Map.Entry<Date, Map<String, Integer>> entry : StatisticEventManager.getInstance().getCookWorkload().entrySet())
        {
            ConsoleHelper.writeMessage(df.format(entry.getKey()));
            for (Map.Entry<String, Integer> cooksEntry : entry.getValue().entrySet())
            {
                ConsoleHelper.writeMessage(String.format("%s - %d min", cooksEntry.getKey(), cooksEntry.getValue()));
            }
            ConsoleHelper.writeMessage("");
        }
    }
    public void printActiveVideoSet()//список активных роликов и оставшееся количество показов по каждому
    {
        TreeMap<String, Integer> map = StatisticAdvertisementManager.getInstance().getActiveVideoSet();
        for (Map.Entry<String, Integer> pair: map.entrySet()){
            ConsoleHelper.writeMessage(pair.getKey()+" - "+pair.getValue());
        }

    }
    public void printArchivedVideoSet()// список НЕактивных роликов (с оставшемся количеством показов равным нулю)
    {
        ArrayList<String> list = StatisticAdvertisementManager.getInstance().getArchivedVideoSet();
        for (String string : list)
            ConsoleHelper.writeMessage(string);
    }
}

