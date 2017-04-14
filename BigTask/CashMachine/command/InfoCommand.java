package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;

import java.util.Locale;
import java.util.ResourceBundle;


class InfoCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"info", Locale.ENGLISH);

    @Override
    public void execute()
    {
        boolean money = false;
        ConsoleHelper.writeMessage(res.getString("before"));
        for (CurrencyManipulator cur : CurrencyManipulatorFactory.getAllCurrencyManipulators()){
            if (cur.hasMoney()){
                    ConsoleHelper.writeMessage(cur.getCurrencyCode() + " - " + cur.getTotalAmount());
                    money = true;
            }
        }
        if (!money)
            ConsoleHelper.writeMessage(res.getString("no.money"));
    }
}

