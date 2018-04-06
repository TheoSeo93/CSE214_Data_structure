package com.cse214.theo.mailroommanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Recycler adapter class for displaying packages from the stacks.
 *
 * @author Theo Seo, SBU ID: 111319497
 *         Homework #3 for CSE 214, fall 2017
 */
public class MailRoomRecyclerAdapter extends RecyclerView.Adapter<MailRoomRecyclerAdapter.ViewHolder> {

    /**
     * Stores mail room data from the constructor.
     */
    private MailRoom mailRoom;

    /**
     * The context field.
     */
    private Context mContext;


    /**
     * This view holder class holds the indices of packages and a list view for each package stack.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {


        /**
         * The alphabetic index of the package stack to be displayed.
         */
        public TextView nameIndex;

        /**
         * The stack number to be displayed.
         */
        public TextView number;

        /**
         * The list of packages for each stack.
         */
        public ListView listView;


        /**
         * This view holder class describes the data of each package stack.
         */
        public ViewHolder(View itemView) {


            super(itemView);


            nameIndex = (TextView) itemView.findViewById(R.id.list_index);

            number = (TextView) itemView.findViewById(R.id.list_number);


            listView = (ListView) itemView.findViewById(R.id.package_list);


        }
    }


    /**
     * Creates a new recycler adapter for a mail room.
     * Whole packages will be passed by mail room object.
     *
     * @param context  The context of the activity.
     * @param mailroom Mail room object that has data set of all package stacks.
     */
    public MailRoomRecyclerAdapter(Context context, MailRoom mailroom) {
        this.mailRoom = mailroom;
        mContext = context;

    }


    /**
     * Returns the context.
     */
    private Context getContext() {
        return mContext;
    }


    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     */
    public MailRoomRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View weekView = inflater.inflate(R.layout.list_item_todo_group, parent, false);

        ViewHolder viewHolder = new ViewHolder(weekView);

        return viewHolder;
    }

    /**
     * List view is created inside the recycler view.
     * Each row of the current recycler view shows the current status of each package stack.
     * Every package inside the package stack will be visualized by text views inside a list view for each row.
     *
     * @param viewHolder Represents the contents of the item at the given position in the data set.
     * @param position   The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MailRoomRecyclerAdapter.ViewHolder viewHolder, int position) {

        ArrayList<Package> packageArrayList = new ArrayList<>();

        int listViewSize = 0;

        if (mailRoom.getPackage(position) != null) {

            listViewSize = mailRoom.getPackage(position).size();

            for (int i = 0; i < listViewSize; i++)
                try {
                    packageArrayList.add(mailRoom.getPackage(position).pop());
                } catch (EmptyStackException e) {
                    e.printStackTrace();
                }


            for (int i = 0; i < listViewSize; i++)
                try {
                    mailRoom.getPackage(position).push(packageArrayList.get(packageArrayList.size() - 1 - i));
                } catch (FullStackException e) {
                    e.printStackTrace();
                }

        }


        TextView textViewTop = viewHolder.nameIndex;

        TextView textViewBottom = viewHolder.number;

        //List view inside the recycler view.
        ListView listView = viewHolder.listView;



        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), R.layout.list_item_todo_group, packageArrayList);



        int listViewHeight = 0;

        //In order to set the height of the list view according to the changing number of items, it needs to be resized every time.
        for (int i = 0; i < listViewSize; i++) {

            listViewAdapter.addItem();
            View listItem = listViewAdapter.getView(i, null, listView);
            //Measures each item's height and width, in this case, I will only use the height.
            listItem.measure(0, 0);

            //The height of the list view of each package is sum of the each package.
            listViewHeight += listItem.getMeasuredHeight();

        }


        listView.setAdapter(listViewAdapter);

        //Layout params tells the parent how it wants to be laid out, so if I set the layout params and request layout to the list iew, the size of the list view will be changed.
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = listViewHeight + (listView.getDividerHeight() * (listViewSize + 1));

        listView.setLayoutParams(params);

        listView.requestLayout();

        switch (position) {
            case 0:
                textViewTop.setText("(A-G)");
                textViewBottom.setText("Stack 1");
                break;

            case 1:

                textViewTop.setText("(H-J)");
                textViewBottom.setText("Stack 2");
                break;

            case 2:

                textViewTop.setText("(K-M)");
                textViewBottom.setText("Stack 3");
                break;

            case 3:

                textViewTop.setText("(N-R)");
                textViewBottom.setText("Stack 4");

                break;

            case 4:

                textViewTop.setText("(S-Z)");
                textViewBottom.setText("Stack 5");
                break;

            case 5:

                textViewTop.setText("Floor");
                textViewBottom.setText("Stack 0");
        }


    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return
     *      The total number of package stacks in a mail room.
     */
    @Override
    public int getItemCount() {
        return mailRoom.size();
    }

    /**
     * Return the view type of the item at position for the purposes of view recycling.
     *
     * @return
     *       Position to query.
     */
    public int getItemViewType(int position) {
        return position;
    }


}


