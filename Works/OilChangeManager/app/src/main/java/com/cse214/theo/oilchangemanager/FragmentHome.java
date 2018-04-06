package com.cse214.theo.oilchangemanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;
/**
 * The class for home fragment.
 * Home fragment has radio buttons that performs list operations such as add, insert, remove, cut, sort and paste.
 * Next to the radio group, there is a cursor controller to move forward, backward, and move directly to the head and tail.
 * On beneath the options listed above, there is a recycler view that displays this data structure dynamically.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #2 for CSE 214, fall 2017
 */
public class FragmentHome extends android.support.v4.app.Fragment {
    /**
     * Inflated view from the xml resource of home fragment.
     */
    private View rootView;



    /**
     * Recycler view that shows the selected car list.
     */
    private RecyclerView recyclerView;

    /**
     * Whenever a car list node is cut, that will go to this variable.
     */
    private Car cutCar;


    /**
     * Current list to be shown on the recycler view
     */
    private CarList currentList = new CarList();

    /**
     * Provides a binding from current car list to the recycler view
     */
    private RecyclerViewAdapter lineRecyclerViewAdapter;

    /**
     * Contains multiple car lists in its array.
     */
    private CarListContainer carListContainer = new CarListContainer();

    /**
     * Array adapter for spinner.
     */
    private ArrayAdapter < String > spinnerArrayAdapter;

    /**
     * The options to be displayed on the spinner
     */
    private String[] spinnerArray = {
            "Select Car List",
            "Joe",
            "Donny",
            "Finished"
    };


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater
     *      The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container
     *        If non-null, this is the parent view that the fragment's UI should be attached to.
     *         The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     *
     * @param savedInstanceState
     *         If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        // Passes the initial car list line container to other fragm
        //  ents
        ((MainActivity) getActivity()).setCarListContainerFromHome(carListContainer);

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        final RadioButton ADD_END = (RadioButton) rootView.findViewById(R.id.add_to_end);

        final RadioButton INSERT = (RadioButton) rootView.findViewById(R.id.insert_before_cursor);

        final RadioButton CUT = (RadioButton) rootView.findViewById(R.id.cut_car);

        final RadioButton PASTE_BEFORE = (RadioButton) rootView.findViewById(R.id.paste_before);

        final RadioButton REMOVE = (RadioButton) rootView.findViewById(R.id.remove_car);

        final RadioButton SORT = (RadioButton) rootView.findViewById(R.id.sort);

        final Spinner SPINNER = (Spinner) rootView.findViewById(R.id.home_spinner);

        final Button radioButtonConfirm = (Button) rootView.findViewById(R.id.select);

        final Button exitButton = (Button) rootView.findViewById(R.id.button_exit);

        final Button forward = (Button) rootView.findViewById(R.id.cursor_forward);

        final Button backWard = (Button) rootView.findViewById(R.id.cursor_back);

        final Button toHead = (Button) rootView.findViewById(R.id.cursor_head);

        final Button toTail = (Button) rootView.findViewById(R.id.cursor_tail);

        final TextView listControl = (TextView) rootView.findViewById(R.id.list_operations);

        spinnerArrayAdapter = new ArrayAdapter(rootView.getContext(), R.layout.spinner_item, spinnerArray);

        GradientDrawable gradientDrawable = new GradientDrawable();

        gradientDrawable.setColor(Color.parseColor("#205953"));

        gradientDrawable.setCornerRadius(6);

        listControl.setBackground(gradientDrawable);

        listControl.setTextColor(Color.parseColor("#acacac"));

