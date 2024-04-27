package org.example.game;

import org.example.resources.TerminalColors;

public class Player {
    private String name;
    private boolean isReady;
    private String color;
    private int x;
    private int y;
    private  int[] pos = {x, y};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
    this.pos = pos;
    }
}
