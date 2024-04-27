package org.example.server;

import org.example.game.Player;
import org.example.resources.TerminalColors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CyclicBarrier;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    private Server server;
    private Player player;
    private PrintWriter out;
    private static int playerCount = 0;
    private String inputLine;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;

        this.server = server;
        this.player = new Player();
        assignColor();
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
    private void assignColor(){
        String[] colors = {TerminalColors.RED, TerminalColors.GREEN, TerminalColors.BLUE, TerminalColors.YELLOW};
        player.setColor(colors[playerCount]);
        playerCount++;
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


    public void checkReady(){
        if(inputLine.equals("ready")){
            if (player.isReady()) {
                out.println("You are now unready");
                player.setReady(false);
            } else {
                out.println("You are now ready");
                player.setReady(true);
            }
        }

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