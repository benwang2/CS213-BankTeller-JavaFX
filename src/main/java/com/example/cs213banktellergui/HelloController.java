package com.example.cs213banktellergui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.InputMismatchException;

public class HelloController {

    AccountDatabase  database = new AccountDatabase();


    @FXML
    private TextField firstName, lastName, dateOfBirth, amount;

    @FXML
    private ToggleGroup accountType, accountAction, campus;



    @FXML
    private VBox printPane, accountPane;

    @FXML
    private Label textOutput;

    @FXML
    protected void submit(){
        RadioButton selectedAccountActionButton = (RadioButton) accountAction.getSelectedToggle();
        if(selectedAccountActionButton.getText().equals("Open")){
            textOutput.setText(checkOpeningAccount());
        }
        else if(selectedAccountActionButton.getText().equals("Close")){

        }


    }



    private String checkOpeningAccount(){
        Account account;
        if(firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty()
                || dateOfBirth.getText().trim().isEmpty()
                || amount.getText().trim().isEmpty()){
            return "Missing data for opening an account.";
        }
        else{
            Profile profile = new Profile(firstName.getText(), lastName.getText(), dateOfBirth.getText());
             if(!profile.isValid()) return "Date of birth invalid.";
             int balance = Integer.parseInt(amount.getText());
             if(balance <= 0){
                 return ("Initial deposit cannot be 0 or negative.");
             }
        RadioButton selectedAccountType = (RadioButton) accountType.getSelectedToggle();
        if(selectedAccountType.getText().equals("Money Market")) {
            account = new MoneyMarket(profile, balance);
            if (balance < 2500) { // Need to change this to mm.MIN_BALANCE
                return "Minimum of $2500 to open a MoneyMarket account.";
            }
        }
        else if (selectedAccountType.getText().equals("College Checking")){
            if(campus.getSelectedToggle() == null) return "Invalid Campus Code.";
            }

        else if (selectedAccountType.getText().equals("Savings")){
            RadioButton selectedButton =  (RadioButton) accountAction.getSelectedToggle();


            }
        }
        return "Account Opened.";
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