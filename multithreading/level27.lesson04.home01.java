package com.javarush.test.level27.lesson04.home01;

/* Модификаторы и дедлоки
Расставьте модификаторы так, чтобы при работе с этим кодом появился дедлок
*/
public class Solution {
    public synchronized Object getData() {
        return new ThreadDeadlock().getData();
    }
}
****
public class ThreadDeadlock {
    Object data;
    public synchronized Object getData() {
        return data;
    }
}
   /* Тут сам принцип показан — если есть последовательный вызов synchronized блоков, значит и есть не нулевая вероятность лока.
        Поле data не приватное, оно может быть изменено и в наследнике и во вложенном классе и т.д*/
