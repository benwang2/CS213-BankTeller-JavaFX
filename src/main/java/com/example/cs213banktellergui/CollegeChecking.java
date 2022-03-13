package com.example.cs213banktellergui;

/**
 * A checking account provided to college students on a verified campus.
 * College checking accounts have no monthly fees and a higher interest rate than regular checking account.
 * @author Benjamin Wang, Akash Shah
 */
public class CollegeChecking extends Checking {
    /**
     * The annual interest rate of the account.
     */
    private static final double INTEREST_RATE = 0.0025;
    /**
     * The possible campuses a college student can reside on.
     */
    public static final String[] CAMPUSES = {"New Brunswick","Newark","Camden"};
    /**
     * The name of the account type.
     */
    private static final String ACCOUNT_TYPE = "College Checking";

    /**
     * The campus code of the account.
     */
    private int campus;

    /**
     * Constructs a College Checking account given an account holder and a campus code.
     * @param holder Profile of the account holder
     * @param amount Initial amount to be deposited into the account
     * @param campus Campus the holder is located on
     */
    public CollegeChecking(Profile holder, double amount, int campus){
        super(holder,amount);
        this.campus = campus;
    }

    /**
     * Opens or reopens an existing college checking account with the parameters provided.
     * @param account Account object containing parameters to open the account with.
     */
    @Override
    public void open(Account account){
        super.open(account);
        CollegeChecking collegeChecking = (CollegeChecking) account;
        this.campus = collegeChecking.campus;
    }

    /**
     * Generates a printable string for a college checking account.
     * @return A string formatted as type::holder::Balance $XXX::campus
     */
    @Override
    public String toString(){
        return super.toString() + "::" + CAMPUSES[campus]; // Replace campus integer with proper print string
    }

    /**
     * Determines if two accounts are equal or not
     * @param obj Object to compare with
     * @return If two accounts are of the same type and have the same holder.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CollegeChecking)) return false;
        CollegeChecking account = (CollegeChecking) obj;
        return this.holder.equals(account.holder);
    }

    /**
     * Gets the monthly fee
     * @return The monthly fee of the account.
     */
    public double getMonthlyFee(){ return 0; }

    /**
     * Gets the type of the account
     * @return A string indicating the account type.
     */
    public String getType(){
        return ACCOUNT_TYPE;
    }

    /**
     * Gets the interest rate of the account
     * @return The interest rate of the account as a string.
     */
    @Override
    public double getInterestRate() { return INTEREST_RATE; }

    /**
     * Gets the campus code of the account.
     * @return The campus code of the account as an integer.
     */
    public int getCampus(){ return campus; }

    /**
     * Sets the campus code of a closed account.
     */
    public void setCampus(int campus){
        this.campus = campus;
    }

}