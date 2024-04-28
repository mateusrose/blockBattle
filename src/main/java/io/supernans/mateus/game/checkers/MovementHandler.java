package io.supernans.mateus.game.checkers;

import io.supernans.mateus.game.entities.Player;
import io.supernans.mateus.game.game.Grid;

import java.util.HashSet;
import java.util.Set;

public class MovementHandler {

    public static HashSet<String> getValidDirections(Player player, Grid grid) {
        HashSet<String> validDirections = new HashSet<>();
        int row = player.getPos()[1];
        int col = player.getPos()[0];
        int height = grid.getHeight();
        int width = Grid.WIDTH;

        if (row > 0) {
            validDirections.add("up");
        }
        if (row < height - 1) {
            validDirections.add("down");
        }
        if (col > 0) {
            validDirections.add("left");
        }
        if (col < width - 1) {
            validDirections.add("right");
        }

        return validDirections;
    }
}
