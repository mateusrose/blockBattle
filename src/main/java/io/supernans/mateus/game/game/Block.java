package io.supernans.mateus.game.game;

public class Block {
    private int width;
    private int height;
    private int[] pos = {width, height};
    private boolean hasTreasure = true;
    private int treasure = 20;

    public Block(int x, int y) {
        this.width = x;
        this.height = y;
    }

    public int getTreasure() {
        return treasure;
    }

    public void recoverTreasure() {
        this.treasure = 0;
    }

    public boolean hasTreasure() {
        return hasTreasure;
    }

    public void findTreasure() {
        this.hasTreasure = false;
    }
}
