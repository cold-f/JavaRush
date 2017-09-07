package com.javarush.test.level37.lesson10.big01;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {
    private static final Object PRESENT = new Object();
    private transient HashMap<E,Object> map;

    public AmigoSet() {
        this.map = new HashMap<>();

    }

    public AmigoSet(Collection<? extends E> collection) {
        this.map = new HashMap<>((int)Math.max(16,collection.size()/.75f));
        this.addAll(collection);
    }


    @Override
    public Iterator<E> iterator() {
        return  map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return map.keySet().remove(o);
    }

    @Override
    public boolean add(E e) {
        boolean b = !map.containsKey(e);
        map.put(e, PRESENT);
        return b;
    }

    @Override
    public Object clone()
    {
        AmigoSet<E> amigoSet = new AmigoSet<>();
        try
        {
            amigoSet.addAll(this);
            amigoSet.map.putAll(this.map);
        }
        catch (Exception ex)
        {
            throw new InternalError();
        }
        return amigoSet;
    }
    private void writeObject(ObjectOutputStream s) throws IOException
    {
        s.defaultWriteObject();

        s.writeObject(map.size());
        for(E e:map.keySet()){
            s.writeObject(e);
        }
        s.writeObject(HashMapReflectionHelper.callHiddenMethod(map,"capacity"));
        s.writeObject(HashMapReflectionHelper.callHiddenMethod(map,"loadFactor"));
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();

        int size = (int)s.readObject();
        Set<E> set = new HashSet<>();
        for(int i =0;i<size;i++){
            set.add((E)s.readObject());
        }
        int capacity = (int)s.readObject();
        float loadFactor = (float)s.readObject();
        map = new HashMap<>(capacity,loadFactor);
        for(E e:set){
            map.put(e,PRESENT);
        }
    }
}
***********************************************************************************

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class HashMapReflectionHelper {
    public static  <T> T callHiddenMethod(HashMap map, String methodName) {
        try {
            Method method = map.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (T) method.invoke(map);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return null;
    }
}
***********************************************************************************
****************************************************************
Задание 6
Открой исходники HashSet (если у тебя нет исходников джавы, то скачай их и подключи), сравни со своим кодом.
Быстро это можно сделать сравнив через буфер. Скопируй код класса HashSet в буфер.
Зайди в класс AmigoSet, далее правая кнопка мыши -> Compare with Clipboard.

Ты только что реализовал сет, аналогичный HashSet. Теперь будешь знать, как внутри устроен HashSet.
Молодец, теперь коллекции тебе не страшны!


****************************************************************
Задание 5

Твое собственное множество AmigoSet реализует интерфейс Serializable. Однако, не сериализуется правильно.

1. Реализуй свою логику сериализации и десериализации.
Вспоминай, какие именно приватные методы нужно добавить, чтоб сериализация пошла по твоему сценарию.
Для сериализации:
* сериализуй сет
* сериализуй capacity и loadFactor у объекта map, они понадобятся для десериализации.
 Т.к. эти данные ограничены пакетом, то воспользуйся утилитным классом HashMapReflectionHelper, чтобы достать их.

Для десериализации:
* вычитай все данные
* создай мапу используя конструктор с capacity и loadFactor

2. Помнишь, что такое transient?


****************************************************************
Задание 4

Твое собственное множество AmigoSet реализует интерфейс Cloneable. Однако, не клонируется правильно.
Напиши свою реализацию метода Object clone(), сделай поверхностное клонирование.

* Склонируй множество, склонируй его мапу
* В случае возникновения исключений выбрось InternalError
* Убери лишнее пробрасывание исключения

Расширь модификатор доступа до public.


****************************************************************
Задание 3

Напиши свою реализацию следующих методов при условии, что нужно работать с ключами мапы:
* Iterator<E> iterator() - очевидно, что это итератор ключей. Получи множество ключей в map, верни его итератор
* int size() - это количество ключей в map, равно количеству элементов в map
* boolean isEmpty()
* boolean contains(Object o)
* void clear()
* boolean remove(Object o)

Ничего своего писать не нужно, используй то, что уже реализовано для множества ключей map.
Используй Alt+Insert => Override methods


****************************************************************
Задание 2

Изобретать механизм работы с хешем не будем, он уже реализован во многих коллекциях.
Мы возьмем коллекцию HashMap и воспользуемся ей.

1. Создай приватную константу Object PRESENT, которую инициализируй объектом Object, это будет наша заглушка.

2. Создай поле HashMap<E,Object> map. Список ключей будет нашим сэтом, а вместо значений будем пихать в мапу заглушку PRESENT.
Напомню, нам нужны только ключи, а вместо значений для всех ключей будем вставлять PRESENT. Там же должно что-то быть :)
Посмотрим, что из этого получится :)

Коллекции обычно имеют несколько конструкторов, поэтому:

3. Создай конструктор без параметров, в котором инициализируй поле map.

4. Создай конструктор с одним параметром Collection<? extends E> collection.
Для инициализации поля map воспользуйся конструктором, в который передается Capacity.
Вычисли свою Capacity по такой формуле: максимальное из 16 и (collection.size()/.75f)
Добавь все элементы из collection в нашу коллекцию.
Нужный метод добавления всех элементов у нас есть благодаря тому, что AbstractSet наследуется от AbstractCollection.

5. Напиши свою реализацию для метода метод add(E e): добавь в map элемент 'e' в качестве ключа и PRESENT в качестве значения.
Верни true, если был добавлен новый элемент, иначе верни false.


****************************************************************
Задание 1

Давай напишем какую-нибудь коллекцию. Пусть это будет твой собственный Set.
Пусть этот класс позволяет вставку NULL.


1. Создай класс AmigoSet. Пусть этот класс наследуется от AbstractSet.
Этот сэт должен сериализоваться и клонироваться (как же без этого??).
Также очевидно, что он должен реализовывать интерфейс Set.

2. Этот класс должен работать с любыми типами, поэтому сделай его дженериком: добавь тип, например, E.
Стандартные буквы, которые используют для дженериков - это E (element), T (type), K (key), V (value).
Названия не принципиальны, но облегчают чтение кода.

3. Воспользуйся горячими клавишами Идеи и реализуй необходимые методы, оставь дефолтовую реализацию.
