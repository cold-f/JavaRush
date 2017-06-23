package com.javarush.test.level33.lesson10.home01;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/* Создание класса по строке xml
Восстановите класс по переданной строке xml.
Класс должен быть в отдельном файле.
Метод getClassName должен возвращать восстановленный класс.
Метод main не участвует в тестировании.
*/
public class Solution {
    public static void main(String[] args) throws JAXBException {
        String xmlData =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<shop>\n" +
                        "    <goods>\n" +
                        "        <names>S1</names>\n" +
                        "        <names>S2</names>\n" +
                        "    </goods>\n" +
                        "    <count>12</count>\n" +
                        "    <profit>123.4</profit>\n" +
                        "    <secretData>String1</secretData>\n" +
                        "    <secretData>String2</secretData>\n" +
                        "    <secretData>String3</secretData>\n" +
                        "    <secretData>String4</secretData>\n" +
                        "    <secretData>String5</secretData>\n" +
                        "</shop>";

        StringReader reader = new StringReader(xmlData);

        JAXBContext context = JAXBContext.newInstance(getClassName());
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Object o = unmarshaller.unmarshal(reader);

        System.out.println(o.toString());
    }

    public static Class getClassName() {
        return Shop.class;  //your class name
    }
}
****************************************************
@XmlRootElement()
public class Shop {

    @XmlElementWrapper(name="goods", nillable = true)
    public ArrayList <String> names = new ArrayList<>();

    public int count;
    public double profit;

    public ArrayList <String> secretData = new ArrayList<>();
}
**********************************************************
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<shop>
    <goods>
        <names>S1</names>
        <names>S2</names>
    </goods>
    <count>12</count>
    <profit>123.4</profit>
    <secretData>String1</secretData>
    <secretData>String2</secretData>
    <secretData>String3</secretData>
    <secretData>String4</secretData>
    <secretData>String5</secretData>
</shop>
