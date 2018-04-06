package com.cse214.theo.sevenflags;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Dialog to initiate the settings for each ride.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class InstantiateDialog extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.instanitate_dialog);

        final TextView titleTxtView = (TextView)findViewById(R.id.instantiate_title);
        final EditText DURATION = (EditText) findViewById(R.id.instantiate_duration);
        final EditText CAPACITY = (EditText) findViewById(R.id.instantiate_capacity);
        final EditText QUEUE = (EditText) findViewById(R.id.instantiate_queue);
        final Button CONFIRM = (Button) findViewById(R.id.instantiate_confirm);
        String title =getIntent().getExtras().getString("TITLE");
        titleTxtView.setText(title);
        CONFIRM.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {


                        if (DURATION.getText().toString().equals("") || CAPACITY.getText().toString().equals("") || QUEUE.getText().toString().equals("")) {

                            Toast.makeText(getBaseContext(), "Please Enter the information.", Toast.LENGTH_LONG).show();

                            return;
                        }
                        int duration = Integer.parseInt(DURATION.getText().toString());
                        int capacity = Integer.parseInt(CAPACITY.getText().toString());
                        int queue = Integer.parseInt(QUEUE.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                        intent.putExtra("INSTANTIATE_DURATION", duration);
                        intent.putExtra("INSTANTIATE_CAPACITY", capacity);
                        intent.putExtra("INSTANTIATE_QUEUE", queue);
                        setResult(RESULT_OK, intent);
                        finish();


                    }

                });


    }


}
