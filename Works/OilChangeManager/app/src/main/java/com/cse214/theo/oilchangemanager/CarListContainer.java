package com.cse214.theo.oilchangemanager;

/**
 * This class is used in order to contain Car lists.
 * In order to merge car lists there is a necessity to have references of the lists.
 * This container has "Donny's list" , "Joe's list" and the "Finished list" where the cut node of the list to be pasted in array.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #2 for CSE 214, fall 2017
 *
 */
public class CarListContainer {

    /**
     * The number of the lists
     */
    private int listCount;

    /**
     *  The array of CarList.
     */
    private CarList[] listsArray;

    final int CAPACITY = 3;



    /**
     * Creates new instance of a CarList container
     */
    public CarListContainer() {

        listsArray = new CarList[CAPACITY];

        listsArray[0] = new CarList();

        listsArray[1] = new CarList();

        listsArray[2] = new CarList();

        listCount = 3;
    }

    /**
     * Returns the list by specified index from the listArray.
     *
     *  @param index
     *      The index of the listArray.
     */
    public CarList getCarList(int index) throws ArrayIndexOutOfBoundsException {

        if (index < 0 || index >= listCount)
            throw new ArrayIndexOutOfBoundsException("The index is out of bounds");

        return listsArray[index];

    }
    /**
     * Sets a list by passing the index value
     *
     *  @param index
     *      The index of the listArray.
     *
     *  @param carList
     *      The car list to be passed.
     */
    public void setCarList(int index, CarList carList) throws ArrayIndexOutOfBoundsException {

        if (index < 0 || index >= listCount)

            throw new ArrayIndexOutOfBoundsException("The index is out of bounds");

        listsArray[index] = carList;
    }



}