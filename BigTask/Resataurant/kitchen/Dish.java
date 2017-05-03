package com.javarush.test.level27.lesson15.big01.kitchen;

public enum  Dish
{
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }


    public static String allDishesToString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Dish.values().length; i++)
        {
            sb.append(Dish.values()[i]).append(", ");
        }
        sb.delete(sb.length()-2, sb.length());
        return sb.toString();
    }
}

