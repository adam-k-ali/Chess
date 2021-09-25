package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.element.PieceType;

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
        return (move.getDistanceMoved() == 1 && oppositeTeams()) || canCastle();
    }

    private boolean canCastle() {
        ChessPiece king = Game.getPiece(move.getStartX(), move.getStartY());
        if (king.getLastMove() != null || moveIntoCheck() || moveThroughCheck() || king.isChecked()) {
            return false;
        }

        ChessPiece rook = Game.getPiece(move.getStartX() > move.getDestX() ? 0 : 7, move.getStartY());
        if (rook == null || rook.getType() != PieceType.ROOK || rook.getLastMove() != null) {
            return false;
        }

        return move.getHorizontalDistance() == 2 && move.isMoveHorizontal() && !horizontalObstruction();
    }

    private boolean moveIntoCheck() {
        return pointGivesCheck(move.getDestX(), move.getDestY());
    }

    private boolean moveThroughCheck() {
        return pointGivesCheck((move.getStartX() + move.getDestX()) / 2, move.getDestY());
    }

    private boolean pointGivesCheck(int x, int y) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                // Ignore the start position of the move of the king we're checking for.
                if (i == move.getStartX() && j == move.getStartY())
                    continue;

                ChessPiece piece = Game.getPiece(i, j);
                if (piece == null)
                    continue;

                Move testMove = new Move(piece.getCurrX(), piece.getCurrY(), x, y);
                if (MoveHandler.validateMove(testMove)) {
                    return true;
                }
            }
        }
        return false;
    }

}
