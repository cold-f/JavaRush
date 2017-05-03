package com.javarush.test.level27.lesson15.big01.kitchen;

import com.javarush.test.level27.lesson15.big01.Tablet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TestOrder extends Order
{
    public TestOrder(Tablet tablet) throws IOException
    {
        super(tablet);
    }

    @Override
    protected void initDishes()
    {
        dishes = new ArrayList<>();

        int randomCountDishes = 1+ ThreadLocalRandom.current().nextInt(3); //случайное количество блюд [1-3]

       for (int i = 0; i < randomCountDishes; i++){
           dishes.add(Dish.values()[ThreadLocalRandom.current().nextInt(Dish.values().length)]); //случайное блюдо. эта реализация для нитей лучше чем random
       }


    }
}

