package com.example.cs213banktellergui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.InputMismatchException;

public class HelloController {

    AccountDatabase  database = new AccountDatabase();


    @FXML private TextField firstName, lastName, dateOfBirth, amount;

    @FXML private ToggleGroup accountType, accountAction, campus;
    @FXML private RadioButton closeRadio;


    @FXML private VBox printPane, accountPane;

    @FXML private Label textOutput;

    @FXML private CheckBox loyalCheckbox;


    /**
     * A helper function that runs
     */
    @FXML
    protected void submit(){
        RadioButton selectedAccountActionButton = (RadioButton) accountAction.getSelectedToggle();
        if(selectedAccountActionButton.getText().equals("Open")){
            textOutput.setText(checkValidAccount());

        }else if(selectedAccountActionButton.getText().equals("Close")){

        }
    }

    private String checkValidAccount() {
        Account account = null;
        if (firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty()
                || dateOfBirth.getText().trim().isEmpty()
                || amount.getText().trim().isEmpty()) {
            return "Missing data for opening an account.";
        } else {
            Profile profile = new Profile(firstName.getText(), lastName.getText(), dateOfBirth.getText());
            if (!profile.isValid()) return "Date of birth invalid.";
            int balance;
            try {
                Integer.parseInt(amount.getText());
            } catch (NumberFormatException e) {
                return "Not a valid amount.";
            }
            balance = Integer.parseInt(amount.getText());
            RadioButton selectedAccountType = (RadioButton) accountType.getSelectedToggle();
            if (selectedAccountType.getText().equals("Money Market")) {
                account = new MoneyMarket(profile, balance);
            } else if (selectedAccountType.getText().equals("College Checking")) {
                if (campus.getSelectedToggle() == null) return "Invalid Campus Code.";
                account = new CollegeChecking(profile, balance, Integer.parseInt(selectedAccountType.getText()));
            } else if (selectedAccountType.getText().equals("Savings")) {
                account = new Savings(profile, balance, loyalCheckbox.isSelected()?1:0);
            }
            account.deposit(balance);
            RadioButton selectedAccountActionButton = (RadioButton) accountAction.getSelectedToggle();
            if (selectedAccountActionButton.getText().equals("Open")) return checkOpenAccount(account);
            else return "";
        }
    }
    /**
     * A helper function to handle the open command.
     * @param account An account object to open.
     * @return A string describing whether the account was already opened, or not enough money to open a MoneyMarket account
     */
    private String checkOpenAccount(Account account){
        boolean accountExists = database.doesAccountExist(account);

        if (!accountExists && account.getBalance() <= 0)
            return("Initial deposit cannot be 0 or negative.");

        if (account instanceof MoneyMarket) {
            MoneyMarket mm = (MoneyMarket) account;
            if (mm.getBalance() < mm.MIN_BALANCE)
                return("Minimum of $2500 to open a MoneyMarket account.");
        }

        if (database.open(account)) {
            return accountExists ? "Account reopened." : "Account opened.";
        } else
            return account.getHolder().toString()+" same account(type) is in the database.";
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

    @FXML
    protected void onAccountActionChanged(){
        amount.setDisable(closeRadio.isSelected());
    }
}