package com.cse214.theo.oilchangemanager;
/**
 * Doubly linked list.
 * The CarListNode contains its data as car object.
 * It has a cursor that points which moves forward and backward.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #2 for CSE 214, fall 2017
 */
public class CarListNode {

	/**
	 * The data of the node as a Car object.
	 */
	private Car data;

	/**
	 * The next node.
	 */
	private CarListNode next;

	/**
	 * The previous node.
	 */
	private CarListNode prev;

	/**
	 * Getter method for returning its data.
	 */
	public Car getData() {
		return data;
	}


	/**
	 * Setter method for data.
	 */
	public void setData(Car data) {
		this.data = data;
	}



	public CarListNode getNext() {
		return next;
	}


	/**
	 * Links next node to the parameter passed.
	 *
	 * 	@param next
	 * 		The node to be linked as next of current node.
	 */
	public void setNext(CarListNode next) {
		this.next = next;
	}


	/**
	 * Getter for previous node.
	 */
	public CarListNode getPrev() {
		return prev;
	}


	/**
	 * Links previous node to the parameter passed.
	 *
	 * 	@param prev
	 * 		The node to be linked as the previous node of current node.
	 */
	public void setPrev(CarListNode prev) {
		this.prev = prev;
	}

	/**
	 * This is used for sorting method because it is more convenient to use dummy head and dummy tail.
	 */
	public CarListNode() {

		data = null;

	}
	/**
	 * Constructs a new CarListNode with Car data.
	 *
	 * 	@param initData
	 * 			If it is null, IllegalArgumentException will be thrown.
	 *
	 */
	public CarListNode(Car initData) throws IllegalArgumentException {

		if (initData == null)
			throw new IllegalArgumentException();

		data = initData;

	}


}