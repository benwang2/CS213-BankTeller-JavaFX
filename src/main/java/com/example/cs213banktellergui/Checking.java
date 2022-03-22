package com.example.cs213banktellergui;

import static com.example.cs213banktellergui.AccountType.C;

/**
 * A standard checking account.
 * @author Benjamin Wang, Akash Shah
 */
public class Checking extends Account {
    /**
     * Monthly fee to be charged if balance is below the minimum balance.
     */
    private static final int MONTHLY_FEE = 25;
    /**
     * Minimum balance an account can have for the fee to be waived.
     */
    private static final int MIN_BALANCE = 1000;
    /**
     * The annual interest rate of the account.
     */
    private static final double INTEREST_RATE = 0.001;




    /**
     * Constructs a checking account given an account holder.
     * @param holder The account holder
     * @param amount The amount of the initial deposit.
     */
    public Checking(Profile holder, double amount){
        super(holder, amount);
    }

    /**
     * Calculates the monthly interest of a checking account.
     * @return The computed monthly interest.
     */
    public double monthlyInterest(){
        return (
                this.balance * this.getInterestRate() / NUM_MONTHS
        );
    }

    /**
     * Calculates the monthly fee of a checking account.
     * @return The computed monthly fee.
     */
    public double fee(){
        if (this.balance < MIN_BALANCE)
            return this.getMonthlyFee();
        return 0;
    }

    /**
     * Gets the monthly fee
     * @return The monthly fee of the account.
     */
    public double getMonthlyFee(){ return MONTHLY_FEE; }

    /**
     * Gets the account type.
     * @return The account type as a string.
     */
    public String getType(){
        return "Checking";
    }

    /**
     * Gets the interest rate of a checking account.
     * @return The interest rate of the account as a double.
     */
    public double getInterestRate() { return INTEREST_RATE; }
}