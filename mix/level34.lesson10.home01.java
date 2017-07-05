package com.javarush.test.level34.lesson10.home01;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/* Настраиваем логгер
Дан файл log4j.properties, который содержит настройки для логгера для разработчиков.
Изменились требования к логированию для продакшена.
Список изменений, которые нужно сделать:
1) Размер файла для логирования не должен превышать 5 мегабайт
2) Файлы лога должны храниться на диске D в директории log, называться должны runApp.log
3) Файлы лога должны содержать 6 последних файлов. Если шестой файл уже заполнен(имеет размер 5Мб),
то нужно удалить самый первый и создать новый.
4) Уровень вывода сообщений в консоль нужно установить на уровне ERROR
5) Минимальный уровень логирования выставить в WARN
*/
public class Solution {
    public static void main(String args[]) throws IOException {
        String logProperties = "src/" + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/log4j.properties";
        Path path = Paths.get(logProperties).toAbsolutePath();
        try (InputStream is = new FileInputStream(path.toFile())) {
            Properties properties = new Properties();
            properties.load(is);
        }
    }
}
***************************************************************
//log4j.properties:

log4j.rootLogger=WARN, file, stdout

# Direct log messages to a log file
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.threshold=WARN
log4j.appender.file.File=D:\\log\\runApp.log
log4j.appender.file.MaxFileSize=5MB
log4j.appender.file.MaxBackupIndex=6
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern= %-5p %c{1}:%L - %m%n

# Direct log messages to stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.threshold=ERROR
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss}

log4j.logger.org.springframework=ERROR
log4j.logger.org.hibernate=ERROR
log4j.logger.org.apache.cxf=ERROR
