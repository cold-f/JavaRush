package com.javarush.test.level33.lesson15.big01;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Helper {

    public static String generateRandomString(){ //будет генерировать случайную строку.
       SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(100, secureRandom).toString(32);
    }

    public static void printMessage(String message){// Он должен выводить  переданный текст в консоль
        System.out.println(message);
    }


}
