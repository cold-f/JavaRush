package com.javarush.test.level34.lesson15.big01.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashSet;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        int tmpLevel = level;
        if (tmpLevel > 60)
            tmpLevel -= 60;
        HashSet<Wall> walls = new HashSet<>();
        HashSet<Box> boxes = new HashSet<>();
        HashSet<Home> homes = new HashSet<>();
        Player player = null;
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(levels.toString()));
            int cellSize =Model.FIELD_SELL_SIZE;
            int x0 = cellSize / 2;
            int y0 = cellSize / 2;
            while (!fileReader.readLine().contains("Maze: " + tmpLevel)) ;
            fileReader.readLine();
            fileReader.readLine();
            double y = Double.parseDouble(fileReader.readLine().split(" ")[2]);
            fileReader.readLine();
            fileReader.readLine();
            fileReader.readLine();
            for (int i = 0; i < y; i++) {
                String read = fileReader.readLine();
                for (int j = 0; j < read.length(); j++)
                    switch (read.charAt(j)) {
                        case 'X':
                            walls.add(new Wall(x0 + j * cellSize, y0 + i * cellSize));
                            break;
                        case '@':
                            player = new Player(x0 + j * cellSize, y0 + i * cellSize);
                            break;
                        case '*':
                            boxes.add(new Box(x0 + j * cellSize, y0 + i * cellSize));
                            break;
                        case '.':
                            homes.add(new Home(x0 + j * cellSize, y0 + i * cellSize));
                            break;
                        case '&':
                            homes.add(new Home(x0 + j * cellSize, y0 + i * cellSize));
                            boxes.add(new Box(x0 + j * cellSize, y0 + i * cellSize));
                            break;
                    }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new GameObjects(walls, boxes, homes, player);
    }




}
