package com.kaneras.chess.logic;

import com.kaneras.chess.graphics.ImageHelper;
import javafx.scene.image.Image;

public class ChessPiece {
    private final PieceType type;
    private Image sprite;
    private final Game.Player owner;

    public ChessPiece(PieceType type, Game.Player owner) {
        this.type = type;
        this.owner = owner;
        this.sprite = ImageHelper.loadImage(type.toString().toLowerCase() + (owner == Game.Player.WHITE ? "_white" : ""));
    }

    public Image getSprite() {
        return sprite;
    }

    public PieceType getType() {
        return type;
    }

    public Game.Player getOwner() {
        return owner;
    }

    public enum PieceType {
        KING, ROOK, BISHOP, QUEEN, KNIGHT, PAWN;
    }
}
