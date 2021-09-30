package com.kaneras.chess.logic.move;

import com.kaneras.chess.graphics.stages.AlertBox;
import com.kaneras.chess.graphics.stages.PawnPromotionOptionBox;
import com.kaneras.chess.graphics.Screen;
import com.kaneras.chess.logic.Board;
import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.PieceType;

/**
 * Handles all moves, allows them to be validated and executed.
 * Also performs a check for win each move.
 */
public class MoveHandler {
    private final Board board;
    public MoveHandler(Board board) {
        this.board = board;
    }

    /**
     * Check if a move from a start tile to a finish tile is legal
     * @param move The move to validate
     * @return true if the move is legal; false otherwise
     */
    public static MoveResult validateMove(Move move, Board board) {
        // Check there's a piece to move
        if (board.getPiece(move.getStartX(), move.getStartY()) == null) {
            return MoveResult.ILLEGAL;
        }

        MoveHelper moveHelper = createMoveHelper(move, board);
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
    public static MoveHelper createMoveHelper(Move move, Board board) {
        switch (board.getPiece(move.getStartX(), move.getStartY()).getType()) {
            case KING:
                return new KingMoveHelper(move, board);
            case QUEEN:
                return new QueenMoveHelper(move, board);
            case BISHOP:
                return new BishopMoveHelper(move, board);
            case PAWN:
                return new PawnMoveHelper(move, board);
            case ROOK:
                return new RookMoveHelper(move, board);
            case KNIGHT:
                return new KnightMoveHelper(move, board);
            default:
                return null;
        }
    }

    /**
     * Move the selected game piece to a new legal destination
     * @param move The move to be performed
     */
    public void performMove(Move move) {
        if (Game.getSelectedTile() == null)
            return;

        MoveResult moveResult = validateMove(move, board);

        if (moveResult == MoveResult.ILLEGAL) {
            return;
        }

        ChessPiece old = board.getPiece(move.getStartX(), move.getStartY());

        // Remove old piece
        if (board.getPiece(move.getDestX(), move.getDestY()) != null) {
            board.removePiece(move.getDestX(), move.getDestY());
        }

        board.getPiece(move.getStartX(), move.getStartY()).onMove(move.getDestX(), move.getDestY());

        if (old.getType() == PieceType.KING && move.getDistanceMoved() == 2) {
            // Handle castling
            ChessPiece rook = board.getPiece(move.getStartX() > move.getDestX() ? 0 : 7, move.getStartY());
            rook.onMove((move.getStartX() + move.getDestX()) / 2, move.getStartY());
        }

        if (moveResult == MoveResult.EN_PASSANT) {
            handleEnPassant(move);
        }

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

        board.updateLastMove(move.getDestX(), move.getDestY());

        Game.deselectTile();
        Game.toggleCurrentPlayer();
        Screen.refresh();
    }

    /**
     * Move pieces in case of en passant
     */
    private void handleEnPassant(Move move) {
        int x = move.getDestX();
        int y = move.getStartY() > move.getDestY() ? move.getDestY() + 1 : move.getDestY() - 1;

        ChessPiece pawn = board.getPiece(x, y);
        if (pawn != null && pawn.getType() == PieceType.PAWN) {
            board.removePiece(pawn.getCurrX(), pawn.getCurrY());
        }
    }

    private void handleCastling(Move move) {
        ChessPiece rook = board.getPiece(move.getStartX() > move.getDestX() ? 0 : 7, move.getStartY());
        if (rook == null)
            return;
        rook.onMove((move.getStartX() + move.getDestX()) / 2, move.getDestY());
    }

    private void checkForWin() {
        if (board.getKing(Game.getCurrentPlayer().other()) == null) {
            // win
            AlertBox.showAlert("Game Over", "Checkmate by " + Game.getCurrentPlayer());
            return;
        }
    }

    private void checkAllForCheck() throws Exception {
        boolean[] flags = new boolean[2];
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                ChessPiece piece = board.getPiece(x, y);
                if (piece == null) {
                    continue;
                }

                ChessPiece king = board.getKing(piece.getOwner().other());
                if (king == null) {
                    continue;
                }

                if (flags[king.getOwner() == Game.Player.WHITE ? 0 : 1])
                    continue;

                Move move = new Move(board, piece.getCurrX(), piece.getCurrY(), king.getCurrX(), king.getCurrY());
                if (validateMove(move, board) != MoveResult.ILLEGAL) {
                    king.setCheck(true);
                    flags[king.getOwner() == Game.Player.WHITE ? 0 : 1] = true;
                }
            }
        }
        if (!flags[0])
            board.getKing(Game.Player.WHITE).setCheck(false);

        if (!flags[1])
            board.getKing(Game.Player.BLACK).setCheck(false);
    }

    /**
     * Handle pawn promotion. This should happen after the move has been completed.
     * @param move
     */
    private void handlePawnPromotion(Move move) {
        if (board.getPiece(move.getDestX(), move.getDestY()).getType() == PieceType.PAWN && move.getDestY() % 7 == 0) {
            board.getPiece(move.getDestX(), move.getDestY()).changeType(PawnPromotionOptionBox.chooseOption());
        }
    }

    /**
     * Check if a piece can be moved to a position; the new position is either empty or the player can 'take' the piece.
     * @param x The new x position
     * @param y The new y position
     * @return true if the piece can move here; false otherwise.
     */
    public boolean canTileBeReoccupied(int x, int y) {
        return board.getPiece(x, y) == null || board.getPiece(x, y).getOwner() != Game.getCurrentPlayer();
    }
}
