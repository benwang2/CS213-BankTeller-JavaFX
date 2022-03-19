package com.example.cs213banktellergui;

import static com.example.cs213banktellergui.AccountType.MM;

/**
 * A Money Market savings account that offers higher interest rates, higher minimum balance, and maximum withdrawals.
 * Accounts with a balance over a certain amount are loyal
 * @author Benjamin Wang, Akash Shah
 */
public class MoneyMarket extends Savings {
    /**
     * The monthly fee to be charged if an account has exceeded the withdraw threshold or is under the minimum balance.
     */
    private static final int MONTHLY_FEE = 10;
    /**
     * Minimum balance an account can have for the fee to be waived.
     */
    public static final int MIN_BALANCE = 2500;
    /**
     * The annual interest rate of the account.
     */
    private static final double INTEREST_RATE = 0.008;
    /**
     * The name of the account type.
     */
    private static final String ACCOUNT_TYPE = "Money Market Savings";
    /**
     * The max amount of withdrawals that can be made on the account.
     */
    private static final int MAX_WITHDRAWALS = 3;

    private int withdrawals = 0;



    /**
     * Constructs a money market with an initial amount.
     * @param holder The account holder
     * @param initialAmount The initial sum of money in the account.
     */
    public MoneyMarket(Profile holder, double initialAmount){
        super(holder, initialAmount, (initialAmount >= 2500) ? 1 : 0);
        balance = initialAmount;
    }

    /**
     * Withdraws an amount from the money market account.
     * @param amount Amount to be withdrawn from the account.
     */
    public void withdraw(double amount){
        super.withdraw(amount);
        withdrawals++;
        this.isLoyal = (this.balance < MIN_BALANCE) ? 0 : 1;
    }

    /**
     * Deposit an amount into an account.
     * @param amount Amount to be deposited into the account.
     */
    public void deposit(double amount){
        super.deposit(amount);
        this.isLoyal = (this.balance < MIN_BALANCE) ? 0 : 1;
    }

    /**
     * Calculates the monthly fee.
     * @return The computed monthly fee
     */
    @Override
    public double fee(){
        if (super.fee() > 0)
            return super.fee();

        if (withdrawals > MAX_WITHDRAWALS)
            return MONTHLY_FEE;

        if (this.balance < MIN_BALANCE)
            return MONTHLY_FEE;

        return 0;
    }

    /**
     * Closes the account and resets the number of withdrawals.
     */
    @Override
    public void close(){
        super.close();
        this.balance = 0;
        this.withdrawals = 0;
    }

    /**
     * Determines if two accounts are equal or not
     * @param obj Object to compare with
     * @return If two accounts are of the same type and have the same holder.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MoneyMarket)) return false;
        MoneyMarket account = (MoneyMarket)obj;
        return this.holder.equals(account.holder);
    }

    /**
     * Generates a printable string for an account.
     * @return A printable string formatted as "type::holder::Balance $XXX::LOYAL::WITHDRAWALS: X"
     */
    @Override
    public String toString(){
        return super.toString() + "::WITHDRAWALS: "+withdrawals;
    }

    /**
     * Gets the type of the account.
     * @return Account type
     */
    public String getType(){
        return ACCOUNT_TYPE;
    }

    //TODO Figure out how to inherit behavior from Savings but also use subclass constants.
    /**
     * Gets the interest rate of a checking account.
     * @return The interest rate of the account as a double.
     */
    @Override
    public double getInterestRate() { return INTEREST_RATE; }

    /**
     * Gets the additional interest rate.
     * @return The additional interest.
     */
    @Override
    public double getAdditionalInterest(){ return ADDITIONAL_INTEREST; }

    /**
     * Gets the monthly fee.
     * @return The monthly fee as an integer.
     */
    @Override
    public double getMonthlyFee(){ return MONTHLY_FEE; }
}