package com.kaneras.chess.logic;

public class GridTile {
    // true for white background, false for black background
    private final boolean isWhiteBackground;
    private final int x, y;
    private ChessPiece piece;

    public GridTile(int x, int y, boolean isWhiteBackground) {
        this.x = x;
        this.y = y;
        this.isWhiteBackground = isWhiteBackground;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setChessPiece(ChessPiece piece) {
        if (this.piece != null && piece != null) {
            throw new IllegalArgumentException("There's already a chess piece here.");
        }
        this.piece = piece;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public boolean isWhiteBackground() {
        return isWhiteBackground;
    }
}
