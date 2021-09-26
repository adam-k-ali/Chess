package com.kaneras.chess.logic.move;

import com.kaneras.chess.graphics.stages.AlertBox;
import com.kaneras.chess.graphics.stages.PawnPromotionOptionBox;
import com.kaneras.chess.graphics.Screen;
import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.PieceType;

/**
 * Handles all moves, allows them to be validated and executed.
 * Also performs a check for win each move.
 */
public class MoveHandler {
    private static int lastX = -1;
    private static int lastY = -1;

    /**
     * Check if a move from a start tile to a finish tile is legal
     * @param move The move to validate
     * @return true if the move is legal; false otherwise
     */
    public static MoveResult validateMove(Move move) {
        // Check there's a piece to move
        if (Game.getPiece(move.getStartX(), move.getStartY()) == null) {
            return MoveResult.ILLEGAL;
        }

        MoveHelper moveHelper = createMoveHelper(move);
        if (moveHelper != null) {
            return moveHelper.isValidMove();
        }

        return MoveResult.ILLEGAL;
    }

    /**
     * Create a move helper object to assist in validating moves
     * @param move The move to be checked
     * @return the move helper object
     */
    public static MoveHelper createMoveHelper(Move move) {
        switch (Game.getPiece(move.getStartX(), move.getStartY()).getType()) {
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

        MoveResult moveResult = validateMove(move);

        if (moveResult == MoveResult.ILLEGAL) {
            return;
        }

        ChessPiece old = Game.getSelectedPiece();

        Game.getSelectedPiece().onMove(move.getDestX(), move.getDestY());

        if (old.getType() == PieceType.KING && move.getDistanceMoved() == 2) {
            // Handle castling
            ChessPiece rook = Game.getPiece(move.getStartX() > move.getDestX() ? 0 : 7, move.getStartY());
            rook.onMove((move.getStartX() + move.getDestX()) / 2, move.getStartY());
        }

        handleEnPassant(old, move);

        if (moveResult == MoveResult.CASTLING) {
            handleCastling(move);
        }

        checkForWin();
        try {
            checkAllForCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }
        handlePawnPromotion(move);

        lastX = move.getDestX();
        lastY = move.getDestY();

        Game.deselectTile();
        Game.toggleCurrentPlayer();
        Screen.refresh();
    }

    /**
     * Move pieces in case of en passant
     */
    private static void handleEnPassant(ChessPiece oldPiece, Move move) {
        if (oldPiece.getType() == PieceType.PAWN) {
            ChessPiece pieceToRemove = MoveHelper.checkEnPassant(move);
            if (pieceToRemove != null && pieceToRemove.getCurrX() == move.getDestX()) {
                Game.removePiece(pieceToRemove);
            }
        }
    }

    private static void handleCastling(Move move) {
        ChessPiece rook = Game.getPiece(move.getStartX() > move.getDestX() ? 0 : 7, move.getStartY());
        if (rook == null)
            return;
        rook.onMove((move.getStartX() + move.getDestX()) / 2, move.getDestY());
    }

    private static void checkForWin() {
        if (Game.getKing(Game.getCurrentPlayer().other()) == null) {
            // win
            AlertBox.showAlert("Game Over", "Checkmate by " + Game.getCurrentPlayer());
            return;
        }
    }

    private static void checkAllForCheck() throws Exception {
        boolean[] flags = new boolean[2];
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                ChessPiece piece = Game.getPiece(x, y);
                if (piece == null) {
                    continue;
                }

                ChessPiece king = Game.getKing(piece.getOwner().other());
                if (king == null) {
                    continue;
                }

                if (flags[king.getOwner() == Game.Player.WHITE ? 0 : 1])
                    continue;

                Move move = new Move(piece.getCurrX(), piece.getCurrY(), king.getCurrX(), king.getCurrY());
                if (validateMove(move) != MoveResult.ILLEGAL) {
                    king.setCheck(true);
                    flags[king.getOwner() == Game.Player.WHITE ? 0 : 1] = true;
                }
            }
        }
        if (!flags[0])
            Game.getKing(Game.Player.WHITE).setCheck(false);

        if (!flags[1])
            Game.getKing(Game.Player.BLACK).setCheck(false);
    }

    /**
     * Handle pawn promotion. This should happen after the move has been completed.
     * @param move
     */
    private static void handlePawnPromotion(Move move) {
        if (Game.getPiece(move.getDestX(), move.getDestY()).getType() == PieceType.PAWN && move.getDestY() % 7 == 0) {
            Game.getPiece(move.getDestX(), move.getDestY()).changeType(PawnPromotionOptionBox.chooseOption());
        }
    }

    /**
     * Check if a piece can be moved to a position; the new position is either empty or the player can 'take' the piece.
     * @param x The new x position
     * @param y The new y position
     * @return true if the piece can move here; false otherwise.
     */
    public static boolean canTileBeReoccupied(int x, int y) {
        return Game.getPiece(x, y) == null || Game.getPiece(x, y).getOwner() != Game.getCurrentPlayer();
    }

    public static boolean isLastMoved(int x, int y) {
        return lastX == x && lastY == y;
    }
}
