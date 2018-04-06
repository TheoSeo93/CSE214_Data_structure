package com.cse214.theo.mailroommanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter class for recycler view which is for listing a specific recipient's packages.
 * This adapter contains a recipient's name, package arrival date and weight.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #3 for CSE 214, fall 2017
 */
public class RecipientRecyclerAdapter extends RecyclerView.Adapter<RecipientRecyclerAdapter.ViewHolder> {

    /**
     * Array list of package converted from package stack.
     */
    private ArrayList<Package> packageArrayList= new ArrayList<>();

    /**
     * String array of hexadecimal colors
     */
    private String[] hexArr;

    /**
     * This is the size of the package stack passed from scrolling activity.
     */
    private int size;

    /**
     * Context given from scrolling activity.
     */
    private Context mContext;

    /**
     * View holder class in a recycler view adapter.
     * This describes an item view and metadata about its place within the recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Text view of recipient name
         */
        private TextView name;

        /**
         * Text view of package arrival date.
         */
        private TextView day;

        /**
         * Text view of package weight.
         */
        private TextView weight;

        /**
         * View that contains text views of package arrival date and package weight.
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

            coloredContainer = itemView.findViewById(R.id.package_container);

            name = (TextView) itemView.findViewById(R.id.package_recipient);

            day = (TextView) itemView.findViewById(R.id.package_date);

            weight = (TextView) itemView.findViewById(R.id.package_weight);


        }
    }


    /**
     * Constructor for view holder.
     * This describes each package's data of a specific recipient.
     *
     * @throws FullStackException
     *      Although there is a try and catch syntax here, this exception will not be thrown because I specified the case when not throwing this exception inside the push method.
     *
     * @throws EmptyStackException
     *      If there is no package to be removed, it will be thrown.
     *
     */
    public RecipientRecyclerAdapter(Context context, PackageStack packageStack) throws FullStackException, EmptyStackException {

         size= packageStack.size();

        for(int i=0;i<size;i++)
                packageArrayList.add(packageStack.pop());

        for(int i=0;i<size;i++)
            packageStack.push(packageArrayList.get(size-1-i));



        mContext = context;

    }

    @Override
    public RecipientRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.recycler_package_list, parent, false);

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
     * @param viewHolder
     *      The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position
     *      The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecipientRecyclerAdapter.ViewHolder viewHolder, int position) {

        generateColors();

        final TextView nameTextView = viewHolder.name;

        final TextView dayTextView = viewHolder.day;

        final TextView weightTextView = viewHolder.weight;

        final View coloredView = viewHolder.coloredContainer;

        GradientDrawable gradientDrawable = new GradientDrawable();

        gradientDrawable.setColor(Color.parseColor(hexArr[0]));

        gradientDrawable.setCornerRadius(10);


        //Since there is no need to show a recipient name on every row, it will be shown only once.
            if(position==0)

            nameTextView.setText(packageArrayList.get(position).getRecipient());

            weightTextView.setText(String.valueOf(packageArrayList.get(position).getWeight()));

            dayTextView.setText(String.valueOf(packageArrayList.get(position).getArrivalDate()));

            coloredView.setBackground(gradientDrawable);


    }

    /**
     * Returns the total number of items in the package stack of a recipient.
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
