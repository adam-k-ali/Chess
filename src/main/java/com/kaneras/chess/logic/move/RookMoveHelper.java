package com.kaneras.chess.logic.move;

/**
 * Helper class for moves of rooks
 */
public class RookMoveHelper extends MoveHelper {

    /**
     * Create a helper object for a move of a rook
     * @param startX The start x position on the grid for the move
     * @param startY The start y position on the grid for the move
     */
    public RookMoveHelper(int startX, int startY) {
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
        return (isMoveVertical(destX, destY) && !isMoveHorizontal(destX, destY) || !isMoveVertical(destX, destY) && isMoveHorizontal(destX, destY)) && !isPathObstructed(destX, destY);
    }

    /**
     * Check if the path the queen takes is obstructed by another piece
     * @param destX The destination x position
     * @param destY The destination y position
     * @return true if the path is obstructed; false otherwise.
     */
    private boolean isPathObstructed(int destX, int destY) {
        if (isMoveHorizontal(destX, destY)) {
            return horizontalObstruction(destX, destY);
        } else if (isMoveVertical(destX, destY)) {
            return verticalObstruction(destX, destY);
        }

        return false;
    }
}
