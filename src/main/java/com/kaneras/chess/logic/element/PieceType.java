package com.kaneras.chess.logic.element;

/**
 * All possible chess piece types
 */
public enum PieceType {
    QUEEN(900),
    KING(90),
    ROOK(50),
    BISHOP(30),
    KNIGHT(30),
    PAWN(10);

    int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
