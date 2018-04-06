package com.cse214.theo.sevenflags;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Start dialog that passes the settings data for the simulation to the scrolling activity.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class StartDialog extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_dialog);

        final EditText regular = (EditText)findViewById(R.id.setting_regular);

        final EditText silver = (EditText)findViewById(R.id.setting_silver);

        final EditText gold = (EditText)findViewById(R.id.setting_gold);

        final EditText length = (EditText)findViewById(R.id.setting_simulation_length);

        final Button CONFIRM = (Button) findViewById(R.id.setting_confirm);


        CONFIRM.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {


                        if (regular.getText().toString().equals("") || silver.getText().toString().equals("") || length.getText().toString().equals("")||gold.getText().toString().equals("")) {

                            Toast.makeText(getBaseContext(), "Please Enter the information.", Toast.LENGTH_LONG).show();

                            return;
                        }
                        int regularNum = Integer.parseInt(regular.getText().toString());

                        int silverNum = Integer.parseInt(silver.getText().toString());

                        int goldNum = Integer.parseInt(gold.getText().toString());

                        int simulLength =  Integer.parseInt(length.getText().toString());

                        Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);

                        intent.putExtra("SETTING_REGULAR", regularNum);

                        intent.putExtra("SETTING_SILVER", silverNum);

                        intent.putExtra("SETTING_GOLD", goldNum);

                        intent.putExtra("SETTING_LENGTH", simulLength);

                        setResult(RESULT_OK, intent);

                        finish();


                    }

                });


    }


}
