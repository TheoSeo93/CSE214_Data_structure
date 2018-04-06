package com.cse214.theo.mailroommanager;

/**
 * This is a package stack of singly linked list.
 * To distinguish floor package stack from others, I used boolean value called isFloor.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #3 for CSE 214, fall 2017
 */
public class PackageStack{

    /**
     * The capacity of each package stack is 7.
     */
    private final int CAPACITY = 7;

    /**
     * The size of the package stack.
     */
    private int manyItems;

    /**
     * Boolean value to set if it's floor or not.
     */
    private boolean isFloor;

    /**
     * The topmost node of the package.
     */
    private PackageNode top;

    /**
     * Creates a new package stack.
     */
    public PackageStack() {

        manyItems = 0;

        top = null;

    }

    /**
     * Sets true if the package stack is for floor.
     *
     * @param isFloor
     *      If it's floor, it will be true.
     */
    public void setFloor(boolean isFloor){

        this.isFloor=isFloor;
    }

    /**
     * Returns the size of each package stack.
     *
     * @return
     *      The number of packages inside the stack.
     */
    public int size(){ return manyItems;  }

    /**
     * Pushes a new node to the stack. If it is floor, there should be no full stack exception.
     * Therefore, I divided this into two case when it is floor and it is not floor.
     *
     * @throws  FullStackException
     *      When the stack is full, it throws a full stack exception.
     *
     * @param x
     *      New package node to be pushed.
     *
     */
    public void push(Package x) throws FullStackException {

        if(isFloor) {


            PackageNode newNode = new PackageNode(x);

            newNode.setNext(top);

            top = newNode;

            manyItems++;

        } else {
            if (isFull())
                throw new FullStackException("The stack is full");

            PackageNode newNode = new PackageNode(x);

            newNode.setNext(top);

            top = newNode;

            manyItems++;
        }
    }

    /**
     * Removes the topmost node from the stack.
     *
     * @throws  EmptyStackException
     *      If it's empty, since there is nothing to remove, empty stack exception will be thrown.
     *
     * @return
     *      The topmost node to be removed.
     */
    public Package pop() throws  EmptyStackException{

        Package result=top.getData();

        if (isEmpty())

                throw new EmptyStackException("It's empty");



            top = top.getNext();

        manyItems--;

        return result;
    }

    /**
     * Returns the topmost package from the stack.
     *
     * @throws  EmptyStackException
     *      If it's empty, since there is nothing to return, empty stack exception will be thrown.
     *
     * @return
     *      The topmost package.
     */
    public Package peek() throws EmptyStackException {

        if (isEmpty())
                throw new EmptyStackException("It's empty");


        return top.getData();

    }

    /**
     * Tells if the stack is full or not
     *
     * @return
     *      The boolean value if the stack is full.
     */
    public boolean isFull() {

        return manyItems == CAPACITY;

    }

    /**
     * Tells if the stack is empty or not
     *
     * @return
     *      The boolean value if the stack is empty.
     */
    public boolean isEmpty() {

        return top == null;

    }


}
