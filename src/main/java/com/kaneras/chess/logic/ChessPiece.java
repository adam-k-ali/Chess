package com.kaneras.chess.logic;

import javafx.scene.image.Image;

public class ChessPiece {
    private int x, y;
    private final PieceType type;
    private final Image sprite;
    private final boolean isWhite;

    public ChessPiece(int x, int y, PieceType type, boolean isWhite) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.isWhite = isWhite;
        this.sprite = new Image(ChessPiece.class.getResourceAsStream("resources/" + type.toString().toLowerCase() + ".png"));
    }

    public Image getSprite() {
        return sprite;
    }

    public void move(int x, int y) {
        // TODO: Make sure move is legal.
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public enum PieceType {
        KING, ROOK, BISHOP, QUEEN, KNIGHT, PAWN;
    }
}
