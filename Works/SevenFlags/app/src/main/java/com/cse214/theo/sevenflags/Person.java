package com.cse214.theo.sevenflags;

import java.util.ArrayList;
import java.util.List;

/**
 * Person which contains an integer id, the max amount of lines they can be on, array or a list of the current lines that they are on and the current status of the person (an enum).
 * This will be the data of each queue.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class Person {

	/**
	 * The person number.
	 */
	private int number;

	/**
	 * The max line that a person can reserve.
	 */
	private int maxLines;

	/**
	 * The list of the rides that a person is on the line.
	 */
	private List<Ride> lines;

	/**
	 * Status of a person whether he is on the ride, holding, or available.
	 */
	private Status status;

	/**
	 * The total number of rides that one person has ridden.
	 */
	private int howManyRides;

	/**
	 * Constructor of Person
	 *
	 * @param number
	 * 		Specific number of the person.
	 * @param maxLines
	 * 		The maximum number of rides that a person can reserve.
	 */
	public Person(int number, int maxLines){
		howManyRides=0;

		this.status=Status.Available;

		this.number=number;

		this.maxLines=maxLines;

		lines = new ArrayList<>(maxLines);

	}

	/**
	 * Constructor of Person
	 *
	 * @param number
	 * 		Specific number of the person.
	 * @throws  IllegalArgumentException
	 * 		throws when non positive number is passed
	 */
	public Person(int number) throws IllegalArgumentException{

		if(number<=0)
			throw new IllegalArgumentException();


		this.number=number;

	}

	/**
	 * Getter of status.
	 *
	 * @return  status of the person
	 *
	 */
	public Status getStatus(){ return status; }

	/**
	 * Getter of maxLines.
	 *
	 * @return  maximum lines that a person can make a reservation for rides.
	 */
	public int getMaxLines(){ return maxLines; }

	/**
	 * Getter of status.
	 *
	 * @return  status of the person
	 *
	 */
	public int getNumber(){ return number; }

	/**
	 * Increases the number of rides a person has ridden.
	 */
	public void increaseHowManyRides(){
		howManyRides+=1;
	}

	/**
	 * Getter of the number of rides.
	 *
	 * @return Total number of rides a person has ridden.
	 *
	 */
	public int getHowManyRides(){ return howManyRides; }

	/**
	 * Getter of the list of rides that a person has ridden.
	 *
	 * @return  The list of the rides.
	 *
	 */
	public List<Ride> getLines(){ return lines; }

	/**
	 * Setter of status.
	 */
	public void setStatus(Status status){
		this.status=status;

	}

}
