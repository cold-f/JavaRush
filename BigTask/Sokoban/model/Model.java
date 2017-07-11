package com.javarush.test.level34.lesson15.big01.model;

import com.javarush.test.level34.lesson15.big01.controller.EventListener;

import java.nio.file.Paths;

public class Model {

    public static final int FIELD_SELL_SIZE = 20;//размер ячейки игрового поля

    private EventListener eventListener;
    private int currentLevel = 1;//текущий уровень
    private GameObjects gameObjects;// Оно будет хранить наши игровые объекты.
    private LevelLoader levelLoader = new LevelLoader(Paths.get("src\\com\\javarush\\test\\level34\\lesson15\\big01\\res\\levels.txt"));

    public void setEventListener(EventListener eventListener){
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {//он должен возвращать все игровые объекты.
        return gameObjects;
    }

    public void restartLevel(int level) {//получает новые игровые объекты для указанного уровня у загрузчика уровня levelLoader и сохраняет их в поле gameObjects.
        gameObjects = levelLoader.getLevel(level);
    }


    public void  restart(){      //перезапускает текущий уровнь, вызывая restartLevel с нужным параметром
        restartLevel(currentLevel);
    }

    public void startNextLevel(){//увеличивает значение переменной currentLevel, хранящей номер текущего уровня и выполняет перезапуск нового уровня
        currentLevel++;
        restart();

    }

    public void move(Direction direction){

        Player player = gameObjects.getPlayer();

        if (checkWallCollision(player, direction)) {
            return;
        }
        if (checkBoxCollision(direction)){
            return;
        }

        int sellSize = FIELD_SELL_SIZE;
        switch (direction) {
            case LEFT:
                player.move(-sellSize, 0);
                break;
            case RIGHT:
                player.move(sellSize, 0);
                break;
            case UP:
                player.move(0, -sellSize);
                break;
            case DOWN:
                player.move(0, sellSize);
        }
        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction){//проверяет столкновение со стеной

        for (Wall wall : gameObjects.getWalls()){

            if(gameObject.isCollision(wall, direction)){
                return true;
            }
        }
        return false;
    }

     public boolean checkBoxCollision(Direction direction) { //проверяет столкновение с ящиками + передвижение
         Player player = gameObjects.getPlayer();

         // найдем во что уперся игрок
         GameObject  stoped = null;
         for (GameObject gameObject: gameObjects.getAll()){
             if (!(gameObject instanceof Player)&&!(gameObject instanceof Home) && player.isCollision(gameObject, direction)){
                 stoped = gameObject;
             }
         }
         //свободное место или дом
         if ((stoped == null)){
             return false;
         }
         if (stoped instanceof Box){
             Box stopedBox = (Box)stoped;
             if (checkWallCollision(stopedBox, direction)){
                 return true;
             }
             for (Box box : gameObjects.getBoxes()){
                 if(stopedBox.isCollision(box, direction)){
                     return true;
                 }
             }
             switch (direction)
             {
                 case LEFT:
                     stopedBox.move(-FIELD_SELL_SIZE, 0);
                     break;
                 case RIGHT:
                     stopedBox.move(FIELD_SELL_SIZE, 0);
                     break;
                 case UP:
                     stopedBox.move(0, -FIELD_SELL_SIZE);
                     break;
                 case DOWN:
                     stopedBox.move(0, FIELD_SELL_SIZE);
             }
         }
         return false;


     }

    public void checkCompletion() { //проверяет пройден ли уровень (на всех ли домах стоят ящики, их координаты должны совпадать)
        boolean yes = true;

        for (Home home : gameObjects.getHomes()) {
            boolean currentyes = false;

            for (Box box : gameObjects.getBoxes()) {
                if ((box.getX() == home.getX()) && (box.getY() == home.getY()))
                    currentyes = true;
            }

            if (!currentyes)
                yes = false;
        }

        if (yes)
            eventListener.levelCompleted(currentLevel);
    }
}
