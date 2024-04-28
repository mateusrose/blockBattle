package io.supernans.mateus.game.game;

import io.supernans.mateus.game.checkers.MovementHandler;
import io.supernans.mateus.server.ClientHandler;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    private boolean hasStarted = false;
    private ConcurrentHashMap<ClientHandler, Boolean> players;
    private Grid grid;
    private int blocksWithTreasure;
    ExecutorService executorService;

    public Game(ConcurrentHashMap<ClientHandler, Boolean> players) {
        this.players = players;
        executorService = Executors.newFixedThreadPool(1);

    }

    public void init() {
        setupGame();
        run();

    }

    public int getBlocksWithTreasure() {
        return blocksWithTreasure;
    }

    public void setBlocksWithTreasure(int blocksWithTreasure) {
        this.blocksWithTreasure = blocksWithTreasure;
    }

    public void setupGame() {
        this.grid = new Grid(4, 4);
        populateGrid();
        for (ClientHandler client : players.keySet()) {
            client.getPlayer().setStartingPos();
            //send message telling where everyone is on the map and what options they have to follow
            client.sendMessage("Game is starting! Everyone is at their starting position!");
        }
        setBlocksWithTreasure(Grid.WIDTH * grid.getHeight());
        this.hasStarted = true;
    }

    public void populateGrid() {
        for (int i = 0; i < Grid.WIDTH; i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                grid.setBlock(i, j, new Block(i, j));
            }
        }
    }

    public void run() {
        while (hasStarted) {
            for (ClientHandler client : players.keySet()) {
                client.getOut().println("Waiting for everyone to put ready");
            }
            run:
            while (blocksWithTreasure > 0) {
                for (ClientHandler client : players.keySet()) {
                    if (!client.getPlayer().isReady()) {
                        continue run;
                    }


                    client.sendMessage("Your turn!");
                    askMovement(client);

                    //send message to client with the current state of the game

                }
            }
        }
    }

    public boolean checkPlayer() {
        for (ClientHandler client1 : players.keySet()) {
            for (ClientHandler client2 : players.keySet()) {
                if (client1 != client2 &&
                        client1.getPlayer().getPos()[0] == client2.getPlayer().getPos()[0] &&
                        client1.getPlayer().getPos()[1] == client2.getPlayer().getPos()[1]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkBlock(ClientHandler client) {
        int[] playerPos = client.getPlayer().getPos();
        Block currentBlock = grid.getBlock(playerPos[0], playerPos[1]);
        return currentBlock.getTreasure() > 0;
    }

    public void askMovement(ClientHandler client) {
        HashSet<String> options = MovementHandler.getValidDirections(client.getPlayer(), grid);
        String direction = client.getInput().getDirection(options);
        client.getPlayer().move(direction);
        client.printCurrentPosition();
    }

    public void unready() {
        for (ClientHandler client : players.keySet()) {
            client.getPlayer().setReady();
        }
    }

    public boolean hasStarted() {
        return hasStarted;
    }
}
