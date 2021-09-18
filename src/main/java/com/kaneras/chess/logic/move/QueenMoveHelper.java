package com.kaneras.chess.logic.move;

public class QueenMoveHelper extends MoveHelper {
    public QueenMoveHelper(int startX, int startY) {
        super(startX, startY);
    }

    @Override
    public boolean isValidMove(int destX, int destY) {
        return (isMoveDiagonal(destX, destY) || isMoveHorizontal(destX, destY) && !isMoveVertical(destX, destY) || !isMoveHorizontal(destX, destY) && isMoveVertical(destX, destY)) && !isPathObstructed(destX, destY);
    }

    private boolean isPathObstructed(int destX, int destY) {
        if (isMoveDiagonal(destX, destY)) {
            return diagonalObstruction(destX, destY);
        }

        if (isMoveHorizontal(destX, destY)) {
            return horizontalObstruction(destX, destY);
        } else if (isMoveVertical(destX, destY)) {
            return verticalObstruction(destX, destY);
        }

        return false;
    }
}
