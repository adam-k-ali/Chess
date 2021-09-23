package com.kaneras.chess.logic.move;

/**
 * Helper class for moves of rooks
 */
public class RookMoveHelper extends MoveHelper {
    /**
     * Create a helper object for a move.
     * @param move The move to check
     */
    public RookMoveHelper(Move move) {
        super(move);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public boolean isValidMove() {
        return (move.isMoveVertical() || move.isMoveHorizontal()) && !isPathObstructed();
    }

    /**
     * Check if the path the rook takes is obstructed by another piece
     * @return true if the path is obstructed; false otherwise.
     */
    @Override
    protected boolean isPathObstructed() {
        if (move.isMoveHorizontal()) {
            return horizontalObstruction();
        } else if (move.isMoveVertical()) {
            return verticalObstruction();
        }

        return false;
    }
}
