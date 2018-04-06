package com.cse214.theo.sevenflags;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Dialog that shows the statistics for the result.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class ResultDialog extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.result_dialog);

         TextView regular = (TextView)findViewById(R.id.regular_ave);
        TextView silver = (TextView)findViewById(R.id.silver_ave);
        TextView gold = (TextView)findViewById(R.id.gold_ave);
        TextView bsod = (TextView)findViewById(R.id.bsod_times);
        TextView kk = (TextView)findViewById(R.id.kk_times);
        TextView tot = (TextView)findViewById(R.id.tot_times);
        TextView gf = (TextView)findViewById(R.id.gf_times);



        final Button CONFIRM = (Button) findViewById(R.id.result_confirm);

        gold.setText(this.getIntent().getExtras().getString("FIRST"));
        silver.setText(this.getIntent().getExtras().getString("SECOND"));
        regular.setText(this.getIntent().getExtras().getString("THIRD"));
        bsod.setText(this.getIntent().getExtras().getString("FOURTH"));
        kk.setText(this.getIntent().getExtras().getString("FIFTH"));
        tot.setText(this.getIntent().getExtras().getString("SIXTH"));
        gf.setText(this.getIntent().getExtras().getString("SEVENTH"));

        CONFIRM.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        finish();


                    }

                });


    }


}
