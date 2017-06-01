package com.javarush.test.level31.lesson08.home01;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/* Null Object Pattern
Почитайте на вики про паттерн "Null Object"
Используйте Files, чтобы в конструкторе класса Solution правильно инициализировать поле fileData объектом ConcreteFileData.
Если возникли какие-то проблемы со чтением файла по пути pathToFile, то инициализируйте поле объектом NullFileData.
*/
public class Solution {
    private FileData fileData;

    public Solution(String pathToFile) {
        try {
            Path file = Paths.get(pathToFile);
            this.fileData = new ConcreteFileData(Files.isHidden(file), Files.isExecutable(file),  Files.isDirectory(file), Files.isWritable(file));
        }
        catch (Exception e) {
           this.fileData = new NullFileData(e);
        }
    }

    public FileData getFileData() {
        return fileData;
    }
}
----------------------------------------------------------------------------------------------
public interface FileData {
    boolean isHidden();

    boolean isExecutable();

    boolean isDirectory();

    boolean isWritable();
}
----------------------------------------------------------------------------------------------
public class ConcreteFileData implements FileData {

    private boolean hidden;
    private boolean executable;
    private boolean directory;
    private boolean writable;

    public ConcreteFileData(boolean hidden, boolean executable, boolean directory, boolean writable) {
        this.hidden = hidden;
        this.executable = executable;
        this.directory = directory;
        this.writable = writable;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public boolean isExecutable() {
        return executable;
    }

    @Override
    public boolean isDirectory() {
        return directory;
    }

    @Override
    public boolean isWritable() {
        return writable;
    }
}
----------------------------------------------------------------------------------------------
public class NullFileData implements FileData {
    private Exception exception;

    public NullFileData(Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public boolean isHidden() {
        return false;  
    }

    @Override
    public boolean isExecutable() {
        return false;  
    }

    @Override
    public boolean isDirectory() {
        return false;  
    }

    @Override
    public boolean isWritable() {
        return false;  
    }
}
----------------------------------------------------------------------------------------------
