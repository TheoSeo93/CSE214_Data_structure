package com.cse214.theo.lunchtimeapp;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
    /**
     * The class for compare fragment
     *
     * @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */

public class FragmentCompare extends android.support.v4.app.Fragment  {


    /**
     * Inflated view from the xml resource of duplicate fragment.
     */
    private View rootView;


    /**
     * Student line container that will be passed from duplicate fragment
     */
    private StudentLineContainer studentLineContainer;


    /**
     * Array adapter for spinner.
     */
    private ArrayAdapter < String > spinnerArrayAdapter;


    /**
     * The options to be displayed on the spinner
     */
    private String[] spinnerArray = {
            "Select Reality",
            "A",
            "B"
    };

    /**
     * The first reality already existing when the student container is created.
     */
    private StudentLine realityA;

    /**
     * The second reality already existing when the student container is created.
     */
    private StudentLine realityB;

    /**
     * Receives and sets the current student line changed either from home fragment or duplicate fragment.
     * The currently selected student line is shared and updated among fragments dynamically whenever it's changed.
     *
     * @param studentLineContainer Currently selected student line passed either from home fragment or duplicate fragment
     */
    public void setStudentLineContainer(StudentLineContainer studentLineContainer) {

        this.studentLineContainer = studentLineContainer;

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * Two spinners will be attached on the activity, and these will give user the options to compare realities.
     * If a reality indicated by either of them chosen has not generated yet, a message will be toasted and go back to the method again.
     *
     * @param inflater
     *      The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container
     *      If non-null, this is the parent view that the fragment's UI should be attached to.
     *      The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setStudentLineContainerFromDuplicate(studentLineContainer);

        rootView = inflater.inflate(R.layout.fragment_compare, container, false);

        final Spinner spinner1 = (Spinner) rootView.findViewById(R.id.compare_spinner1);

        final Spinner spinner2 = (Spinner) rootView.findViewById(R.id.compare_spinner2);

        final Button confirm = (Button) rootView.findViewById(R.id.compare_confirm);

        spinnerArrayAdapter = new ArrayAdapter < String > (rootView.getContext(), (R.layout.spinner_item), spinnerArray);

        spinner1.setAdapter(spinnerArrayAdapter);

        spinner2.setAdapter(spinnerArrayAdapter);

        spinnerArrayAdapter = new ArrayAdapter < String > (rootView.getContext(), android.R.layout.simple_spinner_item, spinnerArray);


        AdapterView.OnItemSelectedListener onItemSelectedListener_spinner1 = new AdapterView.OnItemSelectedListener() {

            /**
             *  Sets the first reality to compare with the other one.
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

                if (position == 0) {

                    realityA = null;

                    return;
                }
                try {

                    realityA = studentLineContainer.getStudentLine(position - 1);

                } catch (ArrayIndexOutOfBoundsException ex) {

                    Toast.makeText(rootView.getContext(), "That reality has not been created yet.", Toast.LENGTH_LONG).show();

                    spinner1.setSelection(0);
                }

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

        };
        AdapterView.OnItemSelectedListener onItemSelectedListener_spinner2 = new AdapterView.OnItemSelectedListener() {




            /**
             *  Sets the second reality to compare with the other one.
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

                if (position == 0) {
                    realityB = null;
                    return;
                }
                try {

                    realityB = studentLineContainer.getStudentLine(position - 1);

                } catch (ArrayIndexOutOfBoundsException ex) {
                    Toast.makeText(rootView.getContext(), "That reality has not been created yet.", Toast.LENGTH_LONG).show();
                    spinner2.setSelection(0);
                }

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

        };
        spinner1.setOnItemSelectedListener(onItemSelectedListener_spinner1);

        spinner2.setOnItemSelectedListener(onItemSelectedListener_spinner2);

        confirm.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a button has been clicked.
             * If any one of the two reality has not been selected, this comes back to the method and toasts a message.
             * If they are the same realities or different realities, this toasts messages accordingly.
             *
             * @param view
             *      The view that was clicked.
             */
            @Override
            public void onClick(View view) {

                if (realityA == null || realityB == null) {
                    Toast.makeText(rootView.getContext(), "Please select options", Toast.LENGTH_LONG).show();
                    return;
                }
                if (realityA.equals(realityB))

                    Toast.makeText(rootView.getContext(), "They are the same realities", Toast.LENGTH_LONG).show();

                else
                    Toast.makeText(rootView.getContext(), "They are different realities", Toast.LENGTH_LONG).show();


            }
        });

        return rootView;

    }


}
