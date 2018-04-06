package com.cse214.theo.lunar_system;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * The student class which stores the web ID and the courses enrolled.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #6 for CSE 214, fall 2017
 */
public class Student implements Serializable{

	/**
	 * The ID name of the student.
	 */
	private String webID;


	/**
	 * ArrayList of the courses that student has enrolled.
	 */
	private List<Course> courses;

	/**
	 * Constructor of student
	 *
	 * @param id
	 * 		The webId of the student.
	 */
	public Student(String id) {
		webID=id;
		courses= new LinkedList<>();
	}

	/**
	 * Getter for the webID.
	 */
	public String getWebID() {
		return webID;
	}

	/**
	 * Getter of the courses enrolled.
	 */
	public LinkedList<Course> getCourses() {
		return (LinkedList)courses;
	}

	
}
