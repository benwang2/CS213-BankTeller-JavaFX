package com.example.cs213banktellergui;

/**
 * Class that holds all accounts and implements methods to manipulate a database of accounts.
 * @author Benjamin Wang, Akash Shah
 */
public class AccountDatabase {
    /**
     * Array structure containing all the accounts in a database.
     */
    private Account [] accounts;
    private int numAccts;
    /**
     * Integer return for when an account is not found in the database.
     */
    private final int NOT_FOUND = -1;

    /**
     * Constructs an AccountDatabase object.
     */
    public AccountDatabase() {
        accounts = new Account[4];
        this.numAccts = 0;
    }

    /**
     * Finds an account in the database that is of the same class and holder.
     * @param account Account to be found in the database.
     * @return An account object with the same class and holder as the parameter.
     */
    private int find(Account account) {
        for (int i = 0; i < numAccts; i++)
            if (account.equals(accounts[i]))
                return i;

        return NOT_FOUND;
    }

    /**
     * Increases the size of the account database by 4.
     */
    private void grow() {
        Account [] newAccounts = new Account[this.accounts.length + 4];

        for(int i = 0; i < this.numAccts; i++)
            newAccounts[i] = this.accounts[i];

        this.accounts = newAccounts;
    }

    /**
     * Inserts an account into the database.
     * @param account The account to inserted into the database.
     */
    private void insert(Account account){
        if (numAccts == this.accounts.length)
            grow();

        this.accounts[this.numAccts] = account;
        numAccts++;
    }

    /**
     * Updates all open accounts in the database, adjusting them for fees and interests.
     * @return A list of the updated accounts formatted as a String.
     */
    public String updateBalances(){
        if (this.numAccts == 0) return "Account database is empty.";
        String printString = "\n*list of accounts with updated balance";
        for (int i = 0; i < this.numAccts; i++){
            if (!accounts[i].getClosed()) {
                accounts[i].chargeFee();
                accounts[i].deposit(accounts[i].monthlyInterest());
            }
            printString += "\n"+accounts[i].toString();
        }
        return printString+"\n*end of list.\n";
    }

    /**
     * Determines if there is another account in the database with the same holder and account type.
     * @param account Account object containing parameters to match
     * @return A boolean indicating whether a matching account can be found.
     */
    public boolean doesAccountExist(Account account){
        for (int i = 0; i < this.numAccts; i++)
            if (account.equals(accounts[i]))
                return true;

        return false;
    }

    /**
     * Determines if there is another account of the same parent type in the database.
     * @param account Account object containing parameters to check with.
     * @return A boolean indicating whether a matching account can be found.
     */
    public boolean doesTypeExist(Account account){
        for (int i = 0; i < this.numAccts; i++)
            if (accounts[i].getHolder().equals(account.getHolder()))
                if (account instanceof Checking)
                    if (accounts[i] instanceof CollegeChecking || accounts[i] instanceof Checking)
                        return true;
                else if (account instanceof Savings)
                    if (accounts[i] instanceof MoneyMarket || accounts[i] instanceof Savings)
                        return true;
        return false;
    }

    /**
     * Searches the account database for a matching account that is closed
     * @param account Account containing parameters to match
     * @return A boolean indicating whether the account is closed or not.
     */
    public boolean isClosed(Account account){
        for (int i = 0; i < this.numAccts; i++)
            if (this.accounts[i].equals(account))
                return (this.accounts[i].getClosed());
        return false;
    }

    /**
     * Opens a new or existing account in the database.
     * @param account The account to be opened.
     * @return A boolean indicating whether the account was successfully opened or not.
     */
    public boolean open(Account account) {
        int accountIndex = this.find(account);

        if (accountIndex != NOT_FOUND) {
            if (accounts[accountIndex].getClosed()) {
                accounts[accountIndex].open(account);
                accounts[accountIndex].deposit(account.getBalance());
            } else
                return false;
        } else {
            if (account instanceof Checking && doesTypeExist(account))
                return false;
            else
                this.insert(account);
        }

        return true;
    }

