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

    public static void init() {
        canvas = new Canvas(Properties.DynamicProperties.canvasWidth, Properties.DynamicProperties.canvasHeight);
        canvas.setFocusTraversable(true);
        canvas.setOnMouseClicked(InputHandler::handleMouseClick);

        setupGrid();
    }

    private static void setupGrid() {
        // Draw Tiles
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                grid[x][y] = new GridTile(x, y, y % 2 == 0 ? x % 2 == 0 : x % 2 == 1);
            }
        }

        // Place the pieces in each row.
        // Black pieces at top of board.
        for (int y = 0; y < 8; y++) {
            if (y == 0 || y == 7) {
                grid[0][y].setChessPiece(new ChessPiece(0, y, ChessPiece.PieceType.ROOK, y == 7));
                grid[7][y].setChessPiece(new ChessPiece(7, y, ChessPiece.PieceType.ROOK, y == 7));

                grid[1][y].setChessPiece(new ChessPiece(1, y, ChessPiece.PieceType.KNIGHT, y == 7));
                grid[6][y].setChessPiece(new ChessPiece(6, y, ChessPiece.PieceType.KNIGHT, y == 7));

                grid[2][y].setChessPiece(new ChessPiece(2, y, ChessPiece.PieceType.BISHOP, y == 7));
                grid[5][y].setChessPiece(new ChessPiece(5, y, ChessPiece.PieceType.BISHOP, y == 7));
            } else if (y == 1 || y == 6) {
                for (int x = 0; x < 8; x++) {
                    grid[x][y].setChessPiece(new ChessPiece(x, y, ChessPiece.PieceType.PAWN, y == 6));
                }
            }
        }

        // Place king and queen pieces
        grid[3][0].setChessPiece(new ChessPiece(3, 0, ChessPiece.PieceType.QUEEN, false));
        grid[4][0].setChessPiece(new ChessPiece(4, 0, ChessPiece.PieceType.KING, false));
        grid[3][7].setChessPiece(new ChessPiece(3, 7, ChessPiece.PieceType.KING, true));
        grid[4][7].setChessPiece(new ChessPiece(4, 7, ChessPiece.PieceType.QUEEN, true));
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

    public static Canvas getCanvas() {
        return canvas;
    }

    public static GridTile getTile(int x, int y) {
        return grid[x][y];
    }

    public static int getTileSize() {
        return (int) (Game.getCanvas().getWidth() / 8);
    }


}
