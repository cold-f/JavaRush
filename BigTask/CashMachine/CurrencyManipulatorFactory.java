package com.javarush.test.level26.lesson15.big01;


import java.util.Collection;
import java.util.HashMap;

public abstract class CurrencyManipulatorFactory
{
    private static HashMap<String, CurrencyManipulator> map = new HashMap<String, CurrencyManipulator>();

    private CurrencyManipulatorFactory() {}

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode){
        if (!map.containsKey(currencyCode)){
            map.put(currencyCode, new CurrencyManipulator(currencyCode));
        }

        return map.get(currencyCode);
    }


    public static Collection<CurrencyManipulator> getAllCurrencyManipulators()
    {
        return map.values();
    }
}


