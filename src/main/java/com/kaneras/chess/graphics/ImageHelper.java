package com.kaneras.chess.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * A class to help in loading and displaying images
 */
public class ImageHelper {
    // All previously loaded images
    private static final HashMap<String, Image> LOADED_IMAGES = new HashMap<>();

    /**
     * Load an image from file
     * @param imageName The path of the image, shouldn't include .png at the end.
     * @return The image object loaded; null if the file couldn't be found.
     */
    public static Image loadImage(String imageName){
        try {
            if (LOADED_IMAGES.containsKey(imageName))
                return LOADED_IMAGES.get(imageName);

            Image image = new Image(ImageHelper.class.getResourceAsStream( "/" + imageName + ".png"));
            LOADED_IMAGES.put(imageName, image);
            return image;
        } catch (NullPointerException e) {
            System.out.println("ERROR: File not found for " + imageName + ".png");
        }
        return null;
    }

    /**
     * Draw the image to the canvas. Will draw a pink and black square if the image is null.
     * @param graphics The graphics context for the canvas.
     * @param image The image to draw
     * @param x The leftmost x position to draw the image
     * @param y The uppermost y position to draw the image
     * @param w The width of the image
     * @param h The height of the image
     */
    public static void drawImage(GraphicsContext graphics, Image image, int x, int y, int w, int h) {
        if (image == null) {
            graphics.setFill(Color.PINK);
            graphics.fillRect(x, y, w/2, h/2);
            graphics.fillRect(x + h/2, y + w/2, w/2, h/2);
            graphics.setFill(Color.BLACK);
            graphics.fillRect(x + w/2, y, w/2, h/2);
            graphics.fillRect(x, y + h/2, w/2, h/2);
            return;
        }
        graphics.drawImage(image, x, y, w, h);
    }
}
