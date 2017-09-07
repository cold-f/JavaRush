package com.javarush.test.level30.lesson02.home01;

import java.math.BigInteger;

/* Конвертер систем счислений
Реализуйте логику метода convertNumberToOtherNumerationSystem, который должен переводить число number.getDigit()
из одной системы счисления(numerationSystem) в другую (expectedNumerationSystem)
бросьте NumberFormatException, если переданное число некорректно, например, число "120" с системой счисления 2
Валидация для - number.getDigit() - целое не отрицательное
Метод main не участвует в тестировании
*/
public class Solution {
    public static void main(String[] args) {
        Number number = new Number(NumerationSystemType._10, "6");
        Number result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._2);
        System.out.println(result);    //expected 110
    }

    public static Number convertNumberToOtherNumerationSystem(Number number, NumerationSystem expectedNumerationSystem) {
        BigInteger bigInteger = new BigInteger(number.getDigit(), number.getNumerationSystem().getNumerationSystemIntValue());
        String str = bigInteger.toString(expectedNumerationSystem.getNumerationSystemIntValue());
        return new Number(expectedNumerationSystem, str);
    }
}
**********************************************************************
public enum NumerationSystemType implements NumerationSystem {
    _16,
    _12,
    _10,
    _9,
    _8,
    _7,
    _6,
    _5,
    _4,
    _3,
    _2;

    @Override
    public int getNumerationSystemIntValue() {
        return Integer.parseInt(this.name().substring(1));
    }
}
**********************************************************************
public class Number {
    private NumerationSystem numerationSystem;
    private String digit;

    public Number(NumerationSystem numerationSystem, String digit) {
        this.numerationSystem = numerationSystem;
        this.digit = digit;
    }

    public NumerationSystem getNumerationSystem() {
        return numerationSystem;
    }

    public String getDigit() {
        return digit;
    }

    @Override
    public String toString() {
        return "Number{" +
                "numerationSystem=" + numerationSystem +
                ", digit='" + digit + '\'' +
                '}';
    }
}
**********************************************************************
public interface NumerationSystem {
    int getNumerationSystemIntValue();
}
**********************************************************************
