package com.kaneras.chess.logic.move;

/**
 * Helper class for moves of bishops
 */
public class BishopMoveHelper extends MoveHelper {

    /**
     * Create a helper object for a move.
     * @param move The move to check
     */
    public BishopMoveHelper(Move move) {
        super(move);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public boolean isValidMove() {
        return move.isMoveDiagonal() && !isPathObstructed();
    }

    /**
     * Check if the path the bishop takes is obstructed by another piece
     * @return true if the path is obstructed; false otherwise.
     */
    @Override
    protected boolean isPathObstructed() {
        return diagonalObstruction();
    }
}
