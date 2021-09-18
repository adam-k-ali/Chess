package com.kaneras.chess.logic;

import com.kaneras.chess.graphics.Screen;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InputHandler {
    public static void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            int x = (int) event.getX() / Game.getTileSize();
            int y = (int) event.getY() / Game.getTileSize();
            if (Game.getSelectedTile() != null && canTileBeReoccupied(x, y)){
                Game.moveSelectedPiece(x, y);
            } else {
                Game.selectTile(x, y);
            }
        } else if (event.getButton() == MouseButton.SECONDARY) {
            Game.deselectTile();
        }
    }

    private static boolean canTileBeReoccupied(int x, int y) {
        return Game.getTile(x, y).getPiece() == null || Game.getTile(x, y).getPiece().getOwner() != Game.getCurrentPlayer();
    }


    public static void handleMouseMove(MouseEvent event) {
        Game.moveHover((int) event.getX() / Game.getTileSize(), (int) event.getY() / Game.getTileSize());
    }
}