    /**
     * Closes an existing account in the database.
     * @param account The account to be closed.
     * @return A boolean indicating whether the account was successfully closed or not.
     */
    public boolean close(Account account) {
        int accountIndex = this.find(account);

        if (accountIndex != NOT_FOUND) {
            if (accounts[accountIndex].getClosed())
                return false;
            accounts[accountIndex].close();
        }

        return accountIndex != NOT_FOUND;
    }

    /**
     * Deposits a balance into an account with a matching profile and class.
     * @param account An account object containing a balance to deposit, class, and profile.
     */
    public void deposit(Account account) {
        int accountIndex = find(account);
        if (accountIndex != NOT_FOUND)
            accounts[accountIndex].deposit(account.getBalance());
    }

    /**
     * Withdraws a balance from an account with a matching profile and class
     * @param account An account object containing a balance to withdraw, class, and profile.
     * @return A boolean indicating whether the withdrawal was successful.
     */
    public boolean withdraw(Account account) {
        int accountIndex = find(account);
        if (accountIndex != NOT_FOUND && account.getBalance() < accounts[accountIndex].getBalance()){
            accounts[accountIndex].withdraw(account.getBalance());
            return true;
        }
        return false;
    }

    /**
     * Prints the accounts in the database in the current order.
     */
    public String print() {
        String result = "";
        if (this.numAccts == 0){
            return "Account database is empty.";
        }

        result += ("*list of accounts in the database*\n");
        for (int i = 0; i < this.numAccts; i++)
            result += this.accounts[i].toString() + "\n";
        result += ("*end of list*\n");

        return result;
    }

    /**
     * Sorts the account database by type in place, then prints it.
     */
    public String printByAccountType() {
        String result = "";
        if (this.numAccts == 0){
            return ("Account database is empty.");
        }

        for(int i = 0; i < this.numAccts - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < this.numAccts; j++) {
                Account acct1 = this.accounts[minIdx];
                Account acct2 = this.accounts[j];
                if (compareAccounts(acct2.getType(), acct1.getType()) == -1) {
                    minIdx = j;
                } else if (compareAccounts(acct2.getType(), acct1.getType()) == 0) {
                    if(acct1 instanceof CollegeChecking) {
                        CollegeChecking cc2 = (CollegeChecking) acct2;
                        CollegeChecking cc1 = (CollegeChecking) acct1;
                        if (cc2.getCampus() < cc1.getCampus()) {
                            minIdx = j;
                        } else if(cc2.getCampus() == cc1.getCampus() && (cc2.getBalance() < cc1.getBalance())){
                            minIdx = j;
                        }
                    }
                    else{
                        if(acct2.getBalance() < acct1.getBalance()){
                            minIdx = j;
                        }
                    }
                }
            }

            Account toSwap = this.accounts[i];
            this.accounts[i] = this.accounts[minIdx];
            this.accounts[minIdx] = toSwap;
        }

        result += ("*list of accounts by account type*\n");
        for (int i = 0; i < this.numAccts; i++)
            result += (this.accounts[i].toString()) + "\n";
        result += ("*end of list*\n");

        return result;
    }

    /**
     * Sorts the account database by fee and interest in place, then prints it.
     */
    public String printFeeAndInterest() {
        String result = "";

        if (this.numAccts == 0){
            return ("Account database is empty.");
        }

        result += ("*list of accounts with fee and monthly interest\n");
        for(int i =0; i < this.numAccts; i++){
            result += accounts[i].toString() +
                    "::fee $" + String.format("%,.2f",accounts[i].fee()) +
                    "::monthly interest $" + String.format("%,.2f",accounts[i].monthlyInterest()) + "\n";
            ;
        }
       result += ("*end of list.\n");
        return result;

    }

    private int getOrderValue(String acct){
        int result = 0;
        switch(acct){
            case "Checking" -> result = 0;
            case "College Checking" -> result = 1;
            case "Money Market Savings" -> result = 2;
            case "Savings" -> result = 3;
        }
        return result;
    }

    private int compareAccounts(String acct2, String acct1){
        if(getOrderValue(acct2) < getOrderValue(acct1)){
            return -1;
        }
        else if(getOrderValue(acct2) > getOrderValue(acct1)){
            return 1;
        }
        return 0;
    }
}