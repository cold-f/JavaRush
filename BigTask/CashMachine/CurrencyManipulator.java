package com.javarush.test.level26.lesson15.big01;

import com.javarush.test.level26.lesson15.big01.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator
{
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<Integer, Integer>();

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public CurrencyManipulator(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public void addAmount(int denomination, int count)
    {
        if (denominations.containsKey(denomination))
            denominations.put(denomination, denominations.get(denomination) + count);
        else
            denominations.put(denomination, count);
    }

    public int getTotalAmount()
    {
        int sum = 0;
        for (Map.Entry<Integer, Integer> pair : denominations.entrySet())
        {
            Integer key = pair.getKey();
            Integer value = pair.getValue();
            sum = sum + (key * value);
        }
        return sum;

    }

    public boolean hasMoney()
    {
        if (denominations.isEmpty())
            return false;
        else
            return true;
    }

    public boolean isAmountAvailable(int expectedAmount)
    {
            return (getTotalAmount() >= expectedAmount);
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException, ConcurrentModificationException
    {
        Map<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        ArrayList<Integer> list = new ArrayList<>();

        for (Map.Entry<Integer, Integer> pair : denominations.entrySet()) {
            for (int i = 0; i < pair.getValue(); i++)
                list.add(pair.getKey());
        }

        Collections.sort(list);
        Collections.reverse(list); //получил отсортированный лист банкнот по убыванию 500 500 300 100 50 50 50...

        int money = expectedAmount;
        int count;

            for (int deno : list)
            {
                if ((money / deno) == 0)
                {
                    continue;
                }
                count = money / deno;
                money = money % deno;
                map.put(deno, count);
            }


        int tempSum = 0;    //считаем сумму денег, которую  подобрали жадным алгоритмом (максимально приближенная сумма, нужная нам)
        for (Map.Entry<Integer, Integer> pair : map.entrySet())
        {
            Integer key = pair.getKey();
            Integer value = pair.getValue();
            tempSum = tempSum + (key * value);
        }


        if (!(tempSum == expectedAmount))          //если нацело не получилось подобрать банкноты, то тогда бросаем ошибку.
            throw new NotEnoughMoneyException();
        else                                        //если получилось - снимаем деньги
        {
            for (Map.Entry<Integer, Integer> pair : map.entrySet())
            {
                Integer key = pair.getKey();
                Integer value = pair.getValue();
                denominations.put(key, denominations.get(key)-value);
            }
            return map;
        }
    }
}



