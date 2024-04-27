package io.supernans.mateus.game.game;

import io.supernans.mateus.game.game.Block;

public class Grid {

    public static int WIDTH;
    private int height;

    private Block[][] blocks;

    public Grid(int width, int height) {
        WIDTH = width;
        this.height = height;
        blocks = new Block[width][height];
    }

    public Block getBlock(int col, int row) {
        return blocks[col][row];
    }

    public void setBlock(int col, int row, Block block) {
        blocks[col][row] = block;
    }

    public int getHeight() {
        return height;
    }

    public Block[][] getBlocks() {
        return blocks;
    }



}
