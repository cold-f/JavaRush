package com.javarush.test.level38.lesson06.home01;

/* Фабрика исключений
Создайте класс - фабрику исключений, который содержит один статический метод, возвращающий нужное исключение.
Этот метод должен принимать один параметр - энум.
Если передан энум:
* ExceptionApplicationMessage, верните исключение Exception
* ExceptionDBMessage, верните исключение RuntimeException
* ExceptionUserMessage, верните Error
иначе верните IllegalArgumentException без сообщения.

В качестве сообщения (message) для каждого возвращаемого объекта используйте элементы энума, в которых все знаки
подчеркивания замените на пробелы. Сообщение должно быть в нижнем регистре за исключением первого символа.
Например, сообщение для ExceptionApplicationMessage.SOCKET_IS_CLOSED - "Socket is closed".

Верните класс созданной фабрики в методе Solution.getFactoryClass.

Энумы не меняйте.
*/

public class Solution {
    public static Class getFactoryClass() {
        return ExceptionFactory.class;
    }
}
******************************************************************************
public class ExceptionFactory {

    public static Throwable getException(Enum enumeration) {

        if (enumeration != null) {
            if (enumeration instanceof ExceptionApplicationMessage) {
                return new Exception(enumeration.name().charAt(0) + enumeration.name().substring(1).toLowerCase().replace("_", " "));
            }
            else if (enumeration instanceof ExceptionDBMessage) {
                return new RuntimeException(enumeration.name().charAt(0) + enumeration.name().substring(1).toLowerCase().replace("_", " "));
            }
            else if (enumeration instanceof ExceptionUserMessage) {
                return new Error(enumeration.name().charAt(0) + enumeration.name().substring(1).toLowerCase().replace("_", " "));
            }
        }
        return new IllegalArgumentException();
    }
}
******************************************************************************
public enum ExceptionApplicationMessage {
    UNHANDLED_EXCEPTION,
    SOCKET_IS_CLOSED
}
******************************************************************************
public enum ExceptionUserMessage {
    USER_DOES_NOT_EXIST,
    USER_DOES_NOT_HAVE_PERMISSIONS
}
******************************************************************************
public enum ExceptionDBMessage {
    NOT_ENOUGH_CONNECTIONS,
    RESULT_HAS_NOT_GOTTEN_BECAUSE_OF_TIMEOUT
}
******************************************************************************
 
