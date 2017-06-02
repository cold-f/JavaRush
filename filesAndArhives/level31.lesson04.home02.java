package com.javarush.test.level31.lesson04.home02;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/* Поиск скрытых файлов
В классе Solution переопределите логику двух методов:
-visitFile кроме своей логики должен добавлять в archived все пути к zip и rar файлам
-visitFileFailed должен добавлять в failed все пути к недоступным файлам и возвращать SKIP_SUBTREE
Пример вывода:
D:/mydir/BCD.zip
Метод main не участвует в тестировании
*/
public class Solution extends SimpleFileVisitor<Path> {
    public static void main(String[] args) throws IOException {
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        final Solution solution = new Solution();
        Files.walkFileTree(Paths.get("D:/"), options, 20, solution);

        List<String> result = solution.getArchived();
        System.out.println("All archived files:");
        for (String path : result) {
            System.out.println("\t" + path);
        }

        List<String> failed = solution.getFailed();
        System.out.println("All failed files:");
        for (String path : failed) {
            System.out.println("\t" + path);
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
        if (file.toString().endsWith(".zip") || file.toFile().getAbsolutePath().endsWith(".rar")) //двумя способами получил полный строковый путь к файлу
          archived.add(file.toFile().getAbsolutePath());

        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
    {
        failed.add(file.toFile().getAbsolutePath());
        return FileVisitResult.SKIP_SUBTREE;
    }

    private List<String> archived = new ArrayList<>();
    private List<String> failed = new ArrayList<>();

    public List<String> getArchived() {
        return archived;
    }

    public List<String> getFailed() {
        return failed;
    }
}
--------------------------------------------------------------------------
All archived files:
	D:\$RECYCLE.BIN\S-1-5-21-1930688752-697488061-117185751-1000\$I1CA6ES.zip
	D:\Новый текстовый документ.rar
All failed files:
	D:\$RECYCLE.BIN\S-1-5-21-3273273566-4260807792-3056660685-1001
	D:\System Volume Information
