package com.cse214.theo.lunchtimeapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
     * The activity class that takes out the student at the first position from the line after serving.
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class DialogServed extends AppCompatActivity {


    /**
     * Called when the activity is starting.
     * Shows a brief dialog that the student has been served and remove the student from the line.
     *
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_served);

        final Button confirm = (Button) findViewById(R.id.served_button);

        final TextView showTxt = (TextView) findViewById(R.id.served_text);

        String name = getIntent().getExtras().getString("SERVED_STUDENT");

        showTxt.setText(name+" has been served today's meal. Enjoy your lunch, "+ name+" !");

        GradientDrawable gradientDrawable = new GradientDrawable();

        gradientDrawable.setColor(Color.parseColor("#6e2c00"));

        gradientDrawable.setCornerRadius(10);

        showTxt.setBackground(gradientDrawable);

        showTxt.setTextSize(COMPLEX_UNIT_SP,26);

        showTxt.setGravity(Gravity.CENTER_HORIZONTAL);


        confirm.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        setResult(RESULT_OK, intent);

                        finish();

                    }
                });

    }
}