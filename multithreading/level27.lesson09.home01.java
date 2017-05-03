package com.javarush.test.level27.lesson09.home01;
/* Producer–consumer
В классе TransferObject расставьте вызов методов wait/notify/notifyAll,
чтобы обеспечить последовательное создание и получение объекта.
Ожидаемый вывод:
...
Put: M
Got: M
Put: N
Got: N
Put: K
Got: K
...
где M, N, K - числа
Метод main не участвует в тестировании
PS: всегда старайтесь использовать любой concurrent список вместо реализации wait/notify/notifyAll.
Однако понимать основные методы необходимо*/
public class Solution {
    public static void main(String args[]) throws InterruptedException {
        TransferObject transferObject = new TransferObject();
        ProducerTask producerTask = new ProducerTask(transferObject);
        ConsumerTask consumerTask = new ConsumerTask(transferObject);

        Thread.sleep(50);
        producerTask.stop();
        consumerTask.stop();
    }
}
********************************************************
public class TransferObject {
    private int value;
    protected volatile boolean isValuePresent = false; //use this variable

    public synchronized int get() {
        if (!isValuePresent)
            try {
                wait();}
            catch (InterruptedException e) {e.printStackTrace();}

        System.out.println("Got: " + value);
        isValuePresent = false;
        notifyAll();
        return value;
    }

    public synchronized void put(int value) {
        if (isValuePresent)
            try {
                wait();}
            catch (InterruptedException e) {e.printStackTrace();}

        this.value = value;
        isValuePresent = true;
        System.out.println("Put: " + value);
        notifyAll();
    }
}
********************************************************
public class ConsumerTask implements Runnable {
    private TransferObject transferObject;
    protected volatile boolean stopped;

    public ConsumerTask(TransferObject transferObject) {
        this.transferObject = transferObject;
        new Thread(this, "ConsumerTask").start();
    }

    public void run() {
        while (!stopped) {
            transferObject.get();
        }
    }

    public void stop() {
        stopped = true;
    }
}
********************************************************
public class ProducerTask implements Runnable {
    private TransferObject transferObject;
    protected volatile boolean stopped;
    static volatile AtomicInteger i = new AtomicInteger(0);

    public ProducerTask(TransferObject transferObject) {
        this.transferObject = transferObject;
        new Thread(this, "ProducerTask").start();
    }

    public void run() {
        while (!stopped) {
            transferObject.put(i.incrementAndGet());
        }
    }

    public void stop() {
        stopped = true;
    }
}
