package com.kaneras.chess.logic;

import com.kaneras.chess.Properties;
import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.util.HashMap;

public class Game {
    private static Canvas canvas;
    private static GridTile[][] grid = new GridTile[8][8];
    private static HashMap<Point, ChessPiece> pieces = new HashMap<>();

    public static void init() {
        canvas = new Canvas(Properties.MIN_WIDTH, Properties.MIN_HEIGHT);
        canvas.setFocusTraversable(true);

        setupGrid();
    }

    private static void setupGrid() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                grid[x][y] = new GridTile(x, y, y % 2 == 0 ? x % 2 == 0 : x % 2 == 1);
            }
        }
        for (int x = 0; x < 8; x++) {

        }
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    public static GridTile getTile(int x, int y) {
        return grid[x][y];
    }

}
