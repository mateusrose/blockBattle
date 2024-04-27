package org.example.game.game;

import org.example.game.Player;
import org.example.server.ClientHandler;

import java.util.concurrent.ConcurrentHashMap;

public class Game {
    private boolean hasStarted = false;
    private ConcurrentHashMap<ClientHandler, Boolean> players;
    private Grid grid;

    public Game(ConcurrentHashMap<ClientHandler,Boolean> players) {
        this.players = players;

    }
    public void init(){
        setupGame();
    }

    public void setupGame() {
        this.grid = new Grid(4,4);
        populateGrid();
        for (ClientHandler client : players.keySet()) {
            client.sendMessage("Game is starting!");
        }
        this.hasStarted=true;
    }
    public void populateGrid() {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                grid.setBlock(i, j, new Block(i, j));
            }
        }
    }



}
