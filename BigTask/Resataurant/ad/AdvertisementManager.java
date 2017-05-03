package com.javarush.test.level27.lesson15.big01.ad;


import com.javarush.test.level27.lesson15.big01.ConsoleHelper;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;
import com.javarush.test.level27.lesson15.big01.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class AdvertisementManager
{
    private final  AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private int timeSeconds;

    public AdvertisementManager(int timeSeconds)
    {
        this.timeSeconds = timeSeconds;
    }


    public void processVideos() throws NoVideoAvailableException{

        Set<Set<Advertisement>> allSet = powerSet(new HashSet<Advertisement>(storage.list())); //в аргументах перевел list в set, а потом получил сет всех сетов. т.е. всех комбиниций роликов
        ArrayList<Advertisement> advertisements = bestList(allSet); //нашел лучший вариант


        if (advertisements.isEmpty())
            throw new NoVideoAvailableException();

        Collections.sort(advertisements, new Comparator<Advertisement>() { //задание 9. сортировка самих роликов для вывода на экран плашета
            @Override
            public int compare(Advertisement o1, Advertisement o2) {                                //можно было сделать корректнее, через Long.copareTo
                return (int) (o2.getAmountPerOneDisplaying() != o1.getAmountPerOneDisplaying() ?  //это сортировка видео в наборе перед показом
                        o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying() :
                        o1.getAmountPerOneDisplaying() * 1000.0 / o1.getDuration() -
                                o2.getAmountPerOneDisplaying() * 1000.0 / o2.getDuration());
            }
        });

        long totalAmount = 0;       //сумма денег за все ролики из набора
        int  totalDuration =0; // Общая продолжительность всех видео из набора
        for (Advertisement advertisement : advertisements){
            totalAmount += advertisement.getAmountPerOneDisplaying();
            totalDuration += advertisement.getDuration();
        }

        StatisticEventManager.getInstance().register(new VideoSelectedEventDataRow(advertisements, totalAmount, totalDuration));// регистрация события "видео выбрано" перед его отображением.*/


        for (Advertisement advertisement : advertisements) {
            ConsoleHelper.writeMessage(String.format("%s is displaying... %d, %d",
                    advertisement.getName(),
                    advertisement.getAmountPerOneDisplaying(),
                    advertisement.getAmountPerOneDisplaying() * 1000 / advertisement.getDuration()));
            advertisement.revalidate();
        }

    }


    private static <T> Set<Set<T>> powerSet(Set<T> originalSet) {  //возвращает ВСЕ возможные комбинации роликов из представленных
        Set<Set<T>> sets = new HashSet<Set<T>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<T>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<T>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    private ArrayList<Advertisement> bestList(Set<Set<Advertisement>> allSet){  //метод возвращает лучший набор роликов
        ArrayList<Set<Advertisement>> allList = new ArrayList<>(allSet); //переводим set в list
        ArrayList<ArrayList<Advertisement>> goodLists = new ArrayList<>();

        for (int i = 0; i < allList.size(); i++){
            ArrayList<Advertisement> each = new ArrayList<>(allList.get(i)); //здесь тоже переводим
            int eachSum =0;
            boolean brak = false;
            for (int j = 0; j < each.size(); j++){
                if (each.get(j).getHits() == 0)   //отсеиваем наборы с роликами, у которых закончилась оплата
                    brak = true;
                eachSum = eachSum + each.get(j).getDuration();
            }
            if (eachSum > timeSeconds)  //отсеиваем наборы роликов, у которых сумарное время больше приготовления заказа
                brak = true;

            if (!brak)
                goodLists.add(each);
        }

        Collections.sort(goodLists, new Comparator<ArrayList<Advertisement>>() //сортируем, чтобы найти лучший набор(задание 10)
        {
            @Override
            public int compare(ArrayList<Advertisement> o1, ArrayList<Advertisement> o2)
            {
                long summadeneg1 = 0;
                long summadeneg2 = 0;

                for (int i = 0; i < o1.size(); i++){
                    summadeneg1 = summadeneg1 + o1.get(i).getAmountPerOneDisplaying();
                }
                for (int i = 0; i < o2.size(); i++){
                    summadeneg2 = summadeneg2 + o2.get(i).getAmountPerOneDisplaying();
                }

                if (summadeneg1 == summadeneg2){
                    int summavremeni1 = 0;
                    int summavremeni2 = 0;

                    for (int i = 0; i < o1.size(); i++){
                       summavremeni1 = summavremeni1 + o1.get(i).getDuration();
                    }
                    for (int i = 0; i < o2.size(); i++){
                        summavremeni2 = summavremeni2 + o2.get(i).getDuration();
                    }

                    if (summavremeni1 == summavremeni2){
                        int summarolikov1 = o1.size();
                        int summarolikov2 = o2.size();
                        return Integer.compare(summarolikov1, summarolikov2); //по возрастанию x1 < x1? тогда  -1
                    }

                    else
                        return Integer.compare(summavremeni2, summavremeni1); //по убыванию
                }
                else
                    return Long.compare(summadeneg2, summadeneg1); //по убыванию
            }
        });

        return goodLists.get(0); //возвращаем лучший из лучших наборов
    }
}

