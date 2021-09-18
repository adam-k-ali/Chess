package com.kaneras.chess.logic.move;

public class KingMoveHelper extends MoveHelper {
    public KingMoveHelper(int startX, int startY) {
        super(startX, startY);
    }

    @Override
    public boolean isValidMove(int destX, int destY) {
        return getDistanceMoved(destX, destY) == 1 && oppositeTeams(destX, destY);
    }
}
