package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;
import com.javarush.test.level26.lesson15.big01.exception.NotEnoughMoneyException;

import java.util.ConcurrentModificationException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


class WithdrawCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"withdraw", Locale.ENGLISH);

    @Override
    public void execute() throws InterruptOperationException
    {

        String CurrencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator CurrencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(CurrencyCode);
        ConsoleHelper.writeMessage(res.getString("before"));
        while (true)
        {
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            String s = ConsoleHelper.readString();

            int sum;
            try {
                sum = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                continue;
            }

            if (!CurrencyManipulator.isAmountAvailable(sum)) {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
            if (sum <= 0 ) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }

            Map<Integer, Integer> map;

            try {
                map = CurrencyManipulator.withdrawAmount(sum);

            for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"),  pair.getKey(),  pair.getValue()+"") );
            }
            }
            catch (NotEnoughMoneyException e)
            {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                continue;
            }
            catch (ConcurrentModificationException e){
                ConsoleHelper.writeMessage("Ошибка. Повторите операцию");
                continue;
            }
            break;
        }
    }
}


