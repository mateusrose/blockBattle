package io.supernans.mateus.game.entities;

import io.supernans.mateus.game.game.Grid;
import io.supernans.mateus.resources.TerminalColors;

import java.util.concurrent.ConcurrentHashMap;

public class Player {
    private String name;
    private boolean isPlaying;
    private boolean isReady;
    private String color;
    private int x;
    private int y;
    private  int[] pos = {x, y};
    private int treasure = 0;
    
    private static final ConcurrentHashMap<String, int[]> STARTING_POSITIONS = new ConcurrentHashMap<>() {{
        put(TerminalColors.RED, new int[]{0, 0});
        put(TerminalColors.BLUE, new int[]{Grid.WIDTH - 1, Grid.WIDTH - 1});
        put(TerminalColors.GREEN, new int[]{0, Grid.WIDTH - 1});
        put(TerminalColors.YELLOW, new int[]{Grid.WIDTH - 1, 0});
    }};
    public void setStartingPos() {
        pos = STARTING_POSITIONS.get(color);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
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
    public void addTreasure(int treasure) {
        this.treasure += treasure;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady() {
        if(isReady){
            isReady = false;
        } else {
            isReady = true;
        }
    }

    public void move(String direction){
        switch (direction){
            case "up":
                pos[1]--;
                break;
            case "down":
                pos[1]++;
                break;
            case "left":
                pos[0]--;
                break;
            case "right":
                pos[0]++;
                break;
        }
    }
}
