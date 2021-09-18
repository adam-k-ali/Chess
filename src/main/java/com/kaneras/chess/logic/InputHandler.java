package com.kaneras.chess.logic;

import com.kaneras.chess.graphics.Screen;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InputHandler {
    public static void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            int x = (int) event.getX() / Game.getTileSize();
            int y = (int) event.getY() / Game.getTileSize();
            if (Game.getSelectedTile() != null && Game.getTile(x, y).getPiece() == null){
                if (Game.validateMove(Game.getSelectedTile().getX(), Game.getSelectedTile().getY(), x, y)) {
                    ChessPiece piece = Game.getSelectedTile().getPiece();
                    Game.getSelectedTile().setChessPiece(null);
                    Game.getTile(x, y).setChessPiece(piece);
                    Game.deselectTile();
                    Screen.refresh();
                }
            } else {
                Game.selectTile(x, y);
            }

        } else if (event.getButton() == MouseButton.SECONDARY) {
            Game.deselectTile();
        }
    }

    public static void handleMouseMove(MouseEvent event) {
        Game.moveHover((int) event.getX() / Game.getTileSize(), (int) event.getY() / Game.getTileSize());
    }
}
