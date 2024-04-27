package org.example.game.game;

public class Grid {

    private int width;
    private int height;

    private Block[][] blocks;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        blocks = new Block[width][height];
    }

    public Block getBlock(int col, int row) {
        return blocks[col][row];
    }

    public void setBlock(int col, int row, Block block) {
        blocks[col][row] = block;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public Block[][] getBlocks() {
        return blocks;
    }



}
