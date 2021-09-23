package com.kaneras.chess.logic.move;

/**
 * Helper class for moves of kings
 */
public class KingMoveHelper extends MoveHelper {
    /**
     * Create a helper object for a move.
     * @param move The move to check
     */
    public KingMoveHelper(Move move) {
        super(move);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public boolean isValidMove() {
        return move.getDistanceMoved() == 1 && oppositeTeams();
    }
}
