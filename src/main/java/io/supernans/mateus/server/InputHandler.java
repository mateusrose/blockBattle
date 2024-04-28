package io.supernans.mateus.server;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringSetInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class InputHandler {
    ClientHandler clientHandler;
    private Prompt prompt;

    public InputHandler(ClientHandler clientHandler) throws IOException {
        this.clientHandler = clientHandler;
        prompt = new Prompt(clientHandler.getClientSocket().getInputStream(), new PrintStream(clientHandler.getClientSocket().getOutputStream(), true));
    }

    public String getDirection(Set<String> options) {
        String optionsString = String.join(", ", options);
        StringSetInputScanner scanner = new StringSetInputScanner(options);
        scanner.setMessage("You can currently go in the following directions: " + optionsString);
        scanner.setError("Invalid direction. Please choose one of the following: " + optionsString);
        return prompt.getUserInput(scanner);
    }
}
