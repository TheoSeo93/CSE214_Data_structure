package com.cse214.theo.roadcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import big.data.DataSourceException;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Side;

/**
 * This is the MainActivity class that runs the application.
 * To make the design simple, there are only three components including text input, fab menu, and an animated text view.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #7 for CSE 214, fall 2017
 */
public class MainActivity extends AppCompatActivity {
    private RoadCalculator roadCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roadCalculator = new RoadCalculator();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        BoomMenuButton bmb = findViewById(R.id.bmb);
        Text first = TextBuilder
                .create("Hello, This is Theo's last homework :) ")
                .setSize(17)
                .setAlpha(0)
                .setColor(Color.BLACK)
                .setPosition(Align.SURFACE_CENTER).build();

        Text second = TextBuilder
                .create("Please enter the url to proceed the application  ")
                .setSize(12)
                .setAlpha(0)
                .setColor(Color.BLACK)
                .setPosition(Align.BOTTOM_OF, first).build();
        TextSurface textSurface = findViewById(R.id.text_surface);

        textSurface.play(
                new Sequential(
                        Slide.showFrom(Side.TOP, first, 1000),
                        Delay.duration(1000),
                        Alpha.hide(first, 1000)
                        , new Sequential(Delay.duration(300), ShapeReveal.create(second, 600, SideCut.show(Side.LEFT), false)))


        );

        final ExtendedEditText extendedEditText = findViewById(R.id.extended_edit_text);
        final TextFieldBoxes textFieldBoxes = findViewById(R.id.text_field_boxes);
        final String prefix = "http://www";


        textFieldBoxes.setEndIcon(R.drawable.ok);

        textFieldBoxes.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    roadCalculator.buildGraph(prefix + extendedEditText.getText());

                } catch (DataSourceException ex) {
                    Intent connection = new Intent(getBaseContext(), ConnectionDialog.class);
                    connection.putExtra("CONNECTION", "Could not read the url");
                    startActivity(connection);
                    return;
                }

                Intent connection = new Intent(getBaseContext(), ConnectionDialog.class);
                connection.putExtra("CONNECTION", "Successfully connected to the xml!");
                startActivity(connection);
            }


        });


        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {

            if (i == 0) {
                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .rotateText(false)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Intent result = new Intent(getBaseContext(), ResultDialog.class);
                                result.putExtra("DATA_CONTENT", roadCalculator.printTowns());
                                startActivity(result);
                            }
                        })
                        .normalText("Print Towns");
                bmb.addBuilder(builder);
            } else if (i == 1) {

                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .rotateText(false)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Intent result = new Intent(getBaseContext(), ResultDialog.class);
                                result.putExtra("DATA_CONTENT", roadCalculator.printRoads());
                                startActivity(result);
                            }
                        })
                        .normalText("Print Roads");
                bmb.addBuilder(builder);

            } else if (i == 2) {

                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .rotateText(false)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                roadCalculator.buildMST();
                                Intent result = new Intent(getBaseContext(), ResultDialog.class);
                                result.putExtra("DATA_CONTENT", roadCalculator.printMST());
                                startActivity(result);
                            }
                        })
                        .normalText("Print Minimum Spanning Tree");
                bmb.addBuilder(builder);

            } else {

                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .rotateText(false)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Intent result = new Intent(getBaseContext(), DjikstraDialog.class);

                                startActivityForResult(result, 0);

                            }
                        })
                        .normalText("Print path");
                bmb.addBuilder(builder);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            //Settings for the simulation.
            if (requestCode == 0) {
                String src = data.getExtras().getString("SOURCE");
                String dest = data.getExtras().getString("DESTINATION");
                Intent result = new Intent(getBaseContext(), ResultDialog.class);

                if (!roadCalculator.getGraph().containsKey(src) || !roadCalculator.getGraph().containsKey(dest)) {
                    Toast.makeText(this.getBaseContext(), "Could not find the nodes ", Toast.LENGTH_LONG).show();
                    return;
                }
                result.putExtra("DATA_CONTENT", roadCalculator.printShortestPath(src, dest));


                startActivity(result);

            }
        }


    }
}
