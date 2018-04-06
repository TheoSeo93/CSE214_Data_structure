package com.cse214.theo.mailroommanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is a dialog activity to take out a package of a specific recipient.
 * The text view that takes recipient name is passed to the ScrollingActivity, in order to take out the package.
 *
 * @author Theo Seo, SBU ID: 111319497
 *         Homework #3 for CSE 214, fall 2017
 */
public class DialogGet extends AppCompatActivity {


    /**
     * This is a built-in method of android studio.
     *
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.get_dialog);

        final EditText NAMETXT = (EditText) findViewById(R.id.deliver_name);

        final Button CONFIRM = (Button) findViewById(R.id.deliver_confirm);


                CONFIRM.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View view) {


                                String name = NAMETXT.getText().toString();


                                    if (name.equals("")) {

                                    Toast.makeText(getBaseContext(), "Please Enter the information.", Toast.LENGTH_LONG).show();

                                    return;
                                }
                                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);


                                intent.putExtra("RECIPIENT_NAME", name);

                                setResult(RESULT_OK, intent);

                                finish();


                            }

                        });




            }






    }
