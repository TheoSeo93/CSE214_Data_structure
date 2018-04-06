package com.cse214.theo.mailroommanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The adapter class for list view to display the package stack.
 * Since list view will be instantiated inside a recycler view adapter, I will use array list in order to make it easy to get a package data according to specified position.
 * The inner class, view holder describes the data of each item in the package stack.
 *
 * @author Theo Seo, SBU ID: 111319497
 *         Homework #3 for CSE 214, fall 2017
 */
public class ListViewAdapter extends ArrayAdapter<ListViewItem> {

    /**
     * Stores color parsed from hexadecimal colors.
     */
    private Stack<Integer> colorStack=new Stack<>();

    /**
     * The data of package stack will be stored in this array list.
     */
    private ArrayList<Package> packageArrayList = new ArrayList<>();

    /**
     * Dummy array list is used only for adding item of list view.
     */
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    /**
     * Instantiates a new list view adapter.
     *
     * @param context
     *      The context where the list view will be attached.
     *
     * @param resource
     *      The layout resource of the list view.
     *
     * @param packageArrayList
     *      Packages originally stored in package stack are stored in this array list.
     *
     */
    public ListViewAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Package> packageArrayList) {

        super(context, resource);

        if (packageArrayList != null)
            this.packageArrayList = packageArrayList;

    }

    /**
     * This view holder class describes the data of packages.
     */
    static class ListViewHolder {

        private TextView day;

        private TextView weight;

        private TextView name;

        private View item;


    }

    /**
     * The number of items from the package stack represented by this Adapter.
     */
    @Override
    public int getCount() {

        return packageArrayList.size();
    }

    /**
     * Get a View that displays the data at the specified position in the package array list.
     *
     * @param position
     *      The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView
     *      The old view to reuse, if possible.
     * @param parent
     *      The parent that this view will eventually be attached to
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(colorStack.isEmpty())
            pickColors();

        final int pos = position;
        final Context context = parent.getContext();
        ListViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recycler_item, parent, false);
            holder = new ListViewHolder();

            holder.day = (TextView) convertView.findViewById(R.id.package_day);
            holder.name = (TextView) convertView.findViewById(R.id.package_name);
            holder.weight = (TextView) convertView.findViewById(R.id.package_weight);
            holder.item = convertView.findViewById(R.id.packageItem);
            convertView.setTag(holder);


        }

        holder = (ListViewHolder) convertView.getTag();
        Package mailPackage = packageArrayList.get(pos);

        holder.weight.setText(String.valueOf(mailPackage.getWeight()));
        holder.name.setText(mailPackage.getRecipient());
        holder.day.setText(String.valueOf(mailPackage.getArrivalDate()));

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(colorStack.pop());
        gd.setCornerRadius(7);
        holder.item.setBackground(gd);

        return convertView;
    }

    /**
     * Push parsed colors into color stack.
     */
    private void pickColors() {

        String[] colors = {
                "#5f839c",
                "#48728e",
                "#316080",
                "#1b4f72",
                "#184766",
                "#153f5b",
                "#12374f",
                "#102f44",
                "#0d2739",
                "#0a1f2d",
                "#081722",
                "#050f16",
                "#02070b",
                "#186a3b",
                "#155f35",
                "#13542f",
                "#104a29",
                "#0e3f23",
                "#0c351d",
                "#092a17",
                "#4d5656",
                "#454d4d",
                "#3d4444",
                "#353c3c",
                "#2e3333",
                "#262b2b",
                "#1e2222",
                "#117a65",
                "#0f6d5a",
                "#0d6150",
                "#0b5546",
                "#0a493c",
                "#083d32",
                "#063028",
                "#117a65",
                "#288774",
        };




      for(String element: colors)
          colorStack.push(Color.parseColor(element));


    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position
     *      The position of the package within the adapter's data set whose row id we want.
     *
     * @return
     *      The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * This method is used to increase to number of list view items for a dummy array list.
     */
    public void addItem() {

        ListViewItem item = new ListViewItem();

        listViewItemList.add(item);


    }
}

