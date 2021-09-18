package com.kaneras.chess.graphics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void showAlert(String title, String message) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button button = new Button("Ok");
        button.setOnAction(e -> stage.close());

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(12, 12, 12, 12));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, button);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
