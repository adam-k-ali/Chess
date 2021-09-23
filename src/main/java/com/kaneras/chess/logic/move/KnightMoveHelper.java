package com.kaneras.chess.logic.move;

/**
 * Helper class for moves of knights
 */
public class KnightMoveHelper extends MoveHelper {
    /**
     * Create a helper object for a move.
     * @param move The move to check
     */
    public KnightMoveHelper(Move move) {
        super(move);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public boolean isValidMove() {
        return (move.movedNVertical(2) && move.movedNHorizontal(1) || move.movedNVertical(1) && move.movedNHorizontal(2)) && !moveClashes();
    }
}
