package com.javarush.test.level30.lesson15.big01.client;

public class ClientGuiController extends Client {     //контроллер
    private ClientGuiModel model = new ClientGuiModel();
    private ClientGuiView view = new ClientGuiView(this);

    @Override
    protected String getServerAddress() { //одноименные методы из view
        return view.getServerAddress();
    }

    @Override
    protected int getServerPort() {
        return  view.getServerPort();
    }

    @Override
    protected String getUserName() {
        return  view.getUserName();
    }

    @Override
    protected SocketThread getSocketThread() { //создавать и возвращать объект типа GuiSocketThread
        return new GuiSocketThread();
    }

    @Override
    public void run()
    {
        SocketThread socketThread = getSocketThread();
        socketThread.run();
      /* Разберись, почему нет необходимости вызывать метод run в отдельном потоке, как мы это делали для консольного клиента

        ответ:
        ActionListener на самом деле создает новую нить с примерно таким именем. В консольном клиенте нам thread был нужен,
        чтобы независимо слушать сервер и считывать данные с консоли*/
    }



    public ClientGuiModel getModel(){
        return model;
    }

    public static void main(String[] args)
    {
        ClientGuiController clientGuiController = new ClientGuiController();
        clientGuiController.run();
    }


    ///-------------------------------///-------------------------------------///


    public class GuiSocketThread extends SocketThread{
        @Override
        protected void processIncomingMessage(String message)//устанавливает новое сообщение у модели и вызывает обновление вывода сообщений у представления
        {
            model.setNewMessage(message);
            view.refreshMessages();

        }

        @Override
        protected void informAboutAddingNewUser(String userName) // добавляет нового пользователя в модель и вызывает обновление вывода пользователей у отображения
        {
           model.addUser(userName);
           view.refreshUsers();
        }

        @Override
        protected void informAboutDeletingNewUser(String userName) //удаляет пользователя из модели и вызывет обновление вывода пользователей у отображения
        {
            model.deleteUser(userName);
            view.refreshUsers();
        }

        @Override
        protected void notifyConnectionStatusChanged(boolean clientConnected)//вызывает аналогичный метод у представления(view)
        {
            view.notifyConnectionStatusChanged(clientConnected);
        }
    }

}
