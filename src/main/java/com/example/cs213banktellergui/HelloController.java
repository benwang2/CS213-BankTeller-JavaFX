package com.example.cs213banktellergui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class HelloController {
    @FXML
    private TextField firstName, lastName, dateOfBirth, amount;

    @FXML
    private ToggleGroup accountType, accountAction, campus;

    @FXML
    private VBox printPane, accountPane;

    @FXML
    private Label textOutput;

    @FXML
    protected void submit() {
        textOutput.setText(accountType.getSelectedToggle().toString());
//        textOutput.setText(accountType.getSelectedToggle().toString());
    }

    private void mutexVisibility(VBox show, VBox hide){
        show.setVisible(true);
        hide.setVisible(false);
    }

    @FXML
    protected void displayAccountPane(){
        mutexVisibility(accountPane, printPane);
    }

    @FXML
    protected void displayPrintPane(){
        mutexVisibility(printPane, accountPane);
    }
}