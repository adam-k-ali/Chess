package com.kaneras.chess.logic;

public class GridTile {
    // true for white background, false for black background
    private final Game.Player bias;
    private final int x, y;
    private ChessPiece piece;

    public GridTile(int x, int y, Game.Player bias) {
        this.x = x;
        this.y = y;
        this.bias = bias;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setChessPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    /**
     * Get the background colour of the tile
     * @return WHITE, BLACK
     */
    public Game.Player getBias() {
        return bias;
    }
}
