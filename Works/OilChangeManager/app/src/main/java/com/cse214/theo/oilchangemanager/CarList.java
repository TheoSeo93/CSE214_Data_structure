package com.cse214.theo.oilchangemanager;
/**
 * The activity class that takes student's position to remove him or her from the line
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #2 for CSE 214, fall 2017
 */
public class CarList {

    /**
     * The head node
     */
    private CarListNode head;

    /**
     * The tail node.
     */
    private CarListNode tail;

    /**
     * The cursor that points the specific node.
     * This node will be a significant reference for highlighting a specific child in the recycler view.
     */
    private CarListNode cursor;

    /**
     * The variable indicating the number of the cars generated.
     */
    private int countCars;

    /**
     * Getter for the head node.
     */
    public CarListNode getHead() {
        return head;
    }

    /**
     *  Getter for the tail node.
     */
    public CarListNode getTail() {
        return tail;
    }

    /**
     * Getter that returns the number of cars.
     */
    public int numCars() {
        return countCars;
    }


    /**
     * Getter method that returns the data of the node that cursor is referencing at.
     */
    public Car getCursorCar() {
        if (cursor == null)
            return null;
        return cursor.getData();
    }

    /**
     *
     * Returns the data of the node at specific index passed as a parameter.
     * Time complexity is O(N).
     *
     *  @param index
     *      This int value specifies the index of the node. If it's bigger than the number of the cars or smaller than 0, IndexOutOfException will be thrown.
     */
    public Car getCar(int index) {
        if (index >= countCars || index < 0)
            throw new IndexOutOfBoundsException();


        CarListNode result = getHead();

        for (int i = 0; i < index; i++)
            result = result.getNext();

        if (result == null)
            return null;

        return result.getData();

    }

    /**
     * Resets the position of the cursor to the head.
     */
    public void resetCursorToHead() {

        if (head == null) {
            cursor = null;
            return;
        }

        cursor = head;

    }

    /**
     * Moves Cursor forward. If this reaches the ends of the list, for instance, head or the tail, it throws the EndOfListException so that it notifies the user that it can't be moved forward anymore.
     */
    public void cursorForward() throws EndOfListException {
        if (cursor == null)
            return;
        if (cursor == tail)
            throw new EndOfListException("End of the list");

        cursor = cursor.getNext();
    }

    /**
     * Moves cursor backward. This works as the same as the cursorForward method when reached to the ends.
     */
    public void cursorBackward() throws EndOfListException {
        if (cursor == null)
            return;
        if (cursor == head)
            throw new EndOfListException("End of the list");

        cursor = cursor.getPrev();

    }

    /**
     * Inserts a new node before the cursor. If the parameter is passed as null, it will throw an IllegalArgumentException
     * If the cursor is not present, which means there is no element, a new node will be added to the head, while the cursor points at the new node.
     * If the cursor is present, a new node will be inserted right before the cursor and the cursor remains at the same position
     *
     *   @param newCar
     *       The data of the new node to be inserted before the cursor. If the parameter passed is null value, it will throw an IllegalArgumentException
     */
    public void insertBeforeCursor(Car newCar) throws IllegalArgumentException {

        if (newCar == null)
            throw new IllegalArgumentException();

        CarListNode newNode = new CarListNode(newCar);

        if (head == null) {

            head = newNode;
            tail = newNode;
            cursor = newNode;
            countCars++;

            return;
        } else {
            if (cursor == head) {
                head = newNode;
                head.setNext(cursor);
                cursor.setPrev(head);
                countCars++;
                return;
            }

            newNode.setNext(cursor);
            newNode.setPrev(cursor.getPrev());
            cursor.getPrev().setNext(newNode);
            cursor.setPrev(newNode);
            countCars++;
        }

    }

    /**
     * Appends a new node after the tail.
     *
     *  @param newCar
     *          Just as the insert method, if the parameter value is null, it throws an IllegalArgumentException
     */
    public void appendToTail(Car newCar) throws IllegalArgumentException {
        CarListNode newNode = new CarListNode(newCar);

        if (newCar == null)
            throw new IllegalArgumentException();
        if (head == null) {
            head = newNode;
            tail = head;
            cursor = newNode;
            countCars++;
            return;
        }

        newNode.setPrev(tail);

        tail.setNext(newNode);

        tail = newNode;

        countCars++;

    }

    /**
     * Removes the specific node at which the cursor is pointing. If there is no car on the list, it throws an EndOfListException
     */
    public Car removeCursor() throws EndOfListException {

        if (cursor == null)
            throw new EndOfListException("End of the list");

        Car result = new Car();

        result.setMake(cursor.getData().getMake());
        result.setOwner(cursor.getData().getOwner());

        //The case where the cursor is pointing at the head node.
        if (cursor == head) {

            //If there is only one node in the list, the head, tail and the cursor references the null.
            if (countCars == 1) {
                head = null;
                tail = null;
                cursor = null;
                countCars = 0;
            } else {
                head = cursor.getNext();
                cursor = head;
                countCars--;
            }

            //The case where the cursor is not pointing at the head node.
        } else {

            if (cursor == tail) {
                tail = cursor.getPrev();
                cursor = cursor.getPrev();
                countCars--;

            } else {

                cursor.getPrev().setNext(cursor.getNext());
                cursor.getNext().setPrev(cursor.getPrev());
                cursor = cursor.getPrev();
                countCars--;
            }


        }
        return result;
    }

