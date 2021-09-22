package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.GridTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for move actions
 */
public abstract class MoveHelper {
    // The start x and y position of the move (on the grid)
    protected int startX;
    protected int startY;

    // The tile that the move starts from and the piece that's being moved.
    protected GridTile startTile;
    protected ChessPiece startPiece;

    /**
     * Create a helper object for a move.
     * @param startX The start x position on the grid for the move
     * @param startY The start y position on the grid for the move
     */
    protected MoveHelper(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        this.startTile = Game.getTile(startX, startY);
        this.startPiece = Game.getTile(startX, startY).getPiece();
    }

    /**
     * Check if a move is legal
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if the move is legal; false otherwise
     */
    public abstract boolean isValidMove(int destX, int destY);

    /**
     * Calculate all legal moves
     * @return A list of points (x, y) that the piece can move to.
     */
    public List<Point> calculateValidMoves() {
        List<Point> points = new ArrayList<>();
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                if (isValidMove(x, y))
                    points.add(new Point(x,y));
            }
        }
        return points;
    }

    /**
     * Get the distance a piece has moved.
     * Currently doesn't work for knights.
     * @param destX The destination x position
     * @param destY The destination y position
     * @return How many tiles the piece has moved over
     */
    protected int getDistanceMoved(int destX, int destY) {
        if (isMoveHorizontal(destX, destY) || isMoveDiagonal(destX, destY)) {
            return Math.abs(startX - destX);
        }
        if (isMoveVertical(destX, destY)) {
            return Math.abs(startY - destY);
        }

        return 0;
    }

    /**
     * Check if a piece is moving n tiles forward
     * @param n the number of tiles moved
     * @param destY the destination y position
     * @return true if the piece has moved n tiles forward; false otherwise.
     */
    protected boolean movedNForward(int n, int destY) {
        ChessPiece piece = Game.getTile(startX, startY).getPiece();
        return piece.getOwner() == Game.Player.BLACK && destY - startY == n || piece.getOwner() == Game.Player.WHITE && destY - startY == -n;
    }

    /**
     * Check if a piece is moving n tiles sideways
     * @param n the number of tiles moved
     * @param destX the destination x position
     * @return true if the piece has moved n tiles sideways; false otherwise.
     */
    protected boolean movedNHorizontal(int n, int destX) {
        return Math.abs(startX - destX) == n;
    }

    /**
     * Check if a piece is moving n tiles vertically
     * @param n the number of tiles moved
     * @param destY the destination y position
     * @return true if the piece has moved n tiles vertically; false otherwise.
     */
    protected boolean movedNVertical(int n, int destY) {
        return Math.abs(startY - destY) == n;
    }

    /**
     * Check if a move is diagonal
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if the move was diagonal; false otherwise.
     */
    protected boolean isMoveDiagonal(int destX, int destY) {
        return Math.abs(startX - destX) == Math.abs(startY - destY);
    }

    /**
     * Check if a move was vertical only.
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if the move was vertical only; false otherwise.
     */
    protected boolean isMoveVertical(int destX, int destY) {
        return Math.abs(startY - destY) > 0 && startX == destX;
    }

    /**
     * Check if a move was horizontal only
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if the move was horizontal only; false otherwise.
     */
    protected boolean isMoveHorizontal(int destX, int destY) {
        return Math.abs(startX - destX) > 0 && startY == destY;
    }

    /**
     * Check if the player moves onto a tile they already occupy with another piece
     * @return If the end tile is already occupied by the same player
     */
    protected boolean moveClashes(int destX, int destY) {
        return !oppositeTeams(destX, destY) && startPiece != null;
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
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if the start and end tile are occupied by different teams; false otherwise.
     */
    protected boolean oppositeTeams(int destX, int destY) {
        ChessPiece finishPiece = Game.getTile(destX, destY).getPiece();
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
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if there is an obstruction; false otherwise.
     */
    protected boolean horizontalObstruction(int destX, int destY) {
        if (moveClashes(destX, destY))
            return true;

        int dx = startX > destX ? -1 : 1;
        for (int i = 1; i < Math.abs(startX - destX); i++) {
            if (tileOccupied(startX + i * dx, startY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the move would jump over another piece when moving vertically only.
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if there is an obstruction; false otherwise.
     */
    protected boolean verticalObstruction(int destX, int destY) {
        if (moveClashes(destX, destY))
            return true;

        int dy = startY > destY ? -1 : 1;
        for (int i = 1; i < Math.abs(startY - destY); i++) {
            if (tileOccupied(startX, startY + i * dy)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the move would jump over another piece when moving diagonally.
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if there is an obstruction; false otherwise.
     */
    protected boolean diagonalObstruction(int destX, int destY) {
        if (moveClashes(destX, destY))
            return true;

        int dx = startX > destX ? -1 : 1;
        int dy = startY > destY ? -1 : 1;
        for (int i = 1; i < Math.abs(startX - destX); i++) {
            if (tileOccupied(startX + i * dx, startY + i * dy)) {
                return true;
            }
        }
        return false;
    }


}
