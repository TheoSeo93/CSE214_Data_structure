package com.cse214.theo.lunar_system;

import java.io.Serializable;

/**
 *Courses will be associated with the Students that are taking them.
 * Each course will have a designated department (Ex. “CSE”), a three-digit course number (Ex. 214), and a semester associated with it (Ex. “F2017”).
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #6 for CSE 214, fall 2017
 */
public class Course implements Serializable {

	/**
	 * Designated department of the course
	 */
	private String department;

	/**
	 * Three-digit course number
	 */
	private int number;

	/**
	 * Semester associated with the course
	 */
	private String semester;

	/**
	 * Constructor of the course.
	 *
	 * @param department
	 * 		Designated department
	 * @param number
	 * 		Three-digit course number
	 * @param semester
	 * 		Semester associated with the course
	 */
	public Course(String department, int number, String semester) {
		this.department = department;
		this.number = number;
		this.semester = semester;
	}

	/**
	 * Getters for each field variable, courseName, department, courseNumber, and semester
	 */
	public String getCourseName() {
		return department + number;
	}

	public String getDepartment() {
		return department;
	}

	public int getNumber() {
		return number;
	}

	public String getSemester() {
		return semester;
	}

}
