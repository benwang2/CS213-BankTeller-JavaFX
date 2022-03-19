package com.example.cs213banktellergui;

/**
 * A class that implements basic functionality for a bank account.
 * @author Benjamin Wang, Akash Shah
 */
public abstract class Account {
    /**
     * Number of months in a year.
     */
    public static final int NUM_MONTHS = 12;
    /**
     * Minimum balance an account can have for the fee to be waived.
     */
    public static int MIN_BALANCE = 0;

    protected Profile holder;
    protected boolean closed;
    protected double balance;


    /**
     * Constructs an account object with a profile.
     * @param holder Profile to construct the account with.
     * @param amount The amount of the initial deposit.
     */
    public Account(Profile holder, double amount){
        this.holder = holder;
        this.balance = amount;
    }

    /**
     * Determine if two accounts are equal using their classes and holders.
     * @param obj Object to be compared to.
     * @return A boolean indicating if both the account class and holder match.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Account)) return false;

        Account account = (Account) obj;
        return getType().equals(account.getType()) && getHolder().equals(account.getHolder());
    }

    /**
     * Generates a printable string for an account.
     * @return A printable string formatted as "type::holder::Balance $XXX::CLOSED"
     */
    @Override
    public String toString() {
        return this.getType()+"::"+this.holder.toString()
                +"::Balance $"+String.format("%,.2f",this.balance)
                +(this.closed ? "::CLOSED":"");
    }

    /**
     * Gets the balance of an account.
     * @return the balance of an account.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Withdraw an amount from an account if it is open.
     * @param amount Amount to be withdrawn from the account.
     */
    public void withdraw(double amount) {
        if (this.closed) return; // Can not withdraw from closed account.
        if(amount > MIN_BALANCE)
            this.balance = this.balance - amount;
    }

    /**
     * Charges an account for its monthly fee
     */
    public void chargeFee(){
        this.balance = this.balance - this.fee();
    };


    /**
     * Deposit an amount of money into an account if it is open
     * @param amount Amount to be deposited into the account.
     */
    public void deposit(double amount) {
        if (this.closed) return; // Can not deposit into closed account.
        if(amount > 0)
            this.balance = this.balance + amount;
    }

    /**
     * Opens the account.
     */
    public void open(Account account){
        this.balance = account.balance;
        this.closed = false;
    }

    /**
     * Closes the account.
     */
    public void close(){
        this.closed = true;
        this.balance = 0;
    }

    /**
     * Calculates the monthly interest for the given account.
     * @return Computed monthly interest
     */
    public abstract double monthlyInterest();

    /**
     * Calculates the monthly fee for the given account.
     * @return Computed monthly fee
     */
    public abstract double fee();

    /**
     * Gets the type of the account.
     * @return Account type
     */
    public abstract String getType();

    /**
     * Gets the holder of the account
     * @return Account holder
     */
    public Profile getHolder(){
        return holder;
    }

    /**
     * Gets the closed value of an Account.
     * @return Boolean value closed
     */
    public boolean getClosed(){
        return closed;
    }
}