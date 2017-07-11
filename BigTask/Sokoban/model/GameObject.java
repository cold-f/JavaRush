package com.javarush.test.level34.lesson15.big01.model;

import java.awt.*;

public abstract class GameObject {
    private int x; //позиция объекта
    private int y;
    private int width; //размер объекта
    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = Model.FIELD_SELL_SIZE;
        this.height = Model.FIELD_SELL_SIZE;
    }

    public abstract void draw(Graphics graphics);


}
