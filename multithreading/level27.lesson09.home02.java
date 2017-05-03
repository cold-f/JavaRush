package com.javarush.test.level27.lesson09.home02;

/* Расставьте wait-notify.
Расставьте wait-notify.
Пример вывода:
Thread-0 MailServer has got: [Person [Thread-1] has written an email 'AAA'] in 1001 ms after start
*/
public class Solution {
    public static void main(String[] args) {
        Mail mail = new Mail();
        Thread server = new Thread(new MailServer(mail));
        Thread amigo = new Thread(new Person(mail));

        server.start();
        amigo.start();
    }
}
****************************************************
public class Mail {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
****************************************************
public class Person implements Runnable {
    private final Mail mail;

    public Person(Mail mail) {
        this.mail = mail;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            Thread.sleep(1000);

            synchronized (mail)
            {
                mail.setText("Person [" + name + "] has written an email 'AAA'");
                mail.notifyAll();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
****************************************************
public class MailServer implements Runnable {
    private Mail mail;

    public MailServer(Mail mail) {
        this.mail = mail;
    }

    @Override
    public void run() {
        long beforeTime = System.currentTimeMillis();

        synchronized (mail)
        {
            try
            {
                mail.wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
            String name = Thread.currentThread().getName();
            long afterTime = System.currentTimeMillis();
            System.out.format("%s MailServer has got: [%s] in %d ms after start", name, mail.getText(), (afterTime - beforeTime));
        }
}
