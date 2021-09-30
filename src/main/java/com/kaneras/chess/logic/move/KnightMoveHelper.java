package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.Board;

/**
 * Helper class for moves of knights
 */
public class KnightMoveHelper extends MoveHelper {
    /**
     * Create a helper object for a move.
     * @param move The move to check
     */
    public KnightMoveHelper(Move move, Board board) {
        super(move, board);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public MoveResult isValidMove() {
        if ((move.movedNVertical(2) && move.movedNHorizontal(1) || move.movedNVertical(1) && move.movedNHorizontal(2)) && !moveClashes() && !kingBecomesChecked()) {
            return MoveResult.LEGAL;
        }
        return MoveResult.ILLEGAL;
    }
}
