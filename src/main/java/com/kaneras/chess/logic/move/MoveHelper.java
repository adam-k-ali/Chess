package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for move actions
 */
public abstract class MoveHelper {
    // The move to check
    protected final Move move;

    /**
     * Create a helper object for a move.
     * @param move The move to check
     */
    protected MoveHelper(Move move) {
        this.move = move;
    }

    /**
     * Check if a move is legal
     * @return true if the move is legal; false otherwise
     */
    public abstract boolean isValidMove();

    /**
     * Check if the player moves onto a tile they already occupy with another piece
     * @return If the end tile is already occupied by the same player
     */
    protected boolean moveClashes() {
        return !oppositeTeams() && move.getStartTile().getPiece() != null;
    }

    /**
     * Check if a tile is occupied already
     * @param x x position of the tile
     * @param y y position of the tile
     * @return true if the tile is already occupied by a piece; false otherwise
     */
    protected boolean tileOccupied(int x, int y) {
        return Game.getTile(x, y).getPiece() != null;
    }

    /**
     * Checks if the start and destination tile are occupied by different teams.
     * @return true if the start and end tile are occupied by different teams; false otherwise.
     */
    protected boolean oppositeTeams() {
        ChessPiece finishPiece = move.getDestTile().getPiece();
        ChessPiece startPiece = move.getStartTile().getPiece();
        if (startPiece != null && finishPiece == null)
            return true;
        if (startPiece == null && finishPiece != null)
            return true;

        if (startPiece.getOwner() == Game.Player.WHITE && finishPiece.getOwner() == Game.Player.BLACK)
            return true;

        return startPiece.getOwner() == Game.Player.BLACK && finishPiece.getOwner() == Game.Player.WHITE;
    }

    /**
     * Checks if the move would jump over another piece when moving horizontally only.
     * @return true if there is an obstruction; false otherwise.
     */
    protected boolean horizontalObstruction() {
        if (moveClashes())
            return true;

        for (int i = 1; i < move.getHorizontalDistance(); i++) {
            if (tileOccupied(move.getStartX() + i * move.getXDirection(), move.getStartY())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the move would jump over another piece when moving vertically only.
     * @return true if there is an obstruction; false otherwise.
     */
    protected boolean verticalObstruction() {
        if (moveClashes())
            return true;

        for (int i = 1; i < move.getVerticalDistance(); i++) {
            if (tileOccupied(move.getStartX(), move.getStartY() + i * move.getYDirection())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the move would jump over another piece when moving diagonally.
     * @return true if there is an obstruction; false otherwise.
     */
    protected boolean diagonalObstruction() {
        if (!move.isMoveDiagonal())
            return false;

        if (moveClashes())
            return true;

        for (int i = 1; i < move.getDistanceMoved(); i++) {
            if (tileOccupied(move.getStartX() + i * move.getXDirection(), move.getStartY() + i * move.getYDirection())) {
                return true;
            }
        }
        return false;
    }

    protected boolean isPathObstructed() {
        return false;
    }


}
