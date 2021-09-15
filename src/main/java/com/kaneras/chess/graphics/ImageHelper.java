package com.kaneras.chess.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class ImageHelper {
    private static final HashMap<String, Image> LOADED_IMAGES = new HashMap<>();
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
