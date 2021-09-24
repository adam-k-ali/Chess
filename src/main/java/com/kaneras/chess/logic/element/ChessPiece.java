package com.kaneras.chess.logic.element;

import com.kaneras.chess.graphics.ImageHelper;
import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.move.Move;
import com.kaneras.chess.logic.move.MoveHandler;
import javafx.scene.image.Image;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Contains the properties of a chess piece
 */
public class ChessPiece {
    private final Deque<Move> history;

    private PieceType type;
    private final Game.Player owner;

    private final int startX;
    private final int startY;

    private int currX;
    private int currY;

    private final Image sprite;

    /**
     * Create a new chess piece
     * @param type The type of the chess piece (e.g. PAWN)
     * @param owner The owner of the chess piece (WHITE or BLACK)
     * @param startX The starting x position of the piece
     * @param startY The starting y position of the piece
     */
    public ChessPiece(PieceType type, Game.Player owner, int startX, int startY) {
        this.history = new ArrayDeque<>();

        this.type = type;
        this.owner = owner;

        this.startX = startX;
        this.startY = startY;

        this.currX = startX;
        this.currY = startY;

        this.sprite = ImageHelper.loadImage(type.toString().toLowerCase() + (owner == Game.Player.WHITE ? "_white" : ""));
    }

    public void move(int destX, int destY) {
        Move move = new Move(currX, currY, destX, destY);
        if (MoveHandler.validateMove(move)) {
            history.add(move);
            currX = destX;
            currY = destY;
        }
    }

    public Deque<Move> getHistory() {
        return history;
    }

    public PieceType getType() {
        return type;
    }

    public Game.Player getOwner() {
        return owner;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getCurrX() {
        return currX;
    }

    public int getCurrY() {
        return currY;
    }

    public void changeType(PieceType type) {
        this.type = type;
    }

    public boolean isAtStart() {
        return startX == currX && startY == currY;
    }

    public Move getLastMove() {
        return history.peekLast();
    }

    /**
     * Get the image to represent the piece on the board
     * @return The piece's sprite
     */
    public Image getSprite() {
        return sprite;
    }

}
