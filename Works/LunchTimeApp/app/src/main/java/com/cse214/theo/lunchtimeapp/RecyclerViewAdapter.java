package com.cse214.theo.lunchtimeapp;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

    /**
     * Custom Recycler view adapter class.
     * This is utilized in order to show the students' name and money in the current line.
     * For each item in the recycler view, there is a text view with round corner and different colors.
     *
     * @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */

public class RecyclerViewAdapter extends RecyclerView.Adapter < RecyclerViewAdapter.ViewHolder > {
    /**
     * An array of students to be shown in the recycler view
     */
    private Student[] students;
    /**
     * Array that contains some colors in hex that i manually picked
     */
    private String[] hexArr;
    /**
     * The context of recycler view
     */
    private Context mContext;

    /**
     * View holder class in a recycler view adapter.
     * This describes an item view and metadata about its place within the recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Text view of student name
         */
        private TextView name;

        /**
         * Text view of student position;
         */
        private TextView number;

        /**
         * Text view of student money
         */
        private TextView money;

        /**
         * View that contains text views of name, position and money.
         */
        private View coloredContainer;

        /**
         * Constructor of view holder.
         *
         * @param itemView
         *      The view which will hold each student's information with text views.
         */
        public ViewHolder(View itemView) {

            super(itemView);

            coloredContainer = itemView.findViewById(R.id.recycler_item_container);

            name = (TextView) itemView.findViewById(R.id.name);

            number = (TextView) itemView.findViewById(R.id.index);

            money = (TextView) itemView.findViewById(R.id.money);


        }
    }


    /**
     * Constructor for view holder.
     * This describes an item view and metadata about its place within the recycler view
     */
    public RecyclerViewAdapter(Context context, Student[] students) {
        this.students = students;

        mContext = context;

    }
    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent
     *      The ViewGroup into which the new View will be added after it is bound to an adapter position.
     *
     * @param viewType
     *       The view type of the new View.
     */
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.recycler_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }
    /**
     * Instantiate a hex color array with the colors I picked
     */
    public void generateColors() {

        hexArr = new String[] {
                "#424949",
                "#1b4f72",
                "#512e5f",
                "#154360",
                "#7e5109",
                "#4a235a",
                "#641e16",
                "#0b5345",
                "#239b56",
                "#ec7063",
                "#0e6251",
                "#6e2c00",
                "#616a6b",
                "#a04000",
                "#4d5656",
                "#7b7d7d",
                "#af601a",
                "#76448a",
                "#784212",
                "#922b21",
                "#b03a2e",
                "#b9770e",
                "#b3b6b7",
                "#cd6155",
        };

    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * In this case, the data will be students' position, name and money
     *
     * @param viewHolder
     *      The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     *
     * @param position
     *      The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position) {

        generateColors();

        final TextView nameTextView = viewHolder.name;

        final TextView moneyTextView = viewHolder.money;

        final TextView indexTextView = viewHolder.number;

        final View coloredView = viewHolder.coloredContainer;

        GradientDrawable gradientDrawable = new GradientDrawable();

        gradientDrawable.setColor(Color.parseColor(hexArr[position]));

        gradientDrawable.setCornerRadius(10);
        //To apply round corner to the colored view, Gradient drawable was used.


        if (students[position] != null) {

            nameTextView.setText(students[position].getName());

            moneyTextView.setText("" + students[position].getMoney() + " $");

            coloredView.setBackground(gradientDrawable);
        }

        indexTextView.setText("" + (position + 1));

    }

    /**
     * Returns the view type of the item at position for the purposes of view recycling.
     *
     * @param position
     *      The position of the item within the adapter's data set.
     */

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    /**
     *  Returns the total number of items in the data set held by the adapter.
     *
     *  @return
     *      returns the size of the students array
     */
    @Override
    public int getItemCount() {
        return students.length;
    }
}