package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.Board;
import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.ChessPiece;

public class Move {
    private final int startX;
    private final int startY;
    private final int destX;
    private final int destY;
    private final Board board;

    public Move(Board board, int startX, int startY, int destX, int destY) {
        this.board = board;
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

    /**
     * Check if a piece is moving n tiles forward
     * @param n the number of tiles moved
     * @return true if the piece has moved n tiles forward; false otherwise.
     */
    public boolean movedNForward(int n) {
        ChessPiece piece = board.getPiece(startX, startY);
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
        return Math.abs(startX - destX) == Math.abs(startY - destY) && Math.abs(startX - destX) > 0;
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

    /**
     * Check if the piece has passed over a point
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @return true if the piece passed over the point; false otherwise
     */
    public boolean hasTraversed(int x, int y) {
        // Knight
        if (movedNVertical(2) && movedNHorizontal(1)) {
            return x == startX ? isNInBound(y, startY, destY) : y == destY;
        } else if (movedNVertical(1) && movedNHorizontal(2)) {
            return y == startY ? isNInBound(x, startX, destX) : x == destX;
        }

        // Diagonal movements
        if (isMoveDiagonal()) {
            return isNInBound(x, startX, destX) && isNInBound(y, startY, destY) && Math.abs(x - startX) == Math.abs(y - startY);
        }

        // Other pieces (horizontal and vertical movements)
        if (isMoveHorizontal()) {
            return isNInBound(x, startX, destX);
        }
        if (isMoveVertical()) {
            return isNInBound(y, startY, destY);
        }

        return false;
    }

    private boolean isNInBound(int n, int i, int j) {
        if (i > j) {
            return i >= n && j <= n;
        } else {
            return i <= n && j >= n;
        }
    }

    public ChessPiece getStartPiece() {
        return board.getPiece(getStartX(), getStartY());
    }

}
