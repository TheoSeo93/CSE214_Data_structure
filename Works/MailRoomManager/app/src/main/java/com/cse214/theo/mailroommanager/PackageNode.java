package com.cse214.theo.mailroommanager;

/**
 * Since I implemented the package stack with singly linked list, there needs to be nodes for packages.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #3 for CSE 214, fall 2017
 */
public class PackageNode  {

    /**
     * The next node of the current node.
     */
    private PackageNode next;

    /**
     * The data of the node, which is package.
     */
    private Package data;

    /**
     * Creates a new node with a package.
     */
    public PackageNode(Package data) {

        this.data=data;

    }

    /**
     * Sets the next node.
     *
     * @param next
     *      The next package node to be connected.
     *
     */
    public void setNext(PackageNode next) {

        this.next=next;

    }

    /**
     * Gets the next node.
     *
     * @return
     *      The next package node.
     */
    public PackageNode getNext() {
        return next;
    }

    /**
     * Gets the package data of the node.
     *
     * @return
     *      The package of package node.
     */
    public Package getData() {
        return data;
    }

    /**
     * Sets the package data of the node.
     *
     * @param data
     *      The package to be set.
     */
    public void setData(Package data) {
        this.data = data;
    }


}
