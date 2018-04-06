package com.cse214.theo.lunchtimeapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;
import static com.cse214.theo.lunchtimeapp.R.id.home_spinner;

    /**
     * The class for home fragment.
     * Home fragment basically has two functions.
     * Firstly, it displays the current state of the line based on the selection from a spinner, which has A and B in a recycler view.
     * The other function is to give user the options to add, cut, remove, serve, swap students' places and update money, whose options will be given by radio buttons.
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class FragmentHome extends android.support.v4.app.Fragment {
    /**
     * Inflated view from the xml resource of home fragment.
     */
    private View rootView;

    /**
     * Currently selected reality, student line to be displayed.
     */
    private StudentLine currentStudentLine;

    /**
     * Recycler view that shows current student line.
     */
    private RecyclerView lineRecyclerView;

    /**
     * Provides a binding from current student line array to views that are displayed within the recycler view
     */
    private RecyclerViewAdapter lineRecyclerViewAdapter;

    /**
     * Contains multiple realities in its own array whose capacity is
     */
    private StudentLineContainer studentLineContainer = new StudentLineContainer();

    /**
     * Array adapter for spinner.
     */
    private ArrayAdapter < String > spinnerArrayAdapter;

    /**
     * The options to be displayed on the spinner
     */
    private String[] spinnerArray = {
            "Select Reality",
            "A",
            "B"
    };


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater
     *      The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container
     *      If non-null, this is the parent view that the fragment's UI should be attached to.
     *      The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ((MainActivity) getActivity()).setStudentLineContainerFromHome(studentLineContainer);
        // Passes the initial student line container to compare fragment and duplicate fragment

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        final RadioButton ADD_STUDENT = (RadioButton) rootView.findViewById(R.id.add_student);

        final RadioButton CUT_FRIEND = (RadioButton) rootView.findViewById(R.id.cut_friend);

        final RadioButton REMOVE_STUDENT = (RadioButton) rootView.findViewById(R.id.remove_student);

        final RadioButton SERVE_STUDENT = (RadioButton) rootView.findViewById(R.id.serve_student);

        final RadioButton UPDATE_MONEY = (RadioButton) rootView.findViewById(R.id.update_money);

        final RadioButton TRADE_PLACES = (RadioButton) rootView.findViewById(R.id.trade_places);

        final Spinner SPINNER = (Spinner) rootView.findViewById(home_spinner);

        final Button radioButtonConfirm = (Button) rootView.findViewById(R.id.select);

        final Button exitButton= (Button) rootView.findViewById(R.id.button_exit);

        spinnerArrayAdapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        exitButton.setOnClickListener(new View.OnClickListener() {

            /**
             *  Exits a program when the exit button is clicked
             *
             * @param v
             *      The view where the click happened
             */
            @Override
            public void onClick(View v) {

                getActivity().finish();

                System.exit(0);
               //Exits the program on click

            }
        });

        SPINNER.setAdapter(spinnerArrayAdapter);

        SPINNER.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             *  Sets a reality to be displayed on the recycler view.
             *  Toasts a message if the reality that clicked item indicates does not exist.
             *  Callback method to be invoked when an item in this view has been selected.
             *  This callback is invoked only when the newly selected position is different from the previously selected position or if there was no selected item.
             *
             * @param parentView
             *      The AdapterView where the selection happened
             * @param selectedItemView
             *      If non-null, this is the parent view that the fragment's UI should be attached to.
             *      The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
             * @param position
             *      The position of the view in the adapter
             * @param id
             *      The row id of the item that is selected
             */
            @Override
            public void onItemSelected(AdapterView < ? > parentView, View selectedItemView, int position, long id) {

                final int spinnerPosition = position;


                if (position != 0){

                    currentStudentLine = studentLineContainer.getStudentLine(position - 1);
                // The student line to be shown on the recycler view.
                // Since the spinner has one more item than the capacity of reality to be generated, position - 1 is passed as an argument.

                lineRecyclerView = (RecyclerView) rootView.findViewById(R.id.RecylcerSheet);


                lineRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), currentStudentLine.getStudents());


                lineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                lineRecyclerView.setAdapter(lineRecyclerViewAdapter);


                lineRecyclerViewAdapter.notifyDataSetChanged();

            }

                radioButtonConfirm.setOnClickListener(new View.OnClickListener() {
                    // Sets the on click listener to the button that executes functions given from radio buttons

                    @Override
                    public void onClick(View v) {

                        if (spinnerPosition == 0) {

                            Toast.makeText(rootView.getContext(), "Please select the reality", Toast.LENGTH_LONG).show();

                            return;
                            //Toasts a message if the check button is clicked and no item from the spinner is checked.
                        }

                        if (ADD_STUDENT.isChecked()) {

                            Intent intent = new Intent(getContext(), DialogAdd.class);

                            startActivityForResult(intent, 1);

                        } else if (CUT_FRIEND.isChecked()) {

                            Intent intent = new Intent(getContext(), DialogCut.class);

                            startActivityForResult(intent, 2);

                        } else if (REMOVE_STUDENT.isChecked()) {

                            Intent intent = new Intent(getContext(), DialogRemove.class);

                            startActivityForResult(intent, 3);

                        } else if (SERVE_STUDENT.isChecked()) {

                            Intent intent = new Intent(getContext(), DialogServed.class);

                            if(currentStudentLine.numStudents()==0){

                                    Toast.makeText(getContext(), "There is no student to be served.", Toast.LENGTH_LONG).show();

                                    return;
                                }

                            intent.putExtra("SERVED_STUDENT",currentStudentLine.getStudent(0).getName());

                            startActivityForResult(intent, 4);

                        } else if (UPDATE_MONEY.isChecked()) {

                            Intent intent = new Intent(getContext(), DialogUpdateMoney.class);

                            startActivityForResult(intent, 5);

                        } else if (TRADE_PLACES.isChecked()) {

                            Intent intent = new Intent(getContext(), DialogTrade.class);

                            int arraySize = currentStudentLine.numStudents();

                            intent.putExtra("CURRENT_STUDENTLINE_SIZE", arraySize);
                            //Passes array size of current student line to DialogTrade class

                            startActivityForResult(intent, 6);
                        } else {
                            Toast.makeText(rootView.getContext(), "Please check the radio button", Toast.LENGTH_LONG).show();
                            //If no radio button is checked, but clicked the button, it notifies the user.
                        }
                    }
                });
            }
            /**
             *
             * Callback method to be invoked when the selection disappears from this view.
             * The selection can disappear for instance when touch is activated or when the adapter becomes empty.nitialized as an empty set.
             *
             * @param parentView
             *     The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView < ? > parentView) {

            }

        });

        return rootView;
    }

    /**
     * Receives and sets the current student line changed from duplicate fragment.
     * The currently selected student line is shared and updated among fragments dynamically whenever it's changed.
     *
     * @param studentLineContainer Currently selected student line passed from the duplicate fragment
     */
    public void setStudentLineContainer(StudentLineContainer studentLineContainer) {
        this.studentLineContainer = studentLineContainer;
    }


    /**
     *  Called when an activity you launched exits, giving you the requestCode you started it with, the resultCode it returned, and any additional data from it.
     *  The result activities that add, cut, remove, serve, update money and trade places with students are started according to their request codes, number 1 through 6
     *
     * @param requestCode
     *      The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from.
     * @param resultCode
     *      The integer result code returned by the child activity through its setResult().
     * @param data
     *       An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                String name = data.getExtras().getString("ADDED_NAME");

                double money = data.getExtras().getDouble("ADDED_MONEY");

                Student newStudent = new Student(name, money);
                //Takes student data from DialogAdd class and creates a new instance of Student

                try {

                    currentStudentLine.addStudent(currentStudentLine.numStudents(), newStudent);

                } catch (DeanException | InvalidArgumentException e) {

                    if (e instanceof DeanException)

                        Toast.makeText(this.getContext(),
                                "Dean Exception: the line is full",
                                Toast.LENGTH_LONG).show();

                    else {

                        Toast.makeText(this.getContext(),
                                "InvalidArgumentException: the index is out of bound",
                                Toast.LENGTH_LONG).show();

                    }
                    return;
                }

                Toast.makeText(this.getContext(),
                        name + " has been added to the line.",
                        Toast.LENGTH_LONG).show();

            } else if (requestCode == 2) {

                if(currentStudentLine.numStudents()==0){

                    Toast.makeText(this.getContext(),
                            "There is no one to cut into.", Toast.LENGTH_LONG).show();

                    return;
                }

                String name = data.getExtras().getString("CUT_NAME");

                double money = data.getExtras().getDouble("CUT_MONEY");

                int index = data.getExtras().getInt("CUT_INDEX");

                int positionToToast = index;

                index -= 1;

                Student newStudent = new Student(name, money);
                //Takes student data from DialogCut class and creates a new instance of Student

                try {

                    currentStudentLine.addStudent(index, newStudent);

                } catch (DeanException | InvalidArgumentException e) {

                    if (e instanceof DeanException)
                        Toast.makeText(this.getContext(),
                                "Dean Exception: the line is full", Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(this.getContext(),
                                "Illegal InvalidArgumentException: the index is out of bound", Toast.LENGTH_LONG).show();
                    }
                    return;
                }

                Toast.makeText(this.getContext(),
                        name + " has been added to the " + positionToToast + "th position.",
                        Toast.LENGTH_LONG).show();

            } else if (requestCode == 3) {
                int index = data.getExtras().getInt("REMOVED_INDEX");
                //Takes the data where the student is from DialogRemove class.

                String studentName = "";

                index -= 1;

                try {

                    studentName = currentStudentLine.removeStudent(index).getName();

                } catch (EmptyLineException | ArrayIndexOutOfBoundsException ex) {

                    if (ex instanceof EmptyLineException)
                        Toast.makeText(this.getContext(),
                                "EmptyLineException: the line is empty", Toast.LENGTH_LONG).show();

                    else {

                        Toast.makeText(this.getContext(),
                                "ArrayIndexOutOfBoundsException: The index is out of bounds", Toast.LENGTH_LONG).show();
                    }
                    return;
                }

                Toast.makeText(this.getContext(),
                        studentName + " has walked out of the line.",
                        Toast.LENGTH_LONG).show();

            } else if (requestCode == 4) {

                String studentName = "";

                try {

                    studentName = currentStudentLine.removeStudent(0).getName();

                } catch (EmptyLineException e) {

                    Toast.makeText(this.getContext(),
                            "EmptyLineException: the line is empty", Toast.LENGTH_LONG).show();

                    return;
                }
                if (!studentName.equals(""))

                    Toast.makeText(this.getContext(),
                            studentName + " walked out from the line",
                            Toast.LENGTH_LONG).show();

            } else if (requestCode == 5) {

                int index = data.getExtras().getInt("UPDATED_INDEX");

                double money = data.getExtras().getDouble("UPDATED_MONEY");

                String studentName = "";

                index -= 1;

                try {

                    studentName = currentStudentLine.getStudent(index).getName();

                    currentStudentLine.getStudent(index).setMoney(money);

                } catch (ArrayIndexOutOfBoundsException e) {

                    Toast.makeText(this.getContext(),
                            "ArrayIndexOutOfBoundsException: The index is out of bounds", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(this.getContext(),
                        studentName + "'s money has been updated as" + money + "$ successfully",
                        Toast.LENGTH_LONG).show();

            } else if (requestCode == 6) {

                int position1 = data.getExtras().getInt("TRADE_1");

                int position2 = data.getExtras().getInt("TRADE_2");

                String studentName_1 = "";

                String studentName_2 = "";

                try {

                    studentName_1 = currentStudentLine.getStudent(position1).getName();

                    studentName_2 = currentStudentLine.getStudent(position2).getName();

                    currentStudentLine.swapStudents(position1, position2);

                } catch (ArrayIndexOutOfBoundsException ex) {

                    Toast.makeText(this.getContext(),
                            "ArrayIndexOutOfBoundsException: The index is out of bounds", Toast.LENGTH_LONG).show();
                    return;

                }
                Toast.makeText(this.getContext(),
                        studentName_1 + " and " + studentName_2 + " has swapped their position successfully",
                        Toast.LENGTH_LONG).show();
            }
            ((MainActivity) getActivity()).setStudentLineContainerFromHome(studentLineContainer);

            lineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            lineRecyclerView.setAdapter(lineRecyclerViewAdapter);

            lineRecyclerViewAdapter.notifyDataSetChanged();

        }
    }


}