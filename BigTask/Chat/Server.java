package com.javarush.test.level30.lesson15.big01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server
{
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>(); //ключ имя клиента, а значение - соединение с ним.

    public static void sendBroadcastMessage(Message message){ // отправляет сообщение  message по всем соединениям из connectionMap
        for (Map.Entry<String, Connection> pair: connectionMap.entrySet()){
            Connection clientConnection = pair.getValue();
            try {
                clientConnection.send(message);
            }
            catch (IOException e) {
                ConsoleHelper.writeMessage("Не удалось отправить сообщение");
            }

        }
    }

    public static void main(String[] args)
    {
        ConsoleHelper.writeMessage("Введите порт сервера:");
        int port = ConsoleHelper.readInt();

        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            while (true)
            {
                ConsoleHelper.writeMessage("Сервер запущен.");
                Handler handler = new Handler(serverSocket.accept());
                handler.start();
            }
        }
        catch (IOException e)
        {
            ConsoleHelper.writeMessage("Произошла ошибка");
        }

    }



    ///-------------------------------///-------------------------------------///



    private static class Handler extends Thread{   //реализовывает протокол общения с клиентом
        private Socket socket;

        public Handler(Socket socket)
        {
            this.socket = socket;
        }


        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{//знакомство сервера с клиентом
            while (true)
            {
                connection.send(new Message(MessageType.NAME_REQUEST));   //Сформировать и отправить команду запроса имени пользователя
                Message message=connection.receive();                     //Получить ответ клиента
                if (message.getType()!=MessageType.USER_NAME)            //Проверить, что получена команда с именем пользователя
                    continue;                                            //если нет, то опять отправить запрос
                String userName = message.getData();
                if (userName == null || userName.isEmpty() || connectionMap.containsKey(userName))
                    continue;
                connectionMap.put(userName, connection);
                connection.send(new Message(MessageType.NAME_ACCEPTED));
                return userName;
            }
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException {//отправка новому участнику инфо об остальных клиентах чата
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) { //connection – соединение с участником, которому будем слать информацию, а userName – его имя
                String otherUserName = pair.getKey();
                if (userName != otherUserName) { //про самого себя не отправляем
                    connection.send(new Message(MessageType.USER_ADDED, otherUserName));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException{ //редактирует сообщение юзера и рассылает всем
            while (true) {                                                         //connection – соединение с участником, а userName – его имя
                Message message = connection.receive();                            //принимаем сообщение
                if (message.getType() == MessageType.TEXT) {
                    String newText = userName + ": " + message.getData();         //редактируем его
                    sendBroadcastMessage(new Message(MessageType.TEXT, newText)); //отправляем всем
                }
                else
                    ConsoleHelper.writeMessage("Ошибка! Сообщение не является текстом");
            }
        }



        public void run (){
            ConsoleHelper.writeMessage("Установлено соединение с удаленным адресом: "+socket.getRemoteSocketAddress());
            String newUserName = null;
            try (Connection connection = new Connection(socket))
            {
                newUserName = serverHandshake(connection);   //рукопожатие с клиентом
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, newUserName)); //Рассылать всем участникам чата информацию об имени присоединившегося
                sendListOfUsers(connection, newUserName);//Сообщать новому участнику о существующих участниках
                serverMainLoop(connection, newUserName);//Запускать главный цикл обработки сообщений сервером
            }
            catch (IOException e) {ConsoleHelper.writeMessage("Ошибка при обмене данными с удаленным адресом");}
            catch (ClassNotFoundException e) {ConsoleHelper.writeMessage("Ошибка при обмене данными с удаленным адресом");}

            if (newUserName != null){
                connectionMap.remove(newUserName);//удаляемся и рассылаем всем сообщение об этом
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, newUserName));
            }
            ConsoleHelper.writeMessage("Соединение с удаленным адресом закрыто");
        }
    }
}

