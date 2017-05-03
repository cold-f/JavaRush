package com.javarush.test.level27.lesson15.big01.statistic.event;

import java.util.Date;

public interface EventDataRow
{
    EventType getType(); //тип записи
    Date getDate();// реализация которого вернет дату создания записи
    int getTime(); // реализация которого вернет время - продолжительность


}
