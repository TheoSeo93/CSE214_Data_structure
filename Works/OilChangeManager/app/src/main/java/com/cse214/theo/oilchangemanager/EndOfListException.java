package com.cse214.theo.oilchangemanager;
/**
 * The Exception class when the cursor has reached to the end, or when trying to remove cursor when there is nothing.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #2 for CSE 214, fall 2017
 */
public class EndOfListException extends Exception {

	public EndOfListException(String msg) {

		super(msg);

	}

}