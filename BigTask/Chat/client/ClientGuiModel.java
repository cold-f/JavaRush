package com.javarush.test.level30.lesson15.big01.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClientGuiModel//клиент
{
   private final Set <String> allUserNames = new HashSet<>();//хранится список всех участников чата
   private String newMessage; //храниться новое сообщение, которое получил GUI-клиент

    public String getNewMessage()
    {
        return newMessage;
    }

    public void setNewMessage(String newMessage)
    {
        this.newMessage = newMessage;
    }

    public Set<String> getAllUserNames()
    {
        return Collections.unmodifiableSet(allUserNames); //здесь запрещается модифицировать возвращаемое множество
    }

    public void addUser(String newUserName){ //добавляет имя участника во множество
        allUserNames.add(newUserName);
    }

    public void deleteUser(String userName){//удаляет имя участника из множества
        allUserNames.remove(userName);

    }

}
