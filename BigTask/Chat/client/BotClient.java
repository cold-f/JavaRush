package com.javarush.test.level30.lesson15.big01.client;

import com.javarush.test.level30.lesson15.big01.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class BotClient extends Client //бот, который может отправлять текущее время или дату, когда его кто-то об этом попросит
{
    @Override
    protected String getUserName()
    {
        int botNumber = ThreadLocalRandom.current().nextInt( 100);
        String botName = "date_bot_"+botNumber;  //date_bot_XX, где XX – любое число от 0 до 99
        return botName;
    }

    @Override
    protected boolean shouldSentTextFromConsole() {//Мы не хотим,чтобы бот отправлял текст введенный в консоль.
        return false;
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }


    public static void main(String[] args)
    {
        BotClient botClient = new BotClient();
        botClient.run();
    }


    ///-------------------------------///-------------------------------------///

    public class BotSocketThread extends SocketThread{

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }


        @Override
        protected void processIncomingMessage(String message)
        {
            ConsoleHelper.writeMessage(message);
            String [] nameAndMessage = message.split(": ");
            String nameMessageRequest = null;
            String dateMessageRequest = null;
            if (nameAndMessage.length == 2)
            {
                nameMessageRequest = nameAndMessage[0];
                dateMessageRequest = nameAndMessage[1];
            }
            SimpleDateFormat sdf = null;

            if (dateMessageRequest != null)
            {
                switch (dateMessageRequest)
                {
                    case "дата":
                        sdf = new SimpleDateFormat("d.MM.YYYY");
                        break;
                    case "день":
                        sdf = new SimpleDateFormat("d");
                        break;
                    case "месяц":
                        sdf = new SimpleDateFormat("MMMM");
                        break;
                    case "год":
                        sdf = new SimpleDateFormat("YYYY");
                        break;
                    case "время":
                        sdf = new SimpleDateFormat("H:mm:ss");
                        break;
                    case "час":
                        sdf = new SimpleDateFormat("H");
                        break;
                    case "минуты":
                        sdf = new SimpleDateFormat("m");
                        break;
                    case "секунды":
                        sdf = new SimpleDateFormat("s");
                        break;
                }
            }
            if (sdf != null)
            {
                String dateMessageResponce = sdf.format(Calendar.getInstance().getTime()); //получаем текущую дату и форматируем ее
                sendTextMessage(String.format("Информация для %s: %s", nameMessageRequest, dateMessageResponce));
            }

        }


    }
}
