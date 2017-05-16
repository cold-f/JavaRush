package com.javarush.test.level28.lesson15.big01.view;

import com.javarush.test.level28.lesson15.big01.Controller;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View
{
    private Controller controller;
    private final String filePath = "./src/" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";
    FileWriter writer;

    public HtmlView() {}

    @Override
    public void update(List<Vacancy> vacancies)
    {
       try {
           updateFile(getUpdatedFileContent(vacancies));
       }
       catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod(){
        controller.onCitySelect("Москва");
    }

    protected Document getDocument() throws IOException{
        Document doc = Jsoup.parse(new File(filePath), "UTF-8");
        return doc;
    }

    private String getUpdatedFileContent(List<Vacancy> list){
        Document document = null;
        try {
            document = getDocument();

        Element element = document.getElementsByAttributeValue("class", "vacancy template").first();
        Element elementTemplate = element.clone();
        elementTemplate.removeAttr("style");
        elementTemplate.removeClass("template");
        document.getElementsByAttributeValue("class", "vacancy").remove();

        for (Vacancy vacancy: list){
            Element temp = elementTemplate.clone();
            temp.getElementsByAttributeValue("class", "city").first().text(vacancy.getCity());
            temp.getElementsByAttributeValue("class", "companyName").first().text(vacancy.getCompanyName());
            temp.getElementsByAttributeValue("class", "salary").first().text(vacancy.getSalary());
            temp.select("a").first().attr("href", vacancy.getUrl()).text(vacancy.getTitle());
            element.before(temp.outerHtml());}
       }
       catch (IOException e) {
            e.printStackTrace();
            return "Some exception occurred";
        }
       return document.html();
    }


    private void updateFile(String fileBody){
        try {
            writer = new FileWriter(filePath);
            writer.write(fileBody);
        }
        catch (IOException e) {e.printStackTrace();}
        finally {
            try {
                writer.close();}
            catch (IOException e) {e.printStackTrace();}
        }
    }

}


