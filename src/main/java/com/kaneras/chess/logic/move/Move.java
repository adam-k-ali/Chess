package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.ChessPiece;
import com.kaneras.chess.logic.element.GridTile;

public class Move {
    private final int startX;
    private final int startY;
    private final int destX;
    private final int destY;

    public Move(int startX, int startY, int destX, int destY) {
        this.startX = startX;
        this.startY = startY;
        this.destX = destX;
        this.destY = destY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getDestX() {
        return destX;
    }

    public int getDestY() {
        return destY;
    }

    public GridTile getStartTile() {
        return Game.getTile(startX, startY);
    }

    public GridTile getDestTile() {
        return Game.getTile(destX, destY);
    }

    /**
     * Check if a piece is moving n tiles forward
     * @param n the number of tiles moved
     * @return true if the piece has moved n tiles forward; false otherwise.
     */
    public boolean movedNForward(int n) {
        ChessPiece piece = Game.getTile(startX, startY).getPiece();
        return piece.getOwner() == Game.Player.BLACK && destY - startY == n || piece.getOwner() == Game.Player.WHITE && destY - startY == -n;
    }

    /**
     * Check if a piece is moving n tiles sideways
     * @param n the number of tiles moved
     * @return true if the piece has moved n tiles sideways; false otherwise.
     */
    public boolean movedNHorizontal(int n) {
        return Math.abs(startX - destX) == n;
    }

    /**
     * Check if a piece is moving n tiles vertically
     * @param n the number of tiles moved
     * @return true if the piece has moved n tiles vertically; false otherwise.
     */
    public boolean movedNVertical(int n) {
        return Math.abs(startY - destY) == n;
    }

    /**
     * Check if a move is diagonal
     * @return true if the move was diagonal; false otherwise.
     */
    public boolean isMoveDiagonal() {
        return Math.abs(startX - destX) == Math.abs(startY - destY);
    }

    /**
     * Check if a move was vertical only.
     * @return true if the move was vertical only; false otherwise.
     */
    public boolean isMoveVertical() {
        return Math.abs(startY - destY) > 0 && startX == destX;
    }

    /**
     * Check if a move was horizontal only
     * @return true if the move was horizontal only; false otherwise.
     */
    public boolean isMoveHorizontal() {
        return Math.abs(startX - destX) > 0 && startY == destY;
    }

    /**
     * Get the distance a piece has moved.
     * @return How many tiles the piece has moved over
     */
    public int getDistanceMoved() {
        if (isMoveDiagonal()) {
            return Math.abs(startX - destX);
        }

        return Math.abs(startX - destX) + Math.abs(startY - destY);
    }

    /**
     * Get the x direction
     * @return 1 for increasing x, -1 for decreasing x, 0 for no change in x.
     */
    public int getXDirection() {
        if (getHorizontalDistance() == 0)
            return 0;
        return startX > destX ? -1 : 1;
    }

    /**
     * Get the change in x
     * @return The distance moved horizontally
     */
    public int getHorizontalDistance() {
        return Math.abs(startX - destX);
    }

    /**
     * Get the y direction
     * @return 1 for increasing y, -1 for decreasing y, 0 for no change in y.
     */
    public int getYDirection() {
        if (getVerticalDistance() == 0)
            return 0;
        return startY > destY ? -1 : 1;
    }

    /**
     * Get the change in y
     * @return The distance moved vertically
     */
    public int getVerticalDistance() {
        return Math.abs(startY - destY);
    }

}
