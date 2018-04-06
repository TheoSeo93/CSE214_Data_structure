package com.cse214.theo.lunchtimeapp;


    /**
     * This class is used in order to contain student lines, which is also a reality, into an another array.
     * In order to duplicate and compare realities, there is a necessity to have references of the realities.
     * Since there was no specified instruction for the upper bound to number of realities, I'll assign the capacity of this array as five.\
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     *
     */

public class StudentLineContainer {
    /**
     * An array of realities
     */
    private StudentLine[] realities;

    /**
     * The number of realities.
     * Increased whenever a new instance is created
     */
    private int realityCount;

    /**
     * The capacity of the array
     */
    final int CAPACITY = 2;



    /**
     * Creates new instance of a student line container
     * Since the instructions says there are two realities, the first two realities are instantiated.
     */
    public StudentLineContainer() {
        realityCount = 2;

        realities = new StudentLine[CAPACITY];

        realities[0] = new StudentLine();

        realities[1] = new StudentLine();


    }

    /**
     * Gets the number of realities
     *
     * @return the number of realities
     */
    public int getRealityCount() {
        return realityCount;

    }

    /**
     * Gets a certain student line based on given index value
     *
     * @return  the reality on the index passed.
     *
     * @throws ArrayIndexOutOfBoundsException
     *    Throws an exception if the index is invalid.
     */
    public StudentLine getStudentLine(int index) throws ArrayIndexOutOfBoundsException {

        if (index < 0 || index >= realityCount)
            throw new ArrayIndexOutOfBoundsException("The index is out of bounds");

        return realities[index];

    }
        /**
         * Sets a certain student line based on specific index value passed.
         *
         * @throws ArrayIndexOutOfBoundsException
         *
         */
    public void setStudentLine(int index, StudentLine studentLine) throws ArrayIndexOutOfBoundsException{

        if (index < 0 || index >= realityCount)

            throw new ArrayIndexOutOfBoundsException("The index is out of bounds");

        realities[index]=studentLine;
    }


}
