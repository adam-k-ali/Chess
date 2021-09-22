package com.kaneras.chess.logic.move;

/**
 * Helper class for moves of knights
 */
public class KnightMoveHelper extends MoveHelper {
    /**
     * Create a helper object for a move of a knight
     * @param startX The start x position on the grid for the move
     * @param startY The start y position on the grid for the move
     */
    public KnightMoveHelper(int startX, int startY) {
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
        return (movedNVertical(2, destY) && movedNHorizontal(1, destX) || movedNVertical(1, destY) && movedNHorizontal(2, destX)) && !moveClashes(destX, destY);
    }
}
