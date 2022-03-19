package com.example.cs213banktellergui;

import static com.example.cs213banktellergui.AccountType.MM;
import static com.example.cs213banktellergui.AccountType.S;

/**
 * A standard savings account.
 * @author Benjamin Wang, Akash Shah
 */
public class Savings extends Account {
    /**
     * Minimum balance an account can have for the fee to be waived.
     */
    private static final int MIN_BALANCE = 300;
    /**
     * The monthly fee to be charged if an account does not exceed the minimum balance.
     */
    private static final int MONTHLY_FEE = 6;

    /**
     * The annual interest rate of the account.
     */
    private static final double INTEREST_RATE = 0.003;
    /**
     * The additional interest for loyal account holders.
     */
    protected static final double ADDITIONAL_INTEREST = 0.0015;

    /**
     * The name of the account type.
     */
    public static final String ACCOUNT_TYPE = "Savings";


    protected int isLoyal;

    /**
     * Constructs a Savings account given a profile
     * @param holder The account holder
     * @param amount The amount of the initial deposit.
     * @param loyalty The loyalty status of the account.
     */
    public Savings(Profile holder, double amount, int loyalty) {
        super(holder, amount);
        this.isLoyal = loyalty;
    }

    /**
     * Determines if two accounts are equal or not
     * @param obj Object to compare with
     * @return If two accounts are of the same type and have the same holder.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Savings)) return false;
        Savings acc = (Savings) obj;
        return this.holder.equals(acc.holder);
    }

    @Override
    public String toString(){
        return super.toString() + (this.isLoyal==1 ? "::Loyal" : "");
    }

    /**
     * Calculates the monthly fee.
     * @return The computed monthly fee
     */
    @Override
    public double fee(){
        if (this.balance < this.MIN_BALANCE)
            return this.getMonthlyFee();

        return 0;
    }

    /**
     * Calculates the monthly interest.
     * @return The computed monthly interest.
     */
    @Override
    public double monthlyInterest(){
        return (
                this.balance * (this.isLoyal==1
                        ? this.getInterestRate() + this.getAdditionalInterest()
                        : this.getInterestRate()
                ) / NUM_MONTHS
        );
    }

    /**
     * Opens or reopens an existing savings account with the parameters provided.
     * @param account Account object containing parameters to open the account with.
     */
    @Override
    public void open(Account account){
        super.open(account);
        Savings savings = (Savings) account;
        this.isLoyal = savings.isLoyal;
    }

    /**
     * Gets the account type.
     * @return The account type as a string.
     */
    public String getType(){
        return ACCOUNT_TYPE;
    }

    /**
     * Gets the interest rate of a checking account.
     * @return The interest rate of the account as a double.
     */
    public double getInterestRate() { return this.INTEREST_RATE; }

    /**
     * Gets the additional interest rate.
     * @return The additional interest.
     */
    public double getAdditionalInterest(){ return this.ADDITIONAL_INTEREST; }

    /**
     * Gets the monthly fee.
     * @return The monthly fee as an integer.
     */
    public double getMonthlyFee(){ return this.MONTHLY_FEE; }
}