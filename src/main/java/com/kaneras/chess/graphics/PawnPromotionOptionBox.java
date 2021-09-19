package com.kaneras.chess.graphics;

import com.kaneras.chess.logic.ChessPiece;
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

public class PawnPromotionOptionBox {
    public static ChessPiece.PieceType chooseOption() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Pawn Promotion");
        stage.setMinWidth(250);

        Label label = new Label();
        label.setText("What piece do you want to replace your pawn with?");

        ObservableList<ChessPiece.PieceType> options = FXCollections.observableArrayList(ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.QUEEN);
        ComboBox<ChessPiece.PieceType> pieceTypeComboBox = new ComboBox<>(options);

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
