package com.kaneras.chess.logic.move;

/**
 * Helper class for moves of kings
 */
public class KingMoveHelper extends MoveHelper {
    /**
     * Create a helper object for a move of a king
     * @param startX The start x position on the grid for the move
     * @param startY The start y position on the grid for the move
     */
    public KingMoveHelper(int startX, int startY) {
        super(startX, startY);
    }

    /**
     * Check if a move is legal
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if the move is legal; false otherwise
     */
    @Override
    public boolean isValidMove(int destX, int destY) {
        return getDistanceMoved(destX, destY) == 1 && oppositeTeams(destX, destY);
    }
}
