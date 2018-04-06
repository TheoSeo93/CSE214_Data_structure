package com.cse214.theo.mailroommanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This is a dialog activity to move a package at the top of a specific package stack to push that to an another stack.
 * The two spinner item delivers scrolling activity the information of from which stack or to which stack the top most should be moved.
 *
 * @author Theo Seo, SBU ID: 111319497
 *         Homework #3 for CSE 214, fall 2017
 */
public class DialogMove extends AppCompatActivity {

    /**
     * Array adapter for spinner that takes string values.
     */
    private ArrayAdapter < String > spinnerArrayAdapter;

    /**
     * The package stack number to which the package to be moved.
     */
    private int destination;


    /**
     * The package stack number from which the package to be moved.
     */
    private int source;


    /**
     * The items of spinner array adapter.
     * In this case, the items are every package stack that stores mails.
     */
    private String[] spinnerArray = {
            "Stack 1",
            "Stack 2",
            "Stack 3",
            "Stack 4",
            "Stack 5",
            "Stack 0(Floor)",

    };


    /**
     * This is a built-in method from android studio.
     *
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.move_dialog);

        final Spinner sourceSpinner = (Spinner) findViewById(R.id.spinner_source);

        final Spinner destinationSpinner = (Spinner) findViewById(R.id.spinner_destination);

        final Button CONFIRM = (Button) findViewById(R.id.move_confirm);

        spinnerArrayAdapter = new ArrayAdapter <> (this, R.layout.spinner_item, spinnerArray);

        sourceSpinner.setAdapter(spinnerArrayAdapter);

        destinationSpinner.setAdapter(spinnerArrayAdapter);

        AdapterView.OnItemSelectedListener onItemSelectedListener_spinner1 = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView < ? > parentView, View selectedItemView, int position, long id) {

                source = position;


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

        };

        sourceSpinner.setOnItemSelectedListener(onItemSelectedListener_spinner1);

        AdapterView.OnItemSelectedListener onItemSelectedListener_spinner2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > parentView, View selectedItemView, int position, long id) {

                destination = position;


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

        };
        destinationSpinner.setOnItemSelectedListener(onItemSelectedListener_spinner2);


        CONFIRM.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View view) {

                                //If no options selected, but the check button is pressed, it toasts a message.
                                if (source==-1||destination==-1) {

                                    Toast.makeText(getBaseContext(), "Please select the options.", Toast.LENGTH_LONG).show();

                                    return;
                                }

                                //If the source position and destination position are selected as the same, it toasts a message, too.
                                else if (source==destination) {

                                    Toast.makeText(getBaseContext(), "Can't move a package to the same position.", Toast.LENGTH_LONG).show();

                                    return;
                                }

                                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);

                                //Passes the source position and destination position value to the scrolling activity intent.
                                intent.putExtra("MOVE_SOURCE",source );

                                intent.putExtra("MOVE_DESTINATION", destination);

                                setResult(RESULT_OK, intent);

                                finish();


                            }

                        });




            }






    }
