package com.javarush.test.level27.lesson15.big01;


import com.javarush.test.level27.lesson15.big01.kitchen.Cook;
import com.javarush.test.level27.lesson15.big01.kitchen.Order;
import com.javarush.test.level27.lesson15.big01.kitchen.Waitor;


import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


public class Restaurant
{
    private final static int ORDER_CREATING_INTERVAL = 100;
    private final static LinkedBlockingQueue<Order> QUEUE = new LinkedBlockingQueue<>(); //очередь из заказов

    public static LinkedBlockingQueue<Order> getQUEUE()
    {
        return QUEUE;
    }

    public static void main(String[] args)
    {
        Cook cook =new Cook("Amigo");
        Cook cook2 =new Cook("Petr");
        Thread threadCook1 = new Thread(cook);
        threadCook1.start();
        Thread threadCook2 = new Thread(cook2);
        threadCook2.start();

        Waitor waitor = new Waitor();
        cook.addObserver(waitor);
        cook2.addObserver(waitor);

        ArrayList<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i <5; i++){
            Tablet tablet = new Tablet(i);
            tablet.setQueue(QUEUE);
            tablets.add(tablet);
        }


      Thread thread = new Thread( new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
      thread.start();
      try {
            Thread.currentThread().sleep(1000);
        }
      catch (InterruptedException e) {}
      thread.interrupt();


            threadCook1.interrupt();
            threadCook2.interrupt();

        try
        {
            threadCook1.join();
            threadCook2.join();
        }
        catch (InterruptedException e) {e.printStackTrace();}


        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }
}

