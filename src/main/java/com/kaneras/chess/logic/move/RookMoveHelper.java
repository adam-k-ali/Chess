package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.Board;

/**
 * Helper class for moves of rooks
 */
public class RookMoveHelper extends MoveHelper {
    /**
     * Create a helper object for a move.
     * @param move The move to check
     */
    public RookMoveHelper(Move move, Board board) {
        super(move, board);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public MoveResult isValidMove() {
        return ((move.isMoveVertical() || move.isMoveHorizontal()) && !isPathObstructed() && !kingBecomesChecked()) ? MoveResult.LEGAL : MoveResult.ILLEGAL;
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
