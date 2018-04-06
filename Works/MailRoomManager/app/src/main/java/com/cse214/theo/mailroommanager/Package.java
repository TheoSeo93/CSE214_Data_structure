package com.cse214.theo.mailroommanager;

/**
 * This is the data inside the package node. Package contains the recipient name, arrival date of the package and the weight of it.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #3 for CSE 214, fall 2017
 */
public class Package {

    /**
     * The string value of recipient name.
     */
    private String recipient;

    /**
     * The int value of the date when the package has arrived.
     */
    private int arrivalDate;

    /**
     * The double value of the weight.
     */
    private double weight;

    /**
     * Getter for recipient.
     *
     * @return
     *      Returns the name of the recipient name.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Getter for arrival date value.
     *
     * @return
     *      Returns the arrival date of the package.
     */
    public int getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Getter for the weight of package..
     *
     * @return
     *      Returns the weight of package.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Creates a new instance of data of the package node.
     *
     * @param recipient
     *      Stores the value for the recipient name.
     *
     * @param arrivalDate
     *      The arrival date when the package is sent.
     *
     * @param weight
     *      The weight of the package.
     */
    public Package(String recipient, int arrivalDate, double weight) {

        this.recipient = recipient;

        this.arrivalDate = arrivalDate;

        this.weight = weight;

    }


}