package com.javarush.test.level36.lesson04.home01;

import java.util.ArrayList;
import java.util.List;

/* MVC - простая версия
В отдельных файлах создай классы Service, Model, View, Controller.
Распредели методы из класса Solution по созданным классам учитывая, что должен получиться MVC.
Создай нужные поля класса при необходимости.
Не добавляй своих других методов.
Метод main оставь в классе Solution.
*/
public class Solution {
    public static void main(String[] args) {
        new View().fireEventShowData();
    }
}

 class View {
    private Controller controller = new Controller();

    public void fireEventShowData() {
        System.out.println(controller.onDataListShow());
    }
}

 class Controller {
    private Model model= new Model();

    public List<String> onDataListShow() {
        return model.getStringDataList();
    }
}

class Model {
    private Service service = new Service();

    public List<String> getStringDataList() {
        return service.getData();
    }
}

class Service { //типа работает с БД

    public List<String> getData() {
        List<String> data = new ArrayList<String>() {{
            add("First string");
            add("Second string");
            add("Third string");
        }};
        return data;
    }
}

//это пассивный подход
