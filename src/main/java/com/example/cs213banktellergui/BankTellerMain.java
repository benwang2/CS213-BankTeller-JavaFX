package com.example.cs213banktellergui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class that runs the GUI
 * @author Benjamin Wang, Akash Shah
 */



public class BankTellerMain extends Application {
    /**
     * Creates and displays a scene given the FXML file on a certain stage
     * @param stage The stage that the scene is displayed onto
     * @throws IOException If FXML file doesn't exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("bankteller-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bank Teller!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Driver function
     * @param args
     */

    public static void main(String[] args) {
        launch();
    }
}