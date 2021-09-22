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

        MoveHelper moveHelper = createMoveHelper(Game.getTile(startX, startY));
        if (moveHelper != null) {
            return moveHelper.isValidMove(finishX, finishY);
        }

        return false;
    }

    /**
     * Create a move helper object to assist in validating moves
     * @param tile The start tile of the move
     * @return the move helper object
     */
    public static MoveHelper createMoveHelper(GridTile tile) {
        switch (tile.getPiece().getType()) {
            case KING:
                return new KingMoveHelper(tile.getX(), tile.getY());
            case QUEEN:
                return new QueenMoveHelper(tile.getX(), tile.getY());
            case BISHOP:
                return new BishopMoveHelper(tile.getX(), tile.getY());
            case PAWN:
                return new PawnMoveHelper(tile.getX(), tile.getY());
            case ROOK:
                return new RookMoveHelper(tile.getX(), tile.getY());
            case KNIGHT:
                return new KnightMoveHelper(tile.getX(), tile.getY());
            default:
                return null;
        }
    }

    /**
     * Move the selected game piece to a new legal destination
     * @param destX Destination tile x position
     * @param destY Destination tile y position
     */
    public static void moveSelectedPiece(int destX, int destY) {
        if (Game.getSelectedTile() == null)
            return;

        if (!validateMove(Game.getSelectedTile().getX(), Game.getSelectedTile().getY(), destX, destY))
            return;

        ChessPiece piece = Game.getSelectedTile().getPiece();
        Game.getSelectedTile().setChessPiece(null);
        if (Game.getTile(destX, destY).getPiece() != null && Game.getTile(destX, destY).getPiece().getType() == ChessPiece.PieceType.KING) {
            // win
            AlertBox.showAlert("Game Over", "Checkmate by " + Game.getCurrentPlayer());
            return;
        }
        Game.getTile(destX, destY).setChessPiece(piece);

        if (Game.getCurrentPlayer() == Game.Player.BLACK) {
            Point whiteKing = getWhiteKing();
            if (whiteKing != null && createMoveHelper(Game.getTile(destX, destY)).isValidMove(whiteKing.x, whiteKing.y)) {
                AlertBox.showAlert("Alert", "Black player calls \"Check!\"");
            }
        } else if (Game.getCurrentPlayer() == Game.Player.WHITE) {
            Point blackKing = getBlackKing();
            if (blackKing != null && createMoveHelper(Game.getTile(destX, destY)).isValidMove(blackKing.x, blackKing.y)) {
                AlertBox.showAlert("Alert", "White player calls \"Check!\"");
            }
        }

        if (piece.getType() == ChessPiece.PieceType.PAWN && destY % 7 == 0) {
            Game.getTile(destX, destY).setChessPiece(new ChessPiece(PawnPromotionOptionBox.chooseOption(), Game.getCurrentPlayer()));
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
