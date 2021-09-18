package com.kaneras.chess.logic.move;

public class BishopMoveHelper extends MoveHelper {
    public BishopMoveHelper(int startX, int startY, int finishX, int finishY) {
        super(startX, startY, finishX, finishY);
    }

    @Override
    public boolean isValidMove() {
        return isMoveDiagonal() && !isPathObstructed();
    }

    private boolean isPathObstructed() {
        return diagonalObstruction();
    }
}
