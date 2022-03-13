package com.example.cs213banktellergui;

/**
 * An object that describes an account holder.
 * @author Benjamin Wang, Akash Shah
 */

public class Profile {
    /**
     * The current calendar date.
     */
    private static final Date today = new Date();

    /**
     * The first name of the profile.
     */
    private final String fname;
    /**
     * The last name of the profile.
     */
    private final String lname;
    /**
     * The date of birth of the profile.
     */
    private final Date dob;

    /**
     * Constructs a Profile from string objects including date of birth, first name, and last name.
     * @param dob Date of birth
     * @param fname First name
     * @param lname Last name
     */
    public Profile (String fname, String lname, String dob) {
        this.dob = new Date(dob);
        this.fname = fname;
        this.lname = lname;
    }

    /**
     * Determines if a profile is valid or not based on their birthdate.
     * @return Returns if a profile's birthday is a valid date and before today.
     */
    public boolean isValid(){
        if (!this.dob.isValid())
            return false;

        if (this.dob.compareTo(today) >= 0)
            return false;

        return true;
    }

    /**
     * Checks if all attributes are equal to another
     * @param p Profile to be compared to
     * @return A boolean that describes whether two profiles are the same.
     */
    public boolean equals(Profile p){
        return  (this.fname.equalsIgnoreCase(p.fname)
                && (this.lname.equalsIgnoreCase(p.lname))
                && (this.dob.compareTo(p.dob)) == 0);
    }

    /**
     * Generates a printable string for a Profile object.
     * @return String formatted as "fname lname dateofbirth"
     */
    public String toString() {
        return this.fname + " " + this.lname + " " + this.dob.toString();
    }

}