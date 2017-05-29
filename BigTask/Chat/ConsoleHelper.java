package com.javarush.test.level30.lesson15.big01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString()
    {
        String s = null;
        while (true)
        {
            try {
                s = reader.readLine();
                break;
            }
            catch (IOException e) {
                writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            }
        }
        return s;
    }

    public static int readInt(){
        int n = 0;
        while (true)
        {
            try
            {
                n = Integer.parseInt(readString());
                break;
            }
            catch (NumberFormatException e)
            {
                writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            }
        }
        return n;
    }
}
