package com.cse214.theo.roadcalculator;

/**
 * An Edge represents an adjacency between two cities in our country. In other words, two cities (Nodes) are adjacent if and only if they have an Edge between them.
 * The Edge class will contain two Node references and an integer representing the cost of laying down Internet cable between the two cities.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #7 for CSE 214, fall 2017
 */
public class Edge implements Comparable<Object> {

	/**
	 * Node A and B are the nodes connected by the current edge.
	 */
	private Node A;

	private Node B;

	/**
	 * The cost of the edge.
	 */
	private int cost;

	/**
	 * Constructor for the edge class.
	 * Initiates the nodes connected with this edge and the cost of it.
	 *
	 * @param A,B
	 * 		The nodes connected with this edge.
	 * @param cost
	 * 		The cost of the edge.
	 */
	public Edge(Node A, Node B, int cost) {
		this.A=A;
		this.B=B;
		this.cost=cost;
	}

	/**
	 * Getters for the node A, B and cost
	 */
	public Node getA() {
		return A;
	}


	public Node getB() {
		return B;
	}


	public int getCost() {
		return cost;
	}

	/**
	 * Returns the string showing the variables of this edge.
	 */
	public String toString() {

		return A.getName() + " to "  + B.getName()+" "+cost;

	}

	/**
	 * Compares this edge to the other edge according to their costs.
	 * Ascending order.
	 *
	 * 	@param otherEdge
	 * 		The other edge to be compared.
	 */
	public int compareTo(Object otherEdge) {
		if (this.cost < ((Edge) otherEdge).getCost())
			return -1;
		else if (this.cost == ((Edge) otherEdge).getCost())
			return 0;
		else
			return 1;

	}

	/**
	 * Compares this edge to the other edge if they are the same.
	 * Since this graph has to be undirected, this method determines the other edge to be the same one if it has the same nodes but in different order.
	 *
	 * 	@param other
	 * 		The other edge to be compared.
	 */
	public boolean redundant(Edge other) {
		if(other.getA().getName().equals(B.getName())&&other.getB().getName().equals(A.getName()))
			return true;
		else return false;
	}

}
