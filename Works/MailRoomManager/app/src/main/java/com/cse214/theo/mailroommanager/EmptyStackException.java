package com.cse214.theo.mailroommanager;

/**
 * This is a custom exception class that throws an exception whenever a package node from package stack is poped when empty.
 *
 * @author Theo Seo, SBU ID: 111319497
 *         Homework #3 for CSE 214, fall 2017
 */
public class EmptyStackException extends Exception {

    public EmptyStackException(String str) {

        super(str);

    }

}