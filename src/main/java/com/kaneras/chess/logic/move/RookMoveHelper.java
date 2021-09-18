package com.kaneras.chess.logic.move;

public class RookMoveHelper extends MoveHelper {
    public RookMoveHelper(int startX, int startY) {
        super(startX, startY);
    }

    @Override
    public boolean isValidMove(int destX, int destY) {
        return (isMoveVertical(destX, destY) && !isMoveHorizontal(destX, destY) || !isMoveVertical(destX, destY) && isMoveHorizontal(destX, destY)) && !isPathObstructed(destX, destY);
    }

    private boolean isPathObstructed(int destX, int destY) {
        if (isMoveHorizontal(destX, destY)) {
            return horizontalObstruction(destX, destY);
        } else if (isMoveVertical(destX, destY)) {
            return verticalObstruction(destX, destY);
        }

        return false;
    }
}
