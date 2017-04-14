package com.javarush.test.level26.lesson15.big01;

import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleHelper
{
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"common", Locale.ENGLISH);

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static void printExitMessage()
    {
        writeMessage(res.getString("the.end"));
    }

    public  static String readString() throws InterruptOperationException{
        String s = null;
        try {
            s = reader.readLine();
            if (s.equalsIgnoreCase(res.getString("operation.EXIT"))) {
                throw new InterruptOperationException();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String askCurrencyCode() throws InterruptOperationException
    {
        writeMessage(res.getString("choose.currency.code"));
        String kod;
        while (true) {
            kod = readString();
            if (kod.length() == 3)
                break;
            else
                writeMessage(res.getString("invalid.data"));
        }
        return kod.toUpperCase();
    }


    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException
    {
        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
        String[] array;
        while (true)
        {
            String s = readString();
            array = s.split(" ");
            int k, l;
            try {
                k = Integer.parseInt(array[0]);
                l = Integer.parseInt(array[1]);
            }
            catch (Exception e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            if (k <= 0 || l <= 0 || array.length > 2) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return array;
    }

    public static Operation askOperation() throws InterruptOperationException{
        writeMessage(res.getString("choose.operation"));
        String ordinalS;
        int ordinalI;
        while (true){
            try
            {
                ordinalS = reader.readLine();
                if (ordinalS.equalsIgnoreCase(res.getString("operation.EXIT")))
                    throw new InterruptOperationException();

                ordinalI = Integer.parseInt(ordinalS);
                if (ordinalI < 0 || ordinalI > 4)
                {
                    writeMessage(res.getString("invalid.data"));
                    continue;
                }
                if (ordinalI == 0)
                    throw new IllegalArgumentException();

            }
            catch (NumberFormatException e)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            catch (IOException e)
            {
                writeMessage(res.getString("invalid.data"));
                continue;
            }

            break;
        }
        return Operation.getAllowableOperationByOrdinal(ordinalI);

    }
}

