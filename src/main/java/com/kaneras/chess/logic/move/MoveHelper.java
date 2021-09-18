package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.ChessPiece;
import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.GridTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MoveHelper {
    protected int startX;
    protected int startY;
    protected GridTile startTile;
    protected ChessPiece startPiece;

    protected MoveHelper(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        this.startTile = Game.getTile(startX, startY);
        this.startPiece = Game.getTile(startX, startY).getPiece();
    }

    public abstract boolean isValidMove(int destX, int destY);

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

    protected int getDistanceMoved(int destX, int destY) {
        if (isMoveHorizontal(destX, destY) || isMoveDiagonal(destX, destY)) {
            return Math.abs(startX - destX);
        }
        if (isMoveVertical(destX, destY)) {
            return Math.abs(startY - destY);
        }

        return 0;
    }

    protected boolean movedNForward(int n, int destY) {
        ChessPiece piece = Game.getTile(startX, startY).getPiece();
        return piece.getOwner() == Game.Player.BLACK && destY - startY == n || piece.getOwner() == Game.Player.WHITE && destY - startY == -n;
    }

    protected boolean movedNHorizontal(int n, int destX) {
        return Math.abs(startX - destX) == n;
    }

    protected boolean movedNVertical(int n, int destY) {
        return Math.abs(startY - destY) == n;
    }

    protected boolean isMoveDiagonal(int destX, int destY) {
        return Math.abs(startX - destX) == Math.abs(startY - destY);
    }

    protected boolean isMoveVertical(int destX, int destY) {
        return Math.abs(startY - destY) > 0 && Math.abs(startX - destX) == 0;
    }

    protected boolean isMoveHorizontal(int destX, int destY) {
        return Math.abs(startX - destX) > 0 && Math.abs(startY - destY) == 0;
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
     * @return True if the tile is already occupied by a piece; false otherwise
     */
    protected boolean tileOccupied(int x, int y) {
        return Game.getTile(x, y).getPiece() != null;
    }

    /**
     * Checks if 2 tiles contain a piece from different teams
     * @return True if the start and end tile are occupied by different teams
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
     * @return True if there is an obstruction; false otherwise.
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
     * @return True if there is an obstruction; false otherwise.
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
     * @return True if there is an obstruction; false otherwise.
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
