package com.kaneras.chess.logic.element;

import com.kaneras.chess.graphics.ImageHelper;
import com.kaneras.chess.logic.Game;
import javafx.scene.image.Image;

/**
 * Contains the properties of a chess piece
 */
public class ChessPiece {
    private final PieceType type;
    private final Image sprite;
    private final Game.Player owner;

    /**
     * Create a new chess piece
     * @param type The type of the chess piece
     * @param owner Who owns the chess piece (WHITE or BLACK)
     */
    public ChessPiece(PieceType type, Game.Player owner) {
        this.type = type;
        this.owner = owner;
        this.sprite = ImageHelper.loadImage(type.toString().toLowerCase() + (owner == Game.Player.WHITE ? "_white" : ""));
    }

    /**
     * Get the image to represent the piece on the board
     * @return The piece's sprite
     */
    public Image getSprite() {
        return sprite;
    }

    /**
     * Get the type of the chess piece
     * @return The type of the chess piece
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Get the owner of the chess piece
     * @return The owner of the chess piece (WHITE or BLACK)
     */
    public Game.Player getOwner() {
        return owner;
    }

    /**
     * All possible chess piece types
     */
    public enum PieceType {
        QUEEN(900), KING(90), ROOK(50), BISHOP(30), KNIGHT(30), PAWN(10);

        int score;
        PieceType(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }
}
