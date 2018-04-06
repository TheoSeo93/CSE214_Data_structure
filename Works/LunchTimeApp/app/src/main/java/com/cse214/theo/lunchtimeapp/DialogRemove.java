package com.cse214.theo.lunchtimeapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

    /**
     * The activity class that takes student's position to remove him or her from the line
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class DialogRemove extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * After receiving the student's index from the user, it sends them to the main activity by intent.
     *
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_remove);

        final EditText REMOVED_INDEX = (EditText) findViewById(R.id.update_index);

        final Button REMOVE_BUTTON = (Button) findViewById(R.id.remove_button);

        REMOVE_BUTTON.setOnClickListener(

                new View.OnClickListener() {

                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        if (REMOVED_INDEX.getText().toString().equals("")) {

                            Toast.makeText(getBaseContext(), "Please Enter the information.", Toast.LENGTH_LONG).show();

                            return;
                        }
                        int index = Integer.parseInt(REMOVED_INDEX.getText().toString());

                        intent.putExtra("REMOVED_INDEX", index);

                        setResult(RESULT_OK, intent);

                        finish();

                    }

                });

    }

}