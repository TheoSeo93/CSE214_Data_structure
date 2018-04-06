package com.cse214.theo.sevenflags;

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
 * Adapter class to show the lines for each ride.
 * In order to visualize the queue data structure, the items of the recycler view will be in horizontal.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.ViewHolder> {

    /**
     * Each ride passed from the constructor to be displayed.
     */
    private Ride ride;

    /**
     * String array of hexadecimal colors
     */
    private String[] hexArr = new String[]{

            "#FFD700",
            "#C0C0C0",
            "#000000"
    };

    /**
     * The status name, on ride, holding and available to determine which queue to be displayed.
     */
    private String name;

    /**
     * Context given from scrolling activity.
     */
    private Context mContext;

    /**
     * The holding queue will be converted into array list data structure to facilitate on binding process.
     */
    private ArrayList<Person> holdingQueue=new ArrayList<>();

    /**
     * The virtual line queue will also be converted into array list.
     */
    private ArrayList<Person> virtualLine= new ArrayList<>();


    /**
     * View holder class in a recycler view adapter.
     * This describes an item view and metadata about its place within the recycler view
     *
     * @param context
     *      The context of the scrolling activity.
     *
     * @param ride
     *      In order to determine whose line to be displayed, the kind of the ride will be specified through the constructor.
     *
     * @param name
     *      To determine which line to be displayed among on ride line, holding queue and virtual queue, this will be used as flag.
     *
     */
    public HorizontalRecyclerAdapter(Context context, Ride ride, String name) {

        this.name = name;

        this.ride = ride;

        this.virtualLine.addAll(ride.getVirtualLine());

        this.holdingQueue.addAll(ride.getHoldingQueue());

        mContext = context;

    }

    /**
     * View holder class in a recycler view adapter.
     * This describes an item view and metadata about its place within the recycler view.
     * View holder items are going to be determined according to the customer types.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {


        /**
         * Text view of each customer's number
         */
        private TextView number;

        /**
         * Constructor of view holder.
         *
         * @param itemView The view which will hold each student's information with text views.
         */
        public ViewHolder(View itemView) {

            super(itemView);

            number = (TextView) itemView.findViewById(R.id.number);


        }
    }

    /**
     * Constructor for view holder.
     * This describes each customer's data.
     */
    @Override
    public HorizontalRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.row, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    /**
     * Returns color after determining which customer it is by checking its max lines.
     * Gold customers will be shown as gold color, silver customers will be shown as silver, and regular to be shown as black color.
     *
     * @param person
     *      Person object to be displayed as colored text view
     * @return
     *      Color of the customers.
     */
    public int determineColor(Person person ){
        if(person.getMaxLines()==1){
            return Color.parseColor("#000000");
        }
        if(person.getMaxLines()==2){
            return Color.parseColor("#C0C0C0");
        } else{
            return Color.parseColor( "#FFD700");
        }

    }



    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(HorizontalRecyclerAdapter.ViewHolder viewHolder, int position) {

        final TextView numTextView = viewHolder.number;


        GradientDrawable gradientDrawable = new GradientDrawable();


        if (name.equals("onRide")) {
            if(ride.getPeopleOnRide().get(position)!=null) {
                numTextView.setText(String.valueOf(ride.getPeopleOnRide().get(position).getNumber()));
                gradientDrawable.setColor(determineColor(ride.getPeopleOnRide().get(position)));
                gradientDrawable.setCornerRadius(30);

                numTextView.setBackground(gradientDrawable);
            }
        } else if (name.equals("holdingQueue")) {
            if(ride.getHoldingQueue().get(position)!=null) {

                numTextView.setText(String.valueOf(holdingQueue.get(position).getNumber()));
                gradientDrawable.setColor(determineColor(holdingQueue.get(position)));
                gradientDrawable.setCornerRadius(30);
                numTextView.setBackground(gradientDrawable);
            }
        } else {
            if(ride.getVirtualLine().get(position)!=null) {
                numTextView.setText((String.valueOf(virtualLine.get(position).getNumber())));
                gradientDrawable.setColor((determineColor(virtualLine.get(position))));
                gradientDrawable.setCornerRadius(30);

                numTextView.setBackground(gradientDrawable);
            }
        }



    }


    /**
     * Returns the total each line.
     *
     * @return
     *      returns the size of each line.
     */
    @Override
    public int getItemCount() {


        if(name.equals("onRide"))
           return ride.getPeopleOnRide().size();

        else if (name.equals("holdingQueue"))
                return ride.getHoldingQueue().size();

       else
            return ride.getVirtualLine().size();

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
