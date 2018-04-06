package com.cse214.theo.roadcalculator;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Represents a City that will be a part of our graph of connected cities.
 * Each city has a name, a HashSet containing which cities it is adjacent to (represented by an edge), and a boolean value to represent if the node has been visited by the graph traversal.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #7 for CSE 214, fall 2017
 */
public class Node {

    /**
     * The city name of this node.
     */
    private String name;

    /**
     * The edges of the node connecting this node to an another node.
     */
    private HashSet<Edge> edges;

    /**
     * The boolean variable showing wheter this node has been visited or not.
     */
    private boolean visited;

    /**
     * The linked list of path storing the nodes from the source node to the destination node.
     * This variable will be used when finding the shortest path for djikstra's algorithm.
     */
    private LinkedList<String> path;

    /**
     * This is the weight of the node.
     * In this case, the weight will be the distance.
     */
    private int distance;

    /**
     * Constructor of the node.
     * Initiates the city name, an empty set of edges, and the path storing the node itself.
     *
     * @param name Initiates the city name from the parameter.
     */
    public Node(String name) {
        this.name = name;
        this.edges = new HashSet<>();
        visited = false;
        this.path = new LinkedList<>();
        this.path.add(name);
    }

    /**
     * Getter of the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the set of edges.
     */
    public HashSet<Edge> getEdges() {
        return edges;
    }

    /**
     * Getter of the boolean value whether it has been visited or not.
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Setter of the boolean value whether it has been visited or not.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Getter of the linked list of Path.
     */
    public LinkedList<String> getPath() {
        return path;
    }

    /**
     * Getter of distance.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Setter of distance.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }



}
