package com.cse214.theo.oilchangemanager;
import java.io.Serializable;

/**
 * The data of the car list node which contains both the enum object make, and the string value, name of the owner.
 * This class implements Serializable because this data have to be passed among fragments.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #2 for CSE 214, fall 2017
 */
public class Car implements Serializable {

    /**
     * The enum object, make, whose kinds are Ford, GMC, Chevy, Jeep, Dodge, Chrysler, and Lincoln.
     */
    private Make make;

    /**
     * The string value, name of the owner.
     */
    private String owner;

    /**
     * Constructs a new Car object with the parameter Make and String value of the car owner name.
     *
     * @param initMake
     * 		The enum object, make, whose kinds are Ford, GMC, Chevy, Jeep, Dodge, Chrysler, and Lincoln.
     *
     * @param initOwner
     * 		The string value, name of the owner.
     *
     */
    public Car(Make initMake, String initOwner) {

        make = initMake;

        owner = initOwner;

    }

    /**
     * An overloaded constructor used in order to construct a temporary car object to remove a cursor.
     */
    public Car() {
        make = null;
        owner = null;
    }

    /**
     * Getter method for the variable make.
     */
    public Make getMake() {
        return make;
    }

    /**
     * Setter method for the variable make
     */
    public void setMake(Make make) {
        this.make = make;
    }

    /**
     * Getter method for the string owner.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Setter method for the variable owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * The enum class that contains kinds of the makers.
     * This class has been written as an inner class.
     *
     */
    public enum Make implements Serializable {

        FORD,
        GMC,
        CHEVY,
        JEEP,
        DODGE,
        CHRYSLER,
        LINCOLN;
    }


}