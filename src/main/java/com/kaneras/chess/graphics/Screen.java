package com.kaneras.chess.graphics;

import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.GridTile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Screen {
    private static GraphicsContext graphics;

    public static void init() {
        Screen.graphics = Game.getCanvas().getGraphicsContext2D();

        drawGrid();
    }

    private static void drawGrid() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                drawGridTile(Game.getTile(x, y));
            }
        }
    }

    private static void drawGridTile(GridTile tile) {
        if (!tile.isWhiteBackground()) {
            graphics.setFill(Color.BLACK);
            graphics.fillRect(tile.getX() * getTileSize(), tile.getY() * getTileSize(), getTileSize(), getTileSize());
        }
    }

    private static int getTileSize() {
        return (int) (Game.getCanvas().getWidth() / 8);
    }

}
