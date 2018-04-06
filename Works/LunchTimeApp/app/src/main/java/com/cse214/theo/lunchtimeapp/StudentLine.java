package com.cse214.theo.lunchtimeapp;
    /**
     * StudentLine class which has an array of students
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class StudentLine implements Cloneable {

    /**
     * Array of students on the line
     */
    private Student[] students;

    /**
     * Number of students existing.
     * Increased when a new instance of student is generated
     */
    private int studentCount;

    /**
     *  The capacity of student line is 20 as the instruction said.
     */
    final int CAPACITY = 20;

    /**
     * Default constructor which initializes this object to an empty list of Students.
     */
    public StudentLine() {
        studentCount = 0;

        students = new Student[CAPACITY];

    }

    /**
     * Returns the total number of Students in the list.
     *
     * @return the number of students
     */
    public int numStudents() {
        return studentCount;

    }
    /**
     * Gets the array of students in the line
     *
     * @return the array of students
     */
    public Student[] getStudents() {
        return students;
    }

    /**
     * Gets the reference to the Student at the given index
     *
     * @return the name of the student
     */
    public Student getStudent(int index) throws ArrayIndexOutOfBoundsException {

        if (index < 0 || index >= studentCount)

            throw new ArrayIndexOutOfBoundsException("The index is out of bounds");

        return students[index];

    }
    /**
     * Removes the given student and moves all students behind this student forward by one index
     *
     * @return Removed student
     *
     * @throws ArrayIndexOutOfBoundsException
     *     An Exception class which indicates index is invalid.
     *
     * @throws EmptyLineException
     *      throws an exception if there is no student on the line.
     */
    public Student removeStudent(int index) throws ArrayIndexOutOfBoundsException, EmptyLineException {
        if (studentCount <= 0)
            throw new EmptyLineException("The Line is Empty. Please add students first");

        if (index < 0 || index >= studentCount)
            throw new ArrayIndexOutOfBoundsException("The index is out of bounds");


        Student removedStudent = students[index];

        for (int i = index; i < studentCount; i++) {

            students[i] = students[i + 1];

        }


        studentCount--;

        return removedStudent;
    }
    /**
     * Adds a student at the given index, moving all other students behind the current student back one index.
     *
     * @return added student with specified name and money.
     *
     * @throws InvalidArgumentException
     *      Throws an exception if the index is too high and would create a hole in the array
     *
     * @throws DeanException
     *      An Exception class which indicates that the user attempted to fill a full array should be created.
     */
    public Student addStudent(int index, Student student) throws InvalidArgumentException, DeanException {


        if (index < 0 || (index > studentCount && index != 0) || (index > studentCount && index == 0))
            throw new InvalidArgumentException("Index is invalid");


        if (CAPACITY <= studentCount)
            throw new DeanException("The line is full, can't add student any more");


        for (int i = studentCount; i > index; i--)
            students[i] = students[i - 1];

        students[index] = student;

        studentCount++;

        return students[index];
    }
    /**
     * If the indices are valid, the two students are swapped.
     *
     * @throws ArrayIndexOutOfBoundsException
     *      Throws an exception if the index is invalid
     */
    public void swapStudents(int index1, int index2) throws ArrayIndexOutOfBoundsException {

        if (index1 >= studentCount || index2 >= studentCount || index1 < 0 || index2 < 0)
            throw new ArrayIndexOutOfBoundsException();

        Student temp = students[index1];

        students[index1] = students[index2];

        students[index2] = temp;

    }
    /**
     * Creates a deep copy of this StudentLine object.
     * If the copy is modified, this object remains unmodified.
     * All the students inside are deep copied as well.
     *
     * @return added student with specified money and name.
     *
     * @throws CloneNotSupportedException
     *      Thrown to indicate that the clone method in class Object has been called to clone an object, but that the object's class does not implement the Cloneable interface.
     */
    public StudentLine clone() throws CloneNotSupportedException {

        StudentLine clonedStudentLine = (StudentLine) super.clone();

        clonedStudentLine.students = this.students.clone();

        for (int i = 0; i < studentCount; i++)
            clonedStudentLine.students[i] = (Student) students[i].clone();

        return clonedStudentLine;

    }
    /**
     * Checks if this student line is equal to another object.
     * The other object must be of type StudentLine, and contain students with the same names in the same order with the same balances for this method to return true.
     *
     * @return boolean value of two student lines' equality
     * If they have the same name and the money amount, the return value is true
     */
    public boolean equals(Object object) {
        if (!(object instanceof StudentLine))
            return false;
        // If it's not an instance of student line, it should return false

        if (((StudentLine) object).studentCount != this.studentCount)
            return false;

        int size = Math.max(((StudentLine) object).studentCount, this.studentCount);

        for (int i = 0; i < size; i++) {
            if (!(this.students[i].equals(((StudentLine) object).getStudents()[i])))
                return false;
        }

        return true;

    }
    /**
     * Describes the information of the student line.
     * Each student's name and money will be printed, but this will not be used because the recycler view shows the description of the current student line
     *
     * @return Returns String value of each student's information in the line
     */
    public String toString() {
        String result = "";
        for (int i = 0; i < studentCount; i++) {
            result += "" + i + 1 + " " + students[i].getName() + " " + students[i].getMoney();
        }
        return result;
    }


}