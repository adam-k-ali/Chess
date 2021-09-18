package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.ChessPiece;
import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.GridTile;

public abstract class MoveHelper {
    protected int startX;
    protected int startY;
    protected int finishX;
    protected int finishY;

    protected GridTile startTile;
    protected GridTile finishTile;
    protected ChessPiece startPiece;
    protected ChessPiece finishPiece;

    protected MoveHelper(int startX, int startY, int finishX, int finishY) {
        this.startX = startX;
        this.startY = startY;
        this.finishX = finishX;
        this.finishY = finishY;

        this.startTile = Game.getTile(startX, startY);
        this.finishTile = Game.getTile(finishX, finishY);
        this.startPiece = Game.getTile(startX, startY).getPiece();
        this.finishPiece = Game.getTile(finishX, finishY).getPiece();
    }

    public abstract boolean isValidMove();

    protected int getDistanceMoved() {
        if (isMoveHorizontal() || isMoveDiagonal()) {
            return Math.abs(startX - finishX);
        }
        if (isMoveVertical()) {
            return Math.abs(startY - finishY);
        }

        return 0;
    }

    protected boolean movedNForward(int n) {
        ChessPiece piece = Game.getTile(startX, startY).getPiece();
        return piece.getOwner() == Game.Player.BLACK && finishY - startY == n || piece.getOwner() == Game.Player.WHITE && finishY - startY == -n;
    }

    protected boolean movedNHorizontal(int n) {
        return Math.abs(startX - finishX) == n;
    }

    protected boolean movedNVertical(int n) {
        return Math.abs(startY - finishY) == n;
    }

    protected boolean isMoveDiagonal() {
        return Math.abs(startX - finishX) == Math.abs(startY - finishY);
    }

    protected boolean isMoveVertical() {
        return Math.abs(startY - finishY) > 0 && Math.abs(startX - finishX) == 0;
    }

    protected boolean isMoveHorizontal() {
        return Math.abs(startX - finishX) > 0 && Math.abs(startY - finishY) == 0;
    }

    /**
     * Check if the player moves onto a tile they already occupy with another piece
     * @return If the end tile is already occupied by the same player
     */
    protected boolean moveClashes() {
        return !oppositeTeams() && startPiece != null;
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
    protected boolean oppositeTeams() {
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
    protected boolean horizontalObstruction() {
        if (moveClashes())
            return true;

        int dx = startX > finishX ? -1 : 1;
        for (int i = 1; i < Math.abs(startX - finishX); i++) {
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
    protected boolean verticalObstruction() {
        if (moveClashes())
            return true;

        int dy = startY > finishY ? -1 : 1;
        for (int i = 1; i < Math.abs(startY - finishY); i++) {
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
    protected boolean diagonalObstruction() {
        if (moveClashes())
            return true;

        int dx = startX > finishX ? -1 : 1;
        int dy = startY > finishY ? -1 : 1;
        for (int i = 1; i < Math.abs(startX - finishX); i++) {
            if (tileOccupied(startX + i * dx, startY + i * dy)) {
                return true;
            }
        }
        return false;
    }


}
