package com.kaneras.chess.logic.move;

public class RookMoveHelper extends MoveHelper {
    public RookMoveHelper(int startX, int startY, int finishX, int finishY) {
        super(startX, startY, finishX, finishY);
    }

    @Override
    public boolean isValidMove() {
        return (isMoveVertical() && !isMoveHorizontal() || !isMoveVertical() && isMoveHorizontal()) && !isPathObstructed();
    }

    private boolean isPathObstructed() {
        if (isMoveHorizontal()) {
            return horizontalObstruction();
        } else if (isMoveVertical()) {
            return verticalObstruction();
        }

        return false;
    }
}
