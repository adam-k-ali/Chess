package com.kaneras.chess.logic.move;

public class KnightMoveHelper extends MoveHelper {
    public KnightMoveHelper(int startX, int startY, int finishX, int finishY) {
        super(startX, startY, finishX, finishY);
    }

    @Override
    public boolean isValidMove() {
        return (movedNVertical(2) && movedNHorizontal(1) || movedNVertical(1) && movedNHorizontal(2)) && !moveClashes();
    }
}
