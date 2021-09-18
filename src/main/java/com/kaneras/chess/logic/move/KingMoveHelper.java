package com.kaneras.chess.logic.move;

public class KingMoveHelper extends MoveHelper {
    public KingMoveHelper(int startX, int startY, int finishX, int finishY) {
        super(startX, startY, finishX, finishY);
    }

    @Override
    public boolean isValidMove() {
        return getDistanceMoved() == 1 && oppositeTeams();
    }
}
