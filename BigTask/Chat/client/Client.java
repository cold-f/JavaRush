package com.javarush.test.level30.lesson15.big01.client;

import com.javarush.test.level30.lesson15.big01.Connection;
import com.javarush.test.level30.lesson15.big01.ConsoleHelper;
import com.javarush.test.level30.lesson15.big01.Message;
import com.javarush.test.level30.lesson15.big01.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client
{
    protected Connection connection;
    private volatile boolean clientConnected = false;


    protected String getServerAddress(){
        ConsoleHelper.writeMessage("Введите адрес сервера:");//Адрес может быть строкой, содержащей ip, если клиент и сервер запущен на разных машинах или ‘localhost’
        return ConsoleHelper.readString();
    }


    protected int getServerPort(){
        ConsoleHelper.writeMessage("Введите порт сервера:");
        return ConsoleHelper.readInt();
    }


    protected String getUserName(){
        ConsoleHelper.writeMessage("Введите имя пользователя:");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSentTextFromConsole(){
        return true;
       /* в данной реализации клиента всегда должен возвращать true (мы всегда отправляем текст введенный в консоль). Этот
        метод может быть переопределен, если мы будем писать какой-нибудь другой  клиент, унаследованный от нашего,
       который не должен отправлять введенный в консоль текст.*/
    }

    protected SocketThread getSocketThread(){
        return new SocketThread();
    }

    protected void sendTextMessage(String text){ //создает новое текстовое сообщение,используя переданный текст и отправляет его серверу
        Message newTextMessage = new Message(MessageType.TEXT, text);
        try {
            connection.send(newTextMessage);
        }
        catch (IOException e) {
            ConsoleHelper.writeMessage("Произошла ошибка");
            clientConnected = false;
        }
    }


    public void run(){
       /* Он должен создавать вспомогательный поток SocketThread, ожидать пока тот установит соединение с сервером, а после этого
        в цикле считывать сообщения с консоли и отправлять их серверу. Условием выхода из цикла будет отключение клиента или ввод пользователем команды 'exit'.
        Для информирования главного потока, что соединение установлено во вспомогательным потоке, используй методы wait и notify объекта класса Client.*/
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);//нужно для того, чтобы при выходе из программы вспомогательный поток прервался автоматически
        socketThread.start();
        synchronized (this) //текущий поток ждет, пока не получит нотификацию (о том, что вспомогательный поток установил соедение с сервером)
        {
            try {
                this.wait();                          //---------------------------------------внимание!запомни, запиши
            }
            catch (InterruptedException e) {ConsoleHelper.writeMessage("Произошла ошибка в методе wait()");}
        }

        if (clientConnected)
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
        else
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");

        String text;
        while (clientConnected &&  !(text = ConsoleHelper.readString()).equals("exit")){
            if (shouldSentTextFromConsole())
                sendTextMessage(text);
        }
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }


    ///-------------------------------///-------------------------------------///



    public class SocketThread extends Thread{//будет отвечать за поток, устанавливающий сокетное соединение и читающий сообщения сервера
        protected void processIncomingMessage(String message) { //выводит текст message в консоль
            ConsoleHelper.writeMessage(message);
        }
        protected void informAboutAddingNewUser(String userName){  //выводит в консоль инфо о том, что участник с именем userName присоединился к чату
            ConsoleHelper.writeMessage(userName + " присоеденился к чату");
        }
        protected void informAboutDeletingNewUser(String userName){ //выводит в консоль, что участник с именем userName покинул чат
            ConsoleHelper.writeMessage(userName + " покинул чат");
        }
        protected void notifyConnectionStatusChanged(boolean clientConnected){
            Client.this.clientConnected = clientConnected;  //устанавливаем флаг
            synchronized (Client.this){  //оповещаем внешний класс ---------------------------------------внимание!запомни, запиши
               Client.this.notify();
            }
        }

       protected void clientHandshake() throws IOException, ClassNotFoundException{//Этот метод будет представлять клиента серверу
         while (true){
            Message inMessage = connection.receive(); //в цикле получаем сообщения
            if (inMessage.getType() == MessageType.NAME_REQUEST){  //если это запрос на имя, то запрашиваем его в консоли и отсылаем
                String userName = Client.this.getUserName();
                Message outMessage = new Message(MessageType.USER_NAME, userName);
                connection.send(outMessage);
            }
            else if (inMessage.getType() == MessageType.NAME_ACCEPTED){ //если сервер принял наше имя, оповещаем наш главный тред об этом
                notifyConnectionStatusChanged(true);
                break;
             }
             else
                 throw new IOException("Unexpected MessageType");
         }

       }


        protected void clientMainLoop() throws IOException, ClassNotFoundException{//Этот метод будет реализовывать главный цикл обработки сообщений сервера
            while (true)
            {
                Message inMessage = connection.receive(); //получаем сообщение от сервера и обрабатываем его
                if (inMessage.getType() == MessageType.TEXT)
                {
                    processIncomingMessage(inMessage.getData());
                }
                else if (inMessage.getType() == MessageType.USER_ADDED)
                {
                    informAboutAddingNewUser(inMessage.getData());
                }
                else if (inMessage.getType() == MessageType.USER_REMOVED)
                {
                    informAboutDeletingNewUser(inMessage.getData());
                } else
                    throw new IOException("Unexpected MessageType");
            }
        }


        public void run(){
            String serverAddress = getServerAddress();
            int serverPort = getServerPort();
            try {
                Socket socket = new Socket(serverAddress, serverPort);
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            }
            catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }
    }
}

