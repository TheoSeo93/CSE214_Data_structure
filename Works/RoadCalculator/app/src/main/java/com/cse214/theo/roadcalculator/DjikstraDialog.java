package com.cse214.theo.roadcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Dialog Class to start to find the shortest path.
 * This dialog takes the source city and destination city from the user.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #7 for CSE 214, fall 2017
 */
public class DjikstraDialog extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.djikstra);

        final EditText source = (EditText) findViewById(R.id.source);

        final EditText destination = (EditText) findViewById(R.id.destination);

        final Button confirm = (Button) findViewById(R.id.djikstra_confirm);


        confirm.setOnClickListener(

                new View.OnClickListener() {

                    public void onClick(View view) {



                        Intent result = new Intent(getApplicationContext(), MainActivity.class);
                        result.putExtra("SOURCE",source.getText().toString().toUpperCase());
                        result.putExtra("DESTINATION", destination.getText().toString().toUpperCase());
                        setResult(RESULT_OK, result);

                        finish();
                    }


                });


    }


}
