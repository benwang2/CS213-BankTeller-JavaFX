package com.example.cs213banktellergui;
import java.util.Calendar;

/**
 * An object that implements manipulating date and date validation.
 * @author Benjamin Wang, Akash Shah
 */
public class Date implements Comparable<Date> {
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUARTERCENTENNIAL = 400;

    private static final int FEBRUARY = 2;
    private static final int[] MONTH_LENGTHS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private Calendar calendar = Calendar.getInstance();
    private int year;
    private int month;
    private int day;

    /**
     * Converts a formatted string to a Date object.
     * @param date String in mm/dd/yy format
     */
    public Date(String date) {
        String[] myDate = date.split("/", 3);
        month = Integer.parseInt(myDate[0]);
        day = Integer.parseInt(myDate[1]);
        year = Integer.parseInt(myDate[2]);
    }

    /**
     * Creates a Date object with the current month, day, and year.
     */
    public Date() {
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        year = calendar.get(Calendar.YEAR);
    }

    /**
     * Determines if a year is a leap year following a certain criteria.
     * @return Returns if a year is a leap year.
     */
    private boolean isLeapYear() {
        if (this.year % QUADRENNIAL != 0)
            return false;

        if (this.year % CENTENNIAL != 0)
            return true;
        else if (this.year % QUARTERCENTENNIAL != 0)
            return false;

        return true;
    }

    /**
     * Determines whether a Date is valid or not.
     * @return Response
     */
    public boolean isValid() {
        if (this.month < 1 || this.month > 12)
            return false;

        if (this.day < 1)
            return false;

        if (this.year < 1900)
            return false;

        if (this.month == FEBRUARY && isLeapYear()) {
            if (this.day > MONTH_LENGTHS[this.month - 1] + 1)
                return false;
        } else {
            if (this.day > MONTH_LENGTHS[this.month - 1])
                return false;
        }

        return true;
    }

    /**
     * Compares one date to another and describes them relatively to each others.
     * @param date Date to compare with.
     * @return Returns -1, 0, or 1, describing which date object is earlier or later.
     */
    @Override
    public int compareTo(Date date) {
        if (date.year != this.year)
            return (this.year > date.year ? 1 : -1);

        if (date.month != this.month)
            return (this.month > date.month ? 1 : -1);

        if (date.day != this.day)
            return (this.day > date.day ? 1 : -1);

        return 0;
    }

    /**
     * Generates a formatted string for a date object.
     * @return A string formatted as "MM/DD/YYYY".
     */
    public String toString(){
        return this.month+"/"+this.day+"/"+this.year;
    }
}