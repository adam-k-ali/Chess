package com.kaneras.chess.graphics;

import com.kaneras.chess.logic.Game;
import com.kaneras.chess.logic.GridTile;
import com.kaneras.chess.logic.move.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Screen {
    private static GraphicsContext graphics;

    public static void init() {
        Screen.graphics = Game.getCanvas().getGraphicsContext2D();

        drawGrid();
    }

    public static void refresh() {
        graphics.clearRect(0, 0, Game.getCanvas().getWidth(), Game.getCanvas().getHeight());
        drawGrid();
    }

    private static void drawGrid() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                drawGridTile(Game.getTile(x, y));
            }
        }
    }

    private static void drawGridTile(GridTile tile) {
        int px = tile.getX() * Game.getTileSize();
        int py = tile.getY() * Game.getTileSize();
        if (!tile.isWhiteBackground()) {
            graphics.setFill(Color.BLACK);
            graphics.fillRect(px, py, Game.getTileSize(), Game.getTileSize());
        }

        if (tile.getPiece() != null)
            ImageHelper.drawImage(graphics, tile.getPiece().getSprite(), px, py, Game.getTileSize(), Game.getTileSize());

        if (Game.isSelected(tile.getX(), tile.getY())) {
            graphics.setFill(Color.BLUE);
            graphics.setGlobalAlpha(0.2);
            graphics.fillRect(px, py, Game.getTileSize(), Game.getTileSize());
            graphics.setGlobalAlpha(1.0);
        } else if (Game.isHoveredOver(tile.getX(), tile.getY())) {
            graphics.setFill(Color.RED);

            if (Game.getSelectedTile() != null) {
                int startX = Game.getSelectedTile().getX();
                int startY = Game.getSelectedTile().getY();
                int finishX = Game.getHoveredTile().getX();
                int finishY = Game.getHoveredTile().getY();
                switch (Game.getSelectedTile().getPiece().getType()) {
                    case KING:
                        if (new KingMoveHelper(startX, startY, finishX, finishY).isValidMove()) {
                            graphics.setFill(Color.GREEN);
                        }
                        break;
                    case QUEEN:
                        if (new QueenMoveHelper(startX, startY, finishX, finishY).isValidMove()) {
                            graphics.setFill(Color.GREEN);
                        }
                        break;
                    case BISHOP:
                        if (new BishopMoveHelper(startX, startY, finishX, finishY).isValidMove()) {
                            graphics.setFill(Color.GREEN);
                        }
                        break;
                    case PAWN:
                        if (new PawnMoveHelper(startX, startY, finishX, finishY).isValidMove()) {
                            graphics.setFill(Color.GREEN);
                        }
                        break;
                    case ROOK:
                        if (new RookMoveHelper(startX, startY, finishX, finishY).isValidMove()) {
                            graphics.setFill(Color.GREEN);
                        }
                        break;
                    case KNIGHT:
                        if (new KnightMoveHelper(startX, startY, finishX, finishY).isValidMove()) {
                            graphics.setFill(Color.GREEN);
                        }
                        break;
                }
            }

            graphics.setGlobalAlpha(0.2);
            graphics.fillRect(px, py, Game.getTileSize(), Game.getTileSize());
            graphics.setGlobalAlpha(1.0);
        }
    }
}
