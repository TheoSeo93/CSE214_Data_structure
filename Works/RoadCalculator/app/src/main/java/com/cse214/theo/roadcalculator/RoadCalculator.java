package com.cse214.theo.roadcalculator;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import big.data.DataSource;

/**
 * Builds a graph, creates and prints out towns, roads, minimum spanning tree, and the shortest path.
 * This is the internally core part of this assignment.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #7 for CSE 214, fall 2017
 */
public class RoadCalculator {

    /**
     * The graph read from the xml file will be stored here.
     */
    private static HashMap<String, Node> graph;

    /**
     * The minimum spanning tree will be stored here as a linked list.
     */
    private static LinkedList<Edge> mst;

    /**
     * The url of the file will be stored in order to be called again restoring the original graph whenever the data set has changed.
     */
    private static String urlLocation;

    /**
     * Builds a graph read from the url, using the external library, big data.jar
     *  @param location
     *      The url location where the xml file exists.
     */
    public static HashMap<String, Node> buildGraph(String location) {
        urlLocation = location;
        HashMap<String, Node> cities = new HashMap<String, Node>();

        DataSource ds = DataSource.connectXML(location);
        ds.load();
        String cityNamesStr = ds.fetchString("cities").toUpperCase();
        String[] cityNames = cityNamesStr.substring(1, cityNamesStr.length() - 1).replace("\"", "").split(",");
        String roadNamesStr = ds.fetchString("roads").toUpperCase();
        String[] roadNames = roadNamesStr.substring(2, roadNamesStr.length() - 2).split("\",\"");

        int distance;

        for (int i = 0; i < cityNames.length; i++) {

            Node cityNode = new Node(cityNames[i].toUpperCase());

            cities.put(cityNames[i].toUpperCase(), cityNode);
        }

        for (int i = 0; i < roadNames.length; i++) {
            String[] strings = StringUtils.split(roadNames[i], ",");
            String nodeA = strings[0];
            String nodeB = strings[1];

            distance = Integer.valueOf(strings[2]);
            if (cities.containsKey(nodeA) && cities.containsKey(nodeB)) {
                cities.get(nodeA).getEdges().add(new Edge(cities.get(nodeA), cities.get(nodeB), distance));
                cities.get(nodeB).getEdges().add(new Edge(cities.get(nodeB), cities.get(nodeA), distance));

            } else if (!cities.containsKey(nodeA) && cities.containsKey(nodeB)) {
                cities.get(nodeA).getEdges().add(new Edge(new Node(nodeA), cities.get(nodeB), distance));
                cities.get(nodeB).getEdges().add(new Edge(new Node(nodeB), cities.get(nodeA), distance));

            } else if (cities.containsKey(nodeA) && !cities.containsKey(nodeB)) {
                cities.get(nodeA).getEdges().add(new Edge(cities.get(nodeA), new Node(nodeB), distance));
                cities.get(nodeB).getEdges().add(new Edge(cities.get(nodeB), new Node(nodeA), distance));

            } else {

                cities.get(nodeA).getEdges().add(new Edge(new Node(nodeA), new Node(nodeB), distance));
                cities.get(nodeB).getEdges().add(new Edge(new Node(nodeB), new Node(nodeA), distance));

            }

        }
        ;

        graph = cities;

        return cities;

    }

