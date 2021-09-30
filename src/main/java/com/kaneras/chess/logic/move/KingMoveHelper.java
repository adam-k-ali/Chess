package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.Board;
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
    public KingMoveHelper(Move move, Board board) {
        super(move, board);
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    @Override
    public MoveResult isValidMove() {
        if (move.getDistanceMoved() > 1) {
            return canCastle();
        }
        return canMoveRegular();
    }

    private MoveResult canMoveRegular() {
        if (moveIntoCheck())
            return MoveResult.ILLEGAL;
        return (move.getDistanceMoved() == 1 && oppositeTeams()) ? MoveResult.LEGAL : MoveResult.ILLEGAL;
    }

    public MoveResult canCastle() {
        ChessPiece king = board.getPiece(move.getStartX(), move.getStartY());
        if (king.getLastMove() != null || moveIntoCheck() || moveThroughCheck() || king.isChecked()) {
            return MoveResult.ILLEGAL;
        }

        ChessPiece rook = board.getPiece(move.getStartX() > move.getDestX() ? 0 : 7, move.getStartY());
        if (rook == null || rook.getType() != PieceType.ROOK || rook.getLastMove() != null) {
            return MoveResult.ILLEGAL;
        }

        if (move.getHorizontalDistance() == 2 && move.isMoveHorizontal() && !castleObstruction(king, rook))
            return MoveResult.CASTLING;

        return MoveResult.ILLEGAL;
    }

    private boolean castleObstruction(ChessPiece king, ChessPiece rook) {
        if (moveClashes())
            return true;
        int dx = king.getCurrX() > rook.getCurrX() ? -1 : 1;
        for (int i = 1; i < Math.abs(king.getCurrX() - rook.getCurrX()); i++) {
            if (tileOccupied(king.getCurrX() + i * dx, king.getCurrY())) {
                return true;
            }
        }
        return false;
    }

    private boolean moveIntoCheck() {
        return pointGivesCheck(move.getDestX(), move.getDestY());
    }

    private boolean moveThroughCheck() {
        return pointGivesCheck((move.getStartX() + move.getDestX()) / 2, move.getDestY());
    }

    private boolean pointGivesCheck(int x, int y) {
        ChessPiece king = board.getPiece(move.getStartX(), move.getStartY());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                // Ignore the start position of the move of the king we're checking for.
                if (i == move.getStartX() && j == move.getStartY())
                    continue;

                ChessPiece piece = board.getPiece(i, j);
                if (piece == null)
                    continue;

                if (piece.getOwner() == king.getOwner())
                    continue;

                Move testMove = new Move(board, piece.getCurrX(), piece.getCurrY(), x, y);
                if (MoveHandler.validateMove(testMove, board) != MoveResult.ILLEGAL) {
                    return true;
                }
            }
        }
        return false;
    }

}
