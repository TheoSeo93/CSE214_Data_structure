package com.cse214.theo.lunchtimeapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.cse214.theo.lunchtimeapp.R.layout.dialog_cut;

/**
 * The activity class that takes the position to cut, student's name and money to add cut him or her into the line.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #1 for CSE 214, fall 2017
 */


public class DialogCut extends AppCompatActivity {

    /**
     * Called when the activity is starting. After receiving information of student's name, position and money, it sends them to the main activity by intent.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(dialog_cut);

        final EditText STUDENT_NAME = (EditText) findViewById(R.id.update_index);

        final EditText MONEY_TEXT = (EditText) findViewById(R.id.update_money);

        final EditText CUT_INDEX = (EditText) findViewById(R.id.cut_index);

        final Button CONFIRM = (Button) findViewById(R.id.cut_button);

        CONFIRM.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        if (STUDENT_NAME.getText().toString().equals("") ||
                                MONEY_TEXT.getText().toString().equals("") ||
                                CUT_INDEX.getText().toString().equals("")) {

                            Toast.makeText(getBaseContext(), "Please Enter the information.", Toast.LENGTH_LONG).show();

                            return;

                        }

                        String name = STUDENT_NAME.getText().toString();

                        double money = Double.parseDouble(MONEY_TEXT.getText().toString());

                        int index = Integer.parseInt(CUT_INDEX.getText().toString());

                        intent.putExtra("CUT_NAME", name);

                        intent.putExtra("CUT_MONEY", money);

                        intent.putExtra("CUT_INDEX", index);

                        setResult(RESULT_OK, intent);

                        finish();

                    }
                });


    }
}