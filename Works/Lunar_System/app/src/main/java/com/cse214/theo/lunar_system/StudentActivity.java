package com.cse214.theo.lunar_system;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The student activity that enables a student user to enroll or drop courses, and view all the courses he has enrolled.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #6 for CSE 214, fall 2017
 */
public class StudentActivity extends AppCompatActivity {

    /**
     * Recycler view and the adapter that shows the courses enrolled so far.
     */
    private RecyclerView courseRecyclerView;
    private RecyclerAdapterStudent recyclerAdapter;

    /**
     * The student who has logged-in
     */
    private Student currentStudent;

    /**
     * Database passed from the main activity.
     */
    private HashMap<String, Student> database;

    /**
     * All the courses enrolled.
     */
    private List<Course> courses;

    /**
     * These three field variables store the tokenized strings of the course information.
     */
    private String semester = "";
    private String department = "";
    private int courseNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student);

        database = (HashMap<String, Student>) this.getIntent().getSerializableExtra("HASHMAP");

        currentStudent = (Student) this.getIntent().getSerializableExtra("CURRENT_STUDENT");
        courses = currentStudent.getCourses();
        final LovelyTextInputDialog lovelyTextInputDialog = new LovelyTextInputDialog(this, R.style.Theme_AppCompat_Dialog_Alert);


        final BoomMenuButton bmb = findViewById(R.id.bmb);

        bmb.getCustomPiecePlacePositions().add(new PointF(0, 0));

        updateRecyclerView();
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {

            if (i == 0) {

                HamButton.Builder builder = new HamButton.Builder()

                        .normalTextRes(R.string.normal_add_class)

                        .subNormalTextRes(R.string.subnormal_add_class)

                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {


                                lovelyTextInputDialog.setTopColorRes(R.color.dialogColor)
                                        .setTitle("Add a class")
                                        .addTextWatcher(new TextWatcher() {

                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                lovelyTextInputDialog.setMessage("Format: department+number+semester");

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                                Pattern department = Pattern.compile("\\b[A-Za-z]{3}");
                                                Pattern courseName = Pattern.compile("\\b[A-Za-z]{3}\\s*[0-9]{3}");
                                                Pattern whole = Pattern.compile("\\b[A-Za-z]{3}\\s*[0-9]{3}\\s*[sSfF]{1}\\s*[0-9]{4}\\b");
                                                Matcher matcher;
                                                if (editable.length() <= 3) {
                                                    matcher = department.matcher(editable);
                                                    if (!matcher.find())
                                                        lovelyTextInputDialog.setMessage("Please write department: 3 Alphabets example: CSE ");
                                                    else
                                                        lovelyTextInputDialog.setMessage("Please add course number: 3 Digits example: 114");

                                                } else if (editable.length() <= 6) {
                                                    matcher = courseName.matcher(editable);
                                                    if (!matcher.find())
                                                        lovelyTextInputDialog.setMessage("Please add course number: 3 Digits example: 114");
                                                    else
                                                        lovelyTextInputDialog.setMessage("Please add semester: 1 alphabet+4 digits example: f2014");
                                                } else {
                                                    matcher = whole.matcher(editable);
                                                    if (!matcher.find()) {

                                                         lovelyTextInputDialog.setMessage("Please add semester Example: f2014");
                                                    }
                                                    else
                                                        lovelyTextInputDialog.setMessage("Correct Format! Now please add this course!");
                                                }

                                            }
                                        })
                                        .setInputFilter("Invalid input, format: [department]+[number]+[semester]", new LovelyTextInputDialog.TextFilter() {

                                            @Override
                                            public boolean check(String text) {

                                                if (text.matches("\\b[A-Za-z]{3}\\s*[0-9]{3}\\s*[fFsS]{1}\\s*[0-9]{4}\\b")) {
                                                    text=text.toUpperCase();
                                                    text=text.replaceAll("\\s+", "");

                                                    Pattern coursePattern = Pattern.compile("\\b[A-Za-z]{3}");
                                                    Pattern numberPattern = Pattern.compile("(?!\\b[A-Za-z]{3}\\s*)[0-9]{3}\\s*(?=[fFsS]{1}\\s*[0-9]{4}\\b)");
                                                    Pattern semesterPattern = Pattern.compile("(?!\\b[A-Za-z]{3}\\s*[0-9]{3}\\s*)([fFsS]{1}\\s*[0-9]{4}\\b)");
                                                    Matcher matcher = coursePattern.matcher(text);

                                                    if (matcher.find()) {
                                                        department = matcher.group(0);
                                                    }

                                                    matcher = numberPattern.matcher(text);

                                                    if (matcher.find()) {
                                                        courseNum = Integer.valueOf(matcher.group(0));
                                                    }

                                                    matcher = semesterPattern.matcher(text);

                                                    if (matcher.find()) {

                                                        semester = matcher.group(0);
                                                    }
                                                    boolean found = false;
                                                    Inner:
                                                    for (int i = 0; i < courses.size(); i++) {
                                                        if (courses.get(i).getDepartment().equals(department) && courses.get(i).getNumber() == courseNum && courses.get(i).getSemester().equals(semester)) {
                                                            found = true;

                                                            Toast.makeText(getBaseContext(), department + courseNum + " has already been enrolled.", Toast.LENGTH_SHORT).show();
                                                            break Inner;
                                                        }

                                                    }
                                                    if (!found) {

                                                        courses.add(new Course(department, courseNum, semester));

                                                        String season =(semester.equals("F")) ? "Fall": "Spring";

                                                        Toast.makeText(StudentActivity.this, "" + department +" "+ courseNum + " is added in the "+season, Toast.LENGTH_SHORT).show();
                                                        updateRecyclerView();
                                                    }

                                                }
                                                return text.matches("\\b[A-Za-z]{3}\\s*[0-9]{3}\\s*[SsFf]{1}\\s*[0-9]{4}\\b");

                                            }
                                        })

                                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {

                                            @Override
                                            public void onTextInputConfirmed(String text) {


                                            }
                                        }).show();

                            }
                        });


                bmb.addBuilder(builder);

            } else if (i == 1) {

                HamButton.Builder builder = new HamButton.Builder()


                        .normalTextRes(R.string.normal_drop_class)

                        .subNormalTextRes(R.string.subnormal_drop_class)

                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {


                                lovelyTextInputDialog.setTopColorRes(R.color.dialogColor)

                                        .setTitle("Enter a course name")
                                        .addTextWatcher(new TextWatcher() {

                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                lovelyTextInputDialog.setMessage("Format: department+number+semester");

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                                Pattern department = Pattern.compile("\\b[A-Za-z]{3}");
                                                Pattern courseName = Pattern.compile("\\b[A-Za-z]{3}\\s*[0-9]{3}");
                                                Pattern whole = Pattern.compile("\\b[A-Za-z]{3}\\s*[0-9]{3}\\s*[SsFf]{1}\\s*[0-9]{4}\\b");
                                                Matcher matcher;
                                                if (editable.length() <= 3) {
                                                    matcher = department.matcher(editable);
                                                    if (!matcher.find())
                                                        lovelyTextInputDialog.setMessage("Please write department: 3 Alphabets example: CSE ");
                                                    else
                                                        lovelyTextInputDialog.setMessage("Please add course number: 3 Digits example: 114");

                                                } else if (editable.length() <= 6) {
                                                    matcher = courseName.matcher(editable);
                                                    if (!matcher.find())
                                                        lovelyTextInputDialog.setMessage("Please add course number: 3 Digits example: 114");
                                                    else
                                                        lovelyTextInputDialog.setMessage("Please add semester: 1 alphabet+4 digits example: f2014");
                                                } else {
                                                    matcher = whole.matcher(editable);
                                                    if (!matcher.find()){

                                                            lovelyTextInputDialog.setMessage("Please add semester Example: f2014");
                                                    }
                                                    else
                                                        lovelyTextInputDialog.setMessage("Correctly formatted! Please enter.");
                                                }

                                            }
                                        })
                                        .setInputFilter("Invalid input, format: [department]+[number]+[semester]", new LovelyTextInputDialog.TextFilter() {

                                            @Override
                                            public boolean check(String text) {

                                                if (text.matches("\\b[A-Za-z]{3}\\s*[0-9]{3}\\s*[A-Za-z]{1}\\s*[0-9]{4}\\b")) {
                                                    text = text.toUpperCase();
                                                    text = text.replaceAll("\\s+", "");
                                                    Pattern coursePattern = Pattern.compile("\\b[A-Za-z]{3}");
                                                    Pattern numberPattern = Pattern.compile("(?!\\b[A-Za-z]{3}\\s*)[0-9]{3}\\s*(?=[fFsS]{1}\\s*[0-9]{4}\\b)");
                                                    Pattern semesterPattern = Pattern.compile("(?!\\b[A-Za-z]{3}\\s*[0-9]{3}\\s*)([fFsS]{1}\\s*[0-9]{4}\\b)");
                                                    Matcher matcher = coursePattern.matcher(text);

                                                    if (matcher.find()) {
                                                        department = matcher.group(0);
                                                    }

                                                    matcher = numberPattern.matcher(text);

                                                    if (matcher.find())
                                                        courseNum = Integer.valueOf(matcher.group(0));


                                                    matcher = semesterPattern.matcher(text);

                                                    if (matcher.find()) {

                                                        semester = matcher.group(0);
                                                    }
                                                    boolean found = false;
                                                    Inner:
                                                    for (int i = 0; i < courses.size(); i++) {
                                                        if (courses.get(i).getDepartment().equals(department) && courses.get(i).getNumber() == courseNum && courses.get(i).getSemester().equals(semester)) {
                                                            currentStudent.getCourses().remove(i);
                                                          String season =(semester.equals("F")) ? "Fall": "Spring";

                                                            found = true;
                                                            updateRecyclerView();
                                                            Toast.makeText(StudentActivity.this, "Dropped " + department +" "+ courseNum +" from "+season, Toast.LENGTH_SHORT).show();
                                                            break Inner;
                                                        }
                                                    }
                                                    if (!found)
                                                        Toast.makeText(StudentActivity.this, "The course has not been enrolled.", Toast.LENGTH_SHORT).show();
                                                }
                                                return text.matches("\\b[A-Za-z]{3}\\s*[0-9]{3}\\s*[SsfF]{1}\\s*[0-9]{4}\\b");

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
            } else if (i == 2) {

                HamButton.Builder builder = new HamButton.Builder()

                        .normalTextRes(R.string.normal_view_class)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Collections.sort(currentStudent.getCourses(), new CourseNameComparator());
                                updateRecyclerView();
                            }
                        })
                        .subNormalTextRes(R.string.subnormal_view_class);



                bmb.addBuilder(builder);

            } else if (i == 3) {

                HamButton.Builder builder = new HamButton.Builder()

                        .normalTextRes(R.string.normal_view_course)

                        .subNormalTextRes(R.string.subnormal_view_course)

                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {

                                Collections.sort(currentStudent.getCourses(), new SemesterComparator());
                                updateRecyclerView();

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
                                database.put(currentStudent.getWebID(),currentStudent);
                                Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
                                resultIntent.putExtra("DATABASE", database);
                                setResult(Activity.RESULT_OK, resultIntent);
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
        new LovelyStandardDialog(this)
                .setTopColorRes(R.color.dialogColor)
                .setButtonsColorRes(R.color.colorPrimary)
                .setTitle("Log-Out")
                .setMessage("Would you really want to log out?")
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.put(currentStudent.getWebID(),currentStudent);
                        Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
                        resultIntent.putExtra("DATABASE", database);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                        Toast.makeText(getBaseContext(), "Bye,"+currentStudent.getWebID(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    /**
     * Updates the recycler view whenever the courses data set has been changed.
     */
    public void updateRecyclerView() {

        courseRecyclerView = this.findViewById(R.id.student_recyclerSheet);

        recyclerAdapter = new RecyclerAdapterStudent(this, currentStudent.getCourses());

        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseRecyclerView.setAdapter(recyclerAdapter);


    }
}
