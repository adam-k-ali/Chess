package com.kaneras.chess.logic.move;

import com.kaneras.chess.graphics.stages.AlertBox;
import com.kaneras.chess.graphics.stages.PawnPromotionOptionBox;
import com.kaneras.chess.graphics.Screen;
import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.GridTile;

import java.awt.*;

/**
 * Handles all moves, allows them to be validated and executed.
 * Also performs a check for win each move.
 */
public class MoveHandler {
    /**
     * Check if a move from a start tile to a finish tile is legal
     * @param startX The start tile's x position
     * @param startY The start tile's y position
     * @param finishX The finish tile's x position
     * @param finishY The finish tile's y position
     * @return true if the move is legal; false otherwise
     */
    public static boolean validateMove(int startX, int startY, int finishX, int finishY) {
        GridTile startTile = Game.getTile(startX, startY);

        if (startTile.getPiece() == null) {
            return false;
        }

        MoveHelper moveHelper = createMoveHelper(new Move(startX, startY, finishX, finishY));
        if (moveHelper != null) {
            return moveHelper.isValidMove();
        }

        return false;
    }

    /**
     * Create a move helper object to assist in validating moves
     * @param move The move to be checked
     * @return the move helper object
     */
    public static MoveHelper createMoveHelper(Move move) {
        switch (move.getStartTile().getPiece().getType()) {
            case KING:
                return new KingMoveHelper(move);
            case QUEEN:
                return new QueenMoveHelper(move);
            case BISHOP:
                return new BishopMoveHelper(move);
            case PAWN:
                return new PawnMoveHelper(move);
            case ROOK:
                return new RookMoveHelper(move);
            case KNIGHT:
                return new KnightMoveHelper(move);
            default:
                return null;
        }
    }

    /**
     * Move the selected game piece to a new legal destination
     * @param move The move to be performed
     */
    public static void performMove(Move move) {
        if (Game.getSelectedTile() == null)
            return;

        if (!validateMove(move.getStartX(), move.getStartY(), move.getDestX(), move.getDestY()))
            return;

        ChessPiece piece = Game.getSelectedTile().getPiece();
        Game.getSelectedTile().setChessPiece(null);
        if (move.getStartTile().getPiece() != null && move.getDestTile().getPiece().getType() == ChessPiece.PieceType.KING) {
            // win
            AlertBox.showAlert("Game Over", "Checkmate by " + Game.getCurrentPlayer());
            return;
        }
        move.getDestTile().setChessPiece(piece);

        if (Game.getCurrentPlayer() == Game.Player.BLACK) {
            Point whiteKing = getWhiteKing();
            if (whiteKing != null && createMoveHelper(new Move(move.getDestX(), move.getDestY(), whiteKing.x, whiteKing.y)).isValidMove()) {
                AlertBox.showAlert("Alert", "Black player calls \"Check!\"");
            }
        } else if (Game.getCurrentPlayer() == Game.Player.WHITE) {
            Point blackKing = getBlackKing();
            if (blackKing != null && createMoveHelper(new Move(move.getDestX(), move.getDestY(), blackKing.x, blackKing.y)).isValidMove()) {
                AlertBox.showAlert("Alert", "White player calls \"Check!\"");
            }
        }

        if (piece.getType() == ChessPiece.PieceType.PAWN && move.getDestY() % 7 == 0) {
            move.getDestTile().setChessPiece(new ChessPiece(PawnPromotionOptionBox.chooseOption(), Game.getCurrentPlayer()));
        }

        Game.deselectTile();
        Game.toggleCurrentPlayer();
        Screen.refresh();
    }

    /**
     * Get the position of the black king
     * @return A point (x, y) position of the black king; null if there is no black king.
     */
    private static Point getBlackKing() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                ChessPiece piece = Game.getTile(x, y).getPiece();
                if (piece != null && piece.getOwner() == Game.Player.BLACK && piece.getType() == ChessPiece.PieceType.KING) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Get the position of the white king
     * @return A point (x, y) position of the white king; null if there is no white king.
     */
    private static Point getWhiteKing() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                ChessPiece piece = Game.getTile(x, y).getPiece();
                if (piece != null && piece.getOwner() == Game.Player.WHITE && piece.getType() == ChessPiece.PieceType.KING) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Check if a piece can be moved to a position; the new position is either empty or the player can 'take' the piece.
     * @param x The new x position
     * @param y The new y position
     * @return true if the piece can move here; false otherwise.
     */
    public static boolean canTileBeReoccupied(int x, int y) {
        return Game.getTile(x, y).getPiece() == null || Game.getTile(x, y).getPiece().getOwner() != Game.getCurrentPlayer();
    }
}
