package com.cse214.theo.oilchangemanager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * The activity class that takes student's position to remove him or her from the line
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #2 for CSE 214, fall 2017
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter < RecyclerViewAdapter.ViewHolder > {

    private CarList carList;
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
         * Text view of car owner name
         */
        private TextView name;

        /**
         * Text view of position;
         */
        private TextView number;

        /**
         * Text view of make
         */
        private TextView make;

        /**
         * View that contains text views of car owner name, position and name of makes.
         */
        private View coloredContainer;

        /**
         * Constructor of view holder.
         *
         * @param itemView The view which will hold each student's information with text views.
         */
        public ViewHolder(View itemView) {

            super(itemView);

            coloredContainer = itemView.findViewById(R.id.recycler_item_container);

            name = (TextView) itemView.findViewById(R.id.owner);

            number = (TextView) itemView.findViewById(R.id.index);

            make = (TextView) itemView.findViewById(R.id.make);


        }
    }


    /**
     * Constructor for view holder.
     * This describes an item view and metadata about its place within the recycler view
     */
    public RecyclerViewAdapter(Context context, CarList carList) {

        this.carList = carList;

        mContext = context;

    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
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
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position) {

        generateColors();

        final TextView nameTextView = viewHolder.name;

        final TextView makeTextView = viewHolder.make;

        final TextView indexTextView = viewHolder.number;

        final View coloredView = viewHolder.coloredContainer;

        GradientDrawable gradientDrawable = new GradientDrawable();


            //If the data, car, that cursor indicates is equal to the item to be bind to the view holder, the change the color so that the item that cursor is pointing at is highlighted.
            if (carList.getCursorCar().equals(carList.getCar(position)))

                gradientDrawable.setColor(Color.parseColor(hexArr[1]));

            else
                gradientDrawable.setColor(Color.parseColor(hexArr[0]));

                gradientDrawable.setCornerRadius(10);

                        nameTextView.setText(carList.getCar(position).getOwner());

                        makeTextView.setText(carList.getCar(position).getMake().toString());


                coloredView.setBackground(gradientDrawable);


                indexTextView.setText("" + (position + 1));
            }

/**
 *  Returns the total number of items in the data set held by the adapter.
 *
 *  @return
 *      returns the size of the number of the cars in the list.
 */
        @Override
        public int getItemCount () {
            return carList.numCars();
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

}
