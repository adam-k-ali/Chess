package com.kaneras.chess.logic;

import com.kaneras.chess.Properties;
import com.kaneras.chess.graphics.Screen;
import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.util.HashMap;

public class Game {
    private static Canvas canvas;
    private static GridTile[][] grid = new GridTile[8][8];
    private static HashMap<Point, ChessPiece> pieces = new HashMap<>();
    private static int selectedX = -1;
    private static int selectedY = -1;

    private static int currHovX = -1;
    private static int currHovY = -1;

    private static Player player;

    public static void init() {
        canvas = new Canvas(Properties.DynamicProperties.canvasWidth, Properties.DynamicProperties.canvasHeight);
        canvas.setFocusTraversable(true);
        canvas.setOnMouseClicked(InputHandler::handleMouseClick);
        canvas.setOnMouseMoved(InputHandler::handleMouseMove);

        setupGrid();
    }

    public static void moveHover(int x, int y) {
        if (currHovX != x || currHovY != y) {
            currHovX = x;
            currHovY = y;
            Screen.refresh();
        }
    }

    public static boolean isHoveredOver(int x, int y) {
        return currHovX == x && currHovY == y;
    }

    public static GridTile getHoveredTile() {
        if (currHovX >= 0 && currHovX <= 7 && currHovY >= 0 && currHovY <= 7) {
            return Game.getTile(currHovX, currHovY);
        } else {
            return null;
        }
    }

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

        // Place the pieces in each row.
        // Black pieces at top of board.
        for (int y = 0; y < 8; y++) {
            if (y == 0 || y == 7) {
                Player owner = y == 7 ? Player.WHITE : Player.BLACK;
                grid[0][y].setChessPiece(new ChessPiece(ChessPiece.PieceType.ROOK, owner));
                grid[7][y].setChessPiece(new ChessPiece(ChessPiece.PieceType.ROOK, owner));

                grid[1][y].setChessPiece(new ChessPiece(ChessPiece.PieceType.KNIGHT, owner));
                grid[6][y].setChessPiece(new ChessPiece(ChessPiece.PieceType.KNIGHT, owner));

                grid[2][y].setChessPiece(new ChessPiece(ChessPiece.PieceType.BISHOP, owner));
                grid[5][y].setChessPiece(new ChessPiece(ChessPiece.PieceType.BISHOP, owner));
            } else if (y == 1 || y == 6) {
                Player owner = y == 6 ? Player.WHITE : Player.BLACK;
                for (int x = 0; x < 8; x++) {
                    grid[x][y].setChessPiece(new ChessPiece(ChessPiece.PieceType.PAWN, owner));
                }
            }
        }

        // Place king and queen pieces
        grid[3][0].setChessPiece(new ChessPiece(ChessPiece.PieceType.QUEEN, Player.BLACK));
        grid[4][0].setChessPiece(new ChessPiece(ChessPiece.PieceType.KING, Player.BLACK));
        grid[3][7].setChessPiece(new ChessPiece(ChessPiece.PieceType.KING, Player.WHITE));
        grid[4][7].setChessPiece(new ChessPiece(ChessPiece.PieceType.QUEEN, Player.WHITE));
    }

    /**
     * Selects a game tile
     * Can't select a tile with no game piece on it.
     * @param x The x position of the tile to select
     * @param y The y position of the tile to select
     */
    public static void selectTile(int x, int y) {
        if (getTile(x, y).getPiece() == null)
            return;
        selectedX = x;
        selectedY = y;
        System.out.println(getTile(x,y).getPiece().getType());
        Screen.refresh();
    }

    public static void deselectTile() {
        selectedX = -1;
        selectedY = -1;
        Screen.refresh();
    }

    public static boolean isSelected(int x, int y) {
        if (selectedX == -1 || selectedY == -1)
            return false;
        return selectedX == x && selectedY == y;
    }

    public static GridTile getSelectedTile() {
        if (selectedX == -1 || selectedY == -1)
            return null;
        return Game.getTile(selectedX, selectedY);
    }

    /**
     * Move the selected game piece to a new legal destination
     * @param x Destination tile x position
     * @param y Destination tile y position
     */
    public static void moveSelectedPiece(int x, int y) {

    }

    public static Canvas getCanvas() {
        return canvas;
    }

    public static GridTile getTile(int x, int y) {
        return grid[x][y];
    }

    public static int getTileSize() {
        return (int) (Game.getCanvas().getWidth() / 8);
    }

    public static void setPlayer(Player player) {
        Game.player = player;
    }

    public static Player getPlayer() {
        return player;
    }

    public enum Player {
        WHITE, BLACK;
    }
}
