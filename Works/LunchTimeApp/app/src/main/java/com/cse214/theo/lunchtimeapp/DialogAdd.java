package com.cse214.theo.lunchtimeapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
    /**
     * The activity class that takes student's name and money to add him or her to the end of the line
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class DialogAdd extends AppCompatActivity {

    /**
     * Called when the activity is starting. After receiving information of student's name and money, it sends them to the main activity intent.
     *
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_add);

        final EditText STUDENT_NAME = (EditText) findViewById(R.id.add_index);

        final EditText MONEY_TEXT = (EditText) findViewById(R.id.add_money);

        final Button CONFIRM = (Button) findViewById(R.id.add_button);

        CONFIRM.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        if (STUDENT_NAME.getText().toString().equals("") || MONEY_TEXT.getText().toString().equals("")) {

                            Toast.makeText(getBaseContext(), "Please Enter the information.", Toast.LENGTH_LONG).show();

                            return;
                        }
                        String name = STUDENT_NAME.getText().toString();

                        double money = Double.parseDouble(MONEY_TEXT.getText().toString());

                        intent.putExtra("ADDED_NAME", name);

                        intent.putExtra("ADDED_MONEY", money);

                        setResult(RESULT_OK, intent);

                        finish();

                    }
                });


    }

}