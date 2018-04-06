package com.cse214.theo.lunar_system;

import java.util.Comparator;
/**
 * Comparator that compares each course according to its department name in alphabetical order.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #6 for CSE 214, fall 2017
 */
public class CourseNameComparator implements Comparator<Course> {


	/**
	 * Compares two courses' names according to their department names in alphabetical order.
	 *
	 * @param left
	 * 		One course to be compared.
	 * @param right
	 * 		The other course to be compared.
	 */
	public int compare(Course left, Course right) {
		String leftDepartment = left.getDepartment();

		String rightDepartment = right.getDepartment();

		int leftNum = left.getNumber();

		int rightNum = right.getNumber();

		if (leftDepartment.compareToIgnoreCase(rightDepartment) > 0)

			return 1;
		
		else if (leftDepartment.compareToIgnoreCase(rightDepartment) == 0) {
			
			if (leftNum > rightNum)
				
				return 1;
			
			else if (leftNum == rightNum)
				
				return 0;
			
			else
				
				return -1;
		} else

			return -1;

	}
}