    /**
     * Merges two lists to the parameter list in the way that each node at for lists alternate one by one, starting from the node of the parameter list.
     * For instance, when destination list is d1 and the source list is s1,s2, the returned list is d1,s2,s2.
     *
     *  @param destination
     *        This is the parameter but at the same time the list to be returned after merged.
     */
    public CarList merge(CarList destination) {


        CarListNode srcNode = this.head;

        CarListNode nextSrc;
        CarListNode nextDest;

        CarListNode destinationNode = destination.getHead();

        //If the passed list has no node at all, it returns the parameter list.
        if (destination.numCars() == 0)
            return this;

        //If the current list, namely the source list, has no node at all, it returns the parameter list, destination.
        if (this.numCars() == 0)
            return destination;


        while (srcNode != null && destinationNode != null) {

            nextDest = destinationNode.getNext();
            nextSrc = srcNode.getNext();

            //The case where each list has different number of nodes.
            if (this.numCars() != destination.numCars()) {
                if (destinationNode.getNext() != null) {
                    destinationNode.getNext().setPrev(srcNode);
                    srcNode.setNext(destinationNode.getNext());
                    destinationNode.setNext(srcNode);
                    srcNode.setPrev(destinationNode);
                } else {
                    srcNode.getNext().setPrev(srcNode);
                    srcNode.setNext(srcNode.getNext());
                    destinationNode.setNext(srcNode);
                    srcNode.setPrev(destinationNode);
                }


                //The case where each list has the same number of nodes.
            } else {

                if (destinationNode.getNext() == null || srcNode.getNext() == null) {

                    destinationNode.setNext(srcNode);
                    srcNode.setPrev(destinationNode);
                    destination.tail = srcNode;

                } else {

                    destinationNode.getNext().setPrev(srcNode);
                    srcNode.setNext(destinationNode.getNext());
                    srcNode.setPrev(destinationNode);
                    destinationNode.setNext(srcNode);

                }

            }

            destinationNode = nextDest;
            srcNode = nextSrc;
        }


        destination.countCars += this.numCars();


        return destination;
    }

    /**
     * Setter method for cursor.
     * This method is used when setting the cursor to the tail node.
     */
    public void setCursor(CarListNode cursor) {
        this.cursor = cursor;
    }

    /**
     * Merge sort method that firstly partitions a single list into many nodes recursively, and merges eventually after sorting.
     * Firstly, in order to figure out the midpoint node of the list, midPtr and fastPtr node variables are used.
     * When the fastPtr reaches to the end, the midPtr is at the middle of the list.
     * Then, another variable called prevPtr references the previous node of the midPtr so that prevPtr, the end of the left node, so that the list can be divided into two lists.
     *
     *  @param head
     *      The head node of the list to be passed.
     *
     */
    public CarListNode sort(CarListNode head) {

        CarListNode prevPtr = head;
        CarListNode midPtr = head;
        CarListNode fastPtr = head;

        //If the head is null, there is nothing to be returned.
        if (head == null)

            return null;

            //If there is only one node in the list, head is to be returned.
        else if (head.getNext() == null)

            return head;


        while (fastPtr != null && fastPtr.getNext() != null) {

            //prevPtr proceeds one step slower than the midPtr
            prevPtr = midPtr;

            //midPtr proceeds to the next node one step at a time
            midPtr = midPtr.getNext();

            //fastPtr proceeds to the next node twice faster than the midPtr.
            fastPtr = fastPtr.getNext().getNext();

        }

        //The next node of the prevPtr should be referencing the null value so that the list can be divided into two lists.
        prevPtr.setNext(null);

        //Recursive call for the left and right side link of the two divided lists. It will eventually divide the list into single nodes.
        CarListNode left = sort(head);
        CarListNode right = sort(midPtr);

        //As learned from the class, dummy head and tail make it possible for the head and tail to be unchanged.
        CarListNode dummyHead = new CarListNode();
        CarListNode dummyTail = new CarListNode();

        CarListNode result = dummyHead;

        //Iterates until either of the left or right node doesn't exist.
        while (left != null && right != null) {

            //Compares the String value of each kinds of make. If the left string value is lower in terms of lexicon, it becomes the next node.
            if (left.getData().getMake().toString().compareTo(right.getData().getMake().toString()) < 0) {

                result.setNext(left);

                left.setPrev(result);

                left = left.getNext();

            } else {

                result.setNext(right);

                right.setPrev(result);

                right = right.getNext();

            }
            result = result.getNext();
        }

        //In case where the left node is null, which means left side of the link has reached to the end, right nodes will be the next nodes.
        if (left == null) {

            result.setNext(right);

            right.setPrev(result);
        }
        if (right == null) {
            result.setNext(left);
            left.setPrev(result);
        }

        dummyTail.setPrev(result);

        return dummyHead.getNext();
    }


}