    /**
     * Builds a minimum spanning tree by Kruskal's algorithm.
     * Originally, the instruction of the assignment requires students to pass the graph as a parameter,
     * but removed the parameter since the class mainly running the application is the MainActivity class.
     */
    public static LinkedList<Edge> buildMST() {

        mst = new LinkedList<>();

        if (graph.isEmpty())
            throw new NullPointerException("The graph is empty");


        ArrayList<Node> vertices = new ArrayList<>(graph.values());

        ArrayList<Edge> edges = new ArrayList<>();


        for (int i = 0; i < vertices.size(); i++) {

            edges.addAll(vertices.get(i).getEdges());
        }

        ArrayList<Edge> edgeArrayList = new ArrayList<>();

        edgeArrayList.addAll(edges);

        for (int i = 0; i < edgeArrayList.size(); i++) {
            for (int j = 0; j < edgeArrayList.size(); j++)
                if (j != i)
                    if (edgeArrayList.get(i).redundant(edgeArrayList.get(j)))
                        edgeArrayList.remove(j);
        }

        Collections.sort(edgeArrayList);

        edgeArrayList.get(0).getA().setVisited(true);
        edgeArrayList.get(0).getB().setVisited(true);

        mst.add(edgeArrayList.get(0));

        edgeArrayList.remove(0);

        while (mst.size() < vertices.size() - 1) {

            Inner:
            for (int i = 0; i < edgeArrayList.size(); i++) {

                if ((edgeArrayList.get(i).getA().isVisited()) != (edgeArrayList.get(i).getB().isVisited())) {

                    edgeArrayList.get(i).getA().setVisited(true);

                    edgeArrayList.get(i).getB().setVisited(true);

                    mst.add(edgeArrayList.get(i));

                    edgeArrayList.remove(i);

                    break Inner;
                }
            }

        }
        return mst;

    }
    /**
     * With Djikstra's algorithm, builds a shortest-path tree
     *
     *  @param graph
     *      The graph built based on the xml file.
     *  @param source
     *      The name of the source node for finding the shortest path.
     *  @param dest
     *      The name of the destination node for finding the shortest path.
     */
    public static int djikstra(HashMap<String, Node> graph, String source, String dest) {

        ArrayList<Node> vertices = new ArrayList<>(graph.values());

        Node nextVertex = graph.get(source);

        for (int i = 0; i < vertices.size(); i++) {

            vertices.get(i).setDistance(Integer.MAX_VALUE);
            if (vertices.get(i).getName().equals(source)) {

                vertices.get(i).setDistance(0);
                vertices.get(i).setVisited(true);

            }

        }

        boolean stop = false;

        while (stop == false) {

            ArrayList<Edge> edges = new ArrayList<>(nextVertex.getEdges());
            ArrayList<Node> neighbors = new ArrayList<>();

            for (int i = 0; i < edges.size(); i++) {

                if (!edges.get(i).getA().getName().equals(nextVertex.getName())) {
                    neighbors.add(edges.get(i).getA());
                } else if (!edges.get(i).getB().getName().equals(nextVertex.getName())) {
                    neighbors.add(edges.get(i).getB());
                }
                if (neighbors.get(i).getDistance() > (nextVertex.getDistance() + edges.get(i).getCost())) {
                    neighbors.get(i).setDistance(nextVertex.getDistance() + edges.get(i).getCost());
                    neighbors.get(i).getPath().clear();
                    neighbors.get(i).getPath().add(0, neighbors.get(i).getName());


                    for (int j = 0; j < nextVertex.getPath().size(); j++) {

                        neighbors.get(i).getPath().add(nextVertex.getPath().get(j));

                    }


                }

            }

            int minimumDistance = Integer.MAX_VALUE;

            for (int i = 0; i < vertices.size(); i++) {

                if (vertices.get(i).getDistance() < minimumDistance && !vertices.get(i).isVisited()) {

                    nextVertex = vertices.get(i);

                    minimumDistance = nextVertex.getDistance();

                }

            }
            System.out.println(nextVertex.getName());
            nextVertex.setVisited(true);

            stop = true;
            for (int i = 0; i < vertices.size(); i++) {

                if (vertices.get(i).isVisited() == false) {
                    stop = false;
                    break;
                }

            }
        }
        Stack<String> reverse = new Stack<>();
        graph.get(dest).getPath().remove(0);
        while (!graph.get(dest).getPath().isEmpty())
            reverse.push(graph.get(dest).getPath().remove(0));

        while (!reverse.isEmpty()) {
            graph.get(dest).getPath().add(reverse.pop());
        }

        return graph.get(dest).getDistance();

    }
    /**
     * Returns all the towns from the graph as a string value.
     */
    public static String printTowns() {
        StringBuilder strBfr = new StringBuilder("Towns: \n");
        ArrayList<Node> vertices = new ArrayList<>(graph.values());
        for (int i = 0; i < vertices.size(); i++) {
            strBfr.append(vertices.get(i).getName() + "\n");

        }

        return strBfr.toString();
    }

    /**
     * Returns all the roads from the graph as a string value.
     */
    public static String printRoads() {

        ArrayList<Node> vertices = new ArrayList<>(graph.values());

        ArrayList<Edge> edges = new ArrayList<>();


        for (int i = 0; i < vertices.size(); i++) {

            edges.addAll(vertices.get(i).getEdges());
        }

        ArrayList<Edge> edgeArrayList = new ArrayList<>();

        edgeArrayList.addAll(edges);

        for (int i = 0; i < edgeArrayList.size(); i++) {
            for (int j = 0; j < edgeArrayList.size(); j++)
                if (j != i)
                    if (edgeArrayList.get(i).redundant(edgeArrayList.get(j)))
                        edgeArrayList.remove(j);
        }
        StringBuilder strBfr = new StringBuilder("Roads: \n");

        for (int i = 0; i < edgeArrayList.size(); i++)
            strBfr.append(edgeArrayList.get(i) + "\n");

        return strBfr.toString();
    }

    /**
     * Returns the information of minimum spanning tree as a string value.
     */
    public static String printMST() {
        StringBuilder strBfr = new StringBuilder("Minnimum Spannig Tree: \n");
        for (int i = 0; i < mst.size(); i++) {
            strBfr.append(mst.get(i).getA().getName() + " " + mst.get(i).getB().getName() + " " + mst.get(i).getCost() + "\n");
        }

        return strBfr.toString();
    }

    /**
     * Getter of the graph.
     */
    public static HashMap<String, Node> getGraph() {
        return graph;
    }

    /**
     * Returns the information of the shortest path as a string value.
     *
     * @param start
     *      Starting node of the shortest path tree.
     * @param dest
     *      Destination node of the shortest path tree.
     */
    public static String printShortestPath(String start, String dest) {
        StringBuilder strBfr = new StringBuilder();

        strBfr.append("Distance: " + djikstra(graph, start, dest) + "\n");
        graph.get(dest).getPath().add(dest);
        strBfr.append("Path: \n");
        for (int i = 0; i < graph.get(dest).getPath().size(); i++) {
            strBfr.append(graph.get(dest).getPath().get(i) + "   ");
        }
        buildGraph(urlLocation);
        return strBfr.toString();
    }


}
