package com.kaneras.chess.logic;

import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.element.PieceType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {
    private final List<ChessPiece> chessPieces;
    private int lastX = - 1;
    private int lastY = - 1;

    public Board() {
        this.chessPieces = new ArrayList<>();
    }

    public Board setupDefaultBoard() {
        chessPieces.clear();

        addChessPiece(PieceType.ROOK, Game.Player.BLACK, 0, 0);
        addChessPiece(PieceType.KNIGHT, Game.Player.BLACK, 1, 0);
        addChessPiece(PieceType.BISHOP, Game.Player.BLACK, 2, 0);
        addChessPiece(PieceType.QUEEN, Game.Player.BLACK, 3, 0);
        addChessPiece(PieceType.KING, Game.Player.BLACK, 4, 0);
        addChessPiece(PieceType.BISHOP, Game.Player.BLACK, 5, 0);
        addChessPiece(PieceType.KNIGHT, Game.Player.BLACK, 6, 0);
        addChessPiece(PieceType.ROOK, Game.Player.BLACK, 7, 0);

        for (int x = 0; x <= 7; x++) {
            addChessPiece(PieceType.PAWN, Game.Player.BLACK, x, 1);
            addChessPiece(PieceType.PAWN, Game.Player.WHITE, x, 6);
        }

        addChessPiece(PieceType.ROOK, Game.Player.WHITE, 0, 7);
        addChessPiece(PieceType.KNIGHT, Game.Player.WHITE, 1, 7);
        addChessPiece(PieceType.BISHOP, Game.Player.WHITE, 2, 7);
        addChessPiece(PieceType.KING, Game.Player.WHITE, 3, 7);
        addChessPiece(PieceType.QUEEN, Game.Player.WHITE, 4, 7);
        addChessPiece(PieceType.BISHOP, Game.Player.WHITE, 5, 7);
        addChessPiece(PieceType.KNIGHT, Game.Player.WHITE, 6, 7);
        addChessPiece(PieceType.ROOK, Game.Player.WHITE, 7, 7);
        return this;
    }

    public void addChessPiece(PieceType type, Game.Player player, int startX, int startY) {
        chessPieces.add(new ChessPiece(this, type, player, startX, startY));
    }

    public ChessPiece getPiece(int x, int y) {
        for (ChessPiece piece : chessPieces) {
            if (piece.getCurrX() == x && piece.getCurrY() == y)
                return piece;
        }
        return null;
    }

    /**
     * Get all chess pieces on the board of a certain type
     * @param type The type of the chess piece
     * @param owner The owner of the chess piece; null if all of type are wanted.
     * @return A list of chess pieces fitting the criteria.
     */
    public List<ChessPiece> getType(PieceType type, Game.Player owner) {
        List<ChessPiece> pieces = new ArrayList<>();
        for (ChessPiece piece : chessPieces) {
            if (piece.getType() == type) {
                if (owner != null && piece.getOwner() == owner) {
                    pieces.add(piece);
                } else if (owner == null) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public List<ChessPiece> getPlayerPieces(Game.Player player) {
        List<ChessPiece> pieces = new ArrayList<>();

        for (ChessPiece piece : chessPieces) {
            if (piece.getOwner() == player)
                pieces.add(piece);
        }
        return pieces;
    }

    public ChessPiece getKing(Game.Player player) {
        for (ChessPiece piece : chessPieces) {
            if (piece.getOwner() == player && piece.getType() == PieceType.KING)
                return piece;
        }
        return null;
    }

    /**
     * Remove the chess piece at a position
     * @param x The x position of the piece
     * @param y The y position of the piece
     */
    public void removePiece(int x, int y) {
        for (Iterator<ChessPiece> iterator = chessPieces.iterator(); iterator.hasNext();) {
            ChessPiece piece = iterator.next();
            if (piece.getCurrX() == x && piece.getCurrY() == y) {
                iterator.remove();
            }
        }
    }

    public void updateLastMove(int x, int y) {
        this.lastX = x;
        this.lastY = y;
    }

    /**
     * Check if the piece at (x, y) was the last piece to be moved
     * @param x The x position of the piece
     * @param y The y position of the piece
     * @return true if piece at (x, y) was the last piece to be moved; false otherwise
     */
    public boolean isLastMoved(int x, int y) {
        return lastX == x && lastY == y;
    }

    public Board makeCopy() {
        Board newBoard = new Board();
        for (ChessPiece piece : chessPieces) {
            newBoard.addChessPiece(piece.getType(), piece.getOwner(), piece.getStartX(), piece.getStartY());
        }
        return newBoard;
    }
}
