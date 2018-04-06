package com.cse214.theo.lunchtimeapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

    /**
     * The main activity class that runs the application
     *
     * @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */

public class MainActivity extends AppCompatActivity {

    /**
     * The container object that contains a multiple realities, which is student lines in different realities
     */
    private StudentLineContainer studentLineContainer;

    /**
     * The fragment functions to duplicate a specified reality to the end
     */
    private FragmentDuplicate fd = new FragmentDuplicate();

    /**
     * The default fragment set at the first time, which shows the current line of students and the radio button options to add, cut, remove, trade and serve students.
     */
    private FragmentHome fh = new FragmentHome();

    /**
     * The fragment functions to compare two realities.
     */
    private FragmentCompare fc = new FragmentCompare();

    /**
     * On navigation view listener for the bottom navigation view that changes the fragment to be shown as clicked accordingly.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener newOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            //Interface for interacting with Fragment objects inside of an Activity

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // Execute a transaction, replacing any existing fragment


            switch (item.getItemId()) {
                case R.id.display_line:

                    transaction.replace(R.id.content, fh).commit();
                    //When this item selected, this replaces current fragment to the default fragment, compare_home.

                    return true;
                case R.id.reality_compare:

                    transaction.replace(R.id.content, fc).commit();
                    //When this item selected, this replaces current fragment to the fragment_compare.

                    return true;
                case R.id.duplicate_reality:

                    transaction.replace(R.id.content, fd).commit();
                    //When this item selected, this replaces current fragment to the fragment_duplicate.

                    return true;

            }


            return false;
        }

    };

    /**
     * Passes and sets the current student line changed from home fragment to compare fragment and duplicate fragment.
     * The currently selected student line is shared and updated among fragments dynamically whenever it's changed.
     *
     * @param studentLineContainer Currently selected student line passed from the home fragment
     */
    public void setStudentLineContainerFromHome(StudentLineContainer studentLineContainer) {

        fd.setStudentLineContainer(studentLineContainer);

        fc.setStudentLineContainer(studentLineContainer);

        // passes down the parameter to duplicate fragment and compare fragment
    }

    /**
     * Passes and sets the current student line changed from duplicate fragment to compare fragment and home fragment.
     * The currently selected student line is shared and updated among fragments dynamically whenever it's changed.
     *
     * @param studentLineContainer Currently selected student line passed from the home fragment
     */

    public void setStudentLineContainerFromDuplicate(StudentLineContainer studentLineContainer) {

        fh.setStudentLineContainer(studentLineContainer);
        fc.setStudentLineContainer(studentLineContainer);

        // passes down the parameter to home fragment and compare fragment
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //Sets the activity content to an explicit view.

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //Represents a bottom navigation bar for application

        navigation.setOnNavigationItemSelectedListener(newOnNavigationItemSelectedListener);
        //Sets OnNavigationItemSelectedListener

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.content, new FragmentHome()).commit();
        //Sets the home fragment as the default fragment to be shown when beginning the application.

    }
}