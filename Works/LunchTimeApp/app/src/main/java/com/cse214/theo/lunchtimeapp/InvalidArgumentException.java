package com.cse214.theo.lunchtimeapp;


    /**
     * An Exception if the index is too high and would create a hole in the array
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */

public class InvalidArgumentException extends Exception {

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param str
     *      The detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public InvalidArgumentException(String str) {

        super(str);


    }


}