package io.supernans.mateus.server;

import io.supernans.mateus.game.entities.Player;
import io.supernans.mateus.resources.TerminalColors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Thread thread;
    private Socket clientSocket;
    private Server server;
    private Player player;
    private PrintWriter out;
    private int clientID;
    private static int playerCount = 0;
    private String inputLine;
    private InputHandler input;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.clientSocket = socket;
        this.server = server;
        this.player = new Player();
        assignColor();
        input = new InputHandler(this);
    }

    public InputHandler getInput() {
        return input;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            setPlayerConfig();

            while ((inputLine = in.readLine()) != null) {
                checkReady();
                server.broadcastMessage(inputLine, this);
            }

        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }

    private void assignColor() {
        String[] colors = {TerminalColors.RED, TerminalColors.GREEN, TerminalColors.BLUE, TerminalColors.YELLOW};
        player.setColor(colors[playerCount]);
        playerCount++;
        this.clientID = playerCount;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void setPlayerConfig() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out.println("Please enter your nickname:");
        String nickname = in.readLine();
        player.setName(nickname);
    }


public void checkReady() {
    if (inputLine.equals("ready")) {
        if (server.getGame() == null) {
            // The game has not started yet, so toggle the isPlaying state
            if (player.isPlaying()) {
                out.println("You are now unready to start playing");
                player.setPlaying(false);
            } else {
                out.println("You are now ready to play!");
                player.setPlaying(true);
            }
        } else {
            // The game has started, so toggle the isReady state
            if (player.isReady()) {
                out.println("You still want to parley");
                player.setReady();
            } else {
                out.println("You are now ready to explore!");
                player.setReady();
            }
        }
    }
}

    public void printCurrentPosition() {
        int[] position = player.getPos();
        System.out.println("Current position of " + player.getName() + ": (" + position[0] + ", " + position[1] + ")");
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }
}