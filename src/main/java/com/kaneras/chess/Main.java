package com.kaneras.chess;

import com.kaneras.chess.graphics.Screen;
import com.kaneras.chess.logic.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Chess");

        Game.init();
        Screen.init();

        VBox layout = new VBox();
        layout.getChildren().addAll(Game.getCanvas());
        Scene scene = new Scene(layout, Properties.StaticProperties.MIN_WIDTH, Properties.StaticProperties.MIN_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
