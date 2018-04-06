package com.cse214.theo.sevenflags;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *This Adapter class is for displaying the current riding status of customers according to their tickets.
 * Riding status of gold, silver and regular customers are going to be shown by this adapter class.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class CustomerRecyclerviewAdapter extends RecyclerView.Adapter<CustomerRecyclerviewAdapter.ViewHolder> {

    /**
     * ArrayList of customers passed throught the constructor.
     */
    private ArrayList<Person> customerArrayList = new ArrayList<>();
    /**
     * The customer types to be passed through the constructor.
     */
    private String name;

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
     * This describes an item view and metadata about its place within the recycler view.
     * View holder items are going to be determined according to the customer types.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Text view showing the customer number.
         */
        private TextView num;

        /**
         * Text view showing the ride that the customer is on the line.
         */
        private TextView line1;

        /**
         * Text view showing the ride that the customer is on the line.
         */
        private TextView line2;

        /**
         * Text view showing the ride that the customer is on the line.
         */
        private TextView line3;

        /**
         * Text view showing the ride that the customer status.
         */
        private TextView status;

        /**
         * Constructor of view holder.
         *
         * @param itemView The view which will hold each student's information with text views.
         */
        public ViewHolder(View itemView) {

            super(itemView);

            switch (name) {
                case "regular":
                    num = (TextView) itemView.findViewById(R.id.regular_num);
                    line1 = (TextView) itemView.findViewById(R.id.regular_line);
                    status = (TextView) itemView.findViewById(R.id.regular_status);
                    break;
                case "silver":
                    num = (TextView) itemView.findViewById(R.id.silver_num);
                    line1 = (TextView) itemView.findViewById(R.id.silver_line1);
                    line2 = (TextView) itemView.findViewById(R.id.silver_line2);
                    status = (TextView) itemView.findViewById(R.id.silver_status);
                    break;
                case "gold":
                    num = (TextView) itemView.findViewById(R.id.gold_num);
                    line1 = (TextView) itemView.findViewById(R.id.gold_line1);
                    line2 = (TextView) itemView.findViewById(R.id.gold_line2);
                    line3 = (TextView) itemView.findViewById(R.id.gold_line3);
                    status = (TextView) itemView.findViewById(R.id.gold_status);
                    break;
            }


        }
    }

    /**
     * Constructor of the adapter.
     *
     * @param context
     *      Context of the activity.
     * @param customerArrayList
     *      The customer arraylist passed.
     * @param name
     *      The name of the customer whether he or she is gold, silver or regular.
     */
    public CustomerRecyclerviewAdapter(Context context, ArrayList<Person> customerArrayList, String name) {
        this.customerArrayList=customerArrayList;
        size = customerArrayList.size();
        this.name = name;
        mContext = context;

    }



    @Override
    public CustomerRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = new View(parent.getContext());
        switch (name) {
            case "regular":
                v = inflater.inflate(R.layout.recycler_regular, parent, false);
                break;
            case "silver":
                v = inflater.inflate(R.layout.recycler_silver, parent, false);
                break;
            case "gold":
                v = inflater.inflate(R.layout.recycler_gold, parent, false);
                break;
        }

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    /**
     * Instantiates a hex color array.
     */
    public void generateColors() {

        hexArr = new String[]{

                "#0b5345"

        };

    }


    @Override
    public void onBindViewHolder(CustomerRecyclerviewAdapter.ViewHolder viewHolder, int position) {

        generateColors();

        TextView num = new TextView(mContext);

        TextView line1 = new TextView(mContext);
        TextView line2 = new TextView(mContext);
        ;
        TextView line3 = new TextView(mContext);
        TextView status = new TextView(mContext);
        switch (name) {
            case "regular":
                num = viewHolder.num;
                line1 = viewHolder.line1;
                status = viewHolder.status;
                break;
            case "silver":
                num = viewHolder.num;
                line1 = viewHolder.line1;
                line2 = viewHolder.line2;
                status=viewHolder.status;
                break;
            case "gold":
                num = viewHolder.num;
                line1 = viewHolder.line1;
                line2 = viewHolder.line2;
                line3 = viewHolder.line3;
                status=viewHolder.status;
                break;
        }



            switch (name) {
                case "regular":
                    if (customerArrayList.get(position) != null) {
                        num.setText(String.valueOf(customerArrayList.get(position).getNumber()));
                        status.setText(customerArrayList.get(position).getStatus().toString());

                        if (!customerArrayList.get(position).getLines().isEmpty())
                            line1.setText(String.valueOf(customerArrayList.get(position).getLines().get(0).getName()));

                    }


                    break;
                case "silver":
                    if (customerArrayList.get(position) != null) {
                        num.setText(String.valueOf(customerArrayList.get(position).getNumber()));
                        status.setText(customerArrayList.get(position).getStatus().toString());
                        if (customerArrayList.get(position).getLines().get(0) != null)
                            line1.setText(String.valueOf(customerArrayList.get(position).getLines().get(0).getName()));
                        if (customerArrayList.get(position).getLines().get(1) != null)
                            line2.setText(String.valueOf(customerArrayList.get(position).getLines().get(1).getName()));
                    }


                    break;
                case "gold":
                    if (customerArrayList.get(position) != null) {
                        num.setText(String.valueOf(customerArrayList.get(position).getNumber()));
                        status.setText(customerArrayList.get(position).getStatus().toString());
                        if (customerArrayList.get(position).getLines().get(0) != null)
                            line1.setText(String.valueOf(customerArrayList.get(position).getLines().get(0).getName()));
                        if (customerArrayList.get(position).getLines().get(1) != null)
                            line2.setText(String.valueOf(customerArrayList.get(position).getLines().get(1).getName()));
                        if (customerArrayList.get(position).getLines().get(2) != null)
                            line3.setText(String.valueOf(customerArrayList.get(position).getLines().get(2).getName()));
                    }


                    break;
            }



    }

    /**
     * Returns the total number of the customer arraylist.
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
