package com.javarush.test.level29.lesson09.task01;

import java.math.BigDecimal;

/* Этот странный BigDecimal
Исправьте ошибку реализации, приводящую к погрешности вычисления, в методе getValue.
Сигнатуру метода не менять. Округление не использовать.
Читайте доп. статью про особенности автобоксинга.
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getValue(1.1d, 1.2d));
    }

    public static BigDecimal getValue(double v1, double v2) {
       BigDecimal b=  new BigDecimal(1.1);//
        return BigDecimal.valueOf(v1).add(BigDecimal.valueOf(v2));

    }
}
// new BigDecimal(1.1) просто выдает 1.100000000000000088817841970012523233890533447265625
/*
Так происходит, потому что примитивный double хранится в формате IEEE754, в котором невозможно представить 1.1 идеально точно (в двоичной системе счисления получается бесконечная периодическая дробь).
 Поэтому там хранится максимально близкое значение к 1.1. А конструктор BigDecimal(double) напротив работает точно: он идеально преобразует заданное число в IEEE754 к десятичному виду (конечная
  двоичная дробь всегда представима в виде конечной десятичной).*/
