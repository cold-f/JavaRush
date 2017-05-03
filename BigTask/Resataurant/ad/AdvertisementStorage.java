package com.javarush.test.level27.lesson15.big01.ad;

import java.util.ArrayList;
import java.util.List;

class AdvertisementStorage
{
    private final List<Advertisement> videos = new ArrayList<Advertisement>();

    private static volatile AdvertisementStorage instance;
    private AdvertisementStorage(){
        Object someContent = new Object();
        videos.add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min //hits 100
        videos.add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min  //hits 10
        videos.add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60));   //10 min  //hits 2
    }

    public static AdvertisementStorage getInstance() {
        if (instance == null)
            synchronized (AdvertisementStorage.class) {
                if (instance == null)
                    instance = new AdvertisementStorage();
            }
        return instance;
    }



    public List<Advertisement> list(){
        return videos;
    }
    public void add(Advertisement advertisement){
        videos.add(advertisement);
    }


}

