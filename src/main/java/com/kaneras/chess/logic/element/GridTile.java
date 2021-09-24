package com.kaneras.chess.logic.element;

import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.ChessPiece;

/**
 * Contains the properties for a tile on the grid
 */
public class GridTile {
    // The background of the tile (WHITE or BLACK)
    private final Game.Player bias;
    // The x and y position of the tile in the grid
    private final int x;
    private final int y;

    /**
     * Create a new grid tile
     * @param x The x position of the tile on the grid
     * @param y The y position of the tile on the grid
     * @param bias The background of the tile (WHITE or BLACK)
     */
    public GridTile(int x, int y, Game.Player bias) {
        this.x = x;
        this.y = y;
        this.bias = bias;
    }

    /**
     * Get the x position of the tile on the grid
     * @return The x position of the tile on the grid
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y position of the tile on the grid
     * @return The y position of the tile on the grid
     */
    public int getY() {
        return y;
    }

    /**
     * Get the background colour of the tile
     * @return WHITE, BLACK
     */
    public Game.Player getBias() {
        return bias;
    }
}
