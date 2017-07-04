package com.javarush.test.level33.lesson15.big01;

public class ExceptionHandler {

    public static void log(Exception e){ //будет выводить краткое описание исключения.
        System.out.println(e.toString());
    }
}
