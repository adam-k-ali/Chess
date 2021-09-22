package com.kaneras.chess.logic.ai;

import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.element.ChessPiece;

import java.util.List;

/**
 * Perform evaluation on the game.
 */
public class AIEvaluation {
    private final Game.Player computer;

    public AIEvaluation(Game.Player computer) {
        this.computer = computer;
    }

    public int getBoardScore() {
        return getScore(computer) + getScore(computer.other());
    }

    public int getScore(Game.Player player) {
        List<ChessPiece> pieces = Game.getPlayerPieces(player);
        int score = 0;
        for (ChessPiece piece : pieces) {
            if (player == Game.Player.WHITE) {
                score += piece.getType().getScore();
            } else {
                score -= piece.getType().getScore();
            }
        }

        return score;
    }
}
