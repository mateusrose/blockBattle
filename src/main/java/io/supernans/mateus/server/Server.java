package io.supernans.mateus.server;

import io.supernans.mateus.game.game.Game;
import io.supernans.mateus.resources.TerminalColors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int MAX_CLIENTS = 2;
    private static final int PORT = 8080;
    private ServerSocket serverSocket;

    private ExecutorService executorService;
    private Game game = null;
    private ConcurrentHashMap<ClientHandler, Boolean> clients = new ConcurrentHashMap<>();

    public void init() {
        executorService = Executors.newFixedThreadPool(MAX_CLIENTS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            this.serverSocket = serverSocket;
            System.out.println("Server is running...");

            while (true) {
                clientHandle();
                if (readyToPlay()) {
                    game = new Game(clients);
                    game.init();


                }
                //gamestart
            }

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean readyToPlay() {
        for (ClientHandler client : clients.keySet()) {
            if (!client.getPlayer().isPlaying()) {
                return false;
            }
        }
        return true;
    }

    public void broadcastGeral(String message) {
        for (ClientHandler client : clients.keySet()) {
            client.sendMessage(message);
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients.keySet()) {
            if (client != sender) {
                client.sendMessage(sender.getPlayer().getColor() + sender.getPlayer().getName() + ": " + TerminalColors.RESET + message);
            }
        }
    }

    private void clientHandle() throws IOException {
        if (clients.size() < MAX_CLIENTS) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            clients.put(clientHandler, true);
            executorService.execute(clientHandler);
        }
    }

    public Game getGame() {
        return game;
    }
}