        //Cursor moves forward when pressed.
        forward.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                try {
                    currentList.cursorForward();

                } catch (EndOfListException e) {
                    Toast.makeText(getContext(), "This is the end of the cursor. Can't move forward.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                updateRecyclerView();
                if (currentList.getCursorCar() != null)
                    Toast.makeText(getContext(), "Cursor is pointing at : " + currentList.getCursorCar().getOwner(), Toast.LENGTH_LONG).show();

            }
        });

        //Cursor moves backward when pressed.
        backWard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                try {
                    currentList.cursorBackward();
                } catch (EndOfListException e) {
                    Toast.makeText(getContext(), "This is the end of the cursor. Can't move backward.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                updateRecyclerView();
                if (currentList.getCursorCar() != null)
                    Toast.makeText(getContext(), "Cursor is pointing at : " + currentList.getCursorCar().getOwner(), Toast.LENGTH_LONG).show();


            }
        });

        //Cursor set to the head when pressed.
        toHead.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                currentList.resetCursorToHead();
                updateRecyclerView();
            }
        });

        //Cursor set to the tail when pressed.
        toTail.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                currentList.setCursor(currentList.getTail());


                updateRecyclerView();
            }
        });

        //Program finishes when pressed.
        exitButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                getActivity().finish();

                System.exit(0);
                //Exits the program on click

            }
        });

        SPINNER.setAdapter(spinnerArrayAdapter);

        SPINNER.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             *  Sets a reality to be displayed on the recycler view.
             *  Toasts a message if the reality that clicked item indicates does not exist.
             *  Callback method to be invoked when an item in this view has been selected.
             *  This callback is invoked only when the newly selected position is different from the previously selected position or if there was no selected item.
             *
             * @param parentView
             *      The AdapterView where the selection happened
             * @param selectedItemView
             *      If non-null, this is the parent view that the fragment's UI should be attached to.
             *      The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
             * @param position
             *      The position of the view in the adapter
             * @param id
             *      The row id of the item that is selected
             */
            @Override
            public void onItemSelected(AdapterView < ? > parentView, View selectedItemView, int position, long id) {

                final int spinnerPosition = position;


                if (position != 0) {
                    currentList = carListContainer.getCarList(position - 1);

                    // The student line to be shown on the recycler view.
                    // Since the spinner has one more item than the capacity of reality to be generated, position - 1 is passed as an argument.

                    updateRecyclerView();

                }

                radioButtonConfirm.setOnClickListener(new View.OnClickListener() {
                    // Sets the on click listener to the button that executes functions given from radio buttons

                    @Override
                    public void onClick(View v) {

                        if (spinnerPosition == 0) {

                            //Toasts a message if the check button is clicked and no item from the spinner is checked.
                            Toast.makeText(rootView.getContext(), "Please select the list", Toast.LENGTH_LONG).show();

                            return;

                        }

                        if (ADD_END.isChecked()) {

                            Intent intent = new Intent(getContext(), DialogAdd.class);

                            startActivityForResult(intent, 1);

                        } else if (INSERT.isChecked()) {

                            Intent intent = new Intent(getContext(), DialogInsert.class);

                            startActivityForResult(intent, 2);

                        } else if (REMOVE.isChecked()) {

                            try {

                                currentList.removeCursor();

                            } catch (EndOfListException ex) {

                                Toast.makeText(getContext(), "EndOfListException: There is nothing to be removed.", Toast.LENGTH_LONG).show();

                                return;
                            }


                            updateRecyclerView();

                        } else if (PASTE_BEFORE.isChecked()) {
                            if (cutCar == null) {
                                Toast.makeText(getContext(), "Please choose an item to be cut first, then paste it.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (currentList.getCursorCar() == null) {
                                Toast.makeText(getContext(), "There is no cursor.", Toast.LENGTH_LONG).show();
                                return;
                            }

                            try {
                                currentList.insertBeforeCursor(cutCar);

                            } catch (IllegalArgumentException ex) {
                                Toast.makeText(getContext(), cutCar.getMake().toString() + " Cannot insert null data.", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(getContext(), currentList.getCursorCar().getOwner(),
                                    Toast.LENGTH_LONG).show();
                            updateRecyclerView();

                            cutCar = null;
                            return;


                        } else if (CUT.isChecked()) {
                            if (currentList.getCursorCar() == null) {
                                Toast.makeText(getContext(), "Cursor doesn't exist.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (cutCar != null) {
                                Toast.makeText(getContext(), "There is an item already cut.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            try {

                                cutCar = currentList.removeCursor();

                            } catch (EndOfListException e) {
                                if (e instanceof EndOfListException)
                                    Toast.makeText(getContext(), "EndOfListException: There is no student to be removed.", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getContext(), "Clone not supported", Toast.LENGTH_LONG).show();
                                return;
                            }

                            Toast.makeText(getContext(), cutCar.getOwner() + ".", Toast.LENGTH_LONG).show();
                            ((MainActivity) getActivity()).setCutCar(cutCar);

                            updateRecyclerView();

                        } else if (SORT.isChecked()) {

                            CarListNode sorted = currentList.sort(currentList.getHead());

                            CarList result = new CarList();
                            for (int i = 0; i < currentList.numCars(); i++) {
                                result.appendToTail(sorted.getData());
                                sorted = sorted.getNext();
                            }
                            currentList = result;
                            updateRecyclerView();
                        } else {
                            Toast.makeText(rootView.getContext(), "Please check the radio button", Toast.LENGTH_LONG).show();
                            //If no radio button is checked, but clicked the button, it notifies the user.
                        }
                    }
                });
            }

            /**
             *
             * Callback method to be invoked when the selection disappears from this view.
             * The selection can disappear for instance when touch is activated or when the adapter becomes empty.nitialized as an empty set.
             *
             * @param parentView
             *     The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView < ? > parentView) {

            }

        });

        return rootView;
    }

    /**
     * Receives and sets the current car list changed from other fragments.
     * The currently selected carList is shared and updated among fragments dynamically whenever it's changed.
     */
    public void setCarListContainer(CarListContainer carListContainer) {
        this.carListContainer = carListContainer;
    }


    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with, the resultCode it returned, and any additional data from it.
     * The result activities that add, cut, remove, serve, update money and trade places with students are started according to their request codes, number 1 through 6
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                String name = data.getExtras().getString("ADDED_NAME");

                Car.Make make = (Car.Make) data.getExtras().getSerializable("ADDED_MAKE");

                Car newCar = new Car(make, name);

                try {

                    currentList.appendToTail(newCar);

                } catch (IllegalArgumentException ex) {
                    Toast.makeText(this.getContext(), " Cannot add a car which is null",
                            Toast.LENGTH_LONG).show();

                    return;
                }
                updateRecyclerView();
                Toast.makeText(this.getContext(),
                        name + "'s car has been added to the list.",
                        Toast.LENGTH_LONG).show();

            } else if (requestCode == 2) {

                String name = data.getExtras().getString("INSERTED_NAME");

                Car.Make make = (Car.Make) data.getExtras().getSerializable("INSERTED_MAKE");

                Car newCar = new Car(make, name);

                try {

                    currentList.insertBeforeCursor(newCar);
                    Toast.makeText(this.getContext(), currentList.getCursorCar().getOwner(),
                            Toast.LENGTH_LONG).show();

                } catch (IllegalArgumentException ex) {
                    Toast.makeText(this.getContext(), " Cannot add a car which is null",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                updateRecyclerView();
                Toast.makeText(this.getContext(),
                        name + " has been inserted before the cursor.",
                        Toast.LENGTH_LONG).show();

            }
        }

        ((MainActivity) getActivity()).setCarListContainerFromHome(carListContainer);



    }

    /**
     * Updates the recycler view whenever the current list has been changed.
     */
    public void updateRecyclerView() {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.RecylcerSheet);


        lineRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), currentList);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerView.setAdapter(lineRecyclerViewAdapter);


        lineRecyclerViewAdapter.notifyDataSetChanged();
    }
}