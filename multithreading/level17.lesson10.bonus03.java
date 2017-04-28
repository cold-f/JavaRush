package com.javarush.test.level17.lesson10.bonus03;

/* Ресторан
1.Разберись, что делает программа. Официант почему-то не относит приготовленные блюда назад к столам :(
2.Исправь ошибку.
Подсказка: это одна строчка
*/

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    public static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Waiter waiterTarget = new Waiter();
        Thread waiter = new Thread(waiterTarget);
        threads.add(waiter);

        Cook cookTarget = new Cook();
        Thread cook = new Thread(cookTarget);
        threads.add(cook);

        waiter.start();
        cook.start();

        Thread.sleep(2000);
        cookTarget.continueWorking = false;
        Thread.sleep(500);
        waiterTarget.continueWorking = false;
    }
}
********************************************
public class Dishes {
    private byte tableNumber;

    public Dishes(byte tableNumber) {
        this.tableNumber = tableNumber;
    }

    public byte getTableNumber() {
        return tableNumber;
    }
}
********************************************
public class Order {
    private long time;
    private byte tableNumber;

    public Order(byte tableNumber) {
        time = (long) (Math.random() * 200);
        this.tableNumber = tableNumber;
    }

    public long getTime() {
        return time;
    }

    public byte getTableNumber() {
        return tableNumber;
    }
}
********************************************
public class Table {
    private static byte tableNumber;
    private byte number = ++tableNumber;

    public Order getOrder () {
        return new Order(number);
    }
}
********************************************
public class Manager {      //singleton
    private static Manager ourInstance = new Manager();

    private final List<Table> restaurantTables = new ArrayList<Table>(10);
    private int currentIndex = 0;

    private final Queue<Order> orderQueue = new ConcurrentLinkedQueue<Order>();        // очередь с заказами
    private final Queue<Dishes> dishesQueue = new ConcurrentLinkedQueue<Dishes>();     // очередь с готовыми блюдами

    public synchronized static Manager getInstance() {
        return ourInstance;
    }

    private Manager() {               // создаем 10 столов
        for (int i = 0; i < 10; i++) {
            restaurantTables.add(new Table());
        }
    }

    public synchronized Table getNextTable() {           // официант ходит по кругу от 1 стола к 10
        Table table = restaurantTables.get(currentIndex);
        currentIndex = (currentIndex + 1) % 10;
        return table;
    }

    public Queue<Order> getOrderQueue() {
        return orderQueue;
    }

    public Queue<Dishes> getDishesQueue() {
        return dishesQueue;
    }
}
********************************************
public class Waiter implements Runnable {
    public boolean continueWorking = true;

    @Override
    public void run() {
        Manager manager = Manager.getInstance();

        while (continueWorking || !manager.getDishesQueue().isEmpty()) {
            if (!manager.getDishesQueue().isEmpty()) {       //относим готовый заказ
                Dishes dishes = manager.getDishesQueue().poll();
                System.out.println("Официант отнес заказ для стола №" + dishes.getTableNumber());

            } else {                                         //берем новый заказ
                Table table = manager.getNextTable();
                Order order = table.getOrder();
                System.out.println("Получен заказ от стола №" + order.getTableNumber());
                manager.getOrderQueue().add(order);
            }

            try {
                Thread.sleep(100);  //walking to the next table

            } catch (InterruptedException e) {
            }
        }
    }
}
********************************************
public class Cook implements Runnable {
    public boolean continueWorking = true;

    @Override
    public void run() {
        boolean needToWait;
        while (continueWorking || needToCookOrders()) {
            try {
                synchronized (this) {
                    needToWait = !needToCookOrders();
                    if (!needToWait) {
                        cooking();
                    }
                }
                if (continueWorking && needToWait) {
                    System.out.println("Повар отдыхает");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    private boolean needToCookOrders() {
        return !Manager.getInstance().getOrderQueue().isEmpty();
    }

    private void cooking() throws InterruptedException {
        Manager manager = Manager.getInstance();
        Order order = manager.getOrderQueue().poll();      // повар берет заказ из очереди
        System.out.println(String.format("Заказ будет готовиться %d мс для стола №%d", order.getTime(), order.getTableNumber()));
        Thread.sleep(order.getTime());     // готовим блюдо
        Dishes dishes = new Dishes(order.getTableNumber());       //  это готовое блюдо

        System.out.println(String.format("Заказ для стола №%d готов", dishes.getTableNumber()));
        manager.getDishesQueue().add(dishes);
    }
}
-----------------------------------------------
Получен заказ от стола №1
Заказ будет готовиться 144 мс для стола №1
Получен заказ от стола №2
Получен заказ от стола №3
Заказ для стола №1 готов
Заказ будет готовиться 69 мс для стола №2
Заказ для стола №2 готов
Заказ будет готовиться 20 мс для стола №3
Заказ для стола №3 готов
Повар отдыхает
Официант отнес заказ для стола №1
Повар отдыхает
Официант отнес заказ для стола №2
Официант отнес заказ для стола №3
Повар отдыхает
Получен заказ от стола №4
Заказ будет готовиться 79 мс для стола №4
Заказ для стола №4 готов
Повар отдыхает
Официант отнес заказ для стола №4
Повар отдыхает
Получен заказ от стола №5
Заказ будет готовиться 127 мс для стола №5
Получен заказ от стола №6
Заказ для стола №5 готов
Заказ будет готовиться 72 мс для стола №6
Официант отнес заказ для стола №5
Получен заказ от стола №7
Заказ для стола №6 готов
Заказ будет готовиться 68 мс для стола №7
Заказ для стола №7 готов
Повар отдыхает
Официант отнес заказ для стола №6
Повар отдыхает
Официант отнес заказ для стола №7
Повар отдыхает
Получен заказ от стола №8
Заказ будет готовиться 179 мс для стола №8
Получен заказ от стола №9
Получен заказ от стола №10
Заказ для стола №8 готов
Заказ будет готовиться 106 мс для стола №9
Официант отнес заказ для стола №8
Заказ для стола №9 готов
Заказ будет готовиться 8 мс для стола №10
Заказ для стола №10 готов
Повар отдыхает
Официант отнес заказ для стола №9
Повар отдыхает
Официант отнес заказ для стола №10
Получен заказ от стола №1
Получен заказ от стола №2
Получен заказ от стола №3
Получен заказ от стола №4
