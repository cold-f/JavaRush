package com.javarush.test.level28.lesson15.big01;


import com.javarush.test.level28.lesson15.big01.model.HHStrategy;
import com.javarush.test.level28.lesson15.big01.model.Model;
import com.javarush.test.level28.lesson15.big01.model.MoikrugStrategy;
import com.javarush.test.level28.lesson15.big01.model.Provider;
import com.javarush.test.level28.lesson15.big01.view.HtmlView;


public class Aggregator
{
    public static void main(String[] args)
    {
        HtmlView htmlView = new HtmlView();
       // Model model = new Model(htmlView, new Provider[]{new Provider(new MoikrugStrategy())});
        Model model = new Model(htmlView, new Provider[]{new Provider(new HHStrategy())});

        Controller controller = new Controller(model);
        htmlView.setController(controller);
        htmlView.userCitySelectEmulationMethod();

    }
}


