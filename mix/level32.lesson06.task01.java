package com.javarush.test.level32.lesson06.task01;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ThreadLocalRandom;

/* Генератор паролей
Реализуйте логику метода getPassword, который должен возвращать ByteArrayOutputStream, в котором будут байты пароля.
Требования к паролю:
1) 8 символов
2) только цифры и латинские буквы разного регистра
3) обязательно должны присутствовать цифры, и буквы разного регистра
Все сгенерированные пароли должны быть уникальные.
Пример правильного пароля:
wMh7SmNu
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        for (int i = 0; i <2; i++){
            baos.write(ThreadLocalRandom.current().nextInt(65, 90 + 1));
            baos.write(ThreadLocalRandom.current().nextInt(48, 57 + 1)); //байты , отвечающие за цифры
            baos.write(ThreadLocalRandom.current().nextInt(97, 122 + 1));
            baos.write(ThreadLocalRandom.current().nextInt(48, 57 + 1));//байты , отвечающие за цифры
        }

        return baos;
    }
}
