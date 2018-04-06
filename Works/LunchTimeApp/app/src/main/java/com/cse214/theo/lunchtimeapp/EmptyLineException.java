package com.cse214.theo.lunchtimeapp;

    /**
     * Exception class when there is no student in the line
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class EmptyLineException extends Exception {

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param str
     * The detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public EmptyLineException(String str) {
        super(str);

    }
}