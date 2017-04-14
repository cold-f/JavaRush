package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;


import java.util.Locale;
import java.util.ResourceBundle;

class LoginCommand implements Command
{

    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"login", Locale.ENGLISH);

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        while (true)
        {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            String SanswerNum = ConsoleHelper.readString();
            String SanswerPin = ConsoleHelper.readString();

            long numberAnswer = 0;
            int pinAnswer  = 0;

            if (SanswerNum.length() != 12 || SanswerPin.length() != 4)
            {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                continue;
            }

            try
            {
                numberAnswer = Long.parseLong(SanswerNum);
                pinAnswer  = Integer.parseInt(SanswerPin);
            }
            catch (NumberFormatException e){

                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                continue;
            }

            if (validCreditCards.containsKey(SanswerNum) && Integer.parseInt(validCreditCards.getString(SanswerNum)) == pinAnswer){
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), SanswerNum));
                break;
            }
            else
            {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), SanswerNum));
                continue;
            }
        }
    }
}

