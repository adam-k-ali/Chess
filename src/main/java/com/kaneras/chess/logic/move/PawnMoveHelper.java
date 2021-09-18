package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.ChessPiece;
import com.kaneras.chess.logic.Game;

//TODO: Pawn Promotion
public class PawnMoveHelper extends MoveHelper {

    public PawnMoveHelper(int startX, int startY) {
        super(startX, startY);
    }

    @Override
    public boolean isValidMove(int destX, int destY) {
        // On the first move, a pawn can move 2 spaces forward.
        if (isAtStart() && movedNForward(2, destY) && Math.abs(startX - destX) == 0) {
            return true;
        }

        // Take the opposite team's piece.
        if (isMoveDiagonal(destX, destY) && getDistanceMoved(destX, destY) == 1) {
            return oppositeTeams(destX, destY) && Game.getTile(destX, destY).getPiece() != null;
        }

        // Checks for if the pawn has moved 1 space forward.
        if (movedNForward(1, destY) && Math.abs(startX - destX) == 0) {
            // Team that moved can take piece if there is one of getTile(x, y).getPiece() the opposite team.
            if (isMoveDiagonal(destX, destY) || moveClashes(destX, destY)) {
                return false;
            }

            return Game.getTile(destX, destY).getPiece() == null;
        }

        return false;
    }

    private boolean isAtStart() {
        return startPiece.getOwner() == Game.Player.WHITE && startY == 6 || startPiece.getOwner() == Game.Player.BLACK && startY == 1;
    }

}
