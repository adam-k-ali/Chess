package com.kaneras.chess.graphics;

import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.element.GridTile;
import com.kaneras.chess.logic.element.PieceType;
import com.kaneras.chess.logic.move.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Handle all basic drawing to the screen.
 */
public class Screen {
    // The graphics context of the canvas
    private static GraphicsContext graphics;

    /**
     * Fetch the graphics context for the canvas.
     * Setup the screen with a basic grid.
     */
    public static void init() {
        Screen.graphics = Game.getCanvas().getGraphicsContext2D();

        drawGrid();
    }

    /**
     * To be called when there's an update to what needs to be drawn to the screen.
     * Clear the screen and redraw the grid.
     */
    public static void refresh() {
        graphics.clearRect(0, 0, Game.getCanvas().getWidth(), Game.getCanvas().getHeight());
        drawGrid();
    }

    /**
     * Draw all the grid tiles on the grid
     */
    private static void drawGrid() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                drawGridTile(Game.getTile(x, y));
            }
        }
    }

    /**
     * Draws an individual grid tile and its game piece sprite.
     * @param tile The tile to draw
     */
    private static void drawGridTile(GridTile tile) {
        // The actual x and y position where the tile starts on screen
        int px = tile.getX() * Game.getTileSize();
        int py = tile.getY() * Game.getTileSize();

        // Set the tile background to black if the tile should be black.
        if (tile.getBias() == Game.Player.BLACK) {
            graphics.setFill(Color.color(0.4, 0.25, 0.25));
            graphics.fillRect(px, py, Game.getTileSize(), Game.getTileSize());
        }

        ChessPiece piece = Game.MAIN_BOARD.getPiece(tile.getX(), tile.getY());
        // If the tile has a piece on it, draw its sprite.
        if (piece != null) {
            drawPieceSprite(piece, px, py);
        }

        drawHighlight(tile, px, py);

        if (piece != null && piece.getType() == PieceType.KING && piece.isChecked()) {
            graphics.setFill(Color.BLUE);
            graphics.fillRect(px, py, Game.getTileSize(), 5);
            graphics.fillRect(px, py, 5, Game.getTileSize());
            graphics.fillRect(px + Game.getTileSize() - 5, py, 5, Game.getTileSize());
            graphics.fillRect(px, py + Game.getTileSize() - 5, Game.getTileSize(), 5);
        }
    }

    /**
     * Draw the sprite of a game piece.
     * The pieces are made slightly transparent when their owner is not currently making a move.
     * @param piece The chess piece to draw
     * @param px The absolute x position of the tile on screen.
     * @param py The absolute y position of the tile on screen.
     */
    private static void drawPieceSprite(ChessPiece piece, int px, int py) {
        if (Game.getCurrentPlayer() != piece.getOwner()) {
            graphics.setGlobalAlpha(0.8);
        }
        ImageHelper.drawImage(graphics, piece.getSprite(), px, py, Game.getTileSize(), Game.getTileSize());

        if (Game.getCurrentPlayer() != piece.getOwner()) {
            graphics.setGlobalAlpha(1.0);
        }
    }

    /**
     * Handle highlighting tiles when they're selected or hovered over.
     * @param tile The tile being drawn
     * @param px The absolute x position of the tile on screen.
     * @param py The absolute y position of the tile on screen.
     */
    private static void drawHighlight(GridTile tile, int px, int py) {
        if (Game.isSelected(tile.getX(), tile.getY())) {

            // The selected tile should be highlighted blue
            graphics.setFill(Color.BLUE);
            graphics.setGlobalAlpha(0.2);
            graphics.fillRect(px, py, Game.getTileSize(), Game.getTileSize());
            graphics.setGlobalAlpha(1.0);

        } else if (Game.isHoveredOver(tile.getX(), tile.getY()) && Game.getSelectedTile() != null) {


            int startX = Game.getSelectedTile().getX();
            int startY = Game.getSelectedTile().getY();
            int finishX = Game.getHoveredTile().getX();
            int finishY = Game.getHoveredTile().getY();

            if (MoveHandler.validateMove(new Move(Game.MAIN_BOARD, startX, startY, finishX, finishY), Game.MAIN_BOARD) != MoveResult.ILLEGAL) {
                // If the player can move the selected piece to the tile, it should be highlighted green.
                graphics.setFill(Color.GREEN);
            } else {
                // If the player can't move the selected piece to the tile, it should be highlighted red.
                graphics.setFill(Color.RED);
            }

            graphics.setGlobalAlpha(0.2);
            graphics.fillRect(px, py, Game.getTileSize(), Game.getTileSize());
            graphics.setGlobalAlpha(1.0);
        }
    }
}
