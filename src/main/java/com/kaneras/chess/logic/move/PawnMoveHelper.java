package com.kaneras.chess.logic.move;

import com.kaneras.chess.logic.Game;

//TODO: Pawn Promotion
public class PawnMoveHelper extends MoveHelper {

    public PawnMoveHelper(int startX, int startY, int finishX, int finishY) {
        super(startX, startY, finishX, finishY);
    }

    @Override
    public boolean isValidMove() {
        // On the first move, a pawn can move 2 spaces forward.
        if (isAtStart() && movedNForward(2) && Math.abs(startX - finishX) == 0) {
            return true;
        }

        // Take the opposite team's piece.
        if (isMoveDiagonal() && getDistanceMoved() == 1) {
            return oppositeTeams() && finishPiece != null;
        }

        // Checks for if the pawn has moved 1 space forward.
        if (movedNForward(1) && Math.abs(startX - finishX) == 0) {
            // Team that moved can take piece if there is one of getTile(x, y).getPiece() the opposite team.
            if (isMoveDiagonal() || moveClashes()) {
                return false;
            }
            if (finishTile.getPiece() == null) {
                return true;
            }
        }

        return false;
    }

    private boolean isAtStart() {
        return startPiece.getOwner() == Game.Player.WHITE && startY == 6 || startPiece.getOwner() == Game.Player.BLACK && startY == 1;
    }

}
