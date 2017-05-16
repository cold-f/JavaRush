package com.javarush.test.level28.lesson15.big01.model;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java junior+%s&page=%d";

    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        try {
            int pageNumber = 0;
            Document doc;
            while (true) {
                doc = getDocument(searchString, pageNumber++);
                if (doc == null) break;
                Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                elements.addAll(doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy vacancy-serp__vacancy_premium" ));
                if (elements.size() == 0) break;
                for (Element element : elements) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));
                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setSiteName("http://hh.ru");
                    String realsSalary = element.getElementsByAttributeValue("data-qa" ,"vacancy-serp__vacancy-compensation").text();
                    String salary = "";
                    if (realsSalary != null) {
                        salary = realsSalary;
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
        String url = String.format(URL_FORMAT, URLEncoder.encode(searchString, "UTF-8"), page); //декодер для того, чтобы иероглифы не выводились при вводе русских букв
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (jsoup)").referrer("none").get();
        return doc;
    }
}