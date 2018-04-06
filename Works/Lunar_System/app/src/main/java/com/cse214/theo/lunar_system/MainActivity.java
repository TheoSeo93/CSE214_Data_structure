package com.cse214.theo.lunar_system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.clans.fab.FloatingActionButton;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import io.ghyeok.stickyswitch.widget.StickySwitch;
/**
 * The activity which will be the main activity class that runs the application.
 * The database will mainly be stored here.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #6 for CSE 214, fall 2017
 *
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The database of student IDs and current enrollment state.
     */
    private HashMap<String, Student> database = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.login_button);

        FloatingActionButton exitWithoutSave = findViewById(R.id.menu_exit);

        FloatingActionButton exitWithSave = findViewById(R.id.menu_save);

        final LovelyTextInputDialog loginDialog = new LovelyTextInputDialog(this, R.style.Theme_AppCompat_Dialog_Alert);

        final StickySwitch stickySwitch = findViewById(R.id.sticky_switch);

        //Loads the previous database if there is any.
        try {
            loadDataBase();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            Toast.makeText(MainActivity.this, "No previous data found.", Toast.LENGTH_SHORT).show();
        }
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {


            }
        });

        // If this FAB is clicked, it will exit with saving the database to the files directory.
        exitWithSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LovelyStandardDialog(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                        .setTopColorRes(R.color.dialogColor)
                        .setButtonsColorRes(R.color.colorPrimary)
                        .setTitle("Log-Out")
                        .setMessage("Are you sure you want to save the data and exit?")
                        .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    saveDatabase();
                                } catch (IOException | ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                                }

                                finish();
                                Toast.makeText(getBaseContext(), "Bye Bye", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

            }
        });

        // If this FAB is clicked, it will exit without saving the database to the files directory.
        exitWithoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LovelyStandardDialog(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert)
                        .setTopColorRes(R.color.dialogColor)
                        .setButtonsColorRes(R.color.colorPrimary)
                        .setTitle("Log-Out")
                        .setMessage("Are you sure you want to exit without save?")
                        .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    FileOutputStream file = new FileOutputStream(new File(getFilesDir(), "Lunar.ser"));

                                    ObjectOutputStream s = new ObjectOutputStream(file);
                                    s.flush();
                                    s.close();
                                } catch (IOException e) {

                                    e.printStackTrace();
                                }
                                finish();
                                Toast.makeText(getBaseContext(), "Bye Bye", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        // Shimmering effect of a title.
        ShimmerFrameLayout container = findViewById(R.id.shimmer_view_container);

        container.setDuration(3000);

        container.startShimmerAnimation();

        //If the user is set to be registrar, then it will pass the database to registrar activity start it.
        //Otherwise, it will start the student activity.
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (stickySwitch.getDirection().toString().equals("RIGHT")) {

                    loginDialog.setTopColorRes(R.color.dialogColor)

                            .setTitle(R.string.dialog_title)

                            .setInputFilter("There is no such student", new LovelyTextInputDialog.TextFilter() {

                                @Override
                                public boolean check(String text) {
                                    text=text.toUpperCase();
                                    if (database.containsKey(text))
                                        return true;

                                    else
                                        return false;
                                }
                            })
                            .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                                @Override
                                public void onTextInputConfirmed(String text) {

                                        text=text.toUpperCase();

                                        Intent intent = new Intent(getBaseContext(), StudentActivity.class);

                                        intent.putExtra("CURRENT_STUDENT", database.get(text));

                                        intent.putExtra("HASHMAP", database);

                                        startActivityForResult(intent, 0);


                                    Toast.makeText(MainActivity.this, "Welcome " + text, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();

                } else {

                    Intent intent = new Intent(view.getContext(), RegistrarActivity.class);

                    intent.putExtra("HASHMAP", database);

                    startActivityForResult(intent, 0);

                    Toast.makeText(MainActivity.this, "Welcome REGISTRAR", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    /**
     * Receives the updated database from either student activity or registrar activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 0) {

                database = (HashMap<String, Student>) data.getSerializableExtra("DATABASE");

            }
        }
    }

    /**
     * Saves the database to the filesDirectory.
     */
    public void saveDatabase() throws IOException, ClassNotFoundException {

        FileOutputStream file = new FileOutputStream(new File(getFilesDir(), "Lunar.ser"));
        ObjectOutputStream s = new ObjectOutputStream(file);
        s.writeObject(database);
        s.close();

    }

    /**
     * Loads the database to the filesDirectory.
     */
    public void loadDataBase() throws IOException, ClassNotFoundException {

        FileInputStream file = new FileInputStream(new File(getFilesDir(), "Lunar.ser"));
        ObjectInputStream s = new ObjectInputStream(file);
        database = (HashMap<String, Student>) s.readObject();
        s.close();

    }

    /**
     * When back button on the phone is touched, then a dialog will pop up to ask the user to ensure exit with saving or not.
     */
    @Override
    public void onBackPressed() {
        new LovelyStandardDialog(this)
                .setTopColorRes(R.color.dialogColor)
                .setButtonsColorRes(R.color.colorPrimary)
                .setTitle("Log-Out")
                .setMessage("Would you like to save and log out?")
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            saveDatabase();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        finish();
                        Toast.makeText(getBaseContext(), "Bye, Bye :)", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        FileOutputStream file = null;
                        try {
                            file = new FileOutputStream(new File(getFilesDir(), "Lunar.ser"));

                            ObjectOutputStream s = new ObjectOutputStream(file);
                            s.flush();
                            s.close();
                        } catch (IOException e) {

                            e.printStackTrace();
                        }

                        Toast.makeText(getBaseContext(), "Bye, Bye :)", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }
}

