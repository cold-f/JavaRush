package com.javarush.test.level27.lesson06.home01;
import java.util.Set;
/* Убираем deadLock используя Открытые вызовы
Синхронизированные методы, которые вызывают внутри себя синхронизированные методы других классов, приводят к dead-lock-у.
1. Перенесите синхронизацию с метода в синхронизированный блок, куда поместите лишь необходимые части кода.
2. Уберите избыточную синхронизацию методов.
3. В стеке вызова методов не должно быть перекрестной синхронизации,
т.е. такого synchronizedMethodAClass().synchronizedMethodBClass().synchronizedMethodAClass()

Этот способ избавления от дэдлока называется Открытые вызовы, о нем читайте в дополнительном материале к лекции.
Метод main не участвует в тестировании.
От меня: вызов синхронизированного метода в синхронизированном методе может выхвать блокировку. этого нужно избегать
*/
public class Solution {
    public static void main(String[] args) {
        final long deadLineTime = System.currentTimeMillis() + 5000; //waiting for 5 sec

        final RealEstate realEstate = new RealEstate();
        Set<Apartment> allApartments = realEstate.getAllApartments();

        final Apartment[] apartments = allApartments.toArray(new Apartment[allApartments.size()]);

        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        realEstate.revalidate();
                    }
                }
            }, "RealEstateThread" + i).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < apartments.length; i++) {
                        apartments[i].revalidate(true);
                    }
                }
            }, "ApartmentThread" + i).start();
        }

        Thread deamonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (System.currentTimeMillis() < deadLineTime)
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException ignored) {
                    }
                System.out.println("The dead lock occurred");
            }
        });
        deamonThread.setDaemon(true);
        deamonThread.start();
    }
}
*************************************************************************
public class Apartment {
    private String location;
    private final RealEstate realEstate;

    public Apartment(RealEstate realEstate) {
        this.realEstate = realEstate;
        setLocation(String.valueOf(Math.random() * 10));
    }

    public synchronized String getLocation() {
        return location;
    }

    public  void setLocation(String location) {
        synchronized (this) {
            this.location = location;
        }
    }

    public  void revalidate(boolean isEmpty) {
        synchronized (this) {
            if (isEmpty)
                realEstate.up(this);
        }

    }
}
*************************************************************************
public class RealEstate {

    private final Set<Apartment> allApartments;
    private final Set<Apartment> activeApartments;

    public RealEstate() {
        allApartments = new HashSet();
        activeApartments = new HashSet();

        //add some data
        allApartments.add(new Apartment(this));
        allApartments.add(new Apartment(this));
        allApartments.add(new Apartment(this));
        allApartments.add(new Apartment(this));
        allApartments.add(new Apartment(this));
        allApartments.add(new Apartment(this));
    }

    public Set<Apartment> getAllApartments() {
        return allApartments;
    }

    public void up(Apartment apartment) {
        synchronized (this) {
            activeApartments.add(apartment);
        }
    }

    public void revalidate() {
        synchronized (this) {   //вынес эту часть в блок, вместо полностью синхронизированного метода
            activeApartments.clear();
        }

        for (Apartment apartment : allApartments) {
            boolean randomValue = Math.random() * 2 % 2 == 0;
            apartment.revalidate(randomValue);
        }
    }
}
