package com.example.cs213banktellergui;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class that acts as a bank teller, handling various commands and transactions.
 * @author Benjamin Wang, Akash Shah
 */
public class BankTeller {
    /**
     * AccountDatabase object containing all the accounts.
     */
    private AccountDatabase database;

    /**
     * Constructs a BankTeller object and initializes its AccountDatabase.
     */
    public BankTeller(){
           database = new AccountDatabase();
    }

    /**
     * Generates an account object from a string.
     * @param inputs A string formatted as "COMMAND ACCOUNT_TYPE FIRST_NAME LAST_NAME DATE_OF_BIRTH EXTRA_INFO"
     * @return An account object described by the string.
     * @throws Exception An exception that describes any errors in input formatting or account generation.
     */
    public Account stringToAccount(String[] inputs) throws Exception {
        AccountType accountType;
        double initialAmount = 0; // var: extra contains an extra parameter for collegeChecking and savings
        int extra = 0;
        Profile profile;
        try {
            accountType = AccountType.valueOf(inputs[1]);
            profile = new Profile(inputs[2], inputs[3], inputs[4]);
        } catch (Exception e) {
            throw new InputMismatchException();
        }
        if (!profile.isValid()) throw new Exception("Date of birth invalid.");
        try {
            if (!inputs[0].equals("C")) {
                initialAmount = Double.parseDouble(inputs[5]);
                if (inputs[0].equals("O") && (accountType == AccountType.CC || accountType == AccountType.S)) {
                    extra = Integer.parseInt(inputs[6]);
                }
            }
        } catch (Exception e) {
            throw new NumberFormatException("Not a valid amount.");
        }

        Account account = switch (accountType) {
            case C -> new Checking(profile, initialAmount);
            case CC -> new CollegeChecking(profile, initialAmount, extra);
            case S -> new Savings(profile, initialAmount, extra);
            case MM -> new MoneyMarket(profile, initialAmount);
        };

        if (account instanceof CollegeChecking){
            CollegeChecking cc = (CollegeChecking) account;
            if (extra < 0 || extra > cc.CAMPUSES.length - 1)
                throw new Exception("Invalid campus code.");
        }

        return account;
    }

    /**
     * A helper function to handle print commands.
     * @param command An enum object that describes which print function to call.
     */
    public void handlePrintCommands(Command command){
        if(command == Command.PT) database.printByAccountType();
        if(command == Command.PI) database.printFeeAndInterest();
        if(command == Command.P)  database.print();
    }

    /**
     * A helper function to handle the open command.
     * @param account An account object to open.
     * @return A string describing whether the account was already opened.
     * @throws Exception An exception describing unexpected behavior while opening the account.
     */
    public String handleOpenCommand(Account account) throws Exception {
        boolean accountExists = database.doesAccountExist(account);

        if (!accountExists && account.getBalance() <= 0)
            throw new Exception("Initial deposit cannot be 0 or negative.");

        if (account instanceof MoneyMarket) {
            MoneyMarket mm = (MoneyMarket) account;
            if (mm.getBalance() < mm.MIN_BALANCE)
                throw new Exception("Minimum of $2500 to open a MoneyMarket account.");
        }

        if (database.open(account)) {
            return accountExists ? "Account reopened." : "Account opened.";
        } else
            throw new Exception(account.getHolder().toString()+" same account(type) is in the database.");
    }

    /**
     * A helper function to handle the close command.
     * @param account An account object to close.
     * @return A string describing whether the account was already closed or not.
     * @throws Exception An exception describing any unexpected behavior that may have occurred while closing an account.
     */
    public String handleCloseCommand(Account account) throws Exception {
        boolean accountExists = database.doesAccountExist(account);
        if (accountExists) {
            boolean isClosed = database.isClosed(account);
            database.close(account);
            return isClosed ? "Account is already closed." : "Account closed.";
        } else
            throw new Exception("Can not close a non-existent account.");
    }

    /**
     * A helper function to handle the withdraw command.
     * @param account An account object containing information for withdrawal.
     * @return A string describing whether the balance was updated successfully.
     * @throws Exception An exception describing any unexpected behavior that may have occurred while withdrawing from the account.
     */
    public String handleWithdrawCommand(Account account) throws Exception {
        boolean accountExists = database.doesAccountExist(account);
        if(!accountExists)
            throw new Exception(account.getHolder().toString() + " " +account.getType() + " is not in the database.");
        else
            if (account.getBalance() <= 0)
                throw new Exception("Withdraw - amount cannot be 0 or negative.");
            else
                return database.withdraw(account) ? "Withdraw - balance updated." : "Withdraw - insufficient fund.";
    }

    /**
     * A helper function to handle the deposit command.
     * @param account An account object containing information for depositing.
     * @return A string describing whether the account was already successfully deposited into.
     * @throws Exception An exception describing any unexpected behavior that may have occurred while depositing into an account.
     */
    public String handleDepositCommand(Account account) throws Exception {
        boolean accountExists = database.doesAccountExist(account);
        if(!accountExists)
            throw new Exception(account.getHolder().toString() + " " +account.getType() + " is not in the database.");
        else
            if (account.getBalance() <= 0)
                throw new Exception("Deposit - amount cannot be 0 or negative.");
            else {
                database.deposit(account);
                return "Deposit - balance updated.";
            }
    }

    /**
     * A helper function that handles all transactions (opening, closing, depositing, and withdrawing)
     * @param inputs A string input formatted as "COMMAND ACCOUNT_TYPE FIRST_NAME LAST_NAME DATE_OF_BIRTH EXTRA_INFO"
     * @return A string describing the behavior of the call.
     * @throws Exception An exception describing any unexpected behavior encountered while processing transactions.
     */
    public String handleTransactions(String[] inputs) throws Exception {
        Command command = Command.valueOf(inputs[0]);
        Account account;
        try {account = stringToAccount(inputs);}
        catch (InputMismatchException e){
            throw new InputMismatchException("Missing data for "+(command==Command.C?"closing":"opening")+" an account");
        } catch (NumberFormatException e){
            throw e;
        }

        try {
            if (command == Command.O) {
                return handleOpenCommand(account);
            } else if (command == Command.C) {
                return handleCloseCommand(account);
            } else if (command == Command.D) {
                return handleDepositCommand(account);
            } else if (command == Command.W) {
                return handleWithdrawCommand(account);
            }
        } catch (Exception e){
            throw e;
        }

        return "OK";
    }

    /**
     * Begins a simulation of BankTeller.
     */
    public void run(){
        System.out.println("Bank Teller is running.");
        Scanner scanner = new Scanner(System.in);

        while(true){
            String [] inputs = scanner.nextLine().split("[\s\t]+");
            Command command;
            try{ command = Command.valueOf(inputs[0]); }
            catch (Exception e){ System.out.println("Invalid command!"); continue; }
            if(command == Command.Q) break;
            if(command == Command.PT || command == Command.PI || command == Command.P){
                handlePrintCommands(command);
                continue;
            }
            if(command == Command.UB){
                System.out.println(database.updateBalances());
                continue;
            }

            try {
                String response = handleTransactions(inputs);
                System.out.println(response);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}