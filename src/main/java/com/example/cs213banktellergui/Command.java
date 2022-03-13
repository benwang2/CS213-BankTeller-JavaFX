package com.example.cs213banktellergui;

/**
 * An enum class that contains the different command types.
 * @author Benjamin Wang, Akash Shah
 */
public enum Command {
    /**
     * Open an account with the desired account type.
     */
    O,
    /**
     * Close an account.
     */
    C,
    /**
     * Deposit money into an existing account.
     */
    D,
    /**
     * Withdraw money from an existing account.
     */
    W,
    /**
     * Display all the accounts in the database.
     */
    P,
    /**
     * Display all the accounts in the database, ordered by account type.
     */
    PT,
    /**
     * Display all the accounts in the database with calculated fees and monthly interests.
     */
    PI,
    /**
     * Update the balances for all accounts with the calculated fees and monthly interests.
     */
    UB,
    /**
     * Stop the program execution.
     */
    Q
}