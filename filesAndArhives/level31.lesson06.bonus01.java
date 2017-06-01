package com.javarush.test.level31.lesson06.bonus01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipInputStream;

/* Разархивируем файл
В метод main приходит список аргументов.
Первый аргумент - имя результирующего файла resultFileName, остальные аргументы - имена файлов fileNamePart.
Каждый файл (fileNamePart) - это кусочек zip архива. Нужно разархивировать целый файл, собрав его из кусочков.
Записать разархивированный файл в resultFileName.
Архив внутри может содержать файл большой длины, например, 50Mb.
Внутри архива может содержаться файл с любым именем.
Пример входных данных. Внутри архива находится один файл с именем abc.mp3:
C:/result.mp3
C:/pathToTest/test.zip.003
C:/pathToTest/test.zip.001
C:/pathToTest/test.zip.004
C:/pathToTest/test.zip.002
*/
public class Solution
{
    public static void main(String[] args) throws IOException
    {
        if (args.length < 2) return;

        String resultFileName = args[0];
        int filePartCount = args.length - 1;
        String[] fileNamePart = new String[filePartCount];
        for (int i = 0; i < filePartCount; i++)
        {
            fileNamePart[i] = args[i + 1];
        }
        Arrays.sort(fileNamePart);

        List<FileInputStream> fisList = new ArrayList<>();
        for (int i = 0; i < filePartCount; i++)
        {
            fisList.add(new FileInputStream(fileNamePart[i]));
        }
        SequenceInputStream seqInStream = new SequenceInputStream(Collections.enumeration(fisList));
        ZipInputStream zipInStream = new ZipInputStream(seqInStream);
        FileOutputStream fileOutStream = new FileOutputStream(resultFileName);
        byte[] buf = new byte[1024 * 1024];
        while (zipInStream.getNextEntry() != null)
        {
            int count;
            while ((count = zipInStream.read(buf)) != -1)
            {
                fileOutStream.write(buf, 0, count);
            }
        }
        seqInStream.close();
        zipInStream.close();
        fileOutStream.close();
    }
}
--------------------------------------------------------------
ЕЩЕ РЕАЛИЗАЦИЯ:
--------------------------------------------------------------
  public static void combineFiles (ArrayList<String> files, String resultFileName) throws FileNotFoundException, IOException {
        Vector<FileInputStream> v = new Vector<FileInputStream>(files.size());
        for (int i = 0; i < files.size(); i++)
            v.add(new FileInputStream(files.get(i)));

        Enumeration<FileInputStream> e = v.elements();

        SequenceInputStream sequenceInputStream = new SequenceInputStream(e);

        ZipInputStream is = new ZipInputStream(sequenceInputStream);
        try {
            for (ZipEntry entry = null; (entry = is.getNextEntry()) != null;) {
                OutputStream os = new BufferedOutputStream(new FileOutputStream(resultFileName));
                try {
                    final int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    for (int readBytes = -1; (readBytes = is.read(buffer, 0, bufferSize)) > -1;) {
                        os.write(buffer, 0, readBytes);
                    }
                    os.flush();
                } finally {
                    os.close();
                }
            }
        } finally {
            is.close();
        }

    }
	
	
	//1 параметр-лист кусочков, 2 параметр - это имя выходного файла
	//обработать ошибки
 // Все это работает только если 1 файл в нашем архиве
