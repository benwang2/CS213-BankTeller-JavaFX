package com.example.cs213banktellergui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label textOutput;

    @FXML
    protected void onHelloButtonClick() {
        textOutput.setText("Welcome to JavaFX Application!");
    }
}