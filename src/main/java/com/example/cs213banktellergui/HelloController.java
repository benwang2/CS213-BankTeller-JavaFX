package com.example.cs213banktellergui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HelloController {

    AccountDatabase  database = new AccountDatabase();

    @FXML private TextField firstName, lastName, dateOfBirth, amount;
    @FXML private ToggleGroup accountType, accountAction, campus, printGroup;
    @FXML private RadioButton closeRadio;
    @FXML private RadioButton savingsRadio, collegeCheckingRadio;
    @FXML private HBox campusPane, loyaltyPane;
    @FXML private VBox printPane, accountPane;
    @FXML private TextArea textOut;
    @FXML private CheckBox loyalCheckbox;


    /**
     * A helper function that runs
     */
    @FXML
    protected void submit(){
        if (accountPane.isVisible()){
            String result = checkValidAccount();
            RadioButton selectedAccountActionButton = (RadioButton) accountAction.getSelectedToggle();
            if(result.equals("Missing data")){
                if(selectedAccountActionButton.getText().equals("Close")){
                    printToOutput(result + " for closing an account.");
                }
                else if(selectedAccountActionButton.getText().equals("Open")){
                    printToOutput(result + " for opening an account.");
                }
            } else if(result.equals("Ok")) {
                Account account = generateAccount();
                switch (selectedAccountActionButton.getText()) {
                    case "Open" -> printToOutput(checkOpenAccount(account));
                    case "Close" -> printToOutput(checkCloseAccount(account));
                    case "Deposit" -> printToOutput(checkDepositCommand(account));
                    case "Withdraw" -> printToOutput(checkWithdrawCommand(account));
                }
            }
            else{
                printToOutput(result);
            }
        } else if (printPane.isVisible()){
            RadioButton selectedPrintActionButton = (RadioButton) printGroup.getSelectedToggle();

            switch (selectedPrintActionButton.getId()){
                case "printByType" -> printToOutput(database.printByAccountType());
                case "printByInterest" -> printToOutput(database.printFeeAndInterest());
                case "printAll" -> printToOutput(database.print());
                case "updateBalances" -> printToOutput(database.updateBalances());
            }
        }
    }

    private void printToOutput(String text){
        textOut.appendText(text+"\n");
    }

    private int getSelectedCampus(){
        int selectedCampus = -1;
        RadioButton selectedCampusButton = (RadioButton) campus.getSelectedToggle();
        switch (selectedCampusButton.getId()){
            case "newBrunswickRadio" -> selectedCampus = 0;
            case "newarkRadio" ->  selectedCampus = 1;
            case "camdenRadio" -> selectedCampus = 2;
        }
        return selectedCampus;
    }

    private Account generateAccount(){
        Account account = null;
        RadioButton selectedAccountTypeButton = (RadioButton) accountType.getSelectedToggle();
        double balance = Double.parseDouble(amount.getText());
        Profile profile = new Profile(firstName.getText(), lastName.getText(), dateOfBirth.getText());

        switch(selectedAccountTypeButton.getText()){

            case "Checking" -> account = new Checking(profile, balance);
            case "Savings" -> account = new Savings(profile, balance, loyalCheckbox.isSelected() ? 1:0);
            case "Money Market" -> account = new MoneyMarket(profile, balance);
            case "College Checking" -> account = new CollegeChecking(profile, balance, getSelectedCampus());
        }

        return account;
    }

    private String checkValidAccount() {
        if (firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty()
                || dateOfBirth.getText().trim().isEmpty()
                || amount.getText().trim().isEmpty()) {
            return "Missing data";
        } else {
            Profile profile = new Profile(firstName.getText(), lastName.getText(), dateOfBirth.getText());
            if (!profile.isValid()) return "Date of birth invalid.";
            try {
                Double.parseDouble(amount.getText());
            } catch (NumberFormatException e) {
                return "Not a valid amount.";
            }

            return "Ok";
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
            return "Initial deposit cannot be 0 or negative.";

        if (account instanceof MoneyMarket) {
            MoneyMarket mm = (MoneyMarket) account;
            if (mm.getBalance() < mm.MIN_BALANCE)
                return "Minimum of $2500 to open a MoneyMarket account.";
        }

        if (database.open(account)) {
            return accountExists ? "Account reopened." : "Account opened.";
        } else
            return account.getHolder().toString()+" same account(type) is in the database.";
    }

    private String checkCloseAccount(Account account){
        boolean accountExists = database.doesAccountExist(account);
        if (accountExists) {
            boolean isClosed = database.isClosed(account);
            database.close(account);
            return isClosed ? "Account is already closed." : "Account closed.";
        } else
            return "Can not close a non-existent account.";
    }

    private String checkDepositCommand(Account account){
        boolean accountExists = database.doesAccountExist(account);
        if(!accountExists)
            return account.getHolder().toString() + " " +account.getType() + " is not in the database.";
        else
        if (account.getBalance() <= 0)
            return "Deposit - amount cannot be 0 or negative.";
        else {
            database.deposit(account);
            return "Deposit - balance updated.";
        }
    }

    private String checkWithdrawCommand(Account account){
        boolean accountExists = database.doesAccountExist(account);
        if(!accountExists)
            return (account.getHolder().toString() + " " +account.getType() + " is not in the database.");
        else
        if (account.getBalance() <= 0)
            return "Withdraw - amount cannot be 0 or negative.";
        else
            return database.withdraw(account) ? "Withdraw - balance updated." : "Withdraw - insufficient fund.";
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

    @FXML
    protected void onAccountTypeChanged(){
        loyaltyPane.setDisable(!savingsRadio.isSelected());
        campusPane.setDisable(!collegeCheckingRadio.isSelected());
    }
}