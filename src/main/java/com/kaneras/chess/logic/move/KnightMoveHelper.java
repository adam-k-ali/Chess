package com.kaneras.chess.logic.move;

public class KnightMoveHelper extends MoveHelper {
    public KnightMoveHelper(int startX, int startY) {
        super(startX, startY);
    }

    @Override
    public boolean isValidMove(int destX, int destY) {
        return (movedNVertical(2, destY) && movedNHorizontal(1, destX) || movedNVertical(1, destY) && movedNHorizontal(2, destX)) && !moveClashes(destX, destY);
    }
}
