package com.cse214.theo.lunchtimeapp;

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
     * The activity class that takes student's name and money to add him or her to the end of the line
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class DialogTrade extends AppCompatActivity {

    /**
     * Array adapter for spinner.
     */
    private ArrayAdapter < Integer > spinnerArrayAdapter;

    /**
     * Options to be displayed on the spinner
     */
    private Integer[] spinnerArray;

    /**
     * Position integer for the left spinner.
     */
    private int tradePlace_1 = -1;

    /**
     *  Position integer for the right spinner
     */
    private int tradePlace_2 = -1;

    /**
     * Called when the activity is starting.
     * Gives you the options to select two positions to trade with two spinners.
     *
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_trade);

        final Spinner SPINNER_1 = (Spinner) findViewById(R.id.trade_spinner1);

        final Spinner SPINNER_2 = (Spinner) findViewById(R.id.trade_spinner2);

        final Button CONFIRM = (Button) findViewById(R.id.trade_confirm);

        final int STUDENTLINE_SIZE = getIntent().getExtras().getInt("CURRENT_STUDENTLINE_SIZE");


        spinnerArray = new Integer[STUDENTLINE_SIZE];

        for (int i = 0; i < STUDENTLINE_SIZE; i++) {
            spinnerArray[i] = i + 1;
        }

        spinnerArrayAdapter = new ArrayAdapter < Integer > (this, android.R.layout.simple_spinner_item, spinnerArray);

        SPINNER_1.setAdapter(spinnerArrayAdapter);

        SPINNER_2.setAdapter(spinnerArrayAdapter);


        AdapterView.OnItemSelectedListener onItemSelectedListener_spinner1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > parentView, View selectedItemView, int position, long id) {

                tradePlace_1 = position;


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


        AdapterView.OnItemSelectedListener onItemSelectedListener_spinner2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > parentView, View selectedItemView, int position, long id) {


                tradePlace_2 = position;


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

        SPINNER_1.setOnItemSelectedListener(onItemSelectedListener_spinner1);

        SPINNER_2.setOnItemSelectedListener(onItemSelectedListener_spinner2);

        CONFIRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                if (tradePlace_1 == -1 || tradePlace_2 == -1) {

                    Toast.makeText(view.getContext(), "Please select the reality", Toast.LENGTH_LONG).show();

                    return;
                }

                intent.putExtra("TRADE_1", tradePlace_1);

                intent.putExtra("TRADE_2", tradePlace_2);

                setResult(RESULT_OK, intent);

                finish();
            }
        });


    }
}