package com.cse214.theo.mailroommanager;

import java.util.ArrayList;

/**
 * Functions listed on the instruction are in this Mail Room class, such as find wrong packages and empty floor
 * This class stores package stacks in an array.
 *
 * @author Theo Seo, SBU ID: 111319497
 *         Homework #3 for CSE 214, fall 2017
 */
public class MailRoom {

    /**
     * The number of package stacks
     */
    private int listCount;


    /**
     * The number of package stacks including floor will be six.
     */
    final int CAPACITY = 6;

    /**
     * The array to store all package stacks.
     */
    private PackageStack[] listsArray = new PackageStack[CAPACITY];


    /**
     * Creates a mail room with instantiating each package stack in the array.
     */
    public MailRoom() {


        for (int i = 0; i < CAPACITY; i++) {

            listsArray[i] = new PackageStack();

        }
        //Signifies the last stack is the floor.
        this.listsArray[5].setFloor(true);

        listCount = 6;

    }

    /**
     * Find a package whose recipient name is not in alphabetical order for each package stack.
     * For instance, if a recipient name of a package is "Robert", but it is in the stack of "N-R", it will be thrown to the floor.
     */
    public void wrongPackagesToFloor() {
        for (int i = 0; i < 6; i++) {

            //If there is no package to find in each package, there is no need to search.
            if (getPackage(i).isEmpty())
                continue;

            int size = getPackage(i).size();

            PackageStack tempStack = new PackageStack();

            for (int j = 0; j < size; j++) {


                try {

                    tempStack.push(getPackage(i).pop());
                    char letter = Character.toLowerCase(tempStack.peek().getRecipient().charAt(0));

                    if (i == 0) {



                        if (!(letter >= 'a' && letter <= 'g'))

                            this.listsArray[5].push(tempStack.pop());

                    } else if (i == 1) {


                        if (!(letter >= 'h' && letter <= 'j'))
                            this.listsArray[5].push(tempStack.pop());

                    } else if (i == 2) {



                        if (!(letter >= 'k' && letter <= 'm'))
                            this.listsArray[5].push(tempStack.pop());


                    } else if (i == 3) {



                        if (!(letter >= 'n' && letter <= 'r'))
                            this.listsArray[5].push(tempStack.pop());

                    } else if (i == 4) {


                        if (!(letter >= 's' && letter <= 'z'))
                            this.listsArray[5].push(tempStack.pop());


                    }


                } catch (FullStackException | EmptyStackException e) {
                    e.printStackTrace();
                }
            }
            while (!tempStack.isEmpty()) {

                try {

                    getPackage(i).push(tempStack.pop());

                } catch (FullStackException | EmptyStackException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    /**
     * Removes all the items on the floor.
     */
    public void emptyFloor() {
        while (!listsArray[5].isEmpty())
            try {
                listsArray[5].pop();
            } catch (EmptyStackException e) {
                e.printStackTrace();
            }

    }
    /**
     * Returns the size of the package stacks.
     * @return
     *      Size of the array pf package stack.
     */
    public int size() {
        return listCount;
    }

    /**
     * Checks whenever date is increased in order to remove outdated packages.
     */
    public void updateMailRoom(int today) {


        for (int i = 0; i < 6; i++) {

            if (getPackage(i).isEmpty())
                continue;
            int size = getPackage(i).size();
            PackageStack tempStack = new PackageStack();
            int count = 0;
            for (int j = 0; j < size; j++) {

                try {

                    int arrivalDate;

                    tempStack.push(this.listsArray[i].pop());
                    arrivalDate = tempStack.peek().getArrivalDate();

                    if (today - arrivalDate > 5) {
                        tempStack.pop();
                        count++;
                    }

                } catch (FullStackException | EmptyStackException e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < size - count; j++) {

                try {

                    this.listsArray[i].push(tempStack.pop());

                } catch (FullStackException | EmptyStackException e) {
                    e.printStackTrace();
                }
            }


        }


    }

    /**
     * Find names of all the recipients.
     *
     * @return
     *      Array list of recipient names.
     */
    public ArrayList<String> getRecipients() throws EmptyStackException, FullStackException {
        ArrayList<String> result = new ArrayList<>();


        for (int i = 0; i < 6; i++) {

            if (getPackage(i).isEmpty())
                continue;
            int size = getPackage(i).size();
            PackageStack tempStack = new PackageStack();

            for (int j = 0; j < size; j++) {


                tempStack.push(this.getPackage(i).pop());
                String name = tempStack.peek().getRecipient();

                if (!result.contains(name))
                    result.add(name);


            }


            for (int j = 0; j < size; j++) {


                this.getPackage(i).push(tempStack.pop());


            }


        }

        return result;

    }

    /**
     * Find all the packages for a specific recipients.
     *
     * @param recipient
     *      The name of the recipient.
     *
     * @return
     *      Package stack of a recipient's packages.
     */
    public PackageStack listRecipientPackages(String recipient) {

        PackageStack recipientPackages = new PackageStack();

        recipientPackages.setFloor(true);

        for (int i = 0; i < 6; i++) {

            if (getPackage(i).isEmpty())
                continue;

            int size = getPackage(i).size();
            PackageStack tempStack = new PackageStack();

            for (int j = 0; j < size; j++) {

                try {


                    tempStack.push(this.getPackage(i).pop());
                    String name = tempStack.peek().getRecipient();

                    if (name.equalsIgnoreCase(recipient))
                        recipientPackages.push(tempStack.peek());


                } catch (FullStackException | EmptyStackException e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < size; j++) {

                try {


                    this.getPackage(i).push(tempStack.pop());

                } catch (FullStackException | EmptyStackException e) {
                    e.printStackTrace();
                }
            }


        }


        return recipientPackages;
    }

    /**
     * Returns the list by specified index from the listArray.
     *
     * @param index The index of the listArray.
     */
    public PackageStack getPackage(int index) throws ArrayIndexOutOfBoundsException {

        if (index < 0 || index >= listCount)
            throw new ArrayIndexOutOfBoundsException("The index is out of bounds");

        return listsArray[index];

    }

}

