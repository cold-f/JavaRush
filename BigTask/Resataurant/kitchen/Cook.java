package com.javarush.test.level27.lesson15.big01.kitchen;

import com.javarush.test.level27.lesson15.big01.ConsoleHelper;
import com.javarush.test.level27.lesson15.big01.Restaurant;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;
import com.javarush.test.level27.lesson15.big01.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;


public class Cook extends Observable implements Runnable  //наблюдаемый официантом.
{
    private String name;

    private LinkedBlockingQueue<Order> queue;
    private boolean stopped = false; //добавил, чтобы не выбрасывало ошибку InterruptedException

   // Нити поваров не хотят завершать свою работу после вызова их interrupt() метода в main(). Это происходит из-за того, что повар в методе run()
   // вызывает метод startCookingOrder(Order order), в котором есть таймаут на время приготовления, Вот здесь это прерывание и проглатывается.
    public void setQueue(LinkedBlockingQueue<Order> queue)
    {
        this.queue = queue;
    }

    public Cook(String name)
    {
        this.name = name;
        setQueue(Restaurant.getQUEUE());
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public void run() {
        try {
            while (!stopped)
            {
                startCookingOrder(Restaurant.getQUEUE().take());
                Thread.currentThread().sleep(10);
            }
        }
        catch (InterruptedException e) {stopped = true;}

    }
    public void startCookingOrder(Order order)
    {
            ConsoleHelper.writeMessage("Start cooking - " + order + ", cooking time " + order.getTotalCookingTime() + "min");

            StatisticEventManager.getInstance().
                    register(new CookedOrderEventDataRow(order.getTablet().toString(), name,
                            order.getTotalCookingTime() * 60, order.getDishes()));
            try {
                Thread.currentThread().sleep(order.getTotalCookingTime()*10);}
            catch (InterruptedException e) {stopped = true;}
            setChanged();
            notifyObservers(order);

    }
}
