package com.kaneras.chess.logic;

import com.kaneras.chess.logic.move.Move;
import com.kaneras.chess.logic.move.MoveHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * A class to handle all inputs
 */
public class InputHandler {

    /**
     * Handle when the mouse is clicked (select or move a piece)
     * @param event The MouseEvent
     */
    public static void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            int x = (int) event.getX() / Game.getTileSize();
            int y = (int) event.getY() / Game.getTileSize();
            MoveHandler moveHandler = new MoveHandler(Game.MAIN_BOARD);
            if (Game.getSelectedTile() != null && moveHandler.canTileBeReoccupied(x, y)) {
                moveHandler.performMove(new Move(Game.MAIN_BOARD, Game.getSelectedTile().getX(), Game.getSelectedTile().getY(), x, y));
//                Game.getSelectedPiece().move(x, y);
            } else {
                Game.selectTile(x, y);
            }
        } else if (event.getButton() == MouseButton.SECONDARY) {
            Game.deselectTile();
        }
    }

    /**
     * Handle when the mouse is moved (update hovered tile)
     * @param event The MouseEvent
     */
    public static void handleMouseMove(MouseEvent event) {
        Game.moveHover((int) event.getX() / Game.getTileSize(), (int) event.getY() / Game.getTileSize());
    }

}
