package com.kaneras.chess.logic.move;

/**
 * Helper class for moves of queens
 */
public class QueenMoveHelper extends MoveHelper {
    /**
     * Create a helper object for a move.
     * @param move The move to check
     */
    public QueenMoveHelper(Move move) {
        super(move);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public MoveResult isValidMove() {
        if ((move.isMoveDiagonal() || move.isMoveHorizontal() || move.isMoveVertical()) && !isPathObstructed()) {
            return MoveResult.LEGAL;
        }
        return MoveResult.ILLEGAL;
    }

    /**
     * Check if the path the queen takes is obstructed by another piece
     * @return true if the path is obstructed; false otherwise.
     */
    @Override
    protected boolean isPathObstructed() {
        if (move.isMoveDiagonal()) {
            return diagonalObstruction();
        }

        if (move.isMoveHorizontal()) {
            return horizontalObstruction();
        } else if (move.isMoveVertical()) {
            return verticalObstruction();
        }

        return false;
    }
}
