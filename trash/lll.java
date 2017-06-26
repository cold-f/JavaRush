package com.javarush.test.level33.lesson10.bonus01;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/* Комментарий внутри xml
Реализовать метод toXmlWithComment, который должен возвращать строку - xml представление объекта obj.
В строке перед каждым тэгом tagName должен быть вставлен комментарий comment.
Сериализация obj в xml может содержать CDATA с искомым тегом. Перед ним вставлять комментарий не нужно.

Пример вызова:  toXmlWithComment(firstSecondObject, "second", "it's a comment")
Пример результата:
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<first>
    <!--it's a comment-->
    <second>some string</second>
    <!--it's a comment-->
    <second>some string</second>
    <!--it's a comment-->
    <second><![CDATA[need CDATA because of < and >]]></second>
    <!--it's a comment-->
    <second/>
</first>
*/
public class Solution {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, JAXBException, TransformerException {
        String result = toXmlWithComment(new AnExample(), "needCDATA", "it's a comment");
        System.out.println(result);
    }


    public static String toXmlWithComment(Object obj, String tagName, String comment) throws ParserConfigurationException, IOException, SAXException, JAXBException, TransformerException {

        JAXBContext jc = JAXBContext.newInstance(obj.getClass());

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();


        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(obj, document);

        Element element = document.getDocumentElement();

        Comment comment1 = document.createComment(comment);

        element.getParentNode().insertBefore(comment1, element);


        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        DOMSource source = new DOMSource(document);

        StringWriter stringWriter = new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        t.transform(source, result);
        return stringWriter.toString();
    }
}




@XmlType(name = "anExample")
@XmlRootElement
class AnExample {
    public String[] needCDATA = new String[]{"need CDATA because of < and >", ""};
}
