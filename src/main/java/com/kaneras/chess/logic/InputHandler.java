package com.kaneras.chess.logic;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InputHandler {
    public static void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Game.selectTile((int) event.getX() / Game.getTileSize(), (int) event.getY() / Game.getTileSize());
        } else if (event.getButton() == MouseButton.SECONDARY) {
            Game.deselectTile();
        }
    }

    public static void handleMouseMove(MouseEvent event) {
        Game.moveHover((int) event.getX() / Game.getTileSize(), (int) event.getY() / Game.getTileSize());
    }
}
