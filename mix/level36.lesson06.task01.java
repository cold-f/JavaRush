package com.javarush.test.level36.lesson06.task01;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

/* Найти класс по описанию
1. Реализует интерфейс List
2. Является приватным статическим классом внутри популярного утилитного класса
3. Доступ по индексу запрещен - кидается исключение IndexOutOfBoundsException
4. Используйте рефлекшн, чтобы добраться до искомого класса
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        for (Class cls : Collections.class.getDeclaredClasses()) {
            if (List.class.isAssignableFrom(cls)
                    && Modifier.isPrivate(cls.getModifiers())
                    && Modifier.isStatic(cls.getModifiers())) {
                try {
                    Constructor constructor = cls.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    List list = (List) constructor.newInstance();
                    try {
                        list.get(0);
                    } catch (IndexOutOfBoundsException e) {
                        return cls;
                    }
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                System.out.println(cls.toString());
            }
        }
        return null;
    }
}
