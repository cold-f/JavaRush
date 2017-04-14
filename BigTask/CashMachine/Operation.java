package com.javarush.test.level26.lesson15.big01;

public enum Operation
{
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i){
        if (i == 0)
            return LOGIN;
        if (i == 1)
            return INFO;
        if (i == 2)
            return DEPOSIT;
        if (i == 3)
            return WITHDRAW;
        if (i == 4)
            return EXIT;
        else
            throw new IllegalArgumentException();
    }
}
