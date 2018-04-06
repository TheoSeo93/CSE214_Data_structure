package com.cse214.theo.mailroommanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is a dialog activity to instantiate a new package with data received from user.
 * The data from two text views that takes package recipient and weight is passed to the main activity, ScrollingActivity in this case, in order to display the current mail room.
 *
 * @author Theo Seo, SBU ID: 111319497
 *         Homework #3 for CSE 214, fall 2017
 */
public class DialogDeliver extends AppCompatActivity {

    /**
     * This is the built-in method for activity.
     *
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_dialog);

        final EditText NAMETXT = (EditText) findViewById(R.id.deliver_name);

        final EditText WEIGHTTXT = (EditText) findViewById(R.id.deliver_weight);

        final Button CONFIRM = (Button) findViewById(R.id.deliver_confirm);


                CONFIRM.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View view) {


                                if (NAMETXT.getText().toString().equals("")||WEIGHTTXT.getText().toString().equals("")) {

                                    Toast.makeText(getBaseContext(), "Please Enter the information.", Toast.LENGTH_LONG).show();

                                    return;
                                }
                                String name = NAMETXT.getText().toString();

                                double weight = Double.parseDouble(WEIGHTTXT.getText().toString());

                                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);

                                intent.putExtra("DELIVER_WEIGHT",weight );

                                intent.putExtra("DELIVER_NAME", name);

                                setResult(RESULT_OK, intent);

                                finish();


                            }

                        });




            }






    }
