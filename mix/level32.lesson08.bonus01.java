package com.javarush.test.level32.lesson08.bonus01;

import java.lang.reflect.Proxy;
import java.util.stream.Stream;

/* Дженерики для создания прокси-объекта
В классе Solution создайте публичный метод getProxy
1) Метод getProxy должен возвращать прокси для любого интерфейса, который наследуется от Item
2) getProxy должен иметь два параметра. Первый - класс возвращаемого типа, второй - классы дополнительных интерфейсов.
3) Используйте ItemInvocationHandler для создания прокси
Метод main не участвует в тестировании
*/
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        test(solution.getProxy(Item.class));                        //true false false
        test(solution.getProxy(Item.class, Small.class));           //true false true
        test(solution.getProxy(Item.class, Big.class, Small.class));//true true true
        test(solution.getProxy(Big.class, Small.class));            //true true true т.к. Big наследуется от Item
        test(solution.getProxy(Big.class));                         //true true false т.к. Big наследуется от Item
    }


    private static void test(Object proxy) {
        boolean isItem = proxy instanceof Item;
        boolean isBig = proxy instanceof Big;
        boolean isSmall = proxy instanceof Small;

        System.out.format("%b %b %b\n", isItem, isBig, isSmall);
    }

    public   <T extends Item> T getProxy(Class<T> someClass, Class<?>... interfaces){
        Class[] mas = new Class[interfaces.length + 1];
        System.arraycopy(interfaces, 0, mas, 0, interfaces.length);
        mas[mas.length-1] = someClass;
        return (T) Proxy.newProxyInstance(someClass.getClassLoader(), mas, new ItemInvocationHandler());
    }


      public static <T> T getProxy2(Class<? extends T> iface, Class<?>...otherIfaces) { //этот метод более универсальный, но он не прошел тестирование
        Class<?>[] allInterfaces = Stream.concat( //(из интеренета)
                Stream.of(iface),
                Stream.of(otherIfaces))
                .distinct()
                .toArray(Class<?>[]::new);

        return (T) Proxy.newProxyInstance(
                iface.getClassLoader(),
                allInterfaces,
                new ItemInvocationHandler());
    }

}
************************************************************************
public class ItemInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
************************************************************************
public interface Item {}
************************************************************************
public interface Big extends Item {}
************************************************************************
public interface Small {}
************************************************************************
