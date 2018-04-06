package com.cse214.theo.roadcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Dialog to show the towns, roads, minimum spanning tree, and the shortest path.
 * All of the results will be shown on a text view.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #7 for CSE 214, fall 2017
 */
public class ResultDialog extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_result);

        final TextView titleTxtView = (TextView) findViewById(R.id.result);


        String data_content = getIntent().getExtras().getString("DATA_CONTENT");
        titleTxtView.setText(data_content);
        titleTxtView.setTextSize(12);


    }


}
