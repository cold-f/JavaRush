package com.javarush.test.level26.lesson02.task01;

import java.util.*;

/* Почитать в инете про медиану выборки
Реализовать логику метода sort, который должен сортировать данные в массиве по удаленности от его медианы
Вернуть отсортированный массив от минимального расстояния до максимального
Если удаленность одинаковая у нескольких чисел, то выводить их в порядке возрастания
*/
public class Solution {

    public static void main(String[] args) {
        Integer[] mass2 = {5,6,3,4,1,2,7,8,9,10,11};
        Integer[] mass3 = {2, 5, 6, 7, 21, 1};
        Integer[] mass4 = {5,2};
        Integer[] mass5 = {5,8,17,15,13,18};


        mass2 = Solution.sort(mass2);
        for (Integer integer : mass2)
        {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.println("**************************");
        mass3 = Solution.sort(mass3);
        for (Integer integer : mass3)
        {
            System.out.print(integer + " ");
        }
        System.out.println();

        System.out.println("**************************");
        mass4 = Solution.sort(mass4);

        for (Integer integer : mass4)
        {
            System.out.print(integer + " ");
        }
        System.out.println();

        System.out.println("**************************");

        mass5 = Solution.sort(mass5);
        for (Integer integer : mass5)
        {
            System.out.print(integer + " ");
        }
        System.out.println();

        System.out.println("**************************");



    }

    public static Integer[] sort(Integer[] array)
    {

        List<Integer> arrayList =  Arrays.asList(array);
        Collections.sort(arrayList);
        final float mediana;
        if (arrayList.size() %2 !=0)
            mediana = arrayList.get(arrayList.size()/2);
        else
            mediana =  (((float)arrayList.get(arrayList.size()/2) + (float)arrayList.get(arrayList.size()/2-1))/2);

        System.out.println(mediana);

        Comparator<Integer> myCompare = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                int result = (int) (Math.abs(o1 - mediana) - Math.abs(o2 -mediana));
                return result == 0 ? o1 - o2 : result;

            }
        };
        Collections.sort(arrayList, myCompare);

        return (Integer[]) arrayList.toArray();
    }
}
