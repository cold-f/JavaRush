package com.javarush.test.level32.lesson04.home01;


import java.io.*;

/* Читаем из потока
Реализуйте логику метода getAllDataFromInputStream. Он должен вернуть StringWriter, содержащий все данные из переданного потока.
Возвращаемый объект ни при каких условиях не должен быть null.
Метод main не участвует в тестировании.
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        StringWriter writer = getAllDataFromInputStream(new FileInputStream("E:\\Desktop\\программирование\\временная\\1.txt"));//"testFile.log"
        System.out.println(writer.toString());
    }

    public static StringWriter getAllDataFromInputStream(InputStream is) throws IOException {
        try(StringWriter result = new StringWriter()) {
            if (is != null) {
                try (InputStreamReader reader = new InputStreamReader(is)) {
                    int count;
                    char[] buffer = new char[64];
                    while ((count = reader.read(buffer)) > 0) {
                        result.write(buffer,0,count);
                    }
                }
            }
            return result;
        }
    }
}
