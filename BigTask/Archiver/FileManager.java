package com.javarush.test.level31.lesson15.big01;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private Path rootPath;       // корневой путь директории, файлы которой нас интересуют
    private List<Path> fileList; // список относительных путей файлов внутри rootPath

    public FileManager(Path rootPath) throws IOException {
        this.rootPath = rootPath;
        this.fileList = new ArrayList<>();
        collectFileList(rootPath);
    }

    public List<Path> getFileList() {
        return fileList;
    }

    private void collectFileList(Path path) throws IOException { //с помощью рекурсии здесь получаем все файлы(их пути) во всех поддиректориях
        if (Files.isRegularFile(path)) {
            Path relativePath = rootPath.relativize(path); //в аргументах передаем более "длинный" путь, тогда метод отрабатывает правильно
            fileList.add(relativePath);
        }

        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) { //это поток, поэтому надо закрывать.
                for (Path file : directoryStream) {
                    collectFileList(file);
                }
            }
        }
    }
}
