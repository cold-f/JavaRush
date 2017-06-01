package com.javarush.test.level31.lesson02.home02;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* Находим все файлы
Реализовать логику метода getFileTree, который должен в директории root найти список всех файлов включая вложенные.
Используйте очередь, рекурсию не используйте.
Верните список всех путей к найденным файлам, путь к директориям возвращать не надо.
Путь должен быть абсолютный.
*/
public class Solution {
    public static void main(String[] args) throws IOException
    {
       List<String> list =  getFileTree("E:\\Desktop\\программирование\\временная");

       for (String s : list){
           System.out.println(s);
       }

    }



    public static List<String> getFileTree(String root) throws IOException {
        List<String> files = new ArrayList<>();
        Queue<File> folders = new LinkedList<>();

        File [] rootFilesAndFolders = new File(root).listFiles();

        for (File file : rootFilesAndFolders){//сначала проходимся по корнейвой папке
            if (file.isDirectory())
                folders.add(file);
            else
                files.add(file.getAbsolutePath());
        }

        while (!folders.isEmpty()){
            File [] innerFilesAndFolders = folders.remove().listFiles();//получаем из очереди папку/файл очередной и удаляем после этого его из очереди
            for (File file: innerFilesAndFolders){
                if (!file.isDirectory())
                    files.add(file.getAbsolutePath());
                else
                    folders.add(file);
            }
        }

        return files;

    }
}
