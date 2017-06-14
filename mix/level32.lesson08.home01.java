package com.javarush.test.level32.lesson08.home01;

import java.lang.reflect.Proxy;

/* Создание прокси-объекта
1) В отдельном файле создать публичный класс CustomInvocationHandler, который будет хэндлером при создании прокси-объекта.
2) CustomInvocationHandler должен иметь один публичный конструктор с одним агументом типа SomeInterfaceWithMethods.
3) Перед вызовом любого метода у оригинального объекта должна выводиться фраза [methodName in].
4) После вызова любого метода у оригинального объекта должна выводиться фраза [methodName out].
5) Реализовать логику метода getProxy, который должен создавать прокси для интерфейса SomeInterfaceWithMethods.
См. пример вывода в методе main. Метод main не участвует в тестировании.
*/
public class Solution {
    public static void main(String[] args) {
        SomeInterfaceWithMethods obj = getProxy();
        obj.stringMethodWithoutArgs();
        obj.voidMethodWithIntArg(1);
    }

    public static SomeInterfaceWithMethods getProxy() {
        SomeInterfaceWithMethodsImpl original = new SomeInterfaceWithMethodsImpl();
        ClassLoader classLoader = original.getClass().getClassLoader();
        Class<?>[] interfaces = original.getClass().getInterfaces();

        CustomInvocationHandler invocationHandler = new CustomInvocationHandler(original);

        return (SomeInterfaceWithMethods) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
***********************************************************************************
public class CustomInvocationHandler implements InvocationHandler {

    private SomeInterfaceWithMethods siwm;

    public CustomInvocationHandler(SomeInterfaceWithMethods siwm) {
        this.siwm = siwm;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {//мы перехватываем вызов оригинального метода и делаем
        System.out.println(method.getName()+" in");                                  //все, что хотим
        method.invoke(siwm, args); //это вызов оригинального метода
        System.out.println(method.getName()+" out");
        return null;
    }
}
***********************************************************************************
public interface SomeInterfaceWithMethods {
    void voidMethodWithoutArgs();

    String stringMethodWithoutArgs();

    void voidMethodWithIntArg(int i);
}
***********************************************************************************
public class SomeInterfaceWithMethodsImpl implements SomeInterfaceWithMethods {
    public void voidMethodWithoutArgs() {
        System.out.println("inside voidMethodWithoutArgs");
    }

    public String stringMethodWithoutArgs() {
        System.out.println("inside stringMethodWithoutArgs");
        return null;
    }

    public void voidMethodWithIntArg(int i) {
        System.out.println("inside voidMethodWithIntArg");
        voidMethodWithoutArgs();
    }
}
**********************************************************************************
/* expected output
        stringMethodWithoutArgs in
        inside stringMethodWithoutArgs
        stringMethodWithoutArgs out
        voidMethodWithIntArg in
        inside voidMethodWithIntArg
        inside voidMethodWithoutArgs
        voidMethodWithIntArg out
        */
//
