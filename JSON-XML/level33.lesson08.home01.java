package com.javarush.test.level33.lesson08.home01;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.StringWriter;

/* Первая сериализация в XML
Раставьте правильно JAXB аннотации у статических классов.
Метод main не участвует в тестировании.
*/
public class Solution {
    public static void main(String[] args) throws IOException, JAXBException {
        Cat cat = new Cat ();
        cat.name = "Murka";
        cat.age = 5;
        cat.weight = 3;

        Dog dog = new Dog ();
        dog.name = "Killer";
        dog.age = 8;
        dog.owner = "Bill Jeferson";

        StringWriter writer = new StringWriter();
        convertToXml(writer, cat);
        convertToXml(writer, dog);
        System.out.println(writer.toString());
       
    }

    public static void convertToXml(StringWriter writer, Object obj) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, writer);
    }

    public static class Pet
    {
        public String name;
    }

    @XmlRootElement(name = "кошка")//это отсебятина, потому что как в лекции переименовывание не работает
    public static class Cat extends Pet
    {

        public int age;
        public int weight;
    }

    @XmlRootElement
    public static class Dog extends Pet
    {
        public int age;
        public String owner;
    }
}
******************************************************************
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<кошка>
    <name>Murka</name>
    <age>5</age>
    <weight>3</weight>
</кошка>
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<dog>
    <name>Killer</name>
    <age>8</age>
    <owner>Bill Jeferson</owner>
</dog>
