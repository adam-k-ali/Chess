package com.kaneras.chess.logic;

import com.kaneras.chess.graphics.ImageHelper;
import javafx.scene.image.Image;

public class ChessPiece {
    private final PieceType type;
    private Image sprite;
    private final boolean isWhite;

    public ChessPiece(PieceType type, boolean isWhite) {
        this.type = type;
        this.isWhite = isWhite;
        this.sprite = ImageHelper.loadImage(type.toString().toLowerCase() + (isWhite ? "_white" : ""));
    }

    public Image getSprite() {
        return sprite;
    }

    public PieceType getType() {
        return type;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public enum PieceType {
        KING, ROOK, BISHOP, QUEEN, KNIGHT, PAWN;
    }
}
