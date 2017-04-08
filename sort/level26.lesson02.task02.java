package com.javarush.test.level26.lesson02.task02;

import java.util.Set;
import java.util.TreeSet;

/* Был бы ум - будет и успех.
Солдаты всегда строились, строятся и будут строиться по убыванию роста.
Отсортируйте солдат, предварительно подумав, что лучше использовать при таких условиях - Comparable или Comparator.
*/
public class Solution {
    public static void main(String[] args) {
        Set<Soldier> soldiers = new TreeSet<>();
        soldiers.add(new Soldier("Ivanov", 170));
        soldiers.add(new Soldier("Petrov", 180));
        soldiers.add(new Soldier("Sidorov", 175));

        for (Soldier soldier : soldiers) {
            System.out.println(soldier.name);
        }
    }

    public static class Soldier implements Comparable<Soldier>{
        private String name;
        private int height;

        public Soldier(String name, int height) {
            this.name = name;
            this.height = height;
        }


        @Override
        public int compareTo(Soldier o)
        {
            return Integer.compare(o.height, this.height);
        }
    }
}
/* статья https://habrahabr.ru/post/247015/ Пишите компараторы правильно
Дело в том, что надо использовать по возможости встроенные реализации, потому что они учитывают нюансы вроде NaN, -0, +0.
А если мы пишем свой компаратор, где вычитаем (0 -1 +1 и тд)  он не все варианты учтет*/
