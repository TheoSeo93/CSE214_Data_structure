package com.cse214.theo.lunar_system;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Comparator that compares each course according to its semester name in alphabetical order.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #6 for CSE 214, fall 2017
 */

public class SemesterComparator implements Comparator<Course> {


	/**
	 * Compares two courses' names according to their semester names in alphabetical order.
	 *
	 * @param left
	 * 		One course to be compared.
	 * @param right
	 * 		The other course to be compared.
	 */
	public int compare(Course left, Course right) {

		int leftYear = 0;

		int rightYear = 0;

		char leftFirst = left.getSemester().charAt(0);

		char rightFirst = right.getSemester().charAt(0);

		Pattern yearPattern = Pattern.compile("(?=\\d).*");

		Matcher leftMatch = yearPattern.matcher(left.getSemester());

		Matcher rightMatch = yearPattern.matcher(right.getSemester());

		if (leftMatch.find()) 
			leftYear = Integer.valueOf(leftMatch.group(0));
		if (rightMatch.find())
			rightYear = Integer.valueOf(rightMatch.group(0));
		
		if (leftYear > rightYear)
			return 1;

		else if (leftYear == rightYear) {
			
			if (leftFirst == rightFirst)
				return 0;
			else if (Character.compare(leftFirst, rightFirst) < 0)
				return 1;
			else
				return -1;

		} else
			return -1;
	}

	
}
