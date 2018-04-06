package com.cse214.theo.lunar_system;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Registrar activity that enables the registrar to register or de register a student, and view the students enrolled in a specific course.
 *
 * @author Theo Seo, SBU ID: 111319497
 *         <p>
 *         Homework #6 for CSE 214, fall 2017
 */
public class RegistrarActivity extends AppCompatActivity {


    /**
     * Arraylist of students that enrolled in this course
     */
    private ArrayList<Student> studentArrayList;


    /**
     * Stores the tokenized strings of department and course number when passed the whole information of a course from the user.
     */
    private String department;
    private int courseNum;

    /**
     * The tableLayout that show who have enrolled in a specific course.
     */
    private TableLayout tableLayout;

    /**
     * This stores the database passed from the main activity.
     */
    private HashMap<String, Student> database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrar);

        database = (HashMap<String, Student>) this.getIntent().getSerializableExtra("HASHMAP");

        tableLayout = findViewById(R.id.registrar_table);

        final LovelyTextInputDialog lovelyTextInputDialog = new LovelyTextInputDialog(this, R.style.Theme_AppCompat_Dialog_Alert);

        studentArrayList = new ArrayList<>(database.values());

        BoomMenuButton bmb = findViewById(R.id.bmb);


        bmb.getCustomPiecePlacePositions().add(new PointF(0, 0));


        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {

            if (i == 0) {

                HamButton.Builder builder = new HamButton.Builder()

                        .normalTextRes(R.string.normal_view_course)

                        .subNormalTextRes(R.string.subnormal_view_course)

                        .listener(new OnBMClickListener() {

                            @Override
                            public void onBoomButtonClick(int index) {

                                lovelyTextInputDialog.setTopColorRes(R.color.dialogColor)

                                        .setTitle("Please enter a course")

                                        .addTextWatcher(new TextWatcher() {

                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                lovelyTextInputDialog.setMessage("Format: department+number");

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                                Pattern department = Pattern.compile("\\b[A-Za-z]{3}");
                                                Pattern courseName = Pattern.compile("(\\b[A-Za-z]{3}\\s*)[0-9]{3}\\s*(?=[fFsS]{1}\\s*[0-9]{4}\\b)");
                                                Matcher matcher;
                                                if (editable.length() <= 3) {
                                                    matcher = department.matcher(editable);
                                                    if (!matcher.find())
                                                        lovelyTextInputDialog.setMessage("Please write department: 3 Alphabets example: CSE ");
                                                    else
                                                        lovelyTextInputDialog.setMessage("Please add course number: 3 Digits example: 114");

                                                } else {

                                                    matcher = courseName.matcher(editable);

                                                    if (!matcher.find())

                                                        lovelyTextInputDialog.setMessage("Please add course number: 3 Digits example: 114");

                                                    else
                                                        lovelyTextInputDialog.setMessage("Correct Format! Now please enter.");
                                                }

                                            }
                                        })
                                        .setInputFilter("Invalid input, format: [department]+[number]+[semester]", new LovelyTextInputDialog.TextFilter() {

                                            @Override
                                            public boolean check(String text) {

                                                if (text.matches("\\b[A-Za-z]{3}\\s*[0-9]{3}")) {
                                                    text.toUpperCase();
                                                    text.replaceAll("\\s+", "");
                                                    Pattern coursePattern = Pattern.compile("\\b[A-Za-z]{3}");
                                                    Pattern numberPattern = Pattern.compile("(?!\\b[A-Za-z]{3}\\s*)[0-9]{3}");
                                                    Matcher matcher;

                                                    matcher = coursePattern.matcher(text);
                                                    if (matcher.find()) {
                                                        department = matcher.group(0);
                                                    }

                                                    matcher = numberPattern.matcher(text);

                                                    if (matcher.find()) {

                                                        courseNum = Integer.valueOf(matcher.group(0));
                                                    }

                                                    updateTableLayout(department + courseNum);


                                                }
                                                return text.matches("\\b[A-Za-z]{3}\\s*[0-9]{3}");

                                            }
                                        })
                                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                                            @Override
                                            public void onTextInputConfirmed(String text) {


                                            }
                                        })
                                        .show();


                            }
                        });

                bmb.addBuilder(builder);

            } else if (i == 1) {

                HamButton.Builder builder = new HamButton.Builder()


                        .normalTextRes(R.string.normal_register)

                        .subNormalTextRes(R.string.subnormal_register)

                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {


                                new LovelyTextInputDialog(RegistrarActivity.this, R.style.Theme_AppCompat_Dialog_Alert)

                                        .setTitle("Register a student")
                                        .setMessage("Please enter a new webId")
                                        .setInputFilter("There exists the same id ", new LovelyTextInputDialog.TextFilter() {

                                            @Override
                                            public boolean check(String text) {
                                                text = text.toUpperCase();

                                                if (database.containsKey(text)) {
                                                    return false;
                                                } else {

                                                    text = text.toUpperCase();
                                                    database.put(text, new Student(text));
                                                    return true;
                                                }


                                            }

                                        })
                                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                                            @Override
                                            public void onTextInputConfirmed(String text) {

                                                Toast.makeText(RegistrarActivity.this, "Registered " + text, Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .show();


                            }
                        });
                bmb.addBuilder(builder);
            } else if (i == 2) {

                HamButton.Builder builder = new HamButton.Builder()

                        .normalTextRes(R.string.normal_deregister)

                        .subNormalTextRes(R.string.subnormal_deregister)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {

                                new LovelyTextInputDialog(RegistrarActivity.this, R.style.Theme_AppCompat_Dialog_Alert)

                                        .setTopColorRes(R.color.dialogColor)

                                        .setTitle("Please enter a webid for the student to be deregistered")

                                        .setInputFilter("There is no such student", new LovelyTextInputDialog.TextFilter() {

                                            @Override
                                            public boolean check(String text) {

                                                text = text.toUpperCase();

                                                if (database.containsKey(text)) {
                                                    database.remove(text);
                                                    studentArrayList = new ArrayList<>(database.values());
                                                    updateTableLayout(department + courseNum);
                                                    return true;
                                                } else
                                                    return false;


                                            }
                                        })
                                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                                            @Override
                                            public void onTextInputConfirmed(String text) {
                                                Toast.makeText(RegistrarActivity.this, text + " deregistered", Toast.LENGTH_SHORT).show();

                                            }
                                        })
                                        .show();

                            }
                        });
                bmb.addBuilder(builder);

            } else {

                HamButton.Builder builder = new HamButton.Builder()

                        .normalTextRes(R.string.normal_logout)

                        .subNormalTextRes(R.string.subnormal_logout)

                        .listener(new OnBMClickListener() {

                            @Override
                            public void onBoomButtonClick(int index) {

                                Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);

                                resultIntent.putExtra("DATABASE", database);

                                setResult(Activity.RESULT_OK, resultIntent);

                                Toast.makeText(RegistrarActivity.this, "Bye :) ", Toast.LENGTH_SHORT).show();


                                finish();
                            }
                        });
                bmb.addBuilder(builder);
            }

        }


    }

    /**
     * When back is pressed, it asks the user with pop up dialog if he wants to log out.
     */
    @Override
    public void onBackPressed() {
        new LovelyStandardDialog(RegistrarActivity.this)
                .setTopColorRes(R.color.dialogColor)
                .setButtonsColorRes(R.color.colorPrimary)
                .setTitle("Log Out")
                .setMessage("Would you really want to log out?")
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
                        resultIntent.putExtra("DATABASE", database);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                        Toast.makeText(getBaseContext(), "Successfully logged-out!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    /**
     * Updates the table layout whenever the dataset has been changed.
     *
     * @param selectedCourse The course department name and the number.
     */
    public void updateTableLayout(String selectedCourse) {
        boolean found = false;
        tableLayout.removeAllViews();
        for (int i = 0; i < studentArrayList.size(); i++) {

            for (int j = 0; j < studentArrayList.get(i).getCourses().size(); j++) {

                if (studentArrayList.get(i).getCourses().get(j).getCourseName().equalsIgnoreCase(selectedCourse)) {

                    View row = getLayoutInflater().inflate(R.layout.table_row, null);

                    TextView column1 = row.findViewById(R.id.enrollment_student);

                    TextView column2 = row.findViewById(R.id.enrollment_semester);

                    column1.setText(studentArrayList.get(i).getWebID());

                    column2.setText(studentArrayList.get(i).getCourses().get(j).getSemester());

                    tableLayout.addView(row);

                    found = true;

                }

            }

        }
        if (!found)
            Toast.makeText(RegistrarActivity.this, "There is no such course", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(RegistrarActivity.this, "Students registered in " + selectedCourse, Toast.LENGTH_SHORT).show();
    }
}