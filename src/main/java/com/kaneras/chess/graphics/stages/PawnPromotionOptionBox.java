package com.kaneras.chess.graphics.stages;

import com.kaneras.chess.logic.element.PieceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * To display an option box for all the options for pawn promotion
 */
public class PawnPromotionOptionBox {

    /**
     * Gives the player the list of resulting PieceTypes for pawn promotion
     * @return The PieceType chosen.
     */
    public static PieceType chooseOption() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Pawn Promotion");
        stage.setMinWidth(250);

        Label label = new Label();
        label.setText("What piece do you want to replace your pawn with?");

        ObservableList<PieceType> options = FXCollections.observableArrayList(PieceType.BISHOP,
                PieceType.KNIGHT,
                PieceType.ROOK,
                PieceType.QUEEN);
        ComboBox<PieceType> pieceTypeComboBox = new ComboBox<>(options);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(12, 12, 12, 12));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, pieceTypeComboBox, submitButton);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
        return pieceTypeComboBox.getValue();
    }
}
