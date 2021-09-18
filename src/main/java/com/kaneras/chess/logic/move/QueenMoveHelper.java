package com.kaneras.chess.logic.move;

public class QueenMoveHelper extends MoveHelper {
    public QueenMoveHelper(int startX, int startY, int finishX, int finishY) {
        super(startX, startY, finishX, finishY);
    }

    @Override
    public boolean isValidMove() {
        return (isMoveDiagonal() || isMoveHorizontal() && !isMoveVertical() || !isMoveHorizontal() && isMoveVertical()) && !isPathObstructed();
    }

    private boolean isPathObstructed() {
        if (isMoveDiagonal()) {
            return diagonalObstruction();
        }

        if (isMoveHorizontal()) {
            return horizontalObstruction();
        } else if (isMoveVertical()) {
            return verticalObstruction();
        }

        return false;
    }
}
