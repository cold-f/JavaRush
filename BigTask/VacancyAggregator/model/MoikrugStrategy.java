package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy {
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?city_id=&location=%s&page=%d&q=java+junior";


    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        try {
            int pageNumber = 0;
            Document doc;
            while (true) {
                doc = getDocument(searchString, pageNumber++);
                if (doc == null) break;
                Elements elements = doc.getElementsByAttributeValue("class", "job  ");
                elements.addAll(doc.getElementsByAttributeValue("class", "job marked" ));
                if (elements.size() == 0) break;
                for (Element element : elements) {
                    Vacancy vacancy = new Vacancy();

                    vacancy.setCompanyName(element.getElementsByClass(  "company_name").first().getElementsByTag("a").first().text());
                    vacancy.setSiteName("https://moikrug.ru");
                    vacancy.setTitle(element.getElementsByClass("title").text());
                    vacancy.setCity(element.getElementsByClass("location").text());
                    vacancy.setUrl("https://moikrug.ru"+element.getElementsByClass("job_icon").attr("href"));

                    String realSalary = element.getElementsByClass( "count").text();
                    String salary = "";
                    if (realSalary != null) {
                        salary = realSalary;
                    }
                    vacancy.setSalary(salary);

                    vacancies.add(vacancy);
                }
            }
        }
        catch (Exception e) {
        }
        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String url = String.format(URL_FORMAT, URLEncoder.encode(searchString, "UTF-8"), page);   //декодер для того, чтобы иероглифы не выводились при вводе русских букв
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (jsoup)").referrer("none").get();
        return doc;
    }
}
