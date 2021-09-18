package com.kaneras.chess.logic.move;

public class BishopMoveHelper extends MoveHelper {
    public BishopMoveHelper(int startX, int startY) {
        super(startX, startY);
    }

    @Override
    public boolean isValidMove(int destX, int destY) {
        return isMoveDiagonal(destX, destY) && !isPathObstructed(destX, destY);
    }

    private boolean isPathObstructed(int destX, int destY) {
        return diagonalObstruction(destX, destY);
    }
}
