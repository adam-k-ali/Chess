package com.kaneras.chess.logic;

import com.kaneras.chess.Properties;
import com.kaneras.chess.graphics.Screen;
import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.element.GridTile;
import com.kaneras.chess.logic.element.PieceType;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * The class that handles all the main logic throughout the chess game.
 */
public class Game {
    private static Canvas canvas;
    private static GridTile[][] grid = new GridTile[8][8];
    public static final Board MAIN_BOARD = new Board().setupDefaultBoard();
    private static int selectedX = -1;
    private static int selectedY = -1;

    private static int currHovX = -1;
    private static int currHovY = -1;

    private static Player currentPlayer;

    /**
     * Create canvas, setup grid and set the first player to be WHITE.
     */
    public static void init() {
        canvas = new Canvas(Properties.DynamicProperties.canvasWidth, Properties.DynamicProperties.canvasHeight);
        canvas.setFocusTraversable(true);
        canvas.setOnMouseClicked(InputHandler::handleMouseClick);
        canvas.setOnMouseMoved(InputHandler::handleMouseMove);

        currentPlayer = Player.WHITE;

        setupGrid();
    }

    /**
     * Change the tile that's hovered over with the mouse.
     * @param x The hovered tile's x position
     * @param y The hovered tile's y position.
     */
    public static void moveHover(int x, int y) {
        if (currHovX != x || currHovY != y) {
            currHovX = x;
            currHovY = y;
            Screen.refresh();
        }
    }

    /**
     * Check if a specific tile is hovered over by the mouse.
     * @param x The tile's x position
     * @param y The tile's y position
     * @return true if the mouse is over the tile; false otherwise.
     */
    public static boolean isHoveredOver(int x, int y) {
        return currHovX == x && currHovY == y;
    }

    /**
     * Get the tile that the mouse is hovering over.
     * @return A GridTile object of the tile being hovered over; null if the mouse is not over a tile.
     */
    public static GridTile getHoveredTile() {
        if (currHovX >= 0 && currHovX <= 7 && currHovY >= 0 && currHovY <= 7) {
            return Game.getTile(currHovX, currHovY);
        } else {
            return null;
        }
    }

    /**
     * Setup the grid with all the GridTiles and place all the pieces in their starting position.
     */
    private static void setupGrid() {
        // Draw Tiles
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (y % 2 == 0) {
                    grid[x][y] = new GridTile(x, y, x % 2 == 0 ? Player.WHITE : Player.BLACK);
                } else {
                    grid[x][y] = new GridTile(x, y, x % 2 == 0 ? Player.BLACK : Player.WHITE);
                }
            }
        }

    }


    /**
     * Selects a game tile
     * Can't select a tile with no game piece on it or if it is a piece belonging to the other player.
     * @param x The x position of the tile to select
     * @param y The y position of the tile to select
     */
    public static void selectTile(int x, int y) {
        if (MAIN_BOARD.getPiece(x, y) == null || MAIN_BOARD.getPiece(x, y).getOwner() != currentPlayer)
            return;
        selectedX = x;
        selectedY = y;
        Screen.refresh();
    }

    /**
     * Deselect the tile that is selected.
     */
    public static void deselectTile() {
        selectedX = -1;
        selectedY = -1;
        Screen.refresh();
    }

    /**
     * Check if a given tile is selected.
     * @param x The tile's x position
     * @param y The tile's y position
     * @return true if the given tile is selected; false otherwise.
     */
    public static boolean isSelected(int x, int y) {
        if (selectedX == -1 || selectedY == -1)
            return false;
        return selectedX == x && selectedY == y;
    }

    /**
     * Get the tile that's selected
     * @return A GridTile object of the selected tile.
     */
    public static GridTile getSelectedTile() {
        if (selectedX == -1 || selectedY == -1)
            return null;
        return Game.getTile(selectedX, selectedY);
    }

    /**
     * Get the canvas to draw to
     * @return Canvas to draw to
     */
    public static Canvas getCanvas() {
        return canvas;
    }

    /**
     * Get a tile at a certain position
     * @param x The tile's x position
     * @param y The tile's y position
     * @return A GridTile object that's at position (x,y)  on the grid.
     */
    public static GridTile getTile(int x, int y) {
        return grid[x][y];
    }

    /**
     * Get the appropriate tile size given the canvas width.
     * @return The size of any tile.
     */
    public static int getTileSize() {
        return (int) (Game.getCanvas().getWidth() / 8);
    }

    /**
     * Switch current player.
     */
    public static void toggleCurrentPlayer() {
        Game.currentPlayer = Game.currentPlayer.other();
        Screen.refresh();
    }

    /**
     * Getter for the current player
     * @return The current player; WHITE or BLACK.
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

//    public static ChessPiece getSelectedPiece() {
//        return MAIN_BOARD.getPiece(selectedX, selectedY);
//    }

    public enum Player {
        WHITE, BLACK;

        public Player other() {
            return this == WHITE ? BLACK : WHITE;
        }
    }
}
