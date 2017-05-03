package com.javarush.test.level27.lesson15.big01.kitchen;

import com.javarush.test.level27.lesson15.big01.ConsoleHelper;
import com.javarush.test.level27.lesson15.big01.Tablet;

import java.io.IOException;
import java.util.List;

public class Order
{
    private Tablet tablet;
    protected List<Dish> dishes;


    public Order(Tablet tablet) throws IOException
    {
        this.tablet = tablet;
        initDishes();
    }

    public List<Dish> getDishes()
    {
        return dishes;
    }

    public Tablet getTablet()
    {
        return tablet;
    }

    @Override
    public String toString() {
        return dishes.isEmpty() ? "" : "Your order: " + dishes + " of " + tablet;
    }

    public int getTotalCookingTime(){
        int cookingTime = 0;
        for (Dish dish: dishes){
            cookingTime += dish.getDuration();
        }
        return cookingTime;
    }

    public boolean isEmpty(){
        return dishes.isEmpty();
    }

    protected void initDishes() throws IOException
    {
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }
}


