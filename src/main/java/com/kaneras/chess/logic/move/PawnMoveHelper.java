package com.kaneras.chess.logic.move;

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
    public PawnMoveHelper(Move move) {
        super(move);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public boolean isValidMove() {
        // On the first move, a pawn can move 2 spaces forward.
        if (isAtStart() && move.movedNForward(2) && move.getHorizontalDistance() == 0) {
            return true;
        }

        // Take the opposite team's piece.
        if (move.isMoveDiagonal() && move.getDistanceMoved() == 1) {
            ChessPiece enPassant = checkEnPassant(move);
            if (enPassant != null && enPassant.getCurrX() == move.getDestX()) {
                return true;
            }
            return (oppositeTeams() && Game.getPiece(move.getDestX(), move.getDestY()) != null);
        }

        // Checks for if the pawn has moved 1 space forward.
        if (move.movedNForward(1) && move.getHorizontalDistance() == 0) {
            // Team that moved can take piece if there is one of getTile(x, y).getPiece() the opposite team.
            if (move.isMoveDiagonal() || moveClashes()) {
                return false;
            }

            return Game.getPiece(move.getDestX(), move.getDestY()) == null;
        }

        return false;
    }

    /**
     * Checks if the pawn is in its starting position
     * @return true if the pawn is at the start; false otherwise
     */
    private boolean isAtStart() {
        return Game.getPiece(move.getStartX(), move.getStartY()).isAtStart();
    }

}
