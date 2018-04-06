package com.cse214.theo.lunar_system;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * The recycler adapter class for showing the current state of a student's course enrollment.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #6 for CSE 214, fall 2017
 */
public class RecyclerAdapterStudent extends RecyclerView.Adapter<RecyclerAdapterStudent.ViewHolder> {

    /**
     * ArrayList of the courses that this student has enrolled
     */
    List<Course> courses;

    /**
     * String array of hexadecimal colors
     */
    private String[] hexArr;

    /**
     * The size of the courses ArrayList
     */
    private int size;

    /**
     * Context given from student activity.
     */
    private Context mContext;

    /**
     * View holder class in a recycler view adapter.
     * This describes an item view and metadata about its place within the recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {


        /**
         * Text view of department name
         */
        private TextView department;

        /**
         * Text view of course number
         */
        private TextView courseNum;

        /**
         * Text view of semester name.
         */
        private TextView semester;


        /**
         * View that contains the above text views.
         * This view will be colored.
         */
        private View coloredContainer;

        /**
         * Constructor of view holder.
         *
         * @param itemView The view which will hold each student's information with text views.
         */
        public ViewHolder(View itemView) {

            super(itemView);

            coloredContainer = itemView.findViewById(R.id.student_container);

            department = itemView.findViewById(R.id.student_dept);

            courseNum=itemView.findViewById(R.id.student_course);

            semester = itemView.findViewById(R.id.student_semester);


        }
    }


    public RecyclerAdapterStudent(Context context, List<Course> courses) {

        size = courses.size();
        this.courses=courses;
        mContext = context;

    }

    @Override
    public RecyclerAdapterStudent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.recycler_item_student, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    /**
     * Instantiates a hex color array.
     */
    public void generateColors() {

        hexArr = new String[]{

                "#0b5345",
                "#cd6155",
        };

    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * In this case, the data will be students' position, name and money
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerAdapterStudent.ViewHolder viewHolder, int position) {

        generateColors();

        final TextView departmentText = viewHolder.department;

        final TextView courseNum = viewHolder.courseNum;

        final TextView semesterText = viewHolder.semester;

        final View coloredView = viewHolder.coloredContainer;

        GradientDrawable gradientDrawable = new GradientDrawable();

        gradientDrawable.setColor(Color.parseColor(hexArr[0]));

        gradientDrawable.setCornerRadius(10);



        coloredView.setBackground(gradientDrawable);
        departmentText.setText(courses.get(position).getDepartment().toUpperCase());

        courseNum.setText(String.valueOf(courses.get(position).getNumber()));

        semesterText.setText(courses.get(position).getSemester().toUpperCase());




    }

    /**
     * Returns the total number of courses that this student has enrolled.
     *
     * @return returns the size of the number of the cars in the list.
     */
    @Override
    public int getItemCount() {
        return size;
    }


    /**
     * Returns the view type of the item at position for the purposes of view recycling.
     *
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
