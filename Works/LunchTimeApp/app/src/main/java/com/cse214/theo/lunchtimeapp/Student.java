package com.cse214.theo.lunchtimeapp;
    /**
     * Student class which has name and money
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class Student implements Cloneable {
    /**
     * Student's name
     */
    private String name;

    /**
     * Student's money
     */
    private double money;


    /**
     * Creates a new instance of student with name and money
     */
    public Student(String name, double money) {

        this.name = name;
        this.money = money;

    }

    /**
     * Gets name of the student
     *
     * @return the name of the student
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets money of the student
     *
     * @return the money of the student
     */
    public double getMoney() {
        return money;
    }

    /**
     * Sets money of the student
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * Clones a student
     *
     * @return cloned student that has the same money and name
     *
     * @throws CloneNotSupportedException
     *      Thrown to indicate that the clone method in class Object has been called to clone an object, but that the object's class does not implement the Cloneable interface.
     */
    public Object clone() throws CloneNotSupportedException {

        Student result = (Student) super.clone();

        return result;
    }

    /**
     * Compares two students' money and name.
     * If they are the same, it returns true;
     *
     * @return boolean value whether two students have the same name and money.
     *
     * @throws NullPointerException
     *      Thrown when an application attempts to use null in a case where an object is required.
     */
    public boolean equals(Object object) throws NullPointerException {

        if (!(object instanceof Student))
            return false;

        if (((Student) object).getName().equals(this.name) && (((Student) object).getMoney() == this.money))
            return true;

        return false;

    }
}