package com.cse214.theo.sevenflags;


/**
 * The holding queue class that has the limit of the size to contain customers on the line.
 *
 * @extends VirtualLine
 * 		This class is extended from the virtual line class which is also extended from linked list.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class HoldingQueue extends VirtualLine {

	/**
	 * The max size that this holding queue can hold people.
	 */
	private int maxSize;

	/**
	 * String array of hexadecimal colors
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * Constructor of the holding queue.
	 *
	 * @param maxSize
	 *      Maximum number of customers that this holding queue can hold.
	 *
	 */
	public HoldingQueue(int maxSize){
		this.maxSize=maxSize;
	}

	/**
	 * Enqueue method that add the specific customer passed as a parameter to the end of this queue.
	 *
	 * @param p
	 *      Person to be added to the end of this line.
	 *
	 */
	@Override
	public void enqueue(Person p)   {
		if (maxSize == size()&&size()!=0)
			return;
		else{

			super.enqueue(p);

		}

	}
}
