package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.ad.AdvertisementManager;
import com.javarush.test.level27.lesson15.big01.ad.NoVideoAvailableException;
import com.javarush.test.level27.lesson15.big01.kitchen.Order;
import com.javarush.test.level27.lesson15.big01.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Tablet
{
    public static java.util.logging.Logger logger = Logger.getLogger(Tablet.class.getName());
    private final int number;
    private LinkedBlockingQueue<Order> queue;

    public int getNumber() {
        return number;
    }

    public Tablet(int number)
    {
        this.number = number;
        setQueue(Restaurant.getQUEUE());
    }

    public void setQueue(LinkedBlockingQueue<Order> queue)
    {
        this.queue = queue;
    }

    public void createOrder(){
        try
        {
            Order order = new Order(this);
            UnionPastofMethods(order);
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }


   public void createTestOrder(){
       try
       {
           Order order = new TestOrder(this);
           UnionPastofMethods(order);
       }
       catch (IOException e)
       {
           logger.log(Level.SEVERE, "Console is unavailable.");
       }

    }

    private void UnionPastofMethods(Order order)
    {
        ConsoleHelper.writeMessage(order.toString());
        if (!order.isEmpty())
        {
            try
            {
                AdvertisementManager ad = new AdvertisementManager(order.getTotalCookingTime()* 60);
                ad.processVideos();
            }
            catch (NoVideoAvailableException e) {
                logger.log(Level.INFO, "No video is available for the order " + order);
            }
            queue.add(order); //только если заказ не пустой, только тогда доблавялем заказ в очередь
        }
    }



    public String toString() {
        return "Tablet{number=" + number + "}";
    }
}



