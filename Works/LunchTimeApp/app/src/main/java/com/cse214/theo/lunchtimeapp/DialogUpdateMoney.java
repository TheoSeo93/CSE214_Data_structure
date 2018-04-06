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
     * @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class DialogUpdateMoney extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * Takes the name, position and the amount of money to be updated.
     *
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_update_money);

        final EditText INDEX = (EditText) findViewById(R.id.update_index);

        final EditText MONEY = (EditText) findViewById(R.id.update_money);

        final Button CONFIRM = (Button) findViewById(R.id.update_confirm);


        CONFIRM.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        if (INDEX.getText().toString().equals("") || MONEY.getText().toString().equals("")) {

                            Toast.makeText(getBaseContext(), "Please Enter the information.", Toast.LENGTH_LONG).show();

                            return;
                        }

                        int indexVar = Integer.parseInt(INDEX.getText().toString());

                        double moneyVar = Double.parseDouble(MONEY.getText().toString());

                        intent.putExtra("UPDATED_INDEX", indexVar);

                        intent.putExtra("UPDATED_MONEY", moneyVar);

                        setResult(RESULT_OK, intent);

                        finish();

                    }
                });


    }
}