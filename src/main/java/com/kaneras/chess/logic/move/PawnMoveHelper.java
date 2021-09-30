package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.Board;
import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.ChessPiece;

/**
 * Helper class for moves of pawns
 */
public class PawnMoveHelper extends MoveHelper {
    /**
     * Create a helper object for a move.
     * @param move The move to check
     */
    public PawnMoveHelper(Move move, Board board) {
        super(move, board);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public MoveResult isValidMove() {
        if (kingBecomesChecked()) {
            return MoveResult.ILLEGAL;
        }
        // On the first move, a pawn can move 2 spaces forward.
        if (isAtStart() && move.movedNForward(2) && move.getHorizontalDistance() == 0) {
            return MoveResult.LEGAL;
        }

        // Take the opposite team's piece.
        if (move.isMoveDiagonal() && move.getDistanceMoved() == 1 && move.movedNForward(1) && !move.movedNHorizontal(0)) {
            ChessPiece enPassant = checkEnPassant(move);
            if (enPassant != null && enPassant.getCurrX() == move.getDestX()) {
                return MoveResult.EN_PASSANT;
            }
            return (oppositeTeams() && board.getPiece(move.getDestX(), move.getDestY()) != null) ? MoveResult.LEGAL : MoveResult.ILLEGAL;
        }

        // Checks for if the pawn has moved 1 space forward.
        if (move.movedNForward(1) && move.getHorizontalDistance() == 0) {
            // Team that moved can take piece if there is one of getTile(x, y).getPiece() the opposite team.
            if (move.isMoveDiagonal() || moveClashes()) {
                return MoveResult.ILLEGAL;
            }

            return (board.getPiece(move.getDestX(), move.getDestY()) == null) ? MoveResult.LEGAL : MoveResult.ILLEGAL;
        }

        return MoveResult.ILLEGAL;
    }

    /**
     * Checks if the pawn is in its starting position
     * @return true if the pawn is at the start; false otherwise
     */
    private boolean isAtStart() {
        return board.getPiece(move.getStartX(), move.getStartY()).isAtStart();
    }

}
