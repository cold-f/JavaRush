package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

   public static List<Dish> getAllDishesForOrder() throws IOException
   {
       writeMessage("Выберите блюдо: "+Dish.allDishesToString());  //здесь потом введи текст дополнительный
       List<Dish> list = new ArrayList<Dish>();
       String s;
        while (!(s = readString()).equalsIgnoreCase("exit")){
            try {
                list.add(Dish.valueOf(s));}
            catch (IllegalArgumentException e) {
                ConsoleHelper.writeMessage(s+" is not detected");}
        }
        return list;
   }


}

