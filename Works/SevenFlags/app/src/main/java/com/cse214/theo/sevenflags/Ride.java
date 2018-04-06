package com.cse214.theo.sevenflags;

import java.util.ArrayList;
import java.util.List;

/**
 * Ride class that has riding queue, holding queue and virtual queue.
 * Based on the duration for the ride, the time left will be decreased for every minute.
 * If time left is 0, new people on the lines will placed to the ride again.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class Ride {

    /**
     * The duration of the ride.
     * This will be set on start.
     */
    private int duration;

    /**
     *Time left for the ride to start again.
     */
    private int timeLeft;

    /**
     * The maximum number that a ride can take people for one time.
     */
    private int capacity;

    /**
     * The total number of customers that have ridden this ride.
     */
    private int howManyCustomers;

    /**
     * The specific name of this ride.
     */
    private String name;

    /**
     * Virtual line that has no limit.
     */
    private VirtualLine virtualLine;

    /**
     * Holding queue that has a limit to hold people on the line.
     */
    private HoldingQueue holdingQueue;

    /**
     * List of people who are riding this ride.
     */
    private List<Person> peopleOnRide;

    /**
     * Constructor of the Ride class.
     */
    public Ride(){

       name=null;
        virtualLine=null;
        holdingQueue=null;
        peopleOnRide = null;

    }

    /**
     * Overloaded constructor that specifies variables at once.
     * @param name
     *      The name of the ride.
     * @param queueSize
     *      Maximum number of people that this ride can hold on the holding queue.
     * @param duration
     *      Period of the ride.
     */
    public Ride(String name, int queueSize ,int capacity,int duration) {

        this.name=name;

        this.timeLeft=this.duration=duration;

        virtualLine = new VirtualLine();

        this.holdingQueue = new HoldingQueue(queueSize);

        peopleOnRide = new ArrayList<>(capacity);

        this.capacity=capacity;

        howManyCustomers=0;

    }

    /**
     * Increases the number of the people who have taken this ride.
     */
    public void increaseHowManyPeople(){
        howManyCustomers+=1;
    }

    /**
     * Getter of the number of the people who have taken the ride.
     */
    public int getHowManyCustomers(){ return howManyCustomers; }

    /**
     * Getter of duration of this ride.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter for the duration.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Getter of the time left for this ride.
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * setter of time left for this ride.
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Getter of name of this ride.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of name of this ride.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of virtual line of this ride.
     */
    public VirtualLine getVirtualLine() {
        return virtualLine;
    }

    /**
     * Getter of the holding queue.
     */
    public HoldingQueue getHoldingQueue() {
        return holdingQueue;
    }

    /**
     * Getter of the list of people who are on the ride.
     */
    public List<Person> getPeopleOnRide() {
        return peopleOnRide;
    }

    /**
     * Maximum number of people that this ride can hold.
     */
    public int getCapacity(){return capacity;}



}
