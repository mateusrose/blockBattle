package org.example.game.game;

public class Block {
    private int width;
    private int height;
    private int[] pos = {width, height};
    private boolean isBad = false;

    public Block(int x, int y) {
        this.width = x;
        this.height = y;
    }

    public boolean isBad() {
        return isBad;
    }

    public void setBad(boolean bad) {
        isBad = bad;
    }
}
