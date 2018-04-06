package com.cse214.theo.roadcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Dialog Class that shows whether the connection to the xml is successful
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #7 for CSE 214, fall 2017
 */
public class ConnectionDialog extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_dialog);

        final TextView titleTxtView = (TextView) findViewById(R.id.connection);


        String data_content = getIntent().getExtras().getString("CONNECTION");
        titleTxtView.setText(data_content);
        titleTxtView.setTextSize(16);



    }


}
