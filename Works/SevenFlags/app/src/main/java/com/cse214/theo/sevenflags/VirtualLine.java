package com.cse214.theo.sevenflags;

import java.util.LinkedList;

/**
 * Virtual line that has no limit for the taking customers into the line.
 * Extends Linked List of Person.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class VirtualLine extends LinkedList<Person> {


    /**
     * Calls the add method from linked list class.
     * Adds a customer to the end of the line.
     */
	public void enqueue(Person p) {

		super.add(p);

	}

    /**
     * Calls the size method from linked list class.
     *
     * @return
     *      The size of this queue.
     */
    @Override
    public int size() {
        return super.size();
    }

    /**
     * Calls the remove method from linked list class.
     * Removes the front element from the queue.
     *
     * @return
     *      Removed person from this queue.
     */
    public Person dequeue(){

		return remove();

	}


}
