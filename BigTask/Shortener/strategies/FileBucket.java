package com.javarush.test.level33.lesson15.big01.strategies;


import com.javarush.test.level33.lesson15.big01.ExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() {
        try {
            this.path =  Files.createTempFile(".txt", null);
            this.path.toFile().deleteOnExit();
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
    }

    public long getFileSize() {//возвращает размер файла на который  указывает path
        long size = 0L;
        try {
            size = Files.size(path);
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
        return size;
    }

    public void putEntry(Entry entry) //сериализовывает переданный entry в файл. Учти, каждый entry может содержать еще один entry.
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()));
            oos.writeObject(entry);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
    }

    public Entry getEntry() // забирает entry из файла. Если файл имеет нулевой размер, возвращает null.
    {
        Entry entry = null;
        if (path.toFile().length() > 0) {
            try {
                ObjectInputStream oin = new ObjectInputStream(new FileInputStream(path.toFile()));
                entry = (Entry) oin.readObject();
            } catch (IOException | ClassNotFoundException e) {
                ExceptionHandler.log(e);
            }
        }
        return entry;
    }

    public void remove() //удаляет файл на который указывает path.
    {
        try {
            Files.delete(path);
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
    }

}
