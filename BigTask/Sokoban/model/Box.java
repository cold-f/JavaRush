package com.javarush.test.level34.lesson15.big01.model;

import java.awt.*;

public class Box extends CollisionObject implements Movable{
    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(getX()-getWidth()/2, getY()-getHeight()/2, getWidth(), getHeight());//тогда центр квадрата, реально в координтах х и y.
    }

    @Override
    public void move(int x, int y) {
        setX(getX()+x);
        setY(getY()+y);
    }





